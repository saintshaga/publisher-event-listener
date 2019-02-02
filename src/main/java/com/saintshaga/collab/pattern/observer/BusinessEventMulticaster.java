package com.saintshaga.collab.pattern.observer;

/**
 * Created by huang on 19-1-17.
 */
public interface BusinessEventMulticaster {
    void addBusinessListener(BusinessListener<? extends BusinessEvent> listener);
    void removeBusinessListener(BusinessListener<? extends BusinessEvent> listener);
    void removeAllListeners();
    void multicastEvent(BusinessEvent event);
}
