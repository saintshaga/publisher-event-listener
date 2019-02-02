package com.saintshaga.collab.pattern.observer;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class BusinessEventHelperTests {

    @Test
    public void normalListenerTest() {
        BusinessListener<? extends BusinessEvent> listener = new SimpleBusinessEventMulticasterTests.MyBusinessListener();
        assertEquals(SimpleBusinessEventMulticasterTests.MyBusinessEvent.class,
                BusinessEventHelper.getGenericEventClass(listener)
                );
    }

    @Test
    public void genericListenerTest() {
        BusinessListener<? extends BusinessEvent> listener = new GenericBusinessListener<Object>();
        assertEquals(GenericBusinessEvent.class,
                BusinessEventHelper.getGenericEventClass(listener)
                );
    }

    @Test
    public void mockedListenerTest() {
        BusinessListener<? extends BusinessEvent> listener = mock(GenericBusinessListener.class);
        assertEquals(GenericBusinessEvent.class,
                BusinessEventHelper.getGenericEventClass(listener));
    }

    static class GenericBusinessEvent<T> extends BusinessEvent {

        public GenericBusinessEvent(Object source) {
            super(source);
        }
    }

    static class GenericBusinessListener<T> implements BusinessListener<GenericBusinessEvent<T>>,  OtherInterface<T> {

        @Override
        public void onBusinessEvent(GenericBusinessEvent<T> event) {

        }
    }

    interface OtherInterface<T> {
    }
}
