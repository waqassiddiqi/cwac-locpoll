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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SerializeHelper {

	/**
	 * Externalize object that implements SimpleSerializable
	 * @param objectToExternalize object to externalize
	 * @return byte array representing object
	 */
	public static byte[] externalize(SimpleSerializable objectToExternalize) {

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(
				byteArrayOutputStream);
		try {
			objectToExternalize.writeToStream(dataOutputStream);
		} catch (IOException e) {
			// IOException should only occur in an OOM situation since we«re
			// writing to memory. App should not proceed.
			throw new RuntimeException(e);
		}

		return byteArrayOutputStream.toByteArray();

	}
	
	/**
	 * Internalize object that implements SimpleSerializable
	 * @param objectToInternalize object to be internalized
	 * @param byteArray byte array representing object
	 * @throws IOException
	 */
	public static void internalize(SimpleSerializable objectToInternalize,
			byte[] byteArray) throws IOException {
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				byteArray);
		DataInputStream dataInputStream = new DataInputStream(
				byteArrayInputStream);
		objectToInternalize.readFromStream(dataInputStream);
	}
}
