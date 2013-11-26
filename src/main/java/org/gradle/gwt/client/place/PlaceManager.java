package org.gradle.gwt.client.place;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.annotations.DefaultPlace;
import com.gwtplatform.mvp.client.annotations.ErrorPlace;
import com.gwtplatform.mvp.client.annotations.UnauthorizedPlace;
import com.gwtplatform.mvp.client.proxy.DefaultPlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.PlaceRequest.Builder;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;

public class PlaceManager extends DefaultPlaceManager {

    private final Builder destinationBuilder;

    @Inject
    public PlaceManager(EventBus eventBus, TokenFormatter tokenFormatter, @DefaultPlace String defaultPlaceNameToken,
            @ErrorPlace String errorPlaceNameToken, @UnauthorizedPlace String unauthorizedPlaceNameToken) {
        super(eventBus, tokenFormatter, defaultPlaceNameToken, errorPlaceNameToken, unauthorizedPlaceNameToken);
        destinationBuilder = new PlaceRequest.Builder().nameToken(unauthorizedPlaceNameToken);
    }

    @Override
    public void revealUnauthorizedPlace(String unauthorizedHistoryToken) {
        System.out.println("PlaceManager: unauthorized acces to " + unauthorizedHistoryToken);

        PlaceRequest destination = destinationBuilder.with("redirect_url", unauthorizedHistoryToken).build();

        revealPlace(destination, true);
    }
}
