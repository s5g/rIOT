using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Networking;

public class StreamSensor : MonoBehaviour {

	public string houseName;
	public string sensorName;
	private const int sensorType = 1;

	[Tooltip("Sample period in seconds.")]
	public int samplePeriod = 10;

	private DateTime epochStart;
	private long lastPostTime;
	private int frameCnt;

	private MeshRenderer meshRenderer;
	private Gradient colorMap;

	private float sensorValue;
	private float minValue = 0;
	private float maxValue = 100;

	// Use this for initialization
	void Start () {
		epochStart = new DateTime(1970, 1, 1, 0, 0, 0, DateTimeKind.Utc);
		lastPostTime = 0;
		frameCnt = 0;

		meshRenderer = gameObject.GetComponent <MeshRenderer>();

		// setup a color gradient
		colorMap = new Gradient();
		GradientColorKey[] gck;
		GradientAlphaKey[] gak;
		gck = new GradientColorKey[2];
		gck[0].color = Color.red;
		gck[0].time = 0.0F;
		gck[1].color = Color.blue;
		gck[1].time = 1.0F;
		gak = new GradientAlphaKey[2];
		gak[0].alpha = 1.0F;
		gak[0].time = 0.0F;
		gak[1].alpha = 0.0F;
		gak[1].time = 1.0F;
		colorMap.SetKeys(gck, gak);

		// set initial value of sensor
		Set(40);
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
		sensorValue = newSensorValue;
		//Debug.LogWarningFormat ("StreamSensor.Set {0}={1}", sensorName, sensorValue.ToString());

		float gradValue = (sensorValue - minValue) / (maxValue - minValue);

		meshRenderer.material.color = colorMap.Evaluate(gradValue);
	}

	private IEnumerator UpdateCloud() {
		string url = HouseManager.GetBaseUrl();
		Debug.LogFormat ("StreamSensor.UpdateCloud: {0}", url);

		WWWForm form = new WWWForm ();
		form.AddField ("houseName", houseName);
		form.AddField ("sensorName", sensorName);
		form.AddField ("sensorData", sensorValue.ToString());
		form.AddField ("sensorType", sensorType);
		using (UnityWebRequest postRequest = 
			UnityWebRequest.Post(url, form)) {
			yield return postRequest.Send ();
			if (postRequest.isError) {
				Debug.LogError (postRequest.error);
			} else {
				Debug.Log ("StreamSensor updated");
			}
		}
	}

	private long getCurrentSeconds() {
		return (long) (DateTime.UtcNow - epochStart).TotalSeconds;
	}
}