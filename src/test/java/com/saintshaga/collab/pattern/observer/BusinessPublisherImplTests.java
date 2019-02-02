package com.saintshaga.collab.pattern.observer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.*;

public class BusinessPublisherImplTests {
    @Test
    public void testPublishEvent() {
        BusinessPublisherImpl publisher = new BusinessPublisherImpl();
        BusinessEventMulticaster multicaster = mock(BusinessEventMulticaster.class);
        publisher.setMulticaster(multicaster);
        publisher.publishEvent(null);
        verify(multicaster, times(1)).multicastEvent(any());
    }

    @Test(expected = IllegalStateException.class)
    public void throwExceptionWhenMulticasterNull() {
        BusinessPublisherImpl publisher = new BusinessPublisherImpl();
        publisher.setMulticaster(null);
        publisher.publishEvent(new BusinessEvent(""));
    }

    @Test
    public void testPublisherFactory() {
        assertEquals(BusinessEventInstanceFactory.getPublisher(), BusinessEventInstanceFactory.getPublisher());
    }
}
