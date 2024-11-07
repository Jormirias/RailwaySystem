/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes;

import dataStructures.*;
import dataStructures.IllegalArgumentException;

import java.io.Serializable;

/**
 * Class which implements a Rail Network
 */
public class Network implements Serializable {

    /**
     * Line Collection
     * STRUCT_CHOICE: We chose to have this be a DoubleList due to the ease of iteration on a DoubleList which is O(n),
     * and the ease of adding or removing elements, which is O(1).
     *
     */
    /*Should this be Dictionaries as their identifier is only their name? It would be more efficient for searching operations*/
    private final DoubleList<Line> lines;

    /**
     * Collection of Station names.
     * For the first phase of the project, we felt that Stations not being unique
     * objects would allow us to squeeze a bit more performance in searches.
     */
    private final DoubleList<String> stationNames;

    // maybe not worth it yet as we do not have all the tools available
    // private Station[] stations;

    public Network()
    {
        lines = new DoubleList<>();
        stationNames = new DoubleList<>();
    }

    /**
     * Add new Line to Network
     * @param lineName receives the line name, which must be unique. The method iterates over the collection of lines to find out if it already exists,
     *        and if it does, it throws an error.
     * @param newStations element with collection of station for the new Line element.
     *
     */
    public void insertLine(String lineName, ListInArray<Station> newStations) throws dataStructures.IllegalArgumentException {
        if (findLineWithName(lineName) != null){
            throw new dataStructures.IllegalArgumentException();
        }

        else{
            lines.addLast(new Line(lineName, newStations));
            addNewStationNames(newStations);
        }
    }

    /**
     * Remove Line from Network
     * @param lineName receives the line name. The method iterates over the collection of lines to find out if it exists, and if it does, it removes it.
     * If it doesn't exist, it throws an error upstream
     * This method doesn't use the findLineWithName method to prevent iterating twice over the same collection. Instead, it automatically removes the line while iterating.
     *
     */
    public void removeLine(String lineName) throws NoSuchElementException {
        Iterator<Line> it = lines.iterator();
        while(it.hasNext()) {
            Line next = it.next();
            if(next.getName().toUpperCase().equals(lineName.toUpperCase())) {
                lines.remove(next);
                return;
            }
        }

        throw new NoSuchElementException();
    }

    /**
     * Consult Line Stations
     * @param lineName receives the line name. The method iterates over the collection of lines to find out if it exists, using the findLineWithName method
     * If it doesn't exist, it throws an error upstream
     * @return If the Line exists, the method returns a collection of its Stations
     *
     */
    public ListInArray<Station> getStations(String lineName) throws NoSuchElementException {
        Line line = findLineWithName(lineName);
        if (line == null){
            throw new NoSuchElementException();
        }
        else
            return line.getStations();
    }

    /**
     * Insert a new Schedule in a Line
     * @param lineName receives the line name. The method iterates over the collection of lines to find out if it exists, using the findLineWithName method
     * If it doesn't exist, it throws an error upstream
     * @param trainNumber indicates the train number associated with the new Schedule
     * @param stationAndTimes is the collections of stops to be associated with the new Schedule
     *
     */
    public void insertSchedule(String lineName, String trainNumber, ListInArray<String[]> stationAndTimes) throws NoSuchElementException, IllegalArgumentException {
        Line line = findLineWithName(lineName);
        if (line == null){
            throw new NoSuchElementException();
        }
        else {
            line.insertSchedule(trainNumber, stationAndTimes);
        }
    }

    /**
     * Remove a new from a Line
     * @param lineName receives the line name. The method iterates over the collection of lines to find out if it exists, using the findLineWithName method
     * If it doesn't exist, it throws an error upstream
     * @param departureStationName indicates the name of the first station of the Schedule
     * @param timeAsString is the time corresponding to the first time of the schedule
     *
     */
    public void removeSchedule(String lineName, String departureStationName, String timeAsString) throws NoSuchElementException, InvalidPositionException {
        Line line = findLineWithName(lineName);
        if (line == null){
            throw new NoSuchElementException();
        }
        else {
            line.removeSchedule(departureStationName, timeAsString);
        }
    }

    public Iterator<Entry<Time, Schedule>> getLineSchedules(String lineName, String departureStationName) throws NoSuchElementException, NullPointerException {
        Line line = findLineWithName(lineName);
        if (line == null){
            throw new NoSuchElementException();
        }
        else {
            return line.getSchedules(departureStationName);
        }
    }

    public String getStationName(String tentativeName) {
        Iterator<String> it = stationNames.iterator();
        while(it.hasNext()) {
            String name = it.next();
            if(name.toUpperCase().equals(tentativeName.toUpperCase())) {
                return name;
            }
        }
        
        return tentativeName;
    }

    /**
     * Helper method
     * @param lineName receives the line name. The method iterates over the collection of lines to find out if it exists
     * @return If the Line exists, the method returns it. Otherwise, it returns null.
     *
     */
    private Line findLineWithName(String lineName) {
        Iterator<Line> it = lines.iterator();
        while(it.hasNext()) {
            Line next = it.next();
            if(next.getName().toUpperCase().equals(lineName.toUpperCase())) {
                 return next;
            }
        }

         return null;
     }

    private void addNewStationNames(ListInArray<Station> newStations) {
        Iterator<Station> newStationsIt = newStations.iterator();
        while(newStationsIt.hasNext()) {
            Station newStation = newStationsIt.next();

            boolean found = false;
            Iterator<String> stationNamesIt = stationNames.iterator();
            while(stationNamesIt.hasNext()) {
                String existantStationName = stationNamesIt.next();
                if(existantStationName.toUpperCase().equals(newStation.getName().toUpperCase())) {
                    found = true;
                    break;
                }
            }
            
            if(!found) {
                stationNames.addLast(newStation.getName());
            }
        }
    }

}
