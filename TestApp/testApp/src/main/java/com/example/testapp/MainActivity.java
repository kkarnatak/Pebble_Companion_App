package com.example.testapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends Activity {
    public static final String HOST = "192.168.84.216";
    public static final int TCP_PORT = 2001;
    public static int number = 0;
    PebbleDictionary data;
    private static final int DELAY_DURATION = 1000;

    AlarmMessageClient objAlarm;
	Button notifyButton;
	//int number=0;
	 // the tuple key corresponding to the weather icon displayed on the watch
   // private static final int ICON_KEY = 0;
    // the tuple key corresponding to the temperature displayed on the watch
    private static final int TEMP_KEY = 0;
    // This UUID identifies the weather app
    //private static final UUID PEBBLE_UUID = UUID.fromString("28AF3DC7-E40D-490F-BEF2-29548C8B0600");
    private static final UUID PEBBLE_UUID = UUID.fromString("6ccf4d3b-17ac-403f-88ad-fca7d85ba2f1");

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         notifyButton = (Button)findViewById(R.id.Not1);
         notifyButton.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//notifyButton.setText("This is a test log");
                    data = new PebbleDictionary();
			       // data.addUint8(ICON_KEY, (byte) 0);

                    new ExtendedThread().execute();
                notifyButton.setEnabled(false);
			        //data.addString(TEMP_KEY, String.format("%d\u00B0C", ++number));
                    //data.addString(TEMP_KEY, String.format("Notif Count: %d", ++number));
            //    number = returnValue();
                /*
                data.addString(TEMP_KEY, String.format("Notif Count: %d", number ));

			        // Send the assembled dictionary to the weather watch-app; this is a no-op if the app isn't running or is not
			        // installed
			        PebbleKit.sendDataToPebble(getApplicationContext(), PEBBLE_UUID, data);
			        Toast.makeText(getApplicationContext(), "Notification number " + number ,Toast.LENGTH_LONG).show();
                 Log.d(this.getClass().getName(),"Notification number " + number);
                 */
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void sendAlertToPebble() {
        final Intent i = new Intent("com.getpebble.action.SEND_NOTIFICATION");

        final Map data = new HashMap();
        data.put("title", "Factory Alert");
        data.put("body", "Error reported in the module.");
        final JSONObject jsonData = new JSONObject(data);
        final String notificationData = new JSONArray().put(jsonData).toString();

        i.putExtra("messageType", "PEBBLE_ALERT");
        i.putExtra("sender", "FactoryWatch");
        i.putExtra("notificationData", notificationData);

        Log.d(this.getClass().getName(), "About to send a modal alert to Pebble: " + notificationData);
        sendBroadcast(i);
    }


    public class ExtendedThread extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            String eventArray[];
            String eventPriority="";
            String event="";
            int number = 0;
            try {
                Socket s = new Socket(HOST, TCP_PORT);
                SocketWrapper wrapper = new SocketWrapper(s);

                // Register
                String message = "<subscription client= \"AlarmMessageClient\" ><topic name=\"AlarmMessage\"/></subscription>";
                s.getOutputStream().write(message.getBytes());
                s.getOutputStream().flush();
                System.out.println("one");

                // Wait for mSystem.out.println("KKLOG : Inside if condition");essages
                boolean running = true;
                while (running) {
                    eventArray = objAlarm.getTextEvent(wrapper);
                    eventPriority = eventArray[0];
                    event = eventArray[2];

                    System.out.println("Event received: " + eventPriority + " " +  event);
                    number++;
                    //data.addString(TEMP_KEY, String.format("Notif Count: %d", number ));
                    data.addString(TEMP_KEY, String.format("%s%s", eventPriority, event ));
                    if(eventPriority.contains("T"))
                    {
                        sendAlertToPebble();
                    }
                    PebbleKit.sendDataToPebble(getApplicationContext(), PEBBLE_UUID, data);
                    Log.d(this.getClass().getName(),"KKLOG: " + "Event: " + eventPriority + event );
                    //
                    final int finalNumber = number;
                    MainActivity.this.runOnUiThread( new Runnable() {
                        @Override
                        public void run() {
                            //
                            notifyButton.setText(""+ finalNumber);
                            //Toast.makeText(getApplicationContext(), "Notification number ", Toast.LENGTH_SHORT).show();
                        }
                    });

                    Log.d(this.getClass().getName(), "Notification number " + number);

                    Thread.sleep(DELAY_DURATION);
                }

                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {


            // Send the assembled dictionary to the weather watch-app; this is a no-op if the app isn't running or is not
            // installed
            Log.d(this.getClass().getName(), "Inside onpostExe");

            super.onPostExecute(o);
        }
    }
}
