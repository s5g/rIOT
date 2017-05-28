using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Networking;

public class StreamSensor : MonoBehaviour {
	public string baseURL = "http://127.0.0.1:8080/homeids/";

	public string houseName;
	public string sensorName;
	public string sampleValue;

	[Tooltip("Sample period in seconds.")]
	public int samplePeriod = 10;

	private DateTime epochStart;
	private long lastPostTime;
	private int frameCnt;
	private UnityWebRequest sensorPost;

	// Use this for initialization
	void Start () {
		epochStart = new DateTime(1970, 1, 1, 0, 0, 0, DateTimeKind.Utc);
		lastPostTime = 0;
		frameCnt = 0;
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

	public void Set(string newSampleValue) {
		sampleValue = newSampleValue;
		Debug.LogWarningFormat ("set {0}={1}", sensorName, sampleValue);
	}

	private IEnumerator UpdateCloud() {
		// TODO send info to cloud
		sensorPost = UnityWebRequest.Get(baseURL + houseName + "/stream/sensornames/" + sensorName + "/" + sampleValue);
		Debug.LogFormat ("ToCloud: stream <{0}, {1}, {2}>", houseName, sensorName, sampleValue);
		yield return sensorPost.Send ();

		if (sensorPost.isError) {
			Debug.Log (sensorPost.error);
		} else {
			Debug.Log ("StreamSensor posted");
		}
	}

	private long getCurrentSeconds() {
		return (long) (DateTime.UtcNow - epochStart).TotalSeconds;
	}
}