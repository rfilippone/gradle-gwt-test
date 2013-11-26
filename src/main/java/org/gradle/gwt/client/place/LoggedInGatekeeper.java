/**
 * 
 */
package org.gradle.gwt.client.place;

import org.gradle.gwt.client.model.Model;

import com.google.inject.Inject;
import com.gwtplatform.mvp.client.annotations.DefaultGatekeeper;
import com.gwtplatform.mvp.client.proxy.Gatekeeper;

/**
 * @author FirstName LastName
 * 
 */
@DefaultGatekeeper
public class LoggedInGatekeeper implements Gatekeeper {

    @Inject Model model;

    /**
     * 
     */
    @Inject
    public LoggedInGatekeeper() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.gwtplatform.mvp.client.proxy.Gatekeeper#canReveal()
     */
    @Override
    public boolean canReveal() {
        System.out.println("can reveal = " + model.isLoggedIn());
        return model.isLoggedIn();
    }
}
