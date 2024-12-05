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

