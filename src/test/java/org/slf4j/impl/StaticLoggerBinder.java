package org.slf4j.impl;

import org.slf4j.ILoggerFactory;
import org.slf4j.mock.MockLoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;

public class StaticLoggerBinder implements LoggerFactoryBinder
{
    private static StaticLoggerBinder SINGLETON = new StaticLoggerBinder();

    public static StaticLoggerBinder getSingleton()
    {
        return SINGLETON;
    }

    @Override
    public ILoggerFactory getLoggerFactory()
    {
        return MockLoggerFactory.get();
    }

    @Override
    public String getLoggerFactoryClassStr()
    {
        return MockLoggerFactory.class.getName();
    }
}
