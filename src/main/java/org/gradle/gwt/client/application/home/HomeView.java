package org.gradle.gwt.client.application.home;

import javax.inject.Inject;

import com.github.gwtbootstrap.client.ui.Label;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class HomeView extends ViewWithUiHandlers<HomeUiHandlers> implements HomePresenter.MyView {
    interface Binder extends UiBinder<Widget, HomeView> {
    }

    @UiField Label dataLabel;

    @Inject
    HomeView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("getDataButton")
    void onGetDataButtonClicked(ClickEvent event) {
        if (getUiHandlers() != null) {
            getUiHandlers().onGetDataClick();
        }
    }

    @Override
    public void setData(String data) {
        dataLabel.setText(data);
    }

}
