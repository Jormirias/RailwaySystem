/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes;

import dataStructures.*;

/**
 * Class which implements a Train Schedule
 */
public class Schedule  {
    int trainNumber;
    /**
     * Stop Collection
     * STRUCT_CHOICE: We chose to have these in a ListInArray, ordered by station, since they're a static entity and shouldn't be changed.
     * Some consultation operations will need to iterate over it, but for those purposes it's indifferent for it to be a LinkedList or a ListInArray. Array takes precedence for spatial complexity considerations.
     *
     */
    private final ListInArray<Stop> stops;

    /**
     * Constructor
     * @param train Defines the trainer number, unique
     * @param newSchedule ListInArray of stops
     *
     */
    public Schedule(int train, ListInArray<Stop> newSchedule) {
        trainNumber = train;
        stops = newSchedule;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public Iterator<Stop> getStops() {
        return this.stops.iterator();
    }

    public Station getOriginStation() {
        return stops.getFirst().getStation();
    }
}
