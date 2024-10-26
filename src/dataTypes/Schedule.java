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
     * All the stops in this schedule.
     */
    private final ListInArray<ScheduleStop> stops;

    public Schedule(int train, ListInArray<ScheduleStop> newSchedule) {
        trainNumber = train;
        stops = newSchedule;
    }

    public Iterator<ScheduleStop> getStops() {
        return this.stops.iterator();
    }

    public Station getOriginStation() {
        return stops.getFirst().getStation();
    }
}
