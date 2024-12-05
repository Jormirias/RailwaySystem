package dataTypes;

import dataStructures.OrderedDictionary;
import dataTypes.interfaces.Time;
import dataTypes.interfaces.*;
import dataStructures.*;

import java.io.Serial;

public class TrainTimeClass implements TrainTime {


    @Serial
    private static final long serialVersionUID = 3264377324290972491L;
    private Time time;
    private Integer train;

    public TrainTimeClass(Time time, int train) {
        this.time = time;
        this.train = train;
    }

    public Time getTime() {
        return time;
    }
    public void setTime(Time time) {
        this.time = time;
    }
    public Integer getTrain() {
        return train;
    }
    public void setTrain(Integer train) {
        this.train = train;
    }


    public int compareTo(TrainTime other) {
        if(this.time.compareTo(other.getTime()) != 0) {
            return this.time.compareTo(other.getTime());

        } else {
            if(this.train < other.getTrain()) {
                return -1;
            }
            else if(this.train > other.getTrain()) {
                return 1;
            }
        }
        return 0;
    }
}