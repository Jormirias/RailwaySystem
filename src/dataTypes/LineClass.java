/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes;

import dataStructures.*;
import dataTypes.exceptions.*;
import dataTypes.interfaces.*;

import java.io.Serializable;

/**
 * Class which implements a Rail Line
 */
public class LineClass implements Line, Serializable {

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
    public LineClass(String lineName, ListInArray<Station> newStations) {
        name = lineName;
        stations = newStations;

        schedulesNormal = new OrderedDoubleList<>();
        schedulesInverted = new OrderedDoubleList<>();
    }

    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public ListInArray<Station> getStations() {
        return stations;
    }

    @Override
    public void insertSchedule(String trainNumber, ListInArray<String[]> stationAndTimesString) throws InvalidScheduleException {
        int train = Integer.parseInt(trainNumber);
        String[] firstStopString = stationAndTimesString.getFirst();

        //Validity Check
        if(!scheduleCheck(stationAndTimesString, firstStopString)) {
            throw new InvalidScheduleException();
        }

        
        //First iterates over all the schedule stops. For each stop, seeks a Station in this line.
        //Then, inserts the train number and time to its collections.
        ListInArray<Stop> stops = new ListInArray<>();
        Iterator<String[]> stationAndTimesStringIt = stationAndTimesString.iterator();
        Iterator<Station> stationIt = stations.iterator();
        while(stationAndTimesStringIt.hasNext()) {
            String[] stationAndTimeString = stationAndTimesStringIt.next();
            String stationName = stationAndTimeString[0];
            Time time = new TimeClass(stationAndTimeString[1]);
            
            while (stationIt.hasNext()) {
                Station station = stationIt.next();
                if (station.getName().equals(stationName)) {
                    station.addStop(train, time);
                    stops.addLast(new StopClass(station, time));
                    break;
                }
            }
        }

        //Create Schedule and put it in corresponding OrderedDoubleList
        Schedule schedule = new ScheduleClass(train, stops);
        Stop firstStop = stops.getFirst();
        if(firstStop.getStation().getName().equals(stations.getFirst().getName())) {
            schedulesNormal.insert(firstStop.getTime(), schedule);
        }
        else if(firstStop.getStation().getName().equals(stations.getLast().getName())) {
            schedulesInverted.insert(firstStop.getTime(), schedule);
        }
    }

    @Override
    public void removeSchedule(String departureStationName, String timeAsString) throws  NoSuchScheduleException {
        Schedule schedule = null;
        Time time = new TimeClass(timeAsString);

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
            throw new NoSuchScheduleException();
        }

        //First iterates over all the schedule stops. For each stop, seeks a Station in this line.
        //Then, removes the train number from this line Station
        int trainNumber = schedule.getTrainNumber();
        Iterator<Stop> stopsIt = schedule.getStops();
        Iterator<Station> stationIt = stations.iterator();
        while(stopsIt.hasNext()) {
            Stop stop = stopsIt.next();

            while (stationIt.hasNext()) {
                Station station = stationIt.next();
                if (station.equals(stop.getStation())) {
                   station.removeStop(trainNumber, stop.getTime());
                   break;
                }
            }
        }
    }

    @Override
    public Iterator<Entry<Time, Schedule>> getSchedules(String departureStationName) throws NoSuchDepartureStationException {
        if(departureStationName.toUpperCase().equals(stations.getFirst().getName().toUpperCase())) {
            return schedulesNormal.iterator();
        }
        else if(departureStationName.toUpperCase().equals(stations.getLast().getName().toUpperCase())) {
            return schedulesInverted.iterator();
        } else {
            throw new NoSuchDepartureStationException();
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
        if (!this.name.equals(other.getName())) {
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
    private boolean scheduleCheck (ListInArray<String[]> stationAndTimesString, String[] firstStopString) {

        boolean inverted;

        //Verificar se a Station da primeira Stop corresponde a uma das 2 estações terminais, e a qual
        if(firstStopString[0].toUpperCase().equals(stations.getFirst().getName().toUpperCase())) {
            inverted=false;
        } else if (firstStopString[0].toUpperCase().equals(stations.getLast().getName().toUpperCase())) {
            inverted = true;
        } else
            return false;

        if(inverted) {
            stationAndTimesString.invert();
        }

        Iterator<String[]> stationAndTimesIt = stationAndTimesString.iterator();
        Iterator<Station> stationIt = stations.iterator();

        Time lastTime = new TimeClass(0,0);
        //itera sobre stationAndTimes, e stations
        while (stationAndTimesIt.hasNext() && stationIt.hasNext()) {
            String[] stationAndTimeString = stationAndTimesIt.next();
            String stationName = stationAndTimeString[0];
            Time time = new TimeClass(stationAndTimeString[1]);

            //se a sequencia de horários não for estritamente crescente, return false
            if(time.compareTo(lastTime) <= 0) {
                return false;
            }

            //Para cada estação dada, compara à atual das stations. se não corresponder, avança à procura dela,
            while (stationIt.hasNext()) {
                Station station = stationIt.next();
                if (station.getName().equals(stationName)) {
                    if(!stationAndTimesIt.hasNext()) {
                        if(inverted) {
                            stationAndTimesString.invert();
                        }
                        return true;
                    } else {
                        break;
                    }
                }

            }

            lastTime = time;
        }
        
        //se a sequencia de estaçoes não segue as da linha, return false
        return false;
    }
}
