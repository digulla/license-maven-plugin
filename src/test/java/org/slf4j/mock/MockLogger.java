package org.slf4j.mock;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.Level;

public class MockLogger implements  Logger
{

    private final String name;
    private final MockLoggerFactory appender;

    public MockLogger( String name, MockLoggerFactory appender )
    {
        this.name = name;
        this.appender = appender;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public boolean isTraceEnabled()
    {
        return true;
    }

    @Override
    public void trace( String msg )
    {
        publish( Level.TRACE, null, msg );
    }

    @Override
    public void trace( String format, Object arg )
    {
        publish( Level.TRACE, null, format, arg );
    }

    @Override
    public void trace( String format, Object arg1, Object arg2 )
    {
        publish( Level.TRACE, null, format, arg1, arg2 );
    }

    @Override
    public void trace( String format, Object... arguments )
    {
        publish( Level.TRACE, null, format, arguments );
    }

    @Override
    public void trace( String msg, Throwable t )
    {
        publish( Level.TRACE, null, msg, t );
    }

    @Override
    public boolean isTraceEnabled( Marker marker )
    {
        return true;
    }

    @Override
    public void trace( Marker marker, String msg )
    {
        publish( Level.TRACE, marker, msg );
    }

    @Override
    public void trace( Marker marker, String format, Object arg )
    {
        publish( Level.TRACE, marker, format, arg );
    }

    @Override
    public void trace( Marker marker, String format, Object arg1, Object arg2 )
    {
        publish( Level.TRACE, marker, format, arg1, arg2 );
    }

    @Override
    public void trace( Marker marker, String format, Object... argArray )
    {
        publish( Level.TRACE, marker, format, argArray );
    }

    @Override
    public void trace( Marker marker, String msg, Throwable t )
    {
        publish( Level.TRACE, marker, msg, t );
    }

    @Override
    public boolean isDebugEnabled()
    {
        return true;
    }

    @Override
    public void debug( String msg )
    {
        publish( Level.DEBUG, null, msg );
    }

    @Override
    public void debug( String format, Object arg )
    {
        publish( Level.DEBUG, null, format, arg );
    }

    @Override
    public void debug( String format, Object arg1, Object arg2 )
    {
        publish( Level.DEBUG, null, format, arg1, arg2 );
    }

    @Override
    public void debug( String format, Object... arguments )
    {
        publish( Level.DEBUG, null, format, arguments );
    }

    @Override
    public void debug( String msg, Throwable t )
    {
        publish( Level.DEBUG, null, msg, t );
    }

    @Override
    public boolean isDebugEnabled( Marker marker )
    {
        return true;
    }

    @Override
    public void debug( Marker marker, String msg )
    {
        publish( Level.DEBUG, marker, msg );
    }

    @Override
    public void debug( Marker marker, String format, Object arg )
    {
        publish( Level.DEBUG, marker, format, arg );
    }

    @Override
    public void debug( Marker marker, String format, Object arg1, Object arg2 )
    {
        publish( Level.DEBUG, marker, format, arg1, arg2 );
    }

    @Override
    public void debug( Marker marker, String format, Object... argArray )
    {
        publish( Level.DEBUG, marker, format, argArray );
    }

    @Override
    public void debug( Marker marker, String msg, Throwable t )
    {
        publish( Level.DEBUG, marker, msg, t );
    }

    @Override
    public boolean isInfoEnabled()
    {
        return true;
    }

    @Override
    public void info( String msg )
    {
        publish( Level.INFO, null, msg );
    }

    @Override
    public void info( String format, Object arg )
    {
        publish( Level.INFO, null, format, arg );
    }

    @Override
    public void info( String format, Object arg1, Object arg2 )
    {
        publish( Level.INFO, null, format, arg1, arg2 );
    }

    @Override
    public void info( String format, Object... arguments )
    {
        publish( Level.INFO, null, format, arguments );
    }

    @Override
    public void info( String msg, Throwable t )
    {
        publish( Level.INFO, null, msg, t );
    }

    @Override
    public boolean isInfoEnabled( Marker marker )
    {
        return true;
    }

    @Override
    public void info( Marker marker, String msg )
    {
        publish( Level.INFO, marker, msg );
    }

    @Override
    public void info( Marker marker, String format, Object arg )
    {
        publish( Level.INFO, marker, format, arg );
    }

    @Override
    public void info( Marker marker, String format, Object arg1, Object arg2 )
    {
        publish( Level.INFO, marker, format, arg1, arg2 );
    }

    @Override
    public void info( Marker marker, String format, Object... argArray )
    {
        publish( Level.INFO, marker, format, argArray );
    }

    @Override
    public void info( Marker marker, String msg, Throwable t )
    {
        publish( Level.INFO, marker, msg, t );
    }

    @Override
    public boolean isWarnEnabled()
    {
        return true;
    }

    @Override
    public void warn( String msg )
    {
        publish( Level.WARN, null, msg );
    }

    @Override
    public void warn( String format, Object arg )
    {
        publish( Level.WARN, null, format, arg );
    }

    @Override
    public void warn( String format, Object arg1, Object arg2 )
    {
        publish( Level.WARN, null, format, arg1, arg2 );
    }

    @Override
    public void warn( String format, Object... arguments )
    {
        publish( Level.WARN, null, format, arguments );
    }

    @Override
    public void warn( String msg, Throwable t )
    {
        publish( Level.WARN, null, msg, t );
    }

    @Override
    public boolean isWarnEnabled( Marker marker )
    {
        return true;
    }

    @Override
    public void warn( Marker marker, String msg )
    {
        publish( Level.WARN, marker, msg );
    }

    @Override
    public void warn( Marker marker, String format, Object arg )
    {
        publish( Level.WARN, marker, format, arg );
    }

    @Override
    public void warn( Marker marker, String format, Object arg1, Object arg2 )
    {
        publish( Level.WARN, marker, format, arg1, arg2 );
    }

    @Override
    public void warn( Marker marker, String format, Object... argArray )
    {
        publish( Level.WARN, marker, format, argArray );
    }

    @Override
    public void warn( Marker marker, String msg, Throwable t )
    {
        publish( Level.WARN, marker, msg, t );
    }

    @Override
    public boolean isErrorEnabled()
    {
        return true;
    }

    @Override
    public void error( String msg )
    {
        publish( Level.ERROR, null, msg );
    }

    @Override
    public void error( String format, Object arg )
    {
        publish( Level.ERROR, null, format, arg );
    }

    @Override
    public void error( String format, Object arg1, Object arg2 )
    {
        publish( Level.ERROR, null, format, arg1, arg2 );
    }

    @Override
    public void error( String format, Object... arguments )
    {
        publish( Level.ERROR, null, format, arguments );
    }

    @Override
    public void error( String msg, Throwable t )
    {
        publish( Level.ERROR, null, msg, t );
    }

    @Override
    public boolean isErrorEnabled( Marker marker )
    {
        return true;
    }

    @Override
    public void error( Marker marker, String msg )
    {
        publish( Level.ERROR, marker, msg );
    }

    @Override
    public void error( Marker marker, String format, Object arg )
    {
        publish( Level.ERROR, marker, format, arg );
    }

    @Override
    public void error( Marker marker, String format, Object arg1, Object arg2 )
    {
        publish( Level.ERROR, marker, format, arg1, arg2 );
    }

    @Override
    public void error( Marker marker, String format, Object... argArray )
    {
        publish( Level.ERROR, marker, format, argArray );
    }

    @Override
    public void error( Marker marker, String msg, Throwable t )
    {
        publish( Level.ERROR, marker, msg, t );
    }

    private void publish( Level level, Marker marker, String format, Object... arguments )
    {
        Object lastArgument = arguments.length == 0 ? null : arguments[ arguments.length - 1 ];
        Throwable t = null;
        if( lastArgument instanceof Throwable )
        {
            t = (Throwable) lastArgument;
            arguments = Arrays.copyOf( arguments, arguments.length - 1 );
        }
        
        LoggingEvent event = new LoggingEvent( name, level, marker, t, format, arguments );
        appender.addEvent( event );
    }
}
