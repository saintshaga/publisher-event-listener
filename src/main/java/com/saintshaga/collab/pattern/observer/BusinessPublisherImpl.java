package com.saintshaga.collab.pattern.observer;

/**
 * Created by huang on 19-2-2.
 */
public class BusinessPublisherImpl implements BusinessPublisher {

    private BusinessEventMulticaster multicaster = SimpleBusinessEventMulticaster.getInstance();

    @Override
    public void publishEvent(BusinessEvent event) {
        getMulticaster().multicastEvent(event);
    }

    public void setMulticaster(BusinessEventMulticaster multicaster) {
        this.multicaster = multicaster;
    }

    private BusinessEventMulticaster getMulticaster() {
        if(this.multicaster == null) {
            throw new IllegalStateException("Multicaster is not initialized.");
        }
        return this.multicaster;
    }
}
