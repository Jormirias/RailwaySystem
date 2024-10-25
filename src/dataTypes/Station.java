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
    private OrderedVector<Time, Integer> stopsByTime;
    private OrderedVector<Integer, Time> stopsByTrain;

    public Station(String name) {
        this.name = name;
        this.stopsByTime = new OrderedVector<Time, Integer>(100);
        this.stopsByTrain = new OrderedVector<Integer, Time>(100);
    }

    public String getName() {
        return name;
    }

    public void addStop(int train, Time time) {
        stopsByTime.insert(time, train);
        stopsByTrain.insert(train, time);
    }
    
}
