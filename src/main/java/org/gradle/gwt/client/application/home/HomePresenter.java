package org.gradle.gwt.client.application.home;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.RestServiceProxy;
import org.gradle.gwt.client.application.layout.LayoutPresenter;
import org.gradle.gwt.client.event.GetDataResultReceivedEvent;
import org.gradle.gwt.client.model.Model;
import org.gradle.gwt.client.place.NameTokens;
import org.gradle.gwt.shared.api.APIService;
import org.gradle.gwt.shared.api.HMACDispatcher;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

public class HomePresenter extends Presenter<HomePresenter.MyView, HomePresenter.MyProxy> implements HomeUiHandlers {
    interface MyView extends View, HasUiHandlers<HomeUiHandlers> {
        public void setData(String data);
    }

    @NameToken(NameTokens.home)
    @ProxyCodeSplit
    public interface MyProxy extends ProxyPlace<HomePresenter> {
    }

    interface MyEventBinder extends EventBinder<HomePresenter> {
    }

    private final MyEventBinder eventBinder = GWT.create(MyEventBinder.class);
    APIService apiService;

    @Inject Model model;
    @Inject HMACDispatcher hmacDispatcher;

    @Inject
    public HomePresenter(EventBus eventBus, MyView view, MyProxy proxy, APIService apiService) {
        super(eventBus, view, proxy, RevealType.Root);
        this.apiService = apiService;

        eventBinder.bindEventHandlers(this, eventBus);
        getView().setUiHandlers(this);
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, LayoutPresenter.SLOT_Content, this);
    }

    @Override
    protected void onBind() {
        super.onBind();
    }

    @Override
    protected void onReset() {
        super.onReset();
    }

    @Override
    public void onGetDataClick() {
        Resource resource = new Resource("api");
        ((RestServiceProxy) apiService).setResource(resource);

        apiService.getData(new MethodCallback<APIService.GetDataResult>() {
            @Override
            public void onSuccess(Method method, APIService.GetDataResult response) {
                getEventBus().fireEvent(new GetDataResultReceivedEvent(response.data));
            }

            @Override
            public void onFailure(Method method, Throwable exception) {
                getEventBus().fireEvent(new GetDataResultReceivedEvent("GetData Error: " + exception.getMessage()));
            }
        });
    }

    @EventHandler
    void onGetDataResultReceived(GetDataResultReceivedEvent event) {
        getView().setData(event.getData());
    }

}
