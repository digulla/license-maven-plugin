package org.slf4j.mock;

import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.event.Level;

public class LoggingEventTest
{
    @Test( expected = IllegalArgumentException.class )
    public void testTooManyPlaceholders1()
    {
        new LoggingEvent( "foo", Level.DEBUG, null, null, "{}" );
    }

    @Test( expected = IllegalArgumentException.class )
    public void testTooManyPlaceholders2()
    {
        new LoggingEvent( "foo", Level.DEBUG, null, null, "{} {}", "x" );
    }

    @Test( expected = IllegalArgumentException.class )
    public void testTooFewPlaceholders()
    {
        new LoggingEvent( "foo", Level.DEBUG, null, null, "a", "x" );
    }

    @Test( expected = IllegalArgumentException.class )
    public void testTooFewPlaceholdersWithThrowable()
    {
        new LoggingEvent( "foo", Level.DEBUG, null, null, "a", "x", new RuntimeException( "Just a test" ) );
    }

    @Test
    public void testFormatString()
    {
        String actual = new LoggingEvent( "foo", Level.DEBUG, null, null, "1{}2", "x" )
            .getFormattedMessage();
        assertEquals( "1x2", actual );
    }

    @Test
    public void testPlaceholderAtStart()
    {
        String actual = new LoggingEvent( "foo", Level.DEBUG, null, null, "{}2", "x" )
            .getFormattedMessage();
        assertEquals( "x2", actual );
    }

    @Test
    public void testPlaceholderAtEnd()
    {
        String actual = new LoggingEvent( "foo", Level.DEBUG, null, null, "1{}", "x" )
            .getFormattedMessage();
        assertEquals( "1x", actual );
    }

    @Test
    public void testSeveralPlaceholders()
    {
        String actual = new LoggingEvent( "foo", Level.DEBUG, null, null, "{}{}{}", "x", 1, true )
            .getFormattedMessage();
        assertEquals( "x1true", actual );
    }

    @Test
    public void testBooleanArray()
    {
        boolean[] array = { true, false };
        String actual = new LoggingEvent( "foo", Level.DEBUG, null, null, "{}", (Object) array )
            .getFormattedMessage();
        assertEquals( "[true, false]", actual );
    }

    @Test
    public void testIntArray()
    {
        int[] array = { -1, 0, 1 };
        String actual = new LoggingEvent( "foo", Level.DEBUG, null, null, "{}", (Object) array )
            .getFormattedMessage();
        assertEquals( "[-1, 0, 1]", actual );
    }

    @Test
    public void testStringArray()
    {
        String[] array = { "foo", "bar" };
        String actual = new LoggingEvent( "foo", Level.DEBUG, null, null, "{}", (Object) array )
            .getFormattedMessage();
        assertEquals( "[foo, bar]", actual );
    }
}
