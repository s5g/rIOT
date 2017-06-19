package templar.riotandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import templar.riotandroid.values.SensorFlowValues;

/**
 * Created by Devin on 6/8/2017.
 */

public class HouseExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<String> mExpandableListHouse;
    private HashMap<String, List<String>> mExpandableListDetail;

    public HouseExpandableListAdapter(Context c, List<String> eLH,
                                       HashMap<String, List<String>> eLD){
        mContext = c;
        mExpandableListHouse = eLH;
        mExpandableListDetail = eLD;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition){
        return mExpandableListDetail
                .get(mExpandableListHouse.get(listPosition)).get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition){
        return expandedListPosition;
    }

    /*
    expandedListText is essentially the sensorName
     */
    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent){
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        final String expandedListData = SensorFlowValues
                .getSensorData()
                .get(expandedListText);
        if(convertView == null){
            LayoutInflater layoutInflater =
                    (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.house_list_item, null);
        }
        TextView expandedListSensorTV = (TextView) convertView.findViewById(R.id.expandedListSensor);
        TextView expandedListDataTV = (TextView) convertView.findViewById(R.id.expandedListData);
        expandedListSensorTV.setText(expandedListText);
        expandedListDataTV.setText(expandedListData);
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition){
        return mExpandableListDetail.get(mExpandableListHouse.get(listPosition)).size();
    }

    @Override
    public Object getGroup(int listPosition){
        return mExpandableListHouse.get(listPosition);
    }

    @Override
    public int getGroupCount(){
        return mExpandableListHouse.size();
    }

    @Override
    public long getGroupId(int listPosition){
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent){
        String listTitle = (String) getGroup(listPosition);
        if(convertView == null){
            LayoutInflater layoutInflater =
                    (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.house_list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.houseLocation);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds(){
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition){
        return true;
    }
}
