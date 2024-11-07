package dataTypes;
import dataStructures.*;

public class Stop  {
    private Station station;
    private Time time;

    public Stop(Station station, Time time) {
        this.station = station;
        this.time = time;
    }

    public Station getStation() {
        return station;
    }


    public Time getTime() {
        return time;
    }

    @Override
    public String toString() {
        return station + " " + time;
    }

    public void setKey(Station station) {
        this.station = station;
    }

    public void setValue(Time time) {
        this.time = time;
    }
}