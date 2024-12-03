/**
* @author Jorge Dias (72360) jmr.dias@alumni.fct.unl.pt
* @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
*/

package dataTypes.interfaces;

import dataStructures.Entry;
import dataStructures.Iterator;
import dataStructures.OrderedDoubleList;
import dataStructures.TwoWayIterator;

import java.io.Serializable;

public interface TrainTime extends Comparable<TrainTime>, Serializable{


    Time getTime();

    Integer getTrain();
}
