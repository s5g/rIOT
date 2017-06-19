package templar.riotandroid.values;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Devin on 6/16/2017.
 */

public class SensorFlowValues {

    private static List<String> sensorList = new ArrayList<>();
    private static HashMap<String, String> sensorData = new HashMap<>();

    public static List<String> getSensorList(){
        return sensorList;
    }

    public static HashMap<String, String> getSensorData() { return sensorData; }
}
