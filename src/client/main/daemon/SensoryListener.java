package main.daemon;

public interface  SensoryListener {
    void odometerUpdate(int tick);
    void headingUpdate(float heading);
}
