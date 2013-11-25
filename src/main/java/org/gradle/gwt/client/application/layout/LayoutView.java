package org.gradle.gwt.client.application.layout;

import javax.inject.Inject;

import com.github.gwtbootstrap.client.ui.Brand;
import com.github.gwtbootstrap.client.ui.Label;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public class LayoutView extends ViewImpl implements LayoutPresenter.MyView {
	interface Binder extends UiBinder<Widget, LayoutView> {
	}

	@UiField HTMLPanel contentPanel;
	@UiField Brand brand;
	@UiField Label data;

	
	@Inject
	LayoutView(Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void setInSlot(Object slot, IsWidget content) {
		if (slot == LayoutPresenter.SLOT_Content) {			
			contentPanel.clear();			
			if (content != null)
			{
				contentPanel.add(content);
			}
		} else 		
		{
			super.setInSlot(slot, content);
		}
	}

	@Override
	public void setBrand(String text) {
		brand.setText(text);
	}

	@Override
	public void setData(String text) {
		data.setText(text);		
	}
}
