package com.worksap.company.collab.pattern.observer;

import org.junit.Test;

import static org.mockito.BDDMockito.*;

public class SimpleBusinessEventMulticasterTests {

    @Test
    public void simpleInvokeListener() {
        MyBusinessEvent event = new MyBusinessEvent("test");
        multicastEvent(true, MyBusinessListener.class, event);
    }

    @Test
    public void dontInvoke_whenListenerNotMatch() {
        MyBusinessEvent event = new MyBusinessEvent("test");
        multicastEvent(false, ParentBusinessListener.class, event);
    }

    @Test
    public void invokeMatchedListerner() {
        MyBusinessEvent event1 = new MyBusinessEvent("test");
        ParentBusinessEvent event2 = new ParentBusinessEvent("test");
        SimpleBusinessEventMulticaster multicaster = new SimpleBusinessEventMulticaster();
        BusinessListener<BusinessEvent> listener1 = mockListener(MyBusinessListener.class);
        BusinessListener<BusinessEvent> listener2 = mockListener(ParentBusinessListener.class);
        multicaster.addBusinessListener(listener1);
        multicaster.addBusinessListener(listener2);
        multicaster.multicastEvent(event1);
        multicaster.multicastEvent(event2);

        verify(listener1, times(1)).onBusinessEvent(event1);
        verify(listener2, times(1)).onBusinessEvent(event2);
    }

    @Test
    public void invokeChildEventParentListener() {
        ChildBusinessEvent event = new ChildBusinessEvent("child");
        multicastEvent(true, ParentBusinessListener.class, event);
    }

    @Test
    public void dontInvokeParentEventChildListener() {
        ParentBusinessEvent event = new ParentBusinessEvent("parent");
        multicastEvent(false, ChildBusinessListener.class, event);
    }

    @Test
    public void invokeChildEventBothListener() {
        ChildBusinessEvent event = new ChildBusinessEvent("child");
        BusinessListener<BusinessEvent> parentListener = mockListener(ParentBusinessListener.class);
        BusinessListener<BusinessEvent> childListener = mockListener(ChildBusinessListener.class);
        BusinessEventMulticaster multicaster = new SimpleBusinessEventMulticaster();
        multicaster.addBusinessListener(parentListener);
        multicaster.addBusinessListener(childListener);
        multicaster.multicastEvent(event);

        verify(parentListener, times(1)).onBusinessEvent(event);
        verify(childListener, times(1)).onBusinessEvent(event);
    }

    @Test
    public void removeListenerTest() {
        ChildBusinessEvent event = new ChildBusinessEvent("child");
        BusinessListener<BusinessEvent> parentListener = mockListener(ParentBusinessListener.class);
        BusinessListener<BusinessEvent> childListener = mockListener(ChildBusinessListener.class);
        BusinessEventMulticaster multicaster = new SimpleBusinessEventMulticaster();
        multicaster.addBusinessListener(parentListener);
        multicaster.addBusinessListener(childListener);
        multicaster.removeBusinessListener(childListener);
        multicaster.multicastEvent(event);

        verify(parentListener, times(1)).onBusinessEvent(event);
        verify(childListener, times(0)).onBusinessEvent(event);
    }

    @Test
    public void removeAllListenersTest() {
        ChildBusinessEvent event = new ChildBusinessEvent("child");
        BusinessListener<BusinessEvent> childListener = mockListener(ChildBusinessListener.class);
        BusinessEventMulticaster multicaster = new SimpleBusinessEventMulticaster();
        multicaster.addBusinessListener(childListener);
        multicaster.removeAllListeners();
        multicaster.multicastEvent(event);

        verify(childListener, times(0)).onBusinessEvent(event);
    }

    private void multicastEvent(boolean match, Class<?> listenerClazz, BusinessEvent event) {
        SimpleBusinessEventMulticaster multicaster = new SimpleBusinessEventMulticaster();
        @SuppressWarnings("unchecked")
        BusinessListener<BusinessEvent> listener = mockListener(listenerClazz);
        multicaster.addBusinessListener(listener);
        multicaster.multicastEvent(event);
        int invocationTimes = match ? 1 : 0;
        verify(listener, times(invocationTimes)).onBusinessEvent(any());
    }

    @SuppressWarnings("unchecked")
    private BusinessListener<BusinessEvent> mockListener(Class<?> listenerClazz) {
        return (BusinessListener<BusinessEvent>)mock(listenerClazz);
    }

    public static class MyBusinessEvent extends BusinessEvent {
        public MyBusinessEvent(Object source) {
            super(source);
        }
    }

    public static class MyBusinessListener implements BusinessListener<MyBusinessEvent> {
        @Override
        public void onBusinessEvent(MyBusinessEvent event) {
        }
    }

    public static class ParentBusinessEvent extends BusinessEvent {
        public ParentBusinessEvent(Object source) {
            super(source);
        }
    }

    public static class ChildBusinessEvent extends ParentBusinessEvent {

        public ChildBusinessEvent(Object source) {
            super(source);
        }
    }

    public static class ParentBusinessListener implements BusinessListener<ParentBusinessEvent> {
        @Override
        public void onBusinessEvent(ParentBusinessEvent event) {
        }
    }

    public static class ChildBusinessListener implements BusinessListener<ChildBusinessEvent> {
        @Override
        public void onBusinessEvent(ChildBusinessEvent event) {
        }
    }

}
