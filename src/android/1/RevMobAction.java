//Copyright (c) 2014 Sang Ki Kwon (Cranberrygame)
//Email: cranberrygame@yahoo.com
//Homepage: http://www.github.com/cranberrygame
//License: MIT (http://opensource.org/licenses/MIT)
package com.cranberrygame.cordova.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CallbackContext;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import android.app.Activity;
import android.util.Log;
//

public class RevMobAction extends CordovaPlugin {
	private CallbackContext callbackContextKeepCallback;

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

		if (action.equals("setUp")) {
			setUp(action, args, callbackContext);

			return true;
		}			
		else if (action.equals("preloadBannerAd")) {
			preloadBannerAd(action, args, callbackContext);
			
			return true;
		}
		else if (action.equals("showBannerAd")) {
			showBannerAd(action, args, callbackContext);

			return true;
		}
		else if (action.equals("reloadBannerAd")) {
			reloadBannerAd(action, args, callbackContext);
			
			return true;
		}			
		else if (action.equals("hideBannerAd")) {
			hideBannerAd(action, args, callbackContext);
			
			return true;
		}
		else if (action.equals("preloadFullScreenAd")) {
			preloadFullScreenAd(action, args, callbackContext);
			
			return true;
		}
		else if (action.equals("showFullScreenAd")) {
			showFullScreenAd(action, args, callbackContext);
						
			return true;
		}
		else if (action.equals("preloadPopupAd")) {
			preloadPopupAd(action, args, callbackContext);
			
			return true;
		}
		else if (action.equals("showPopupAd")) {
			showPopupAd(action, args, callbackContext);
						
			return true;
		}
		else if (action.equals("preloadAdLinkAd")) {
			preloadFullScreenAd(action, args, callbackContext);
			
			return true;
		}
		else if (action.equals("showAdLinkAd")) {
			showFullScreenAd(action, args, callbackContext);
						
			return true;
		}
		
		return false; // Returning false results in a "MethodNotFound" error.
	}
	
	private void setUp(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		//Activity activity=cordova.getActivity();
		//webView
		//args.length()
		//args.getString(0)
		//args.getString(1)
		//args.getInt(0)
		//args.getInt(1)
		//args.getBoolean(0)
		//args.getBoolean(1)
		//JSONObject json = args.optJSONObject(0);
		//json.optString("adUnit")
		//json.optString("adUnitFullScreen")
		//JSONObject inJson = json.optJSONObject("inJson");
		final String adUnit = args.getString(0);
		Log.d(LOG_TAG, String.format("%s", adUnit));			
		final String adUnitFullScreen = args.getString(1);				
		Log.d(LOG_TAG, String.format("%s", adUnitFullScreen));
		final boolean isOverlap = args.getBoolean(2);				
		Log.d(LOG_TAG, String.format("%b", isOverlap));
		final boolean isTest = args.getBoolean(3);				
		Log.d(LOG_TAG, String.format("%b", isTest));
		
		callbackContextKeepCallback = callbackContext;
			
		cordova.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				_setUp(adUnit, adUnitFullScreen, isOverlap, isTest);
			}
		});
	}

	private void preloadBannerAd(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		cordova.getActivity().runOnUiThread(new Runnable(){
			@Override
			public void run() {
				_preloadBannerAd();
			}
		});
	}

	private void showBannerAd(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		final String position = args.getString(0);
		Log.d(LOG_TAG, String.format("%s", position));
		final String size = args.getString(1);
		Log.d(LOG_TAG, String.format("%s", size));
	
		cordova.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				_showBannerAd(position, size);
			}
		});
	}

	private void reloadBannerAd(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		cordova.getActivity().runOnUiThread(new Runnable(){
			@Override
			public void run() {
				_reloadBannerAd();
			}
		});
	}
	
	private void hideBannerAd(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		cordova.getActivity().runOnUiThread(new Runnable(){
			@Override
			public void run() {
				_hideBannerAd();
			}
		});
	}

	private void preloadFullScreenAd(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		cordova.getActivity().runOnUiThread(new Runnable(){
			@Override
			public void run() {
				_preloadFullScreenAd();
			}
		});
	}

	private void showFullScreenAd(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		cordova.getActivity().runOnUiThread(new Runnable(){
			@Override
			public void run() {
				_showFullScreenAd();
			}
		});
	}

	private void preloadPopupAd(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		cordova.getActivity().runOnUiThread(new Runnable(){
			@Override
			public void run() {
				_preloadPopupAd();
			}
		});
	}

	private void showPopupAd(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		cordova.getActivity().runOnUiThread(new Runnable(){
			@Override
			public void run() {
				_showPopupAd();
			}
		});
	}

	private void preloadAdLinkAd(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		cordova.getActivity().runOnUiThread(new Runnable(){
			@Override
			public void run() {
				_preloadAdLinkAd();
			}
		});
	}

	private void showAdLinkAd(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		cordova.getActivity().runOnUiThread(new Runnable(){
			@Override
			public void run() {
				_showAdLinkAd();
			}
		});
	}

	protected abstract void _setUp(String adUnit, String adUnitFullScreen, boolean isOverlap, boolean isTest);
	protected abstract void _preloadBannerAd();
	protected abstract void _showBannerAd(String position);
	protected abstract void _reloadBannerAd();
	protected abstract void _hideBannerAd();
	protected abstract void _preloadFullScreenAd();
	protected abstract void _showFullScreenAd();
	protected abstract void _preloadPopupAd();
	protected abstract void _showPopupAd();
	protected abstract void _preloadAdLinkAd();
	protected abstract void _showAdLinkAd();	
}