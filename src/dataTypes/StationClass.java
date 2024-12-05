/**
 * @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
 * @author Jorge Dias (72360) jmr.dias@campus.fct.unl.pt
 */

package dataTypes;
import dataStructures.*;
import dataTypes.interfaces.*;

import java.io.Serial;

/**
 * Class which implements a Train Station
 */
public class StationClass implements Station {

    @Serial
    private static final long serialVersionUID = 2425715342222161264L;
    
    /**
     * The name and the stops of this Station.
     */
    private final String StationName;

    /**
     * The stops are ordered by the Time at which they are performed.
     * Normal and reverse are relative to the line this station is associated to.
     */
    private OrderedDictionary<Time, Train> stopsNormal;
    private OrderedDictionary<Time, Train> stopsReverse;

    /**
     * Constructor.
     * @param name - the name of the Station.
     */
    public StationClass(String name) {
        this.StationName = name;
        this.stopsNormal = new AVLTree<>();
        this.stopsReverse = new AVLTree<>();
    }

    @Override
    public String getName() {
        return StationName;
    }

    @Override
    public boolean hasStops(boolean isInverted) {
        if(isInverted) {
            return !stopsReverse.isEmpty();
        } else {
            return !stopsNormal.isEmpty();
        }
    }

    @Override
    public void addStop(Time time, Train train, boolean isInverted) {
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
    public Iterator<Entry<Time, Train>> getStops(boolean isInverted) {
        if(isInverted) {
            return stopsReverse.iterator();
        } else {
            return stopsNormal.iterator();
        }
    }

    @Override
    public boolean isStopValid(Time departureTime, Time arrivalTime, boolean isInverted) {
        if(hasStops(isInverted)) {
            Iterator<Entry<Time, Train>> it = getStops(isInverted);
            
            Train previousTrain = null;
            Train nextTrain = null;
            while(it.hasNext())    {
                Entry<Time, Train> next = it.next();
                nextTrain = next.getValue();
                Time nextStopTime = next.getKey();
                int timeComparsion = arrivalTime.compareTo(nextStopTime);
                if(timeComparsion == 0) { // two trains going the same direction can't be stopped at the same station at the same time.
                    return false;
                }
                if(timeComparsion < 0) { // first stop which will come after the current one.
                    break;
                }

                previousTrain = nextTrain;
            }

            if(previousTrain != null && previousTrain.departsAfter(departureTime)) {
                return false;
            }
            return nextTrain == previousTrain || !nextTrain.departsBefore(departureTime);
        }

        return true;
    }

    @Override
    public boolean equals(Object other) {
        if(!other.getClass().equals(this.getClass())) {
            return false;
        }

        StationClass otherStation = (StationClass) other;
        return this.StationName.equalsIgnoreCase(otherStation.getName());
    }

    @Override
    public boolean testName(String other) {
        return this.StationName.equalsIgnoreCase(other.trim());
    }

    @Override
    public String toString() {
        return StationName;
    }

    @Override
    public Stack<Train> findBestScheduleTrains(Time time, boolean isInverted) {
        Stack<Train> trainsInOrder = new StackInList<>();
        if(hasStops(isInverted)) {
            Iterator<Entry<Time, Train>> stopsIt = getStops(isInverted);
            while(stopsIt.hasNext()) {
                Entry<Time, Train> stop = stopsIt.next();
                Time stopTime = stop.getKey();
                Train stopTrain = stop.getValue();
                if(time.compareTo(stopTime) < 0) {
                    break;
                }

                trainsInOrder.push(stopTrain);
            }
        }

        return trainsInOrder;
    }
    
}
