package com.saintshaga.collab.pattern.observer;

import java.util.List;

public class BusinessEventInstanceFactory {

    private static BusinessPublisher publisher = new BusinessPublisherImpl();

    public static BusinessPublisher getPublisher() {
        return publisher;
    }

    public static BusinessEventMulticaster getMulticaster() {
        return SimpleBusinessEventMulticaster.getInstance();
    }

    private List<BusinessListener> listeners;

    public BusinessPublisher businessPublisher() {
        if(listeners != null) {
            listeners.forEach(listener ->
                    BusinessEventInstanceFactory.getMulticaster()
                    .addBusinessListener(listener));
        }
        return BusinessEventInstanceFactory.getPublisher();
    }


}
