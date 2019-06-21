package org.slf4j.mock;

import java.util.Arrays;

import org.slf4j.Marker;
import org.slf4j.event.Level;

public class LoggingEvent
{
    private final String loggerName;
    private final Level level;
    private Marker marker;
    private final String formattedMessage;
    private final Throwable t;

    public LoggingEvent( String loggerName, Level level, Marker marker, Throwable t, String format, Object... arguments )
    {
        this.loggerName = loggerName;
        this.level = level;
        this.marker = marker;
        this.t = t;
        this.formattedMessage = formatMessage( format, arguments );
    }

    private String formatMessage( String format, Object[] arguments )
    {
        int start = 0;
        int pos;
        int arg = 0;
        StringBuilder buffer = new StringBuilder();

        while ( ( pos = format.indexOf( "{}", start ) ) != -1 )
        {
            buffer.append( format.substring( start, pos ) );
            start = pos + 2;

            if ( arg >= arguments.length )
            {
                throw new IllegalArgumentException( "More {} placeholders than arguments" );
            }

            convert( buffer, arguments[ arg ] );
            arg ++;
        }

        if ( arg != arguments.length )
        {
            throw new IllegalArgumentException( "Expected " + arguments.length + " {} placeholders but there were only " + arg );
        }

        if ( start < format.length() )
        {
            buffer.append( format.substring( start ) );
        }

        return buffer.toString();
    }

    private void convert( StringBuilder buffer, Object value )
    {
        if( value.getClass().isArray() )
        {
            Class<?> componentType = value.getClass().getComponentType();

            if ( componentType.isAssignableFrom( boolean.class ) )
            {
                buffer.append( Arrays.toString( (boolean[]) value ) );
            }
            else if ( componentType.isAssignableFrom( byte.class ) )
            {
                buffer.append( Arrays.toString( (byte[]) value ) );
            }
            else if ( componentType.isAssignableFrom( short.class ) )
            {
                buffer.append( Arrays.toString( (short[]) value ) );
            }
            else if ( componentType.isAssignableFrom( char.class ) )
            {
                buffer.append( Arrays.toString( (char[]) value ) );
            }
            else if ( componentType.isAssignableFrom( int.class ) )
            {
                buffer.append( Arrays.toString( (int[]) value ) );
            }
            else if ( componentType.isAssignableFrom( long.class ) )
            {
                buffer.append( Arrays.toString( (long[]) value ) );
            }
            else if ( componentType.isAssignableFrom( float.class ) )
            {
                buffer.append( Arrays.toString( (float[]) value ) );
            }
            else if ( componentType.isAssignableFrom( double.class ) )
            {
                buffer.append( Arrays.toString( (double[]) value ) );
            }
            else
            {
                buffer.append( Arrays.toString( (Object[]) value ) );
            }
        }
        else
        {
            buffer.append( value );
        }
    }

    public String getLoggerName()
    {
        return loggerName;
    }

    public Level getLevel()
    {
        return level;
    }

    public Marker getMarker()
    {
        return marker;
    }

    public String getFormattedMessage()
    {
        return formattedMessage;
    }

    public Throwable getThrowable()
    {
        return t;
    }
}
