package templar.riotandroid.framework;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import templar.riotandroid.ExpandableListDataHub;
import templar.riotandroid.HouseExpandableListAdapter;
import templar.riotandroid.R;
import templar.riotandroid.sync.SensorFlowSyncIntentService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private List<String> mExpandableListHouse;
    private HashMap<String, List<String>> mExpandableListDetail;

    private static String ecosystemName;
    private Intent mSyncIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!hasEcosystem()){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_main);
        initializeAppBar();
        initializeExpandedListView();

        mSyncIntent = new Intent(this, SensorFlowSyncIntentService.class);
        startService(mSyncIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menu_refresh:
                refresh();
                break;
            default:
                break;
        }
        return true;
    }

    private void refresh() {
        startService(mSyncIntent);
    }

    private boolean hasEcosystem(){
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        return !(sharedPreferences
                .getString(getString(R.string.ecosystem_name), "null")
                .equals("null")
        );
    }

    private void setEcosystemName(){
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        ecosystemName = sharedPreferences.getString(getString(R.string.ecosystem_name), "null");
        Log.e(TAG, ecosystemName);
    }

    private void initializeAppBar(){
        setEcosystemName();
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle("Hello");
        setSupportActionBar(toolbar);
    }

    private void initializeExpandedListView(){
        ExpandableListView expandableListView =
                (ExpandableListView) findViewById(R.id.main_expandablelistview);
        mExpandableListDetail = ExpandableListDataHub.getData();
        mExpandableListHouse = new ArrayList<String> (mExpandableListDetail.keySet());
        ExpandableListAdapter expandableListAdapter =
                new HouseExpandableListAdapter(this, mExpandableListHouse, mExpandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){
            @Override
            public boolean onChildClick(ExpandableListView parent,
                                        View v, int groupPosition, int childPosition, long id){
                Toast.makeText(getApplicationContext(), "Child clicked!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}
