/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes;

import dataStructures.*;

import java.io.Serializable;

/**
 * Class which implements a Rail Line
 */
public class Line implements Serializable {

    /**
     * String Line name
     * Unique identifier for a Line
     *
     */
    private final String name;

    /**
     * Station Collection
     * STRUCT_CHOICE: We chose to have this be a ListInArray for spatial concerns; it's meant to be an immutable collection, so an array will suffice for its purposes.
     * Some consultation operations will need to iterate over it, but for those purposes it's indifferent for it to be a LinkedList or a ListInArray
     *
     */
    private final ListInArray<Station> stations;

    /**
     * Schedule Collections
     * STRUCT_CHOICE: We chose to have these be OrderedDoubleLists, ordered by departure time, since the MH command will need to iterate over them and it's convenient to have them ordered.
     * 2 different collections since the schedule may contain the Stations by order or by reversed order
     *
     */
    // Ordered by departure time.
    private final OrderedDoubleList<Time, Schedule> schedulesNormal;
    private final OrderedDoubleList<Time, Schedule> schedulesInverted;


    /**
     * Line Constructor
     * Receives the lineName and the Station collection, allocates them, and initializes two Schedule Collections, one normal and the other for the inverted line direction
     *
     */
    public Line(String lineName, ListInArray<Station> newStations) {
        name = lineName;
        stations = newStations;

        schedulesNormal = new OrderedDoubleList<>();
        schedulesInverted = new OrderedDoubleList<>();
    }

    /**
     * Returns the name of the Line
     * @return the String holding the name of this Line.
     *
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the Station collection of the Line
     * @return the Collection of Stations in this Line
     *
     */
    public ListInArray<Station> getStations() {
        return stations;
    }

    /* Currently only supports one way */
    public void insertSchedule(String trainNumber, ListInArray<ScheduleStop> stationAndTimes) throws dataStructures.IllegalArgumentException {
        int train = Integer.parseInt(trainNumber);
        ScheduleStop firstStop = stationAndTimes.getFirst();

        if(!firstStop.getStation().equals(stations.getFirst()) && !firstStop.getStation().equals(stations.getLast())) {
            throw new dataStructures.IllegalArgumentException();
        }
        
        if(!scheduleCheck(train, stationAndTimes)) {
            throw new dataStructures.IllegalArgumentException();
        }

        Schedule schedule = new Schedule(train, stationAndTimes);
        if(firstStop.getStation().equals(stations.getFirst())) {
            schedulesNormal.insert(firstStop.getTime(), schedule);
        }
        else if(firstStop.getStation().equals(stations.getLast())) {
            schedulesInverted.insert(firstStop.getTime(), schedule);
        }
    }

    public void removeSchedule(String departureStationName, String timeAsString) {
        Schedule schedule = null;
        Time time = new Time(timeAsString);

        if(departureStationName.equals(stations.getFirst().getName())) {
            schedule = schedulesNormal.remove(time);
        }
        else if(departureStationName.equals(stations.getLast().getName())) {
            schedule = schedulesInverted.remove(time);
        } else {
            // throw
        }

        int trainNumber = schedule.getTrainNumber();
        Iterator<ScheduleStop> stopsIt = schedule.getStops();
        Iterator<Station> stationIt = stations.iterator();
        while(stopsIt.hasNext()) {
            ScheduleStop stop = stopsIt.next();

            while (stationIt.hasNext()) {
                Station station = stationIt.next();
                if (station.equals(stop.getStation())) {
                   station.removeStop(trainNumber, stop.getTime());
                   break;
                }
            }
        }
    }

    public Iterator<Entry<Time, Schedule>> getSchedules(String departureStationName) {
        if(departureStationName.equals(stations.getFirst().getName())) {
            return schedulesNormal.iterator();
        }
        else if(departureStationName.equals(stations.getLast().getName())) {
            return schedulesInverted.iterator();
        } else {
            // throw
        }

        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final Line other = (Line) obj;
        if (!this.name.equals(other.name)) {
            return false;
        }

        return true;
    }

    private boolean scheduleCheck (int train, ListInArray<ScheduleStop> stationAndTimes) {

        Iterator<ScheduleStop> stationAndTimesIt = stationAndTimes.iterator();
        Iterator<Station> stationIt = stations.iterator();

        Time lastTime = null;
        while (stationAndTimesIt.hasNext() && stationIt.hasNext()) {
            ScheduleStop stationAndTime = stationAndTimesIt.next();
            String stationName = stationAndTime.getStation().getName();
            Time time = stationAndTime.getTime();

            //se a sequencia de horários não for cronologica, return false
            if(lastTime != null && time.compareTo(lastTime) < 0) {
                return false;
            }

            while (stationIt.hasNext()) {
                Station station = stationIt.next();
                if (station.getName().equals(stationName)) {
                   station.addStop(train, time);
                   break;
                }
            }

            lastTime = time;
        }
        
        //se a sequencia de estaçoes não segue as da linha, return false
        return !stationAndTimesIt.hasNext();
    }
}
