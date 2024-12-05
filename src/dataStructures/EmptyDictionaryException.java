/**
 * @author Tomás Silva (69720) tpd.silva@campus.fct.unl.pt
 * @author Jorge Dias (72360) jmr.dias@campus.fct.unl.pt
 */
package dataStructures;

@SuppressWarnings("ALL")
public class EmptyDictionaryException extends RuntimeException
{

    public EmptyDictionaryException( )
    {
        super();
    }

    public EmptyDictionaryException( String message )
    {
        super(message);
    }

}

