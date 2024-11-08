/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes;


import java.io.Serializable;

import dataStructures.Entry;
import dataStructures.TwoWayIterator;
import dataStructures.OrderedDoubleList;

/**
 * Class which implements a Train Station
 */
public class Station implements Serializable {

    /**
     * Serial Version UID of the Class
     */
    static final long serialVersionUID = 0L;
    private final String name;

    /**
     * Collections of trains and times for the station to have as reference
     * STRUCT_CHOICE: We chose to have these be OrderedDoubleList (SHOULD BE A BST IN FASE 2?), since the MH command will need to iterate over the structure.
     *
     */
    private final OrderedDoubleList<Time, Integer> stops;

    public Station(String name) {
        this.name = name;
        this.stops = new OrderedDoubleList<>(); //Passar aqui para 10000??
    }

    public String getName() {
        return name;
    }

    public void addStop(Time time, int train) {
        stops.insert(time, train);
    }

    public void removeStop(Time time) {
        stops.remove(time);
    }

    @Override
    public boolean equals(Object other) {
        if(!other.getClass().equals(this.getClass())) {
            return false;
        }

        Station otherStation = (Station) other;
        return this.name.equalsIgnoreCase(otherStation.getName());
    }

    public boolean testName(String other) {
        return this.name.equalsIgnoreCase(other.trim());
    }

    @Override
    public String toString() {
        return name;
    }


    public TwoWayIterator<Entry<Time, Integer>> stopsIterator() {
        return stops.iterator();
    }
    
}
