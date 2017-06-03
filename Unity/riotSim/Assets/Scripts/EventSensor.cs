using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Networking;

public class EventSensor : MonoBehaviour {

	public string houseName;
	public string sensorName;

	public Material onMaterial;
	public Material offMaterial;

	private MeshRenderer meshRenderer;
	private int frameCnt;

	private bool sensorValue;

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
		Debug.LogWarningFormat ("EventSensor.Set: {0}={1}", sensorName, sensorValue);
		meshRenderer.material = sensorValue ? onMaterial : offMaterial;
		StartCoroutine (UpdateCloud ());
	}

	private IEnumerator UpdateCloud() {
		string url = HouseManager.GetBaseUrl() + houseName + "/event/sensornames/" + sensorName + "/sensordatas/" + sensorValue;
		Debug.LogFormat ("EventSensor.UpdateCloud: {0}", url);

		UnityWebRequest getRequest = UnityWebRequest.Get(url);
		yield return getRequest.Send ();

		if (getRequest.isError) {
			Debug.LogError (getRequest.error);
		} else {
			Debug.Log ("EventSensor updated");
		}
	}
}