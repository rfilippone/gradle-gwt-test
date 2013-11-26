package org.gradle.gwt.client.application.login;

import java.util.Date;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.RestServiceProxy;
import org.gradle.gwt.client.application.layout.LayoutPresenter;
import org.gradle.gwt.client.event.FirstEvent;
import org.gradle.gwt.client.model.Model;
import org.gradle.gwt.client.place.NameTokens;
import org.gradle.gwt.shared.api.APIService;
import org.sgx.gwtsjcl.client.SJCL;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

public class LoginPresenter extends Presenter<LoginPresenter.MyView, LoginPresenter.MyProxy> implements LoginUiHandlers {
    interface MyView extends View, HasUiHandlers<LoginUiHandlers> {
        public String getUsername();

        public String getPwd();
    }

    @Inject Model model;

    private String destination;
    private final PlaceManager placeManager;
    private final APIService apiService;

    @ContentSlot public static final Type<RevealContentHandler<?>> SLOT_Login = new Type<RevealContentHandler<?>>();

    @NameToken(NameTokens.login)
    @ProxyCodeSplit
    @NoGatekeeper
    public interface MyProxy extends ProxyPlace<LoginPresenter> {
    }

    @Inject
    public LoginPresenter(EventBus eventBus, MyView view, MyProxy proxy, PlaceManager placeManager, APIService apiService) {
        super(eventBus, view, proxy, LayoutPresenter.SLOT_Content);
        this.placeManager = placeManager;
        this.apiService = apiService;

        getView().setUiHandlers(this);
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        destination = request.getParameter("redirect_url", "home");
        System.out.println("login: before access to " + destination);
    }

    @Override
    public void onLoginClicked() {
        Resource resource = new Resource("api");
        ((RestServiceProxy) apiService).setResource(resource);
        APIService.LoginParameters p = new APIService.LoginParameters();
        SJCL crypto = SJCL.sjcl();
        DateTimeFormat fmt = DateTimeFormat.getFormat(PredefinedFormat.ISO_8601);
        p.nonce = crypto.codec().hex().fromBits(crypto.hash().sha256().hash(fmt.format(new Date())));
        String pwd = crypto.codec().hex().fromBits(crypto.hash().sha256().hash(getView().getPwd()));
        p.hpwd = crypto.codec().hex().fromBits(crypto.hash().sha256().hash(pwd + p.nonce));

        apiService.login(getView().getUsername(), p, new MethodCallback<APIService.LoginResult>() {
            @Override
            public void onSuccess(Method method, APIService.LoginResult response) {
                model.setLoggedIn();
                model.ssk = response.ssk;
                model.user = getView().getUsername();
                PlaceRequest.Builder builder = new PlaceRequest.Builder().nameToken(destination);
                placeManager.revealPlace(builder.build());
                getEventBus().fireEvent(new FirstEvent("Il server dice: " + response.text));
            }

            @Override
            public void onFailure(Method method, Throwable exception) {
                model.setLoggedOut();
                model.ssk = "";
                model.user = "";
                getEventBus().fireEvent(new FirstEvent("Errore: " + exception.getMessage()));
            }
        });
    }
}
