using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Networking;

public class EventSensor : MonoBehaviour {

	public string houseName;
	public string sensorName;
	private const int sensorType = 0;

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
		string url = HouseManager.GetBaseUrl();
		Debug.LogFormat ("EventSensor.UpdateCloud: {0}", url);

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
				Debug.Log ("EventSensor updated");
			}
		}
	}
}