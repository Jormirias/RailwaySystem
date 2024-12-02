/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes;
import dataStructures.*;
import dataTypes.interfaces.*;

/**
 * Class which implements a Train Station
 */
public class StationClass implements Station {

    /**
     * Serial Version UID of the Class
     */
    static final long serialVersionUID = 0L;
    private final String StationName;

    private OrderedDictionary<Time, Train> stopsNormal;
    private OrderedDictionary<Time, Train> stopsReverse;

    public StationClass(String name) {
        this.StationName = name;
        this.stopsNormal = new AVLTree<>();
        this.stopsReverse = new AVLTree<>();
    }

    public String getName() {
        return StationName;
    }

    public boolean hasStops(boolean isInverted) {
        if(isInverted) {
            return !stopsReverse.isEmpty();
        } else {
            return !stopsNormal.isEmpty();
        }
    }

    public void addStop(Time time, Train train, boolean isInverted) {
        if(isInverted) {
            stopsReverse.insert(time, train);
        } else {
            stopsNormal.insert(time, train);
        }
    }

    public void removeStop(Time time, boolean isInverted) {
        if(isInverted) {
            stopsReverse.remove(time);
        } else {
            stopsNormal.remove(time);
        }
    }

    public Iterator<Entry<Time, Train>> getStops(boolean isInverted) {
        if(isInverted) {
            return stopsReverse.iterator();
        } else {
            return stopsNormal.iterator();
        }
    }

    public boolean isStopValid(String lineName, Time departureTime, Time arrivalTime, boolean isInverted) {
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
            if(nextTrain != previousTrain && nextTrain.departsBefore(departureTime)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void addStop(String lineName, Time time, Train train, boolean isInverted) {
        addStop(time, train, isInverted);
    }

    @Override
    public void removeStop(String lineName, Time time, boolean isInverted) {
        removeStop(time, isInverted);
    }

    @Override
    public boolean equals(Object other) {
        if(!other.getClass().equals(this.getClass())) {
            return false;
        }

        StationClass otherStation = (StationClass) other;
        return this.StationName.equalsIgnoreCase(otherStation.getName());
    }

    public boolean testName(String other) {
        return this.StationName.equalsIgnoreCase(other.trim());
    }

    @Override
    public String toString() {
        return StationName;
    }

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
