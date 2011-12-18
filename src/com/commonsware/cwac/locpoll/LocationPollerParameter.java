package com.commonsware.cwac.locpoll;

import java.io.Serializable;
import java.util.Vector;

public class LocationPollerParameter implements Serializable {

	private static final long serialVersionUID = 1L;

	public static String KEY = "com.commonsware.cwac.locpoll.LocationPollerParameter";
	
	private long timeout;
	private Vector<String> providers;
	
	public LocationPollerParameter() {
		this.providers = new Vector<String>();
	}
	
	public int describeContents() {
		return 0;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public Vector<String> getProviders() {
		return providers;
	}

	public void setProviders(Vector<String> providers) {
		this.providers = providers;
	}

	public void addProvider(String provider) {
		this.providers.add(provider);
	}
}
