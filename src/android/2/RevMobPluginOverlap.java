//Copyright (c) 2014 Sang Ki Kwon (Cranberrygame)
//Email: cranberrygame@yahoo.com
//Homepage: http://www.github.com/cranberrygame
//License: MIT (http://opensource.org/licenses/MIT)
package com.cranberrygame.cordova.plugin.ad.admob;

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

//Util
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Surface;

//
import android.annotation.TargetApi;

//revmob
import java.util.ArrayList;
import java.util.GregorianCalendar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.revmob.RevMob;
import com.revmob.RevMobAdsListener;
import com.revmob.RevMobParallaxMode;
import com.revmob.RevMobTestingMode;
import com.revmob.RevMobUserGender;
import com.revmob.ads.banner.RevMobBanner;
import com.revmob.ads.fullscreen.RevMobFullscreen;
import com.revmob.ads.link.RevMobLink;
import com.revmob.ads.popup.RevMobPopup;
import com.revmob.android.R;
import com.revmob.internal.RMLog;

class Util {

	//ex) Util.alert(cordova.getActivity(),"message");
	public static void alert(Activity activity, String message) {
		AlertDialog ad = new AlertDialog.Builder(activity).create();  
		ad.setCancelable(false); // This blocks the 'BACK' button  
		ad.setMessage(message);  
		ad.setButton("OK", new DialogInterface.OnClickListener() {  
			@Override  
			public void onClick(DialogInterface dialog, int which) {  
				dialog.dismiss();                      
			}  
		});  
		ad.show(); 		
	}
	
	//https://gitshell.com/lvxudong/A530_packages_app_Camera/blob/master/src/com/android/camera/Util.java
	public static int getDisplayRotation(Activity activity) {
	    int rotation = activity.getWindowManager().getDefaultDisplay()
	            .getRotation();
	    switch (rotation) {
	        case Surface.ROTATION_0: return 0;
	        case Surface.ROTATION_90: return 90;
	        case Surface.ROTATION_180: return 180;
	        case Surface.ROTATION_270: return 270;
	    }
	    return 0;
	}

	public static final String md5(final String s) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
        }
        return "";
    }
}

public class RevMobPluginOverlap implements PluginDelegate {
	protected Plugin plugin;	
	//
	protected static final String LOG_TAG = "RevMobPlugin";
	//
	protected String mediaId;
	protected boolean isOverlap;
	//
	protected String bannerPreviousPosition;	
	protected String bannerPreviousSize;
	protected int lastOrientation;
	//
	protected boolean bannerAdPreload;	
	protected boolean fullScreenAdPreload;
/*
	//admob
	protected RelativeLayout bannerViewLayout;
	protected AdView bannerView;
	protected InterstitialAd interstitialView;
*/	
	//revmob
	protected RelativeLayout bannerViewLayout;
	private RevMobBanner bannerView;
	private RevMobFullscreen interstitialView;
	private RevMobPopup popup;
	private RevMobLink link;	
	private RevMobAdsListener revmobListener;
	private RevMob revmob;
	
	public RevMobPluginOverlap(Plugin plugin_) {
		plugin = plugin_;
	}

	public void _setUp(String mediaId, boolean isOverlap) {
		this.mediaId = mediaId;
		this.isOverlap = isOverlap;
		
		Log.d(LOG_TAG, String.format("%s", mediaId));			
		Log.d(LOG_TAG, String.format("%b", isOverlap));

		revmobListener = new MyRevMobAdsListener();
		revmob = RevMob.startWithListener(cordova.getActivity(), revmobListener);			
		
		lastOrientation = -1;		
		handleLayoutChangeOverlap();
	}
	
