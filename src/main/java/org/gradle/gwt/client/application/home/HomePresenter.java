package org.gradle.gwt.client.application.home;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.RestServiceProxy;
import org.gradle.gwt.client.application.error.ErrorPresenter;
import org.gradle.gwt.client.application.layout.LayoutPresenter;
import org.gradle.gwt.client.event.FirstEvent;
import org.gradle.gwt.client.event.GetDataResultReceivedEvent;
import org.gradle.gwt.client.model.Model;
import org.gradle.gwt.client.place.NameTokens;
import org.gradle.gwt.shared.api.APIService;
import org.gradle.gwt.shared.api.APIService.GetDataResult;
import org.gradle.gwt.shared.api.HMACDispatcher;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;


public class HomePresenter extends
		Presenter<HomePresenter.MyView, HomePresenter.MyProxy> implements
		HomeUiHandlers {
	interface MyView extends View, HasUiHandlers<HomeUiHandlers> {
		public String getText();
	}
	
	@NameToken(NameTokens.home)
	@ProxyCodeSplit
	public interface MyProxy extends ProxyPlace<HomePresenter> {
	}

	@Inject Model model;
	@Inject HMACDispatcher hmacDispatcher;
	
	ErrorPresenter errorPresenter;
	APIService apiService;
	
	@Inject
	public HomePresenter(EventBus eventBus, MyView view, MyProxy proxy, ErrorPresenter errorPresenter, APIService apiService) {
		super(eventBus, view, proxy, RevealType.Root);		
		this.errorPresenter = errorPresenter;
		this.apiService = apiService;
		getView().setUiHandlers(this);
	}
	
	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, LayoutPresenter.SLOT_Content, this);
	}
	
	protected void onBind() {
		super.onBind();
	}

	protected void onReset() {
		super.onReset();
	}

	@Override
	public void onLogInClick() {		
		Resource resource = new Resource("api");		
		((RestServiceProxy)apiService).setResource(resource);		
		
		apiService.login(getView().getText(), new MethodCallback<APIService.LoginResult>() {
			@Override
			public void onSuccess(Method method, APIService.LoginResult response) {
				model.isLoggedIn = true;
				model.ssk = response.ssk;
				model.user = getView().getText();
				getEventBus().fireEvent(new FirstEvent("Il server dice: " + response.text));
			}			
			
			@Override
			public void onFailure(Method method, Throwable exception) {
				getEventBus().fireEvent(new FirstEvent("Errore: " + exception.getMessage()));
			}
		});
	}

	@Override
	public void onGetDataClick() {
		Resource resource = new Resource("api");
		((RestServiceProxy)apiService).setResource(resource);
				
		apiService.getData(new MethodCallback<APIService.GetDataResult>() {
			@Override
			public void onSuccess(Method method, GetDataResult response) {
				getEventBus().fireEvent(new GetDataResultReceivedEvent(response.data));				
			}
			
			@Override
			public void onFailure(Method method, Throwable exception) {
				getEventBus().fireEvent(new GetDataResultReceivedEvent("GetData Error: " + exception.getMessage()));
			}
		});
	}
}
