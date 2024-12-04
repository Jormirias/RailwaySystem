package dataTypes;

import dataStructures.*;
import dataTypes.interfaces.*;

public class TrainClass implements Train {

    /**
     * Serial Version UID of the Class
     */
    static final long serialVersionUID = 0L;
    private final int number;
    private final Dictionary<String, Boolean> stops; // for best schedule
    private final Time departureTime; // for schedule insertion

    public TrainClass(int number, ListInArray<Station> stations, Time departureTime) {
        this.number = number;
        this.departureTime = departureTime;

        this.stops = new SepChainHashTable<>();
        Iterator<Station> it = stations.iterator();
        while(it.hasNext()) {
            stops.insert(it.next().getName(), false);
        }
    }

    public int getNumber() {
        return number;
    }

    public boolean departsBefore(Time time) {
        return departureTime.compareTo(time) < 0;
    }

    public boolean departsAfter(Time time) {
        return departureTime.compareTo(time) > 0;
    }

    public void setAsStop(Station station) {
        stops.insert(station.getName(), true);
    }

    public boolean stopsAt(Station station) {
        return stops.find(station.getName());
    }
}
