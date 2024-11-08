package dataTypes;
import dataTypes.exceptions.*;
import dataTypes.interfaces.*;

public class StopClass implements Stop {

    /**
     * Serial Version UID of the Class
     */
    static final long serialVersionUID = 0L;
    private Station station;
    private Time time;

    public StopClass(Station station, Time time) {
        this.station = station;
        this.time = time;
    }

    public Station getStation() {
        return station;
    }

    @Override
    public Time getTime() {
        return time;
    }

    @Override
    public String toString() {
        return station + " " + time;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}