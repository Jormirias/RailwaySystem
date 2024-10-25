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
     * ALl the stops in this schedule.
     */
    private final ListInArray<EntryClass<Station,Time>> stops;

    public Schedule(int train, ListInArray<EntryClass<Station,Time>> newSchedule) {
        trainNumber = train;
        stops = newSchedule;
    }

    /**
     * Only to be used while building the schedule.
     */
    public void addStop(Station station, Time time) {
        ScheduleStop stop = new ScheduleStop(station, time);
        //stops.addLast(stop);
    }

    public Iterator<EntryClass<Station, Time>> getStops() {
        return this.stops.iterator();
    }

    public Station getOriginStation() {
        return stops.getFirst().getKey();
    }
}
