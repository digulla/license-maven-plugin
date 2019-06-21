package org.slf4j.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import com.google.common.base.Predicate;

public class MockLoggerFactory implements ILoggerFactory
{
    private static final MockLoggerFactory INSTANCE = new MockLoggerFactory();

    private final Map<String, MockLogger> loggers = new HashMap<>();
    private List<LoggingEvent> events = new ArrayList<LoggingEvent>();

    public static MockLoggerFactory get()
    {
        return INSTANCE;
    }

    public void beforeTest()
    {
        System.out.println( "Resetting logging..." );
        events.clear();
    }

    public String dump( Predicate<LoggingEvent> filter )
    {
        StringBuilder buffer = new StringBuilder();
        String delim = "";

        for ( LoggingEvent event : events )
        {
            if ( filter.apply( event ) )
            {
                buffer.append( delim );
                delim = "\n";

                append( buffer, event );
            }
        }

        return buffer.toString();
    }

    private void append( StringBuilder buffer, LoggingEvent event )
    {
        buffer
            .append( event.getLevel() )
            .append( ' ' )
            .append( event.getFormattedMessage() );

        if ( event.getThrowable() != null )
        {
            buffer
                .append( '\n' )
                .append( event.getThrowable().getClass().getName() )
                .append( ": " )
                .append( event.getThrowable().getMessage() );
        }
    }

    @Override
    public Logger getLogger( String name )
    {
        MockLogger result = loggers.get( name );
        if ( result == null )
        {
            result = createLogger( name );
            loggers.put( name, result );
        }

        return result;
    }

    private MockLogger createLogger( String name )
    {
        return new MockLogger( name, this );
    }

    public void addEvent( LoggingEvent event )
    {
        events.add( event );

        StringBuilder buffer = new StringBuilder();
        append( buffer, event );
        System.out.println( buffer );
    }
}
