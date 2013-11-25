package org.gradle.gwt.client.application.error;

import org.gradle.gwt.client.place.NameTokens;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

public class ErrorPresenter extends
		Presenter<ErrorPresenter.MyView, ErrorPresenter.MyProxy> {
	interface MyView extends View {
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_Error = new Type<RevealContentHandler<?>>();

	@NameToken(NameTokens.error)
	@ProxyCodeSplit
	public interface MyProxy extends ProxyPlace<ErrorPresenter> {
	}

	@Inject
	public ErrorPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy, RevealType.Root);

	}

}
