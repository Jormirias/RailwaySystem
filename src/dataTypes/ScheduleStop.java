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
}
