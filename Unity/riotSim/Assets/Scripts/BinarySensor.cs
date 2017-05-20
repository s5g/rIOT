using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BinarySensor : MonoBehaviour {

	public string houseName;
	public string sensorName;

	public Material onMaterial;
	public Material offMaterial;

	private MeshRenderer meshRenderer;
	private bool state;
	private int frameCnt;

	// Use this for initialization
	void Start () {
		state = false;
		frameCnt = 0;

		meshRenderer = gameObject.GetComponent <MeshRenderer>();
		meshRenderer.material = state ? onMaterial : offMaterial;
		Debug.LogWarningFormat ("material={0}", meshRenderer.materials[0].ToString());
	}
	
	// Update is called once per frame
	void Update () {
		if (frameCnt % 100 == 0) {
			Debug.LogFormat ("<{0}, {1}, {2}>", houseName, sensorName, state);
		}
		frameCnt++;
	}

	public void Set() {
		state = !state;
		Debug.LogWarningFormat ("set={0}", state);
		meshRenderer.material = state ? onMaterial : offMaterial;
		UpdateCloud ();
	}

	private void UpdateCloud() {
		// TODO send info to cloud
		Debug.LogFormat ("ToCloud: <{0}, {1}, {2}>", houseName, sensorName, state);
	}
}
