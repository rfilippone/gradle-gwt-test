package org.gradle.gwt.client.application.home;

import javax.inject.Inject;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class HomeView extends ViewWithUiHandlers<HomeUiHandlers> implements
		HomePresenter.MyView {
	interface Binder extends UiBinder<Widget, HomeView> {
	}

	@UiField TextBox text;
	@UiField Button logInButton;
	@UiField Button getDataButton;
	
	
	@Inject
	HomeView(Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public String getText() {
		return text.getText();
	}
	
	@UiHandler("logInButton")
	void onlogInButtonClicked(ClickEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onLogInClick();
		}
	}	

	@UiHandler("getDataButton")
	void onSgetDataButtonClicked(ClickEvent event) {
		if (getUiHandlers() != null) {
			getUiHandlers().onGetDataClick();
		}
	}	
}
