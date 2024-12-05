/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes;

import dataStructures.*;
import dataTypes.interfaces.*;

import java.io.Serial;

/**
 * Class which implements a Train Schedule
 */
public class ScheduleClass implements Schedule {

    @Serial
    private static final long serialVersionUID = 1338431840628436262L;
    int trainNumber;
    /**
     * All of the Stops that the train running this Schedule performs.
     */
    private final ListInArray<Stop> stops;

    /**
     * Constructor
     * @param train Defines the trainer number, unique
     * @param newSchedule ListInArray of stops
     *
     */
    public ScheduleClass(int train, ListInArray<Stop> newSchedule) {
        trainNumber = train;
        stops = newSchedule;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public TwoWayIterator<Stop> getStops() {
        return this.stops.iterator();
    }

    public Station getOriginStation() {
        return stops.getFirst().getStation();
    }

    public Time getDepartureTime() {
        return stops.getFirst().getTime();
    }
}
