package org.gradle.gwt.client.event;

import com.google.web.bindery.event.shared.binder.GenericEvent;

public class FirstEvent extends GenericEvent {
	private final String text;

	public FirstEvent(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}