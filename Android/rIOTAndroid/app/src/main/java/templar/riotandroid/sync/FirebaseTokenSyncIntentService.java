package templar.riotandroid.sync;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import templar.riotandroid.R;

/**
 * Created by Devin on 6/29/2017.
 */

public class FirebaseTokenSyncIntentService extends IntentService {
    private static final String TAG = FirebaseTokenSyncIntentService.class.getSimpleName();

    protected FirebaseTokenSyncIntentService(){
        super("FirebaseTokenSyncIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        final String firebase_token = sharedPreferences.getString(getString(R.string.firebase_token), "null");
        if (firebase_token.equals("null")) {
            Log.e(TAG, "Null firebase token");

        } else {
            JSONObject params = new JSONObject();
            try{
                params.put("firebaseToken", firebase_token);
            }catch(JSONException e){
                e.printStackTrace();
            }

            final RequestQueue requestQueue = Volley.newRequestQueue(this);
            String tokenUpdateUrl = getString(R.string.endless_shadow_base) +
                    getString(R.string.endless_shadow_create_token);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    tokenUpdateUrl, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e(TAG, "token successfully added on endless-shadow");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Error at tokensync: " + error);
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }
            };
            requestQueue.add(jsonObjectRequest);
        }
    }
}
