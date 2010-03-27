#!/usr/bin/python
################################################################################
# Default port: 21567                                                          #
# Default host: localhost                                                      #
#                                                                              #
# This daemon serves as the means of communicaton between client programs      #
# that want to control PatherBot and the physical COM port.                    #
#                                                                              #
# The protocol for communicating with this daemon is as follows:               #
# On connection to port daemon sends to client:                                #
#    "BUSY"  if daemon is already at maximum capcity of clients                #
#    "OK"    if daemon accepts the client                                      #
#                                                                              #
#                                                                              #
# Authors: Sandro Badame                                                       #
################################################################################
import getopt
import logging.handlers

import os
import serial
import socket
import sys
import threading
from serial.serialutil import SerialException
from threading import Thread
import diagnostic

#Open the com port
_serialport = ""
if os.name == 'posix': #Linux/Mac
    _serialport = "/dev/ttyUSB0" #@IndentOk
elif os.name == 'nt': #Windows
    _serialport = "COM3" #@IndentOk

#Internet address for clients attaching to this daemon
_hostname = "localhost" 
_port = 21567

#Open a webserver that will display the contents of the log file
_dport = 8000

def printHelp():
    print('''
usage: python pather-daemon.py COMMANDS
   --help      See this message
-p --port      The port to open for this daemon to bind to and for clients to attach to [default is %d]
-h --hostname  The host name for this daemon to bind to and for clients to attach to [default is %s]
-x             The daemon will not send commands through a serial port, but will instead print commands to standard out
-m --mute      The daemon will not make any sounds when a command is received [default is for sound to be made]
-s --serial    The serial device that this daemon will connect to [default is %s, Note: The default is OS dependent]
-d --dport     The daemon will start a webserver that will output the contents of the log file
''' % (_port, _hostname, _serialport))

#Handle logging
LOG_FILENAME = 'log/pather-daemon.log'
log = logging.getLogger('pather-daemon')
log.setLevel(logging.DEBUG)

#Log file
filehandler = logging.handlers.RotatingFileHandler(LOG_FILENAME)
filehandler.setFormatter(logging.Formatter("%(asctime)s - %(name)s - %(levelname)s - %(message)s"))
log.addHandler(filehandler)

#Standard out log
consolehandler = logging.StreamHandler(sys.stdout)
consolehandler.setFormatter(logging.Formatter("%(message)s"))
log.addHandler(consolehandler)

#Output to serial port or is this a test run?
_USESERIAL = True

#Parse arguments passed to this script
try:
    opts, args = getopt.getopt(sys.argv[1:], "mxh:p:s:", ["help", "port=", "hostname=", "serial=", "mute"])
except:
    printHelp()
    sys.exit(2)

for opt, args in opts:
    if opt == "--help":
        printHelp()
        sys.exit()
    elif opt in ("-p", "--port"):
        _port = int(args)
    elif opt in ("-h", "--hostname"):
        _hostname = args
        log.info("-h option detected, hostname=%s" % _hostname)
    elif opt == "-x":
        _USESERIAL = False
        log.info("-x option detected, print output to STDOUT instead of the serial port")
    elif opt in ("-m", "--mute"):
        _USESOUND = False
        log.info("-m option detected, no sound will be played when commands are received.")
    elif opt in ("-s", "--serial"):
            _serialport = args
    log.info("-s option detected, using port=%s" % _serialport);
    

#Grab the parallel port and clear it
patherport = None
if _USESERIAL:
    try:
        patherport = serial.Serial(_serialport);
    except SerialException:
        patherport = None
        _USESERIAL = False
        log.info("ERROR: port %s could not be opened, defaulting to -X mode" % _serialport)
        
def handleCommand(command):
    global log
    log.info("Command Sent: %s" % command)
    if patherport != None:
        patherport.write(command)

#Emergency stop command for the pather
def STOP_BOT():
    handleCommand("PW,0,0")
    handleCommand("PW,1,0")

#This is done both to reset the pins and confirm that everything is working.
STOP_BOT()

class ClientRead(threading.Thread):
    def __init__(self, socket, addr):
        Thread.__init__(self)
        self.socket = socket
        self.addr = addr
    
    def run(self):
        data = ""
        while True:
            chunk = str(self.socket.recv(4096))
            if chunk == "":
                #Uh oh this isn't supposed to happen!
                STOP_BOT()
                log.info("Client has disconnected from daemon")
                break
            data = data + chunk
            while "!" in data: #Messages must be deliminated by a "!"
                i = data.find("!")
                command = data[0:i]
                if command == "CLOSE":
                    self.socket.shutdown(socket.SHUT_RDWR)
                    self.socket.close()
                    break
                else:
                    handleCommand(self, data[0:i])
                data = data[i + 1:]
                
            log.info("Data Received from %s: %s" % (self.addr, data))

    def notifyCommandSent(self, command):
        self.socket.sendAll("Command: %s" % command)
        
# Class that is responsible to read info from the UBW32
class SerialReader(threading.Thread):
    
    def __init__(self, serial):
        self.serial = serial
    
    def run(self):
        for response in self.serial:
            log.info("Received response: %s" % response)

if _USESERIAL: #Start a serial port reader
    serial = SerialReader();

#Grab onto the socket
server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
try:
    server.bind((_hostname, _port))
except:
    log.error("Could not bind to port:%d" % _port)
    print("Could not bind to port:%d" % _port)
    sys.exit(2)

server.listen(0)
log.info("Listening to port %d for clients." % _port)

#Start the log diagnostic thread
diagnostic.startServer()

#begin loop
while True:
    (clientsocket, address) = server.accept()
    log.info("Connection made from %s" % address)
    ClientRead(clientsocket, address).start()
    
