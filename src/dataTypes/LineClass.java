/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes;

import dataStructures.*;
import dataTypes.exceptions.*;
import dataTypes.interfaces.*;

/**
 * Class which implements a Rail Line
 */
public class LineClass implements Line {

    /**
     * Serial Version UID of the Class
     */
    static final long serialVersionUID = 0L;
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
    public Iterator<Station> getStations() {
        return stations.iterator();
    }

    /**
     * Insert a new Schedule into the Line
     * It first checks the schedule validity using the method scheduleCheck, sending an error upstream if it's invalid
     * If it's confirmed as valid, adds it to the corresponding schedule collection depending on its initial station
     *      AND also adds it to the Collections inside each os the Stations in its Stops (doing this here sacrifices efficiency
     *      in the insertion and removal but makes Consult actions more efficient in Temporal Complexity, like  command MH)
     *
     */
    public void insertSchedule(String trainNumber, ListInArray<String[]> stationAndTimesString) throws InvalidScheduleException {

        int train = Integer.parseInt(trainNumber);
        String[] firstStopString = stationAndTimesString.getFirst();

        //Validity Check
        if(!scheduleCheck(stationAndTimesString, firstStopString)) {
            throw new InvalidScheduleException();
        }

        boolean isInverted;
        if(firstStopString[0].equals(stations.getFirst().getName())) {
            isInverted = false;
        } else {
            isInverted = true;
        }
        
        //First iterates over all the schedule stops. For each stop, seeks a Station in this line.
        //Then, inserts the train number and time to its collections.
        ListInArray<Stop> stops = new ListInArray<>();
        TwoWayIterator<String[]> stationAndTimesStringIt = stationAndTimesString.iterator();

        while(stationAndTimesStringIt.hasNext()) {
            String[] stationAndTimeString = stationAndTimesStringIt.next();
            String stationName = stationAndTimeString[0];
            Time time = new TimeClass(stationAndTimeString[1]);
            Iterator<Station> stationIt = stations.iterator();
            
            while (stationIt.hasNext()) {
                Station station = stationIt.next();
                if (station.getName().equals(stationName)) {
                    station.addStop(time, train, isInverted);
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

    /**
     * Remove a Schedule from the Line
     * It first checks if the Station name is one of the 2 terminals of the line. If not, it throws an error, represented in the output as an empty string.
     * Then it iterates the collection for the given departure time. If it doesn't exist, it throws an error.
     * If it exists, the corresponding schedule is removed from the collection.
     * THEN, each station
     *
     */
    public void removeSchedule(String departureStationName, String timeAsString) throws  NoSuchScheduleException {
        Schedule schedule = null;
        Time time = new TimeClass(timeAsString);
        boolean isInverted = false;

        //Compares the input station to both collections' first element's station. If they match, procedes to iterate the collection
        // for a given Time key, and if it is found, it's removed. Otherwise, errors in the .remove() will results in output error messages.
        if(departureStationName.equalsIgnoreCase(stations.getFirst().getName())) {
            schedule = schedulesNormal.remove(time);
        }
        else if(departureStationName.equalsIgnoreCase(stations.getLast().getName())) {
            schedule = schedulesInverted.remove(time);
            isInverted = true;
        }

        if(schedule == null) {
            throw new NoSuchScheduleException();
        }

        //First iterates over all the schedule stops. For each stop, seeks a Station in this line.
        //Then, removes the train number from this line Station
        TwoWayIterator<Stop> stopsIt = schedule.getStops();
        while(stopsIt.hasNext()) {
            Stop stop = stopsIt.next();

            Iterator<Station> stationIt = stations.iterator();
            while (stationIt.hasNext()) {
                Station station = stationIt.next();
                if (station.equals(stop.getStation())) {
                   station.removeStop(stop.getTime(), isInverted);
                   break;
                }
            }
        }
    }

    public Iterator<Entry<Time, Schedule>> getSchedules(String departureStationName) throws NoSuchDepartureStationException {
        if(departureStationName.equalsIgnoreCase(stations.getFirst().getName())) {
            return schedulesNormal.iterator();
        }
        else if(departureStationName.equalsIgnoreCase(stations.getLast().getName())) {
            return schedulesInverted.iterator();
        } else {
            throw new NoSuchDepartureStationException();
        }
    }

    public Schedule bestSchedule(String departureStationName, String arrivalStationName, String timeAsString)
            throws NoSuchDepartureStationException, ImpossibleRouteException {

        //search for the arrival station, already searching for teh first station, also
        TwoWayIterator<Station> stationIt = stations.iterator();
        Station firstStation = null;
        Station lastStation = null;
        Station currStation;
        boolean isInverted = false;
        while (stationIt.hasNext()) {
            currStation = stationIt.next();
            if (currStation.testName(departureStationName)) {
                firstStation = currStation;
            }
            if (currStation.testName(arrivalStationName)) {
                lastStation = currStation;
                break;
            }
        }
        if (lastStation == null) {
            throw new ImpossibleRouteException();
        }

        //then, search for the departure station and establish which List (normal or inverted) to use

        if (firstStation == null) {
            isInverted = true;
            //Iterate from the last station to the end of Stations collection..
            while (stationIt.hasNext()) {
                currStation = stationIt.next();
                if (currStation.testName(departureStationName)) {
                    firstStation = currStation;
                    break;
                }
            }

            if (firstStation == null) {
                throw new NoSuchDepartureStationException();
            }
        }

        //iterate backwards until you find correct time; if not, throw
        int trainByLastStation;
        Time targetTime = new TimeClass(timeAsString);
        TwoWayIterator<Entry<Time, Integer>> stopsIt = lastStation.stopsIterator(isInverted);
        stopsIt.fullForward();
        int targetTrain = -1;
        while(stopsIt.hasPrevious()) {
            Entry<Time, Integer> stop = stopsIt.previous();
            if (stop.getKey().compareTo(targetTime) <= 0) {
                trainByLastStation = stop.getValue();
                if(doesTrainPassDeparture(trainByLastStation, firstStation, isInverted)) {
                    targetTrain =  trainByLastStation;
                    break;
                }
            }
        }

        Schedule bestSchedule = null;
        if(targetTrain != -1) {
            bestSchedule = findSchedule(targetTrain, isInverted);
        } else {
            throw new ImpossibleRouteException();
        }

        return bestSchedule; // should never happen

        //1 ITERADOR DA COLLECTION DE STATIONS
        //procurar collection de stations, encontrar departureStationName IF NOT, RETURN NullPointerException
        //procurar collection de stations, para a frente (set Normal), e para trás (set Inverted) IF NOT, RETURN IllegalArgumentException

        //1 ITERADOR DAS STOPS EM STATION
        //procurar na arrivalStationName pela Stop mais próxima da timeAsString - Ver se esse train number passa na departureStationName (conforme seja Normal ou Inverted)
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final LineClass other = (LineClass) obj;
        return this.name.equals(other.name);
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
        boolean invertFlag = false;

        //Verificar se a Station da primeira Stop corresponde a uma das 2 estações terminais, e a qual
        if(firstStopString[0].equalsIgnoreCase(stations.getFirst().getName())) {
            inverted=false;
        } else if (firstStopString[0].equalsIgnoreCase(stations.getLast().getName())) {
            inverted = true;
        } else
            return false;

        if(inverted) {
            stationAndTimesString.invert();
            invertFlag = true;
        }

        TwoWayIterator<String[]> stationAndTimesIt = stationAndTimesString.iterator();
        Iterator<Station> stationIt = stations.iterator();
        Time lastTime = new TimeClass(0, 0);
        if(inverted) {
            stationAndTimesIt.rewind();
            lastTime = new TimeClass(23, 59);
        }
        //itera sobre stationAndTimes, e stations
        while (stationAndTimesIt.hasNext() && stationIt.hasNext()) {
            String[] stationAndTimeString = stationAndTimesIt.next();
            String stationName = stationAndTimeString[0];
            Time time = new TimeClass(stationAndTimeString[1]);

            //se a sequencia de horários não for estritamente crescente, return false

            if(invertFlag && time.compareTo(lastTime) >= 0) {
                return false;
            }
            else if (!invertFlag && time.compareTo(lastTime) <= 0){
                return false;
            }

            //Para cada estação dada, compara à atual das stations. se não corresponder, avança à procura dela,
            while (stationIt.hasNext()) {
                Station station = stationIt.next();
                if (station.getName().equals(stationName)) {
                    if(station.isStopValid(this.name, departureTime, time, inverted)) {
                        break;
                    } else {
                        return false;
                    }
                }
            }

            lastTime = time;
        }
        if(invertFlag) {
            stationAndTimesString.invert();
        }

        //se a sequencia de estaçoes não segue as da linha, return false
        return !stationAndTimesIt.hasNext();
    }

    private boolean doesTrainPassDeparture (int targetTrain, Station originStation, boolean isInverted) {
        TwoWayIterator<Entry<Time, Integer>> stopsIt = originStation.stopsIterator(isInverted);

        while (stopsIt.hasNext()) {
            Entry<Time, Integer> next = stopsIt.next();
            int trainNumber = next.getValue();
            if (trainNumber == targetTrain) {
                return true;
            }
        }
        return false;
    }

    private Schedule findSchedule (int trainNumber, boolean isInverted) {
        TwoWayIterator<Entry<Time, Schedule>> schedulesIt;
        if (isInverted) {
            schedulesIt = schedulesInverted.iterator();
        } else {
            schedulesIt = schedulesNormal.iterator();
        }

        while (schedulesIt.hasNext()) {
            Entry<Time, Schedule> next = schedulesIt.next();
            if (next.getValue().getTrainNumber() == (trainNumber)) {
                return next.getValue();
            }
        }
        return null;
    }

}
