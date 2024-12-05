package dataTypes;
import dataTypes.exceptions.*;
import dataTypes.interfaces.*;

import java.io.Serial;

public class StopClass implements Stop {

    @Serial
    private static final long serialVersionUID = 8511184505874733381L;
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