	//@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)	
	protected void handleLayoutChangeOverlap() {
		//http://stackoverflow.com/questions/24539578/cordova-plugin-listening-to-device-orientation-change-is-it-possible
		//http://developer.android.com/reference/android/view/View.OnLayoutChangeListener.html
		//https://gitshell.com/lvxudong/A530_packages_app_Camera/blob/master/src/com/android/camera/ActivityBase.java
    	plugin.getWebView().addOnLayoutChangeListener(new View.OnLayoutChangeListener(){
		    @Override
	        public void onLayoutChange(View v, int left, int top, int right, int bottom,
	                int oldLeft, int oldTop, int oldRight, int oldBottom) {
				if (left == oldLeft && top == oldTop && right == oldRight
						&& bottom == oldBottom) {
					return;
				}

				Log.d(LOG_TAG, "onLayoutChange");
				//Util.alert(cordova.getActivity(), "onLayoutChange");
				
				int orientation = Util.getDisplayRotation(plugin.getCordova().getActivity());
				if(orientation != lastOrientation) {
					Log.d(LOG_TAG, String.format("orientation: %d", orientation));
					//Util.alert(cordova.getActivity(), String.format("orientation: %d", orientation));
					if (bannerPreviousSize != null && bannerPreviousSize.equals("SMART_BANNER")) {
						Log.d(LOG_TAG, String.format("position: %s, size: %s", bannerPreviousPosition, bannerPreviousSize));
						//Util.alert(cordova.getActivity(), String.format("position: %s, size: %s", position, size));

						//overlap
						//http://stackoverflow.com/questions/11281562/android-admob-resize-on-landscape
						if (bannerView != null) {							
							//if banner is showing
							RelativeLayout bannerViewLayout = (RelativeLayout)bannerView.getParent();
							if (bannerViewLayout != null) {
								//bannerViewLayout.removeView(bannerView);
								//bannerView.destroy();
								//bannerView = null;				
								Log.d(LOG_TAG, String.format("position: %s, size: %s", bannerPreviousPosition, bannerPreviousSize));
								//Util.alert(cordova.getActivity(), String.format("position: %s, size: %s", position, size));

								//http://stackoverflow.com/questions/3072173/how-to-call-a-method-after-a-delay-in-android
								final Handler handler = new Handler();
								handler.postDelayed(new Runnable() {
									@Override
									public void run() {
										_showBannerAd(bannerPreviousPosition, bannerPreviousSize);
									}
								}, 1);//after 1ms
							}
						}						
					}
				}
            
				lastOrientation = orientation;		
	        }		    
		});
    }
	
	public void _preloadBannerAd() {
		bannerAdPreload = true;

		_hideBannerAd();
		
		loadBannerAd();	            	
	}
	
	private void loadBannerAd() {
	
		if (bannerView == null) {
/*		
			//
			bannerView = new AdView(plugin.getCordova().getActivity());//			
			bannerView.setAdUnitId(this.adUnit);
			bannerView.setAdListener(new MyBannerViewListener());		
			//https://developers.google.com/mobile-ads-sdk/docs/admob/android/banner
			if(bannerPreviousSize == null) {
				bannerPreviousSize = "SMART_BANNER";
			}
			if (bannerPreviousSize.equals("BANNER")) {
				bannerView.setAdSize(AdSize.BANNER);//Banner (320x50, Phones and Tablets)
			} 
			else if (bannerPreviousSize.equals("LARGE_BANNER")) {
				bannerView.setAdSize(AdSize.LARGE_BANNER);//Large banner (320x100, Phones and Tablets)
			}
			else if (bannerPreviousSize.equals("MEDIUM_RECTANGLE")) {
				bannerView.setAdSize(AdSize.MEDIUM_RECTANGLE);//Medium rectangle (300x250, Phones and Tablets)
			}
			else if (bannerPreviousSize.equals("FULL_BANNER")) {
				bannerView.setAdSize(AdSize.FULL_BANNER);//Full banner (468x60, Tablets)
			}
			else if (bannerPreviousSize.equals("LEADERBOARD")) {
				bannerView.setAdSize(AdSize.LEADERBOARD);//Leaderboard (728x90, Tablets)
			}
			else if (bannerPreviousSize.equals("SMART_BANNER")) {
				bannerView.setAdSize(AdSize.SMART_BANNER);//Smart banner (Auto size, Phones and Tablets) //https://developers.google.com/mobile-ads-sdk/docs/admob/android/banner#smart
			}
			else {
				bannerView.setAdSize(AdSize.SMART_BANNER);
			}		
*/			
			bannerView = revmob.createBanner(plugin.getCordova().getActivity(), revmobListener);			
		}
			
/*			
		//https://developer.android.com/reference/com/google/android/gms/ads/AdRequest.Builder.html
		AdRequest.Builder builder = new AdRequest.Builder();
		if(isTest) {
			builder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR); 
			//builder.addTestDevice("INSERT_YOUR_HASHED_DEVICE_ID_HERE");
			//Java code to force all devices to show test ads
			//http://stackoverflow.com/questions/9028852/java-code-to-force-all-devices-to-show-test-ads
			String ANDROID_ID = Settings.Secure.getString(plugin.getCordova().getActivity().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
			String deviceId = Util.md5(ANDROID_ID).toUpperCase();
			builder.addTestDevice(deviceId);		
		}
		AdRequest request = builder.build();
		bannerView.loadAd(request);	            	
*/		
	}
	
