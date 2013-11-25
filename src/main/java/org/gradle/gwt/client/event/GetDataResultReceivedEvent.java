package org.gradle.gwt.client.event;

import com.google.web.bindery.event.shared.binder.GenericEvent;

public class GetDataResultReceivedEvent extends GenericEvent {
	private final String data;

	public GetDataResultReceivedEvent(String data) {
		this.data = data;
	}

	public String getData() {
		return data;
	}
}