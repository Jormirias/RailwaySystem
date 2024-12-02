package dataTypes;

import dataStructures.OrderedDictionary;
import dataTypes.interfaces.Time;
import dataTypes.interfaces.*;
import dataStructures.*;

public class TrainTimeClass implements TrainTime {

    /**
     * Serial Version UID of the Class
     */
    static final long serialVersionUID = 0L;

    private OrderedDoubleList<Integer, Time> trainTimes;

    public TrainTimeClass(int train, Time time) {
        trainTimes = new OrderedDoubleList<>();
        addTrain(train, time);
    }

    public TwoWayIterator<Entry<Integer, Time>> getTrains() {
        return trainTimes.iterator();
    }

    public void addTrain(int train, Time time) {
        this.trainTimes.insert(train, time);
    }

    public void removeTrain(int train) {
        this.trainTimes.remove(train);
    }
}