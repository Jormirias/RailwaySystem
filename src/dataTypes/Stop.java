package dataTypes;
import dataStructures.*;

public class Stop<Station, Time> implements Entry<Station, Time> {
    private Station station;
    private Time time;

    public Stop(Station station, Time time) {
        this.station = station;
        this.time = time;
    }

    @Override
    public Station getKey() {
        return station;
    }

    @Override
    public Time getValue() {
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