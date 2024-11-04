/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes;

import dataStructures.*;
import dataStructures.IllegalArgumentException;

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
     * STRUCT_CHOICE: We chose to have these be OrderedDoubleLists (in the future, Hash maps by Time), ordered by departure time, since the RH command will need to iterate over them and it's convenient to have them ordered.
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

    /**
     * Insert a new Schedule into the Line
     * It first checks the schedule validity using the method scheduleCheck, sending an error upstream if it's invalid
     * If it's confirmed as valid, adds it to the corresponding schedule collection depending on its initial station
     *      AND also adds it to the Collections inside each os the Stations in its Stops (doing this here sacrifices efficiency
     *      in the insertion and removal but makes Consult actions more efficient in Temporal Complexity, like  command MH)
     *
     */
    public void insertSchedule(String trainNumber, ListInArray<Stop<Station, Time>> stationAndTimes) throws IllegalArgumentException {

        int train = Integer.parseInt(trainNumber);
        Stop<Station,Time> firstStop = stationAndTimes.getFirst();

        //Validity Check
        if(!scheduleCheck(stationAndTimes, firstStop)) {
            throw new IllegalArgumentException();
        }

        //Create Schedule and put it in corresponding OrderedDoubleList
        Schedule schedule = new Schedule(train, stationAndTimes);
        if(firstStop.getKey().equals(stations.getFirst())) {
            schedulesNormal.insert(firstStop.getValue(), schedule);
        }
        else if(firstStop.getKey().equals(stations.getLast())) {
            schedulesInverted.insert(firstStop.getValue(), schedule);
        }

        //First iterates over all the schedule stops. For each stop, seeks a Station in this line.
        //Then, inserts the train number and time to its collections.
        Iterator<Stop<Station,Time>> stopsIt = schedule.getStops();
        Iterator<Station> stationIt = stations.iterator();
        while(stopsIt.hasNext()) {
            Stop<Station,Time> stop = stopsIt.next();

            while (stationIt.hasNext()) {
                Station station = stationIt.next();
                if (station.equals(stop.getKey())) {
                    station.addStop(train, stop.getValue());
                    break;
                }
            }
        }
    }

    /**
     * Remove a Schedule from the Line
     * It first checks if the Station name is one of the 2 terminals of the line. If not, it throws an error, represented in the output as an empty string.
     * Then it iterates the collection for the given departure time. If it doesn't exist, it throws an error.
     * If it exists, the corresponding schedule is removed from the collection.
     * THEN, each station
     *
     */
    public void removeSchedule(String departureStationName, String timeAsString) throws  InvalidPositionException {
        Schedule schedule = null;
        Time time = new Time(timeAsString);

        //Compares the input station to both collections' first element's station. If they match, procedes to iterate the collection
        // for a given Time key, and if it is found, it's removed. Otherwise, errors in the .remove() will results in output error messages.
        if(departureStationName.toUpperCase().equals(stations.getFirst().getName().toUpperCase())) {
            schedule = schedulesNormal.remove(time);
        }
        else if(departureStationName.toUpperCase().equals(stations.getLast().getName().toUpperCase())) {
            schedule = schedulesInverted.remove(time);
        } else {
            // should not be hit.
        }

        if(schedule == null) {
            throw new InvalidPositionException();
        }

        //First iterates over all the schedule stops. For each stop, seeks a Station in this line.
        //Then, removes the train number from this line Station
        int trainNumber = schedule.getTrainNumber();
        Iterator<Stop<Station,Time>> stopsIt = schedule.getStops();
        Iterator<Station> stationIt = stations.iterator();
        while(stopsIt.hasNext()) {
            Stop<Station,Time> stop = stopsIt.next();

            while (stationIt.hasNext()) {
                Station station = stationIt.next();
                if (station.equals(stop.getKey())) {
                   station.removeStop(trainNumber, stop.getValue());
                   break;
                }
            }
        }
    }

    public Iterator<Entry<Time, Schedule>> getSchedules(String departureStationName) throws NullPointerException {
        if(departureStationName.toUpperCase().equals(stations.getFirst().getName().toUpperCase())) {
            return schedulesNormal.iterator();
        }
        else if(departureStationName.toUpperCase().equals(stations.getLast().getName().toUpperCase())) {
            return schedulesInverted.iterator();
        } else {
            throw new NullPointerException();
        }
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


    /**
     * Helper method to check a Schedule validity
     * @return It first checks the first stop validity, sending a false upstream if it isn't one of the line's terminal
     *         Secondly, it checks if the order of Times in Stops is strictly ascending, sending a false if it's not
     *         Lastly, it checks the order consistency comparing to the Stations collection. If it isn't consistent, sends a false upstream
     *         If are checks are passed, it returns TRUE
     *
     */
    private boolean scheduleCheck (ListInArray<Stop<Station,Time>> stationAndTimes, Stop<Station,Time> firstStop) {

        boolean inverted;

        //Verificar se a Station da primeira Stop corresponde a uma das 2 estações terminais, e a qual
        if(firstStop.getKey().equals(stations.getFirst())) {
            inverted=false;
        } else if (firstStop.getKey().equals(stations.getLast())) {
            inverted = true;
        } else
            return false;

        if(inverted) {
            stationAndTimes.invert();
        }
        Iterator<Stop<Station,Time>> stationAndTimesIt = stationAndTimes.iterator();
        Iterator<Station> stationIt = stations.iterator();

        Time lastTime = new Time(0,0);
        //itera sobre stationAndTimes, e stations
        while (stationAndTimesIt.hasNext() && stationIt.hasNext()) {
            Stop<Station,Time> stationAndTime = stationAndTimesIt.next();
            String stationName = stationAndTime.getKey().getName();
            Time time = stationAndTime.getValue();

            //se a sequencia de horários não for estritamente crescente, return false
            if(time.compareTo(lastTime) <= 0) {
                return false;
            }

            //Para cada estação dada, compara à atual das stations. se não corresponder, avança à procura dela,
            while (stationIt.hasNext()) {
                Station station = stationIt.next();
                if (station.getName().equals(stationName)) {
                   break;
                }
            }

            lastTime = time;
        }
        
        //se a sequencia de estaçoes não segue as da linha, return false
        return !stationAndTimesIt.hasNext();
    }
}
