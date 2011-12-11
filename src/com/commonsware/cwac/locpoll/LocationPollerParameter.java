package com.commonsware.cwac.locpoll;

import java.util.Vector;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

public class LocationPollerParameter implements Parcelable {

	public static String KEY = "LocationPollerParameter";
	
	private Intent mIntentToBroadcastOnCompletion;
	private long mTimeout;
	private Vector<String> mProviders;

	private LocationPollerParameter(Parcel in) {
		readFromParcel(in);
	}
	
	public LocationPollerParameter() {
		this.mProviders = new Vector<String>();
	}
	
	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(mIntentToBroadcastOnCompletion, flags);
		dest.writeLong(mTimeout);
		dest.writeArray(mProviders.toArray());
	}

	private void readFromParcel(Parcel in) {
		mIntentToBroadcastOnCompletion = (Intent)in.readParcelable(null);
		mTimeout = in.readLong();
		Object[] objectArray = in.readArray(null);
		// TODO: better way to do this?
		mProviders = new Vector<String>();
		for (Object object : objectArray) {
			mProviders.add((String) object);
		}
	}
	public static final Parcelable.Creator<LocationPollerParameter> CREATOR = new Parcelable.Creator<LocationPollerParameter>() {
		public LocationPollerParameter createFromParcel(Parcel in) {
			return new LocationPollerParameter(in);
		}

		public LocationPollerParameter[] newArray(int size) {
			return new LocationPollerParameter[size];
		}
	};

	public Intent getIntentToBroadcastOnCompletion() {
		return mIntentToBroadcastOnCompletion;
	}

	public void setIntentToBroadcastOnCompletion(
			Intent intentToBroadcastOnCompletion) {
		this.mIntentToBroadcastOnCompletion = intentToBroadcastOnCompletion;
	}

	public long getTimeout() {
		return mTimeout;
	}

	public void setTimeout(long timeout) {
		this.mTimeout = timeout;
	}

	public Vector<String> getProviders() {
		return mProviders;
	}

	public void setProviders(Vector<String> providers) {
		this.mProviders = providers;
	}

	public void addProvider(String provider) {
		this.mProviders.add(provider);
	}
}
