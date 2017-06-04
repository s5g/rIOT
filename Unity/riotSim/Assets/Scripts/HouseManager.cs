using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class HouseManager : MonoBehaviour {

	//private const string localBaseUrl = "http://127.0.0.1:5000/homeids/";
	private const string localBaseUrl = "http://192.168.1.108:5000/homeids/";
	private const string remoteBaseUrl = "https://endless-shadow.herokuapp.com/homeids/";

	private static bool IsRemote = false;

	// Use this for initialization
	void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
		
	}

	public void Set(bool newIsRemote) {
		IsRemote = newIsRemote;
		Debug.LogFormat ("HouseManager: IsRemote={0}", IsRemote);
	}

	public static string GetBaseUrl() {
		return IsRemote ? remoteBaseUrl : localBaseUrl;
	}
}
