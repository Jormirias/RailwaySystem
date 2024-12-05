/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes;

import dataStructures.OrderedDictionary;
import dataTypes.interfaces.Time;
import dataTypes.interfaces.*;
import dataStructures.*;

import java.io.Serial;

public class TrainTimeClass implements TrainTime {


    @Serial
    private static final long serialVersionUID = 3264377324290972491L;

    /**
     * Time at which the train performs the stop at the station.
     * The number of the train.
     */
    private Time time;
    private Integer train;

    /**
     * Constructor
     * @param time - Time of the stop.
     * @param train - number of the train.
     */
    public TrainTimeClass(Time time, int train) {
        this.time = time;
        this.train = train;
    }

    @Override
    public Time getTime() {
        return time;
    }

    @Override
    public Integer getTrain() {
        return train;
    }

    @Override
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