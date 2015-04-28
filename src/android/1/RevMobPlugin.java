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

public class RevMobPlugin extends RevMobAction {
	private static final String LOG_TAG = "RevMobPlugin";
	//
	private String adUnit;
	//
	private RevMobBanner banner;
	private RevMobFullscreen fullscreen;
	private RevMobPopup popup;
	private RevMobLink link;	
	//
	private RevMob revmob;
	private RevMobAdsListener revmobListener;
	
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);		
    }
	
//cranberrygame start
	@Override
    public void onPause(boolean multitasking) {
		//put your code	
        super.onPause(multitasking);
    }
    
    @Override
    public void onResume(boolean multitasking) {
        super.onResume(multitasking);
		//put your code	
    }
	
    @Override
    public void onDestroy() {
		//put your code	
        super.onDestroy();
    }
//cranberrygame end
	
	private void _setUp(String adUnit, String adUnitFullScreen, boolean isOverlap, boolean isTest){
		this.adUnit = adUnit;
		this.adUnitFullScreen = adUnitFullScreen;
		this.isOverlap = isOverlap;
		this.isTest = isTest;
		
		revmobListener = new MyRevMobAdsListener();
		revmob = RevMob.startWithListener(cordova.getActivity(), revmobListener);		
	}
	
	private void _preloadBannerAd(){
		banner = revmob.createBanner(cordova.getActivity(), revmobListener);			
	}	
	
	private void _showBannerAd(String position){
		if (banner != null) {
			ViewGroup view = (ViewGroup) findViewById(R.id.banner);
			view.removeAllViews();
			view.addView(banner);
			banner = null;			
		}
		else {
			//revmob.showBanner(currentActivity, Gravity.TOP, null, revmobListener);
			revmob.showBanner(currentActivity, Gravity.BOTTOM, null, revmobListener);
		}
	}

	private void _reloadBannerAd(){

	}
	
	private void _hideBannerAd(){
		if (banner != null) {
			banner.hide();
			banner = null;
		}
		else {
			revmob.hideBanner(currentActivity);
		}
	}	
		
	private void _preloadFullScreenAd(){
		fullscreen = revmob.createFullscreen(cordova.getActivity(), revmobListener);
	}	
	
	private void _showFullScreenAd(){
		if (fullscreen != null) {
			fullscreen.show();
			fullscreen = null;
		}
		else {
			revmob.showFullscreen(cordova.getActivity());
		}
	}	

	public void _preloadPopupAd() {
		popup = revmob.createPopup(cordova.getActivity(), revmobListener);
	}
	
	public void _showPopupAd() {
		if (popup != null) {
			popup.show();
			popup == null;
		}
		else {
			revmob.showPopup(cordova.getActivity());
		}
	}
	
	public void _preloadAdLinkAd() {
		link = revmob.createAdLink(cordova.getActivity(), revmobListener);
	}
	
	public void _showAdLinkAd() {
		if (link != null) {
			link.open();
			link = null;
		}
		else {
			revmob.openAdLink(cordova.getActivity(), revmobListener);
		}
	}
	
	//--------------------------
/*	
		PluginResult pr = new PluginResult(PluginResult.Status.OK, "onBannerAdPreloaded");
		pr.setKeepCallback(true);
		callbackContextKeepCallback.sendPluginResult(pr);
		//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
		//pr.setKeepCallback(true);
		//callbackContextKeepCallback.sendPluginResult(pr);
*/

	class MyRevMobAdsListener implements RevMobAdsListener() {
		@Override
		public void onRevMobSessionIsStarted() {
			toastOnUiThread("RevMob session is started.");
		}
    
		@Override
		public void onRevMobSessionNotStarted(String message) {
			toastOnUiThread("RevMob session failed to start.");
		}
    
		@Override
		public void onRevMobAdReceived() {
			toastOnUiThread("RevMob ad received.");
		}

		@Override
		public void onRevMobAdNotReceived(String message) {
			toastOnUiThread("RevMob ad not received.");
		}

		@Override
		public void onRevMobAdDisplayed() {
			toastOnUiThread("Ad displayed.");
		}

		@Override
		public void onRevMobAdClicked() {
			toastOnUiThread("Ad clicked.");
		}

		@Override
		public void onRevMobAdDismiss() {
			toastOnUiThread("Ad dismissed.");
		}
		
		@Override
		public void onRevMobEulaIsShown() {
			RMLog.i("[RevMob Sample App] Eula is shown.");	
		}

		@Override
		public void onRevMobEulaWasAcceptedAndDismissed() {
			RMLog.i("[RevMob Sample App] Eula was accepeted and dismissed.");
		}

		@Override
		public void onRevMobEulaWasRejected() {
			RMLog.i("[RevMob Sample App] Eula was rejected.");
			
		}
	};					
}