package templar.riotandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import templar.riotandroid.sync.SensorFlowSyncIntentService;

public class MainActivity extends AppCompatActivity {

    private List<String> mExpandableListHouse;
    private HashMap<String, List<String>> mExpandableListDetail;
    private Intent mSyncIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);
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

    private void refresh(){
        startService(mSyncIntent);
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
