/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes;


import java.io.Serializable;
import dataStructures.OrderedVector;

/**
 * Class which implements a Train Station
 */
public class Station  implements Serializable {
    private final String name;

    /**
     * All the stops at this station.
     */
    private OrderedVector<Integer, Time> stopsByTrain;
    private OrderedVector<Time, Integer> stopsByTime;

    public Station(String name) {
        this.name = name;
        this.stopsByTrain = new OrderedVector<Integer, Time>(100);
        this.stopsByTime = new OrderedVector<Time, Integer>(100);
    }

    public String getName() {
        return name;
    }

    public void addStop(int train, Time time) {
        stopsByTrain.insert(train, time);
        stopsByTime.insert(time, train);
    }

    public void removeStop(int train, Time time) {
        stopsByTrain.remove(train);
        stopsByTime.remove(time);
    }

    @Override
    public boolean equals(Object other) {
        if(!other.getClass().equals(this.getClass())) {
            return false;
        }

        Station otherStation = (Station) other;
        if(!this.name.equals(otherStation.getName())) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return name;
    }
    
}
