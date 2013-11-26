package org.gradle.gwt.client.application.login;

import javax.inject.Inject;

import com.github.gwtbootstrap.client.ui.SubmitButton;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class LoginView extends ViewWithUiHandlers<LoginUiHandlers> implements LoginPresenter.MyView {
    interface Binder extends UiBinder<Widget, LoginView> {
    }

    @UiField SubmitButton loginButton;
    @UiField TextBox usernameTextBox;

    @Inject
    LoginView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("loginButton")
    void onClicked(ClickEvent event) {
        if (getUiHandlers() != null) {
            getUiHandlers().onLoginClicked();
        }
    }

    @Override
    public String getUsername() {
        return usernameTextBox.getText();
    }
}
