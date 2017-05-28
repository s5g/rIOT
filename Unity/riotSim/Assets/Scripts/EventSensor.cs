using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Networking;

public class EventSensor : MonoBehaviour {
	public string baseURL = "http://127.0.0.1:8080/homeids/";

	public string houseName;
	public string sensorName;

	public Material onMaterial;
	public Material offMaterial;

	private MeshRenderer meshRenderer;
	private bool sensorValue;
	private int frameCnt;

	private UnityWebRequest sensorPost;

	// Use this for initialization
	void Start () {
		sensorValue = false;
		frameCnt = 0;

		meshRenderer = gameObject.GetComponent <MeshRenderer>();
		meshRenderer.material = sensorValue ? onMaterial : offMaterial;
		Debug.LogWarningFormat ("material={0}", meshRenderer.materials[0].ToString());
	}

	// Update is called once per frame
	void Update () {
		if (frameCnt % 100 == 0) {
			//Debug.LogFormat ("<{0}, {1}, {2}>", houseName, sensorName, sensorValue);
		}
		frameCnt++;
	}

	public void Set() {
		sensorValue = !sensorValue;
		Debug.LogWarningFormat ("set {0}={1}", sensorName, sensorValue);
		meshRenderer.material = sensorValue ? onMaterial : offMaterial;
		StartCoroutine (UpdateCloud ());
	}

	private IEnumerator UpdateCloud() {

		sensorPost = UnityWebRequest.Get(baseURL + houseName + "/event/sensornames/" + sensorName + "/" + sensorValue);
		Debug.LogFormat ("ToCloud: event <{0}, {1}, {2}>", houseName, sensorName, sensorValue);
		yield return sensorPost.Send ();

		if (sensorPost.isError) {
			Debug.Log (sensorPost.error);
		} else {
			Debug.Log ("EventSensor posted");
		}
	}
}