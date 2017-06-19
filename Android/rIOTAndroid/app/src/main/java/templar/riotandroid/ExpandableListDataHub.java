package templar.riotandroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import templar.riotandroid.values.SensorFlowValues;

/**
 * Created by Devin on 6/8/2017.
 */

public class ExpandableListDataHub {

    public static HashMap<String, List<String>> getData(){
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> kitchen = new ArrayList<String>();
        kitchen = SensorFlowValues.getSensorList();

        expandableListDetail.put("Kitchen Sensors", kitchen);
        return expandableListDetail;
    }
}
