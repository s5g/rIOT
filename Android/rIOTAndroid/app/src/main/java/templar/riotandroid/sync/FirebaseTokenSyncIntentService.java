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
    protected void onHandleIntent(Intent intent){
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        String tokenUpdateUrl = getString(R.string.endless_shadow_base) +
                getString(R.string.endless_shadow_create_token);

        final StringRequest stringRequest2 = new StringRequest(Request.Method.POST,
                tokenUpdateUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "token successfully added on endless-shadow");
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Log.e(TAG, "Error at getDataUrl: " + error);
            }
        });
    }

}
