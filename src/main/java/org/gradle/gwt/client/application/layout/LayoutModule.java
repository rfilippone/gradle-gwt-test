package org.gradle.gwt.client.application.layout;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class LayoutModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		bindPresenter(LayoutPresenter.class, LayoutPresenter.MyView.class,
				LayoutView.class, LayoutPresenter.MyProxy.class);
	}
}
