/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes;


import java.io.Serializable;

import dataStructures.Entry;
import dataStructures.TwoWayIterator;
import dataStructures.OrderedDoubleList;
import dataTypes.exceptions.*;
import dataTypes.interfaces.*;

/**
 * Class which implements a Train Station
 */
public class StationClass implements Station {

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
    private OrderedDoubleList<Time, Integer> stopsNormal;
    private OrderedDoubleList<Time, Integer> stopsReverse;

    public StationClass(String name) {
        this.name = name;
        this.stopsNormal = new OrderedDoubleList<>();
        this.stopsReverse = new OrderedDoubleList<>();
    }

    public String getName() {
        return name;
    }

    @Override
    public void addStop(Time time, int train, boolean isInverted) {
        if(isInverted) {
            stopsReverse.insert(time, train);
        } else {
            stopsNormal.insert(time, train);
        }
    }

    @Override
    public void removeStop(Time time, boolean isInverted) {
        if(isInverted) {
            stopsReverse.remove(time);
        } else {
            stopsNormal.remove(time);
        }
    }

    @Override
    public boolean equals(Object other) {
        if(!other.getClass().equals(this.getClass())) {
            return false;
        }

        StationClass otherStation = (StationClass) other;
        return this.name.equalsIgnoreCase(otherStation.getName());
    }

    public boolean testName(String other) {
        return this.name.equalsIgnoreCase(other.trim());
    }

    @Override
    public String toString() {
        return name;
    }


    public TwoWayIterator<Entry<Time, Integer>> stopsIterator(boolean isInverted) {
        if(isInverted) {
            return stopsReverse.iterator();
        } else {
            return stopsNormal.iterator();
        }
    }
    
}
