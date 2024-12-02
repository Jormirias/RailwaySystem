/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes.interfaces;

import dataStructures.Entry;
import dataStructures.Iterator;
import dataStructures.OrderedDoubleList;

import java.io.Serializable;

public interface TrainTime extends Serializable{


    public Iterator<Entry<Integer, Time>> getTrains();

    public void addTrain(int train, Time time);

    public void removeTrain(int train);
}
