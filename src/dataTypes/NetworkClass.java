/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes;

import dataStructures.*;
import dataTypes.exceptions.*;
import dataTypes.interfaces.*;

/**
 * Class which implements a Rail Network
 */
public class NetworkClass implements Network {

    /**
     * Line Collection
     * STRUCT_CHOICE: We chose to have this be a DoubleList due to the ease of iteration on a DoubleList which is O(n),
     * and the ease of adding or removing elements, which is O(1).
     *
     */
    /*Should this be Dictionaries as their identifier is only their name? It would be more efficient for searching operations*/
    private DoubleList<Line> lines;

    /**
     * Collection of Station names.
     * For the first phase of the project, we felt that Stations not being unique
     * objects would allow us to squeeze a bit more performance in searches.
     */
    private DoubleList<String> stationNames;

    public NetworkClass()
    {
        lines = new DoubleList<>();
        stationNames = new DoubleList<>();
    }

    @Override
    public void insertLine(String lineName, ListInArray<Station> newStations) throws LineAlreadyExistsException {
        if (findLineWithName(lineName) != null){
            throw new LineAlreadyExistsException();
        }
        else{
            lines.addLast(new LineClass(lineName, newStations));
            addNewStationNames(newStations);
        }
    }

    @Override
    public void removeLine(String lineName) throws NoSuchLineException {
        Iterator<Line> it = lines.iterator();
        while(it.hasNext()) {
            Line next = it.next();
            if(next.getName().toUpperCase().equals(lineName.toUpperCase())) {
                lines.remove(next);
                return;
            }
        }

        throw new NoSuchLineException();
    }

    @Override
    public ListInArray<Station> getStations(String lineName) throws NoSuchLineException {
        Line line = findLineWithName(lineName);
        if (line == null){
            throw new NoSuchLineException();
        }
        else
            return line.getStations();
    }

    @Override
    public void insertSchedule(String lineName, String trainNumber, ListInArray<String[]> stationAndTimes) throws NoSuchLineException, InvalidScheduleException {
        Line line = findLineWithName(lineName);
        if (line == null){
            throw new NoSuchLineException();
        }
        else {
            line.insertSchedule(trainNumber, stationAndTimes);
        }
    }

    @Override
    public void removeSchedule(String lineName, String departureStationName, String timeAsString) throws NoSuchLineException, NoSuchScheduleException {
        Line line = findLineWithName(lineName);
        if (line == null){
            throw new NoSuchLineException();
        }
        else {
            line.removeSchedule(departureStationName, timeAsString);
        }
    }

    @Override
    public Iterator<Entry<Time, Schedule>> getLineSchedules(String lineName, String departureStationName) throws NoSuchLineException, NoSuchDepartureStationException {
        Line line = findLineWithName(lineName);
        if (line == null){
            throw new NoSuchLineException();
        }
        else {
            return line.getSchedules(departureStationName);
        }
    }

    @Override
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

    /**
     * If the collection of Stations to be added has new names,
     * add them to existing name list.
     * @param newStations - Collection of new Stations to be added.
     */
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
