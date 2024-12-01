package dataTypes;

import dataStructures.*;
import dataTypes.interfaces.*;

public class TrainClass {
    private final int number;
    private final Dictionary<String, Boolean> stops; // for best schedule
    private final Time departureTime; // for schedule insertion

    public TrainClass(int number, Time departureTime) {
        this.number = number;
        this.stops = new SepChainHashTable<>();
        this.departureTime = departureTime;
    }

    public boolean departsBefore(Time time) {
        return departureTime.compareTo(time) < 0;
    }

    public boolean departsAfter(Time time) {
        return departureTime.compareTo(time) > 0;
    }
}
