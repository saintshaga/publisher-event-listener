package com.worksap.company.collab.pattern.observer;

import com.google.common.reflect.TypeToken;

public class BusinessEventHelper {

    private BusinessEventHelper(){}

    public static Class getGenericEventClass(BusinessListener<? extends BusinessEvent> listener) {
        return TypeToken.of(listener.getClass()).getSupertype(BusinessListener.class)
                .resolveType(BusinessListener.class.getTypeParameters()[0]).getRawType();
    }
}
