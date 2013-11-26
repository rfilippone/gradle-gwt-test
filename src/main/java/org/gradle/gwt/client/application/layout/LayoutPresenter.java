package org.gradle.gwt.client.application.layout;

import org.gradle.gwt.client.event.FirstEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

public class LayoutPresenter extends Presenter<LayoutPresenter.MyView, LayoutPresenter.MyProxy> {
    interface MyView extends View {
        void setBrand(String text);
    }

    interface MyEventBinder extends EventBinder<LayoutPresenter> {
    }

    private final MyEventBinder eventBinder = GWT.create(MyEventBinder.class);

    @ContentSlot public static final Type<RevealContentHandler<?>> SLOT_Content = new Type<RevealContentHandler<?>>();

    @ProxyCodeSplit
    @NoGatekeeper
    public interface MyProxy extends Proxy<LayoutPresenter> {
    }

    @Inject
    public LayoutPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
        super(eventBus, view, proxy, RevealType.RootLayout);

        eventBinder.bindEventHandlers(this, eventBus);
    }

    @Override
    protected void onBind() {
        super.onBind();
    }

    @Override
    protected void onReset() {
        super.onReset();
    }

    @EventHandler
    void onFirst(FirstEvent event) {
        getView().setBrand(event.getText());
    }
}
