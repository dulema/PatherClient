'''
Created on Mar 17, 2010
This does the job of creating a simple webserver that lets you easily see what the pather is doing. 
@author: sandro
'''

import BaseHTTPServer
from BaseHTTPServer import BaseHTTPRequestHandler

top = '''
<html>
    <head>
        <title>Pather-daemon diagnostics</title>
        <!-- This is how we refresh the page ala Drudge report-->
        <script language="JavaScript">
            setTimeout("window.location.href=unescape(window.location.pathname)",1*1000);
        </script>
    </head>
    <body>
        <noscript><h1>Warning: Your browser doesn't allow JavaScript, so this log won't be refreshed automatically</h1></noscript>
'''

bottom = '''
    </body>
</html>
'''

def getContent():
    logfile = open("log/pather-daemon.log", "r")
    middle = "<ul>"
    for line in logfile:
        middle = middle + "<li>%s</li>" % line
    middle = middle + "</ul>"
    return top + middle + bottom



class LogViewerHTTPHandler(BaseHTTPRequestHandler):
    
    def do_GET(self):
        try:
            self.send_response(200)
            self.send_header('Content-type', 'text/html')
            self.end_headers()
            self.wfile.write(getContent())
            return
        except IOError:
            self.send_error(404, 'File Not Found: %s' % self.path) 

def startServer():
    HandlerClass = LogViewerHTTPHandler
    ServerClass = BaseHTTPServer.HTTPServer
    Protocol = "HTTP/1.0"
    
    server_address = ('127.0.0.1', 8000)
    HandlerClass.protocol_version = Protocol
    httpd = ServerClass(server_address, HandlerClass)
    
    sa = httpd.socket.getsockname()
    print("Serving HTTP on %s port %s..." % (sa[0], sa[1]))
    httpd.serve_forever()
    
if __name__ == '__main__':
    startServer()
