package com.saintshaga.collab.pattern.observer;

import java.util.EventListener;

/**
 * Created by huang on 19-1-17.
 */
public interface BusinessListener<E extends BusinessEvent> extends EventListener {
    void onBusinessEvent(E event);
}
