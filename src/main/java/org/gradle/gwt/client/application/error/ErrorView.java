package org.gradle.gwt.client.application.error;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public class ErrorView extends ViewImpl implements ErrorPresenter.MyView {
	interface Binder extends UiBinder<Widget, ErrorView> {
	}

	@Inject
	ErrorView(Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));
	}
}
