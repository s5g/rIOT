using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Networking;

public class StreamSensor : MonoBehaviour {

	public string houseName;
	public string sensorName;

	[Tooltip("Sample period in seconds.")]
	public int samplePeriod = 10;

	private DateTime epochStart;
	private long lastPostTime;
	private int frameCnt;

	private string sensorValue;

	// Use this for initialization
	void Start () {
		epochStart = new DateTime(1970, 1, 1, 0, 0, 0, DateTimeKind.Utc);
		lastPostTime = 0;
		frameCnt = 0;
		sensorValue = "40";
	}

	// Update is called once per frame
	void Update () {
		if (frameCnt % 100 == 0) {
			//Debug.LogFormat ("<{0}, {1}, {2}>", houseName, sensorName, sampleValue);
		}
		frameCnt++;

		long now = getCurrentSeconds ();
		if (now - lastPostTime > samplePeriod) {
			lastPostTime = now;
			StartCoroutine (UpdateCloud ());
		}
	}

	public void Set(float newSensorValue) {
		sensorValue = newSensorValue.ToString();
		Debug.LogWarningFormat ("StreamSensor.Set {0}={1}", sensorName, sensorValue);
	}

	private IEnumerator UpdateCloud() {
		string url = HouseManager.GetBaseUrl() + houseName + "/stream/sensornames/" + sensorName + "/" + sensorValue;
		Debug.LogFormat ("StreamSensor.UpdateCloud: {0}", url);

		UnityWebRequest getRequest = UnityWebRequest.Get(url);
		yield return getRequest.Send ();

		if (getRequest.isError) {
			Debug.LogError (getRequest.error);
		} else {
			Debug.Log ("StreamSensor updated");
		}
	}

	private long getCurrentSeconds() {
		return (long) (DateTime.UtcNow - epochStart).TotalSeconds;
	}
}