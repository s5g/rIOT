package templar.riotandroid.sync;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import templar.riotandroid.R;
import templar.riotandroid.values.SensorFlowValues;

/**
 * Created by Devin on 6/16/2017.
 */

public class SensorFlowSyncIntentService extends IntentService {
    private static final String TAG = SensorFlowSyncIntentService.class.getSimpleName();

    protected SensorFlowSyncIntentService(){
        super("SensorFlowSyncIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent){
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        String getSensorsUrl = getString(R.string.endless_shadow_base)
                + getString(R.string.endless_shadow_get_sensors);
        String getDataUrl = getString(R.string.endless_shadow_base)
                + getString(R.string.endless_shadow_get_data);

        final StringRequest stringRequest2 = new StringRequest(Request.Method.GET,
                getDataUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "getDataResponse!");
                handleData(response);
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Log.e(TAG, "Error at getDataUrl: " + error);
            }
        });

        StringRequest stringRequest1 = new StringRequest(Request.Method.GET,
                getSensorsUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "getSensorResponse!");
                handleSensors(response);
                requestQueue.add(stringRequest2);
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Log.e(TAG, "Error at getSensorUrl: " + error);
            }
        });

        requestQueue.add(stringRequest1);
    }

    /*
    Takes the response - which is a JSON string - and converts it to a Java JSON object.
    Parses the JSON Object to get the name of all unique sensors in endless-shadow and adds
    the new sensors to our local SensorList
     */
    private void handleSensors(String response){
        try{
            JSONObject json_result = new JSONObject(response);
            JSONArray jsonArray_sensors = json_result.getJSONArray("rows");
            JSONObject json_sensor;
            String sensorName;
            int sensorCount = json_result.getInt("rowCount");

            for(int i = 0; i < sensorCount; i++){
                json_sensor = jsonArray_sensors.getJSONObject(i);
                sensorName = json_sensor.getString("sensorname");
                Log.e(TAG, sensorName);
                if(!SensorFlowValues.getSensorList().contains(sensorName)){
                    SensorFlowValues
                            .getSensorList()
                            .add(sensorName);
                }
            }

            //Log.e(TAG, "sensor length is " + json_result.length());
            //Log.e(TAG, "rowCount is " + sensorCount);
        }catch(org.json.JSONException e){
            Log.e(TAG, "JSONException with string: " + response);
        }
    }

    private void handleData(String response){
        try{
            JSONObject json_result = new JSONObject(response);
            JSONArray jsonArray_data = json_result.getJSONArray("rows");
            JSONObject json_sensor;
            String sensorName;
            String sensorData;
            int sensorCount = json_result.getInt("rowCount");
            List<String> sensorList = SensorFlowValues.getSensorList();
            HashMap<String, String> sensorDataHash = SensorFlowValues.getSensorData();


            for(int i = 0; i < sensorCount; i++){
                json_sensor = jsonArray_data.getJSONObject(i);
                sensorName = json_sensor.getString("sensorname");
                sensorData = json_sensor.getString("sensordata");
                Log.e(TAG, "<sensorname: " + sensorName + ">, <sensordata: " + sensorData + ">");

                if(!sensorList.contains(sensorName)){
                    Log.e(TAG, sensorName + " was not in 'sensorName' list... has been added");
                    sensorList.add(sensorName);
                }
                sensorDataHash.put(sensorName, sensorData);
            }
        }catch(org.json.JSONException e){
            Log.e(TAG, "JSONException with string: " + response);
        }
    }

}
