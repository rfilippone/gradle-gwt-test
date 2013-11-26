package org.gradle.gwt.client.model;

import com.google.inject.Singleton;

@Singleton
public class Model {

    public String user;
    public String ssk;
    private boolean loggedIn = false;

    public Model() {
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn() {
        loggedIn = true;
    }

    public void setLoggedOut() {
        loggedIn = false;
    }

}
