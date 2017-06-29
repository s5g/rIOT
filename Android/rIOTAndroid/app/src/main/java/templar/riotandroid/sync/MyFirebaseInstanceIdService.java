package templar.riotandroid.sync;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import templar.riotandroid.R;

/**
 * Created by Devin on 6/29/2017.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstanceIdService.class.getSimpleName();

    @Override
    public void onTokenRefresh(){
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.firebase_token), refreshedToken);
        editor.apply();
        Log.e(TAG, "firebase_token sharedpref added");

        Intent intent = new Intent(this, FirebaseTokenSyncIntentService.class);
        startActivity(intent);
    }
}
