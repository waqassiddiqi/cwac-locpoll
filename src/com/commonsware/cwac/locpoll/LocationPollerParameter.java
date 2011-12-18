/**
 * 
 *  Copyright 2011 Birkett Enterprise Ltd
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * 
 */

package com.commonsware.cwac.locpoll;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

public class LocationPollerParameter implements SimpleSerializable {

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
	
	public void writeToStream(DataOutputStream dataOutputStream) throws IOException {
		dataOutputStream.writeLong(timeout);
		dataOutputStream.writeInt(providers.size());
		for (String provider : providers) {
			dataOutputStream.writeUTF(provider);
		}
	}
	
	public void readFromStream(DataInputStream dataInputStream) throws IOException {
		timeout = dataInputStream.readLong();
		int count = dataInputStream.readInt();
		providers.removeAllElements();
		for (int i = 0; i < count; i++) {
			providers.add(dataInputStream.readUTF());
		}
	}

}
