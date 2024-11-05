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
     * Collections of trains and times for the station to have as reference
     * STRUCT_CHOICE: We chose to have these be OrderedVector (SHOULD BE A BST IN FASE 2?), ordered, since the MH command will need to iterate over them and that enables binary search.
     *
     */
    private OrderedVector<Integer, Time> stopsByTrain;
    private OrderedVector<Time, Integer> stopsByTime;

    public Station(String name) {
        this.name = name;
        this.stopsByTrain = new OrderedVector<Integer, Time>(100); //Passar aqui para 10000??
        this.stopsByTime = new OrderedVector<Time, Integer>(100);//Passar aqui para 10000??
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
        if(!this.name.toUpperCase().equals(otherStation.getName().toUpperCase())) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return name;
    }
    
}
