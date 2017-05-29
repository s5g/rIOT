using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class HouseManager : MonoBehaviour {

	private const string localBaseUrl = "http://127.0.0.1:8080/homeids/";
	private const string remoteBaseUrl = "http://riotbase-168223.appspot.com/homeids/";

	private static bool IsRemote = true;

	// Use this for initialization
	void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
		
	}

	public void Set(bool newIsRemote) {
		IsRemote = newIsRemote;
	}

	public static string GetBaseUrl() {
		return IsRemote ? remoteBaseUrl : localBaseUrl;
	}
}
