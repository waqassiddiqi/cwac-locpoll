/***
	Copyright (c) 2010 CommonsWare, LLC
	
	Licensed under the Apache License, Version 2.0 (the "License"); you may
	not use this file except in compliance with the License. You may obtain
	a copy of the License at
		http://www.apache.org/licenses/LICENSE-2.0
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
*/

package com.commonsware.cwac.locpoll.demo;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.commonsware.cwac.locpoll.LocationPoller;
import com.commonsware.cwac.locpoll.LocationPollerParameter;
import com.commonsware.cwac.locpoll.LocationPollerResult;

public class LocationPollerDemo extends Activity {
	public static final String TAG = "LocationPollerDemo";
	private static final int PERIOD = 20000; // 1 minutes
	private PendingIntent pi = null;
	private AlarmManager mgr = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
			
		mgr=(AlarmManager)getSystemService(ALARM_SERVICE);
		
		Intent i=new Intent(this, LocationPoller.class);
		
		Bundle bundle = new Bundle();
		LocationPollerParameter parameter = new LocationPollerParameter(bundle);
		parameter.setIntentToBroadcastOnCompletion(new Intent(this, LocationReceiver.class));
		
		// try GPS and fall back to NETWORK_PROVIDER
		parameter.setProviders(new String[] {LocationManager.GPS_PROVIDER, LocationManager.NETWORK_PROVIDER});
		parameter.setTimeout(60000);
		i.putExtras(bundle);
				
		pi = PendingIntent.getBroadcast(this, 0, i, 0);
		mgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), PERIOD, pi);
		
		Toast.makeText(this, "Location polling every 1 minute", Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		IntentFilter filter = new IntentFilter(LocationPollerParameter.INTENT_TO_BROADCAST_ON_COMPLETION_KEY);
		filter.setPriority(1); //Set highest priority so it will receive the broadcast before other subscribers
		registerReceiver(locationReceiver, filter);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		unregisterReceiver(locationReceiver);
	}
	
	public void omgPleaseStop(View v) {
		mgr.cancel(pi);
		finish();
	}
	
	private BroadcastReceiver locationReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			
			Log.d(TAG, "called");
			
			Bundle b = intent.getExtras();
	        
	        LocationPollerResult locationResult = new LocationPollerResult(b);
	        Location currentLocation = locationResult.getLocation();
	        if(currentLocation == null) {
	        	currentLocation = locationResult.getLastKnownLocation();
	        	
	        	Log.d(TAG, "Location received: " + currentLocation.toString());
	        }
	        
	        //Abort broadcast so no location updates will be sent to other receivers
	        abortBroadcast();
		}
	};
}
