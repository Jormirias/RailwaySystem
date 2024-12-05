/**
 * @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
 * @author Jorge Dias (72360) jmr.dias@campus.fct.unl.pt
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
     * The lines which pass through the stations associated to this StationRegistry, ordered alphanumerically.
     */
    private OrderedDictionary<String, String> lines;

    /**
     * All of the trains which pass through the stations associated to this StationRegistry.
     * Ordered by time at which they happen.
     */
    private OrderedDictionary<TrainTime, Time> trainTimes;

    public StationRegistryClass(String name) {
        this.name = name;
        this.lines = new AVLTree<>();
        this.trainTimes = new AVLTree<>();
    }

    @Override
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

    @Override
    public void addTrainTime(Time departureTime, int train, Time time) {

        trainTimes.insert(new TrainTimeClass(departureTime, train), time );

    }

    @Override
    public void removeTrainTime(TrainTime train) {
        trainTimes.remove(train);
    }

    @Override
    public Iterator<Entry<TrainTime, Time>> getTrainTimes() {
        if(hasTrainTimes()) {
            return trainTimes.iterator();
        }
        else {
            return null;
        }
    }

    @Override
    public boolean hasTrainTimes() {
        return !trainTimes.isEmpty();
    }

}
