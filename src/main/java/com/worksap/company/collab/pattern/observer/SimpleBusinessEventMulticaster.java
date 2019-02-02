package com.worksap.company.collab.pattern.observer;


import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by huang on 19-1-17.
 */
public class SimpleBusinessEventMulticaster implements BusinessEventMulticaster {

    private static BusinessEventMulticaster multicaster = new SimpleBusinessEventMulticaster();

    private Set<BusinessListener<? extends BusinessEvent>> listeners = new LinkedHashSet<>();
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public static BusinessEventMulticaster getInstance() {
        return multicaster;
    }

    @Override
    public void addBusinessListener(BusinessListener<? extends BusinessEvent> listener) {
        try {
            lock.writeLock().lock();
            this.listeners.add(listener);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void removeBusinessListener(BusinessListener<? extends BusinessEvent> listener) {
        try {
            lock.writeLock().lock();
            this.listeners.remove(listener);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void removeAllListeners() {
        try {
            lock.writeLock().lock();
            this.listeners.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void multicastEvent(BusinessEvent event) {
        for(BusinessListener<? extends BusinessEvent> listener : getEffectiveListeners(event.getClass())) {
            invokeListener(listener, event);
        }
    }

    @SuppressWarnings("unchecked")
    private void invokeListener(BusinessListener listener, BusinessEvent event) {
        listener.onBusinessEvent(event);
    }

    @SuppressWarnings("unchecked")
    private List<BusinessListener<? extends BusinessEvent>> getEffectiveListeners(Class<? extends BusinessEvent> eventClass) {
        List<BusinessListener<?>> effectiveListeners = new ArrayList<>();
        for(BusinessListener<?> listener : getAllListeners()) {
            Class declaredListenerClass = BusinessEventHelper.getGenericEventClass(listener);
            if(declaredListenerClass.isAssignableFrom(eventClass)) {
                effectiveListeners.add(listener);
            }
        }
        return effectiveListeners;
    }

    private Collection<BusinessListener<? extends BusinessEvent>> getAllListeners() {
        try {
            lock.readLock().lock();
            return this.listeners;
        } finally {
            lock.readLock().unlock();
        }
    }
}
