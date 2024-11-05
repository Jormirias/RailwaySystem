/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes;

import dataStructures.OrderedVector;
import dataTypes.interfaces.*;

/**
 * Class which implements a Train Station
 */
public class StationClass  implements Station {
    private final String name;

    /**
     * Collections of trains and times for the station to have as reference
     * STRUCT_CHOICE: We chose to have these be OrderedVector (SHOULD BE A BST IN FASE 2?), ordered, since the MH command will need to iterate over them and that enables binary search.
     *
     */
    private OrderedVector<Integer, Time> stopsByTrain;
    private OrderedVector<Time, Integer> stopsByTime;

    public StationClass(String name) {
        this.name = name;
        this.stopsByTrain = new OrderedVector<Integer, Time>(100); //Passar aqui para 10000??
        this.stopsByTime = new OrderedVector<Time, Integer>(100);//Passar aqui para 10000??
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void addStop(int train, Time time) {
        stopsByTrain.insert(train, time);
        stopsByTime.insert(time, train);
    }

    @Override
    public void removeStop(int train, Time time) {
        stopsByTrain.remove(train);
        stopsByTime.remove(time);
    }

    @Override
    public String toString() {
        return name;
    }
    
}
