//Copyright (c) 2014 Sang Ki Kwon (Cranberrygame)
//Email: cranberrygame@yahoo.com
//Homepage: http://cranberrygame.github.io
//License: MIT (http://opensource.org/licenses/MIT)
package com.cranberrygame.cordova.plugin.ad.revmob;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import android.app.Activity;
import android.util.Log;
//
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.os.Build;
import android.provider.Settings;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.os.Handler;
//
import java.util.*;//Random

interface Plugin {
	public CordovaWebView getWebView();
	public CordovaInterface getCordova();
	public CallbackContext getCallbackContextKeepCallback();
}

interface PluginDelegate {
	public void _setLicenseKey(String email, String licenseKey);	
	public void _setUp(String mediaId, boolean isOverlap);
	public void _preloadBannerAd();
	public void _showBannerAd(String position, String size);
	public void _reloadBannerAd();
	public void _hideBannerAd();
	public void _preloadFullScreenAd();
	public void _showFullScreenAd();
	public void _preloadPopupAd();
	public void _showPopupAd();
	public void _preloadLinkAd();
	public void _showLinkAd();
	public void _preloadVideoAd();
	public void _showVideoAd();
	public void _preloadRewardedVideoAd();
	public void _showRewardedVideoAd();
    public void onPause(boolean multitasking);
    public void onResume(boolean multitasking);
    public void onDestroy();
}

public class RevMobPlugin extends CordovaPlugin implements PluginDelegate, Plugin {
	private static final String LOG_TAG = "RevMobPlugin";	
	protected CallbackContext callbackContextKeepCallback;
	//
	protected PluginDelegate pluginDelegate;
	//
	public String email;
	public String licenseKey;
	public boolean validLicenseKey;	
	public String TEST_MEDIA_ID = "553f02ab62ca37580b9830e7";
	
    @Override
	public void pluginInitialize() {
		super.pluginInitialize();
		//
    }
		
	//@Override
	//public void onCreate(Bundle savedInstanceState) {//build error
	//	super.onCreate(savedInstanceState);
	//	//
	//}
	
	//@Override
	//public void onStart() {//build error
	//	super.onStart();
	//	//
	//}
	
  	@Override
    public void onPause(boolean multitasking) {		
        super.onPause(multitasking);
		pluginDelegate.onPause(multitasking);		
    }
      
    @Override
    public void onResume(boolean multitasking) {
        super.onResume(multitasking);
        pluginDelegate.onResume(multitasking);
    }
  	
	//@Override
	//public void onStop() {//build error
	//	super.onStop();
	//	//
	//}
	
    @Override
    public void onDestroy() {
        super.onDestroy();
		pluginDelegate.onDestroy();		
    }
	
	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

