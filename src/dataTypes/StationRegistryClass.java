/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes;

import dataStructures.*;
import dataTypes.interfaces.*;

import java.io.Serial;

/**
 * Class which implements a Train Station
 */
public class StationRegistryClass implements StationRegistry {

    @Serial
    private static final long serialVersionUID = 6160880975967045803L;
    private final String name;

    /**
     * Collections of lines and times for the station registry to have as reference
     * STRUCT_CHOICE: We chose to have these be OrderedDoubleList, since Lines should be ordered alphabetically
     * and the TrainTimes should be ordered by Departure time.
     *
     */
    private OrderedDoubleList<String, String> lines;

    private OrderedDictionary<TrainTime, Time> trainTimes;

    public StationRegistryClass(String name) {
        this.name = name;
        this.lines = new OrderedDoubleList<>();
        this.trainTimes = new AVLTree<>();
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        if(!other.getClass().equals(this.getClass())) {
            return false;
        }

        StationRegistryClass otherStation = (StationRegistryClass) other;
        return this.name.equalsIgnoreCase(otherStation.getName());
    }

    public boolean testName(String other) {
        return this.name.equalsIgnoreCase(other.trim());
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void addLine(String line) {
        lines.insert(line.toUpperCase(), line);
    }

    @Override
    public void removeLine(String line) { lines.remove(line.toUpperCase()); }

    @Override
    public boolean hasLines() {
        return !lines.isEmpty();
    }

    @Override
    public Iterator<Entry<String, String>> getLines() {
        return lines.iterator();
    }

    public void addTrainTime(Time departureTime, int train, Time time) {

        trainTimes.insert(new TrainTimeClass(departureTime, train), time );

    }

    public void removeTrainTime(TrainTime train) {
        trainTimes.remove(train);
    }

    public Iterator<Entry<TrainTime, Time>> getTrainTimes() {
        if(hasTrainTimes()) {
            return trainTimes.iterator();
        }
        else {
            return null;
        }
    }

    public boolean hasTrainTimes() {
        return !trainTimes.isEmpty();
    }

}