	public void _showBannerAd(String position, String size) {
		Log.d(LOG_TAG, String.format("%s", position));
		Log.d(LOG_TAG, String.format("%s", size));
		
		if (bannerIsShowingOverlap() && position.equals(bannerPreviousPosition) && size.equals(bannerPreviousSize)) {		
			return;
		}
		
		this.bannerPreviousPosition = position;	
		this.bannerPreviousSize = size;

		if(bannerAdPreload) {
			bannerAdPreload = false;
		}
		else{
			_hideBannerAd();
		
			loadBannerAd();
		}

		addBannerViewOverlap(position, size);
	}
	
	protected boolean bannerIsShowingOverlap() {
		boolean bannerIsShowing = false;
		if (bannerView != null) {							
			//if banner is showing
			RelativeLayout bannerViewLayout = (RelativeLayout)bannerView.getParent();
			if (bannerViewLayout != null) {
				bannerIsShowing = true;
			}
		}				
		
		return bannerIsShowing;
	}
	
	protected void addBannerViewOverlap(String position, String size) {
		if(bannerViewLayout == null) {
			bannerViewLayout = new RelativeLayout(plugin.getCordova().getActivity());//	
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
			//webView.addView(bannerViewLayout, params);
			bannerViewLayout.setLayoutParams(params);
			plugin.getWebView().addView(bannerViewLayout);
		}
		
		//http://tigerwoods.tistory.com/11
		//http://developer.android.com/reference/android/widget/RelativeLayout.html
		//http://stackoverflow.com/questions/24900725/admob-banner-poitioning-in-android-on-bottom-of-the-screen-using-no-xml-relative
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(AdView.LayoutParams.WRAP_CONTENT, AdView.LayoutParams.WRAP_CONTENT);
		if (position.equals("top-left")) {
			Log.d(LOG_TAG, "top-left");		
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);		
		}
		else if (position.equals("top-center")) {		
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		}
		else if (position.equals("top-right")) {		
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		}
		else if (position.equals("left")) {
			params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			params.addRule(RelativeLayout.CENTER_VERTICAL);			
		}
		else if (position.equals("center")) {
			params.addRule(RelativeLayout.CENTER_HORIZONTAL);	
			params.addRule(RelativeLayout.CENTER_VERTICAL);	
		}
		else if (position.equals("right")) {	
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			params.addRule(RelativeLayout.CENTER_VERTICAL);	
		}
		else if (position.equals("bottom-left")) {		
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);		
			params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);		
		}
		else if (position.equals("bottom-center")) {				
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		}
		else if (position.equals("bottom-right")) {
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		}
		else {		
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		}
		
		//bannerViewLayout.addView(bannerView, params);
		bannerView.setLayoutParams(params);
		bannerViewLayout.addView(bannerView);
	}
	
	public void _reloadBannerAd() {
		loadBannerAd();
	}
	
	public void _hideBannerAd() {
		if (bannerView == null)
			return;

		removeBannerViewOverlap();
	}
	
	protected void removeBannerViewOverlap() {
		RelativeLayout bannerViewLayout = (RelativeLayout)bannerView.getParent();
		if (bannerViewLayout != null) {
			bannerViewLayout.removeView(bannerView);
			bannerView.destroy();
			bannerView = null;				
		}
	}
	
	public void _preloadFullScreenAd() {
		fullScreenAdPreload = true;

		loadFullScreenAd();
	}
	
	private void loadFullScreenAd() {
		if (interstitialView == null) {
/*		
			interstitialView = new InterstitialAd(plugin.getCordova().getActivity());
			interstitialView.setAdUnitId(this.adUnitFullScreen);
			interstitialView.setAdListener(new MyInterstitialViewListener());					
*/
			interstitialView = revmob.createFullscreen(plugin.getCordova().getActivity(), revmobListener);			
		}		
		
/*		
		AdRequest.Builder builder = new AdRequest.Builder();
		if(isTest) {
			builder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR); 
			//builder.addTestDevice("INSERT_YOUR_HASHED_DEVICE_ID_HERE");				
			String ANDROID_ID = Settings.Secure.getString(plugin.getCordova().getActivity().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
			String deviceId = Util.md5(ANDROID_ID).toUpperCase();
			builder.addTestDevice(deviceId);		
		}
		AdRequest request = builder.build();			
		interstitialView.loadAd(request);		
*/		
	}
	
	public void _showFullScreenAd() {
		if(fullScreenAdPreload) {
			fullScreenAdPreload = false;

			interstitialView.show();
			interstitialView = null;//
		}
		else {
//			loadFullScreenAd();
			revmob.showFullscreen(plugin.getCordova().getActivity());
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

//////////////////////////////	
	
/*	
   //http://developer.android.com/reference/com/google/android/gms/ads/AdListener.html
    class MyBannerViewListener extends AdListener {

    	public void onAdLoaded() {
    		Log.d(LOG_TAG, "onAdLoaded");

    		if (bannerAdPreload) {
    			PluginResult pr = new PluginResult(PluginResult.Status.OK, "onBannerAdPreloaded");
    			pr.setKeepCallback(true);
    			plugin.getCallbackContextKeepCallback().sendPluginResult(pr);
    			//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
    			//pr.setKeepCallback(true);
    			//plugin.getCallbackContextKeepCallback().sendPluginResult(pr);
    		}
    		
    		PluginResult pr = new PluginResult(PluginResult.Status.OK, "onBannerAdLoaded");
    		pr.setKeepCallback(true);
    		plugin.getCallbackContextKeepCallback().sendPluginResult(pr);
    		//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
    		//pr.setKeepCallback(true);
    		//plugin.getCallbackContextKeepCallback().sendPluginResult(pr);
    	}
    	public void onAdFailedToLoad(int errorCode) {
    		Log.d(LOG_TAG, "onAdFailedToLoad");
    	}
    	public void onAdOpened() {
    		Log.d(LOG_TAG, "onAdOpened");//overlay screen opened
    	}
    	public void onAdClosed() {
    		Log.d(LOG_TAG, "onAdClosed");
    	}
    	public void onAdLeftApplication() {
    		Log.d(LOG_TAG, "onAdLeftApplication");
    	}
    }

    class MyInterstitialViewListener extends AdListener {

    	public void onAdLoaded() {
    		Log.d(LOG_TAG, "onAdLoaded");
    		
    		if(fullScreenAdPreload) {
    			PluginResult pr = new PluginResult(PluginResult.Status.OK, "onFullScreenAdPreloaded");
    			pr.setKeepCallback(true);
    			plugin.getCallbackContextKeepCallback().sendPluginResult(pr);
    			//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
    			//pr.setKeepCallback(true);
    			//plugin.getCallbackContextKeepCallback().sendPluginResult(pr);
    		}
    		
    		PluginResult pr = new PluginResult(PluginResult.Status.OK, "onFullScreenAdLoaded");
    		pr.setKeepCallback(true);
    		plugin.getCallbackContextKeepCallback().sendPluginResult(pr);
    		//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
    		//pr.setKeepCallback(true);
    		//plugin.getCallbackContextKeepCallback().sendPluginResult(pr);		
    		
    		if(!fullScreenAdPreload) {
    			interstitialView.show();
    		}	
    	}
		
    	public void onAdFailedToLoad(int errorCode) {
    		Log.d(LOG_TAG, "onAdFailedToLoad");
    	}
		
    	public void onAdLeftApplication() {
    		Log.d(LOG_TAG, "onAdLeftApplication");
    	}
		
    	public void onAdOpened() {
    		Log.d(LOG_TAG, "onAdOpened");
    		
    		PluginResult pr = new PluginResult(PluginResult.Status.OK, "onFullScreenAdShown");
    		pr.setKeepCallback(true);
    		plugin.getCallbackContextKeepCallback().sendPluginResult(pr);
    		//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
    		//pr.setKeepCallback(true);
    		//plugin.getCallbackContextKeepCallback().sendPluginResult(pr);		
    	}
		
    	public void onAdClosed() {
    		Log.d(LOG_TAG, "onAdClosed");

    		PluginResult pr = new PluginResult(PluginResult.Status.OK, "onFullScreenAdHidden");
    		pr.setKeepCallback(true);
    		plugin.getCallbackContextKeepCallback().sendPluginResult(pr);
    		//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
    		//pr.setKeepCallback(true);
    		//plugin.getCallbackContextKeepCallback().sendPluginResult(pr);    		
    	}
    }

    public void onPause(boolean multitasking) {
		if (bannerView != null) {
		    bannerView.pause();
		}
    }
      
    public void onResume(boolean multitasking) {
        if (bannerView != null) {
            bannerView.resume();
        }
    }
  	
    public void onDestroy() {
        if (bannerView != null) {
            bannerView.destroy();
        }
    }
*/	
}