		if (action.equals("setLicenseKey")) {
			setLicenseKey(action, args, callbackContext);

			return true;
		}		
		else if (action.equals("setUp")) {
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
		else if (action.equals("preloadVideoAd")) {
			preloadVideoAd(action, args, callbackContext);
			
			return true;
		}
		else if (action.equals("showVideoAd")) {
			showVideoAd(action, args, callbackContext);
						
			return true;
		}
		else if (action.equals("preloadRewardedVideoAd")) {
			preloadRewardedVideoAd(action, args, callbackContext);
			
			return true;
		}
		else if (action.equals("showRewardedVideoAd")) {
			showRewardedVideoAd(action, args, callbackContext);
						
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
		else if (action.equals("preloadLinkAd")) {
			preloadLinkAd(action, args, callbackContext);
			
			return true;
		}
		else if (action.equals("showLinkAd")) {
			showLinkAd(action, args, callbackContext);
						
			return true;
		}
		
		return false; // Returning false results in a "MethodNotFound" error.
	}
	
	private void setLicenseKey(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		final String email = args.getString(0);
		final String licenseKey = args.getString(1);				
		Log.d(LOG_TAG, String.format("%s", email));			
		Log.d(LOG_TAG, String.format("%s", licenseKey));
		
		cordova.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				_setLicenseKey(email, licenseKey);
			}
		});
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
		//json.optString("adUnitBanner")
		//json.optString("adUnitFullScreen")
		//JSONObject inJson = json.optJSONObject("inJson");
		//final String adUnitBanner = args.getString(0);
		//final String adUnitFullScreen = args.getString(1);				
		//final boolean isOverlap = args.getBoolean(2);				
		//final boolean isTest = args.getBoolean(3);
		//final String[] zoneIds = new String[args.getJSONArray(4).length()];
		//for (int i = 0; i < args.getJSONArray(4).length(); i++) {
		//	zoneIds[i] = args.getJSONArray(4).getString(i);
		//}			
		//Log.d(LOG_TAG, String.format("%s", adUnitBanner));			
		//Log.d(LOG_TAG, String.format("%s", adUnitFullScreen));
		//Log.d(LOG_TAG, String.format("%b", isOverlap));
		//Log.d(LOG_TAG, String.format("%b", isTest));
		final String mediaId = args.getString(0);
		final boolean isOverlap = args.getBoolean(1);				
		Log.d(LOG_TAG, String.format("%s", mediaId));			
		Log.d(LOG_TAG, String.format("%b", isOverlap));
		
		callbackContextKeepCallback = callbackContext;
		
		//if(isOverlap)
			pluginDelegate = new RevMobPluginOverlap(this);
		//else
		//	pluginDelegate = new RevMobPluginSplit(this);
		
		cordova.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				_setUp(mediaId, isOverlap);
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
		final String size = args.getString(1);
		Log.d(LOG_TAG, String.format("%s", position));
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
	
	private void preloadVideoAd(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		cordova.getActivity().runOnUiThread(new Runnable(){
			@Override
			public void run() {
				_preloadVideoAd();
			}
		});
	}

	private void showVideoAd(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		cordova.getActivity().runOnUiThread(new Runnable(){
			@Override
			public void run() {
				_showVideoAd();
			}
		});
	}
	
	private void preloadRewardedVideoAd(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		cordova.getActivity().runOnUiThread(new Runnable(){
			@Override
			public void run() {
				_preloadRewardedVideoAd();
			}
		});
	}

	private void showRewardedVideoAd(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		cordova.getActivity().runOnUiThread(new Runnable(){
			@Override
			public void run() {
				_showRewardedVideoAd();
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

	private void preloadLinkAd(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		cordova.getActivity().runOnUiThread(new Runnable(){
			@Override
			public void run() {
				_preloadLinkAd();
			}
		});
	}

	private void showLinkAd(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		cordova.getActivity().runOnUiThread(new Runnable(){
			@Override
			public void run() {
				_showLinkAd();
			}
		});
	}
	
	//cranberrygame start: Plugin

	public CordovaWebView getWebView() {
		return webView;
	}

	public CordovaInterface getCordova() {
		return cordova;
	}

	public CallbackContext getCallbackContextKeepCallback() {
		return callbackContextKeepCallback;
	}

	//cranberrygame end: Plugin
	
	//cranberrygame start: RevMobPluginPluginDelegate

	public void _setLicenseKey(String email, String licenseKey) {
		//pluginDelegate._setLicenseKey(email, licenseKey);
		this.email = email;
		this.licenseKey = licenseKey;
		
		//
		String str1 = Util.md5("cordova-plugin-: " + email);
		String str2 = Util.md5("cordova-plugin-ad-revmob: " + email);
		String str3 = Util.md5("com.cranberrygame.cordova.plugin.: " + email);
		String str4 = Util.md5("com.cranberrygame.cordova.plugin.ad.revmob: " + email);
		if(licenseKey != null && (licenseKey.equalsIgnoreCase(str1) || licenseKey.equalsIgnoreCase(str2) || licenseKey.equalsIgnoreCase(str3) || licenseKey.equalsIgnoreCase(str4))) {
			this.validLicenseKey = true;
			//
			String[] excludedLicenseKeys = {"xxx"};
			for (int i = 0 ; i < excludedLicenseKeys.length ; i++) {
				if (excludedLicenseKeys[i].equals(licenseKey)) {
					this.validLicenseKey = false;
					break;
				}
			}			
			if (this.validLicenseKey)
				Log.d(LOG_TAG, String.format("%s", "valid licenseKey"));
			else
				Log.d(LOG_TAG, String.format("%s", "invalid licenseKey"));
		}
		else {
			Log.d(LOG_TAG, String.format("%s", "invalid licenseKey"));
			this.validLicenseKey = false;
		}
		//if (!this.validLicenseKey)
		//	Util.alert(plugin.getCordova().getActivity(),"Cordova RevMob: nvalid email / license key. You can get free license key from https://play.google.com/store/apps/details?id=com.cranberrygame.pluginsforcordova");			
	}
	
	public void _setUp(String mediaId, boolean isOverlap) {
		if (!validLicenseKey) {	
			if (new Random().nextInt(100) <= 1) {//0~99					
				mediaId = TEST_MEDIA_ID;
			}
		}

		pluginDelegate._setUp(mediaId, isOverlap);
	}
	
	public void _preloadBannerAd() {
		pluginDelegate._preloadBannerAd();           	
	}
		
	public void _showBannerAd(String position, String size) {
		pluginDelegate._showBannerAd(position, size);		
	}
	
	public void _reloadBannerAd() {
		pluginDelegate._reloadBannerAd();
	}
	
	public void _hideBannerAd() {
		pluginDelegate._hideBannerAd();
	}
		
	public void _preloadFullScreenAd() {
		pluginDelegate._preloadFullScreenAd();
	}
	
	public void _showFullScreenAd() {
		pluginDelegate._showFullScreenAd();
	}

	public void _preloadVideoAd() {
		pluginDelegate._preloadVideoAd();
	}
	
	public void _showVideoAd() {
		pluginDelegate._showVideoAd();
	}
	
	public void _preloadRewardedVideoAd() {
		pluginDelegate._preloadRewardedVideoAd();
	}
	
	public void _showRewardedVideoAd() {
		pluginDelegate._showRewardedVideoAd();
	}
		
	public void _preloadPopupAd() {
		pluginDelegate._preloadPopupAd();
	}
	
	public void _showPopupAd() {
		pluginDelegate._showPopupAd();
	}

	public void _preloadLinkAd() {
		pluginDelegate._preloadLinkAd();
	}
	
	public void _showLinkAd() {
		pluginDelegate._showLinkAd();
	}
	
	//cranberrygame end: RevMobPluginPluginDelegate
}
