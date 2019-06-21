package org.slf4j.mock;

import java.util.HashSet;
import java.util.Set;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.event.Level;

import com.google.common.base.Predicate;

public class LoggingRule extends TestWatcher
{
    private Class<?>[] loggers;

    public LoggingRule( Class<?>... loggers )
    {
        this.loggers = loggers;
    }

    @Override
    protected void starting( Description description )
    {
        super.starting( description );
        
        MockLoggerFactory.get().beforeTest();
    }

    public String dump( Level level )
    {
        return dump( level, loggers );
    }

    public String dump( Level level, Class<?>... loggers )
    {
        Set<String> loggerNames = toLoggerNames( loggers );
        EventPredicate filter = new EventPredicate( level, loggerNames );
        return MockLoggerFactory.get().dump( filter );
    }

    private static class EventPredicate implements Predicate<LoggingEvent>
    {

        private final Level level;
        private final Set<String> loggerNames;

        public EventPredicate( Level level, Set<String> loggerNames )
        {
            this.level = level;
            this.loggerNames = loggerNames;
        }

        @Override
        public boolean apply( LoggingEvent input )
        {
            if ( level.compareTo( input.getLevel() ) > 0 )
            {
                return false;
            }

            return loggerNames.contains( input.getLoggerName() );
        }
    }
    
    private Set<String> toLoggerNames( Class<?>[] types )
    {
        Set<String> result = new HashSet<String>();
        for ( Class<?> type : types )
        {
            result.add( type.getName() );
        }
        return result;
    }
}
