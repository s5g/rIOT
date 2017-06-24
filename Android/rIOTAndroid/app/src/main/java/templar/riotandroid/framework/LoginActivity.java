package templar.riotandroid.framework;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import templar.riotandroid.R;

/**
 * Created by Devin on 6/22/2017.
 */

public class LoginActivity extends AppCompatActivity {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;

        initializeInterface();
    }

    private void initializeInterface(){
        final EditText login_ecosystem = (EditText) findViewById(R.id.login_ecosystem);
        final EditText login_pass1 = (EditText) findViewById(R.id.login_pass1);
        final EditText login_pass2 = (EditText) findViewById(R.id.login_pass2);
        Button login_button = (Button) findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ecosystem_string = login_ecosystem.getText().toString();
                String pass1_string = login_pass1.getText().toString();
                String pass2_string = login_pass2.getText().toString();

                if(ecosystem_string.length() == 0){
                    Toast.makeText(mContext, getString(R.string.missing_ecosystem), Toast.LENGTH_SHORT).show();
                }else if(pass1_string.length() == 0){
                    Toast.makeText(mContext, getString(R.string.missing_pass1), Toast.LENGTH_SHORT).show();
                }else if(pass2_string.length() == 0){
                    Toast.makeText(mContext, getString(R.string.missing_pass2), Toast.LENGTH_SHORT).show();
                }else if(!pass1_string.equals(pass2_string)){
                    Toast.makeText(mContext, getString(R.string.nonmatching_pass), Toast.LENGTH_SHORT).show();
                }else{
                    SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                            getString(R.string.preference_file_key),
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(getString(R.string.ecosystem_name), ecosystem_string);
                    editor.apply();
                    finish();
                }
            }
        });
    }
}
