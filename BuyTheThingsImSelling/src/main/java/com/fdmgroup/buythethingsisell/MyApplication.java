package com.fdmgroup.buythethingsisell;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.fdmgroup.buythethingsisell.restcontrollers.RestController;

public class MyApplication extends Application {
	@Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<Class<?>>();
        s.add(RestController.class);
        return s;
    }
}
