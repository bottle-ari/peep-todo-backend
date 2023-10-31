package com.peeptodo.peeptodo_backend.util;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

public class Utils {
    public static Object unproxy(Object object) {
        if (object instanceof HibernateProxy) {
            return Hibernate.unproxy(object);
        } else{
            return object;
        }
    }
}
