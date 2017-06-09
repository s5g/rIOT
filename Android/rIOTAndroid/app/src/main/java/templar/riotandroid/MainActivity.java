package templar.riotandroid;

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

public class MainActivity extends AppCompatActivity {

    private List<String> mExpandableListHouse;
    private HashMap<String, List<String>> mExpandableListDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);
        //initializeRecyclerView();
        initializeExpandedListView();
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

    }

    private void initializeRecyclerView(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = (RecyclerView) this.findViewById(R.id.main_recyclerview);
        HouseAdapter houseAdapter = new HouseAdapter(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(houseAdapter);
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
