package dataTypes;

public class ScheduleStop {
    Station station;
    Time time;

    public ScheduleStop(Station station, Time time) {
        this.station = station;
        this.time = time;
    }

    public Station getStation() {
        return this.station;
    }

    public Time getTime() { 
        return this.time;
    }

    @Override
    public String toString() {
        return station + " " + time;
    }
}
