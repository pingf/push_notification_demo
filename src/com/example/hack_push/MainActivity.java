package com.example.hack_push;

import java.io.IOException;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {

    Context context;
    TextView my_message;
    GcmBroadcastReceiver receiver;
    static final String TAG = "hack push";
    String SENDER_ID = "**********"; 
    GoogleCloudMessaging gcm;
    String regid="regid_test";
    static final String DISPLAY_MESSAGE_ACTION =
            "com.example.hack_push.DISPLAY_MESSAGE";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 registerReceiver(mHandleMessageReceiver, new IntentFilter(
	                DISPLAY_MESSAGE_ACTION));
		my_message = (TextView) findViewById(R.id.my_message);

		context = getApplicationContext();
		
		gcm = GoogleCloudMessaging.getInstance(this);
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(context);
					}
					regid = gcm.register(SENDER_ID);
					Log.i(TAG, "regid :" + regid);
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
					Log.i(TAG, msg);
				}
				return "registration id: " + regid + "\n";
			}

			@Override
			protected void onPostExecute(String msg) {
				my_message.append(msg);
			}
		}.execute(null, null, null);
            
	}
	
    private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getExtras().getString("message");
            my_message.append(message + "\n");           
        }
    };
	
	
	
}
