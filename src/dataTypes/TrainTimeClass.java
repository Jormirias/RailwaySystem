package dataTypes;

import dataTypes.interfaces.Time;
import dataTypes.interfaces.*;

public class TrainTimeClass implements TrainTime {

    /**
     * Serial Version UID of the Class
     */
    static final long serialVersionUID = 0L;
    private int train;
    private Time time;

    public TrainTimeClass(int train, Time time) {
        this.train = train;
        this.time = time;
    }

    public int getTrain() {
        return train;
    }

    @Override
    public Time getTime() {
        return time;
    }

    @Override
    public String toString() {
        return train + " " + time;
    }

    public void setTrain(int train) {
        this.train = train;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}