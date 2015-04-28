//Copyright (c) 2014 Sang Ki Kwon (Cranberrygame)
//Email: cranberrygame@yahoo.com
//Homepage: http://www.github.com/cranberrygame
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
//admob
import com.google.android.gms.ads.AdView;
//revmob
import com.revmob.RevMob;
import com.revmob.RevMobAdsListener;
import com.revmob.RevMobParallaxMode;
import com.revmob.RevMobTestingMode;
import com.revmob.RevMobUserGender;
import com.revmob.ads.banner.RevMobBanner;
import com.revmob.ads.fullscreen.RevMobFullscreen;
import com.revmob.ads.link.RevMobLink;
import com.revmob.ads.popup.RevMobPopup;
import com.revmob.internal.RMLog;
//
import java.util.*;

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
	protected String email;
	protected String licenseKey;
	protected String TEST_MEDIA_ID = "553f02ab62ca37580b9830e7";
	//
	protected String bannerPreviousPosition;	
	protected String bannerPreviousSize;
	protected int lastOrientation;
	//
	protected boolean bannerAdPreload;	
	protected boolean fullScreenAdPreload;
	protected boolean popupAdPreload;
	protected boolean adLinkAdPreload;
/*
	//admob
	protected RelativeLayout bannerViewLayout;
	protected AdView bannerView;
	protected InterstitialAd interstitialView;
*/	
	//revmob
	protected RelativeLayout bannerViewLayout;
	protected RevMobBanner bannerView;
	protected RevMobFullscreen interstitialView;
	protected RevMobPopup popup;
	protected RevMobLink link;	
	protected RevMob revmob;
	
	public RevMobPluginOverlap(Plugin plugin_) {
		plugin = plugin_;
	}

	public void _setUp(String mediaId, boolean isOverlap) {
		this.mediaId = mediaId;
		this.isOverlap = isOverlap;

		_setLicenseKey(((RevMobPlugin)plugin).email, ((RevMobPlugin)plugin).licenseKey);
		
		//
		String str1 = Util.md5("com.cranberrygame.cordova.plugin.: " + email);
		String str2 = Util.md5("com.cranberrygame.cordova.plugin.ad.revmob: " + email);
		if(licenseKey != null && (licenseKey.equalsIgnoreCase(str1) || licenseKey.equalsIgnoreCase(str2))) {
			Log.d(LOG_TAG, String.format("%s", "valid licenseKey"));
			revmob = RevMob.startWithListenerForWrapper(plugin.getCordova().getActivity(), mediaId, null);
		}
		else {
			Log.d(LOG_TAG, String.format("%s", "invalid licenseKey"));
			if (new Random().nextInt(100) <= 1) {//0~99					
				revmob = RevMob.startWithListenerForWrapper(plugin.getCordova().getActivity(), TEST_MEDIA_ID, null);
			}
			else {
				revmob = RevMob.startWithListenerForWrapper(plugin.getCordova().getActivity(), mediaId, null);
			}
		}
		
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
	
	public void _setLicenseKey(String email, String licenseKey) {
		this.email = email;
		this.licenseKey = licenseKey;
	}
	
	public void _preloadBannerAd() {
		bannerAdPreload = true;
		
		_hideBannerAd();
		
		loadBannerAd();
	}
	
	private void loadBannerAd() {
		if (bannerView == null) {
			bannerView = revmob.createBanner(plugin.getCordova().getActivity(), new BannerAdRevMobAdsListener());
		}
		
		//
		//bannerView.load();//duplicate
	}
	
	public void _showBannerAd(String position, String size) {
		
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
		removeBannerViewOverlap();
	}
	
	protected void removeBannerViewOverlap() {
		if (bannerView == null)
			return;

		RelativeLayout bannerViewLayout = (RelativeLayout)bannerView.getParent();
		if (bannerViewLayout != null) {
			bannerViewLayout.removeView(bannerView);
			//bannerView.destroy();//revmob build error
			bannerView = null;				
		}
	}
	
	public void _preloadFullScreenAd() {
		fullScreenAdPreload = true;

		loadFullScreenAd();		
	}
	
	private void loadFullScreenAd() {
		interstitialView = revmob.createFullscreen(plugin.getCordova().getActivity(), new FullScreenAdRevMobAdsListener());
	}
	
	public void _showFullScreenAd() {
		if(fullScreenAdPreload) {
			fullScreenAdPreload = false;

			interstitialView.show();
		}
		else {
			loadFullScreenAd();
		}		
	}
    
	public void _preloadPopupAd() {
		popupAdPreload = true;

		loadPopupAd();
	}

	public void loadPopupAd() {
		popup = revmob.createPopup(plugin.getCordova().getActivity(), new PopupAdRevMobAdsListener());
	}
	
	public void _showPopupAd() {
		if(popupAdPreload) {
			popupAdPreload = false;

			popup.show();
		}
		else {
			loadPopupAd();
		}		
	}
	
	public void _preloadAdLinkAd() {
		adLinkAdPreload = true;

		loadAdLinkAd();
	}
	
	public void loadAdLinkAd() {
		link = revmob.createAdLink(plugin.getCordova().getActivity(), new AdLinkAdRevMobAdsListener());
	}
	
	public void _showAdLinkAd() {
		if(adLinkAdPreload) {
			adLinkAdPreload = false;

			link.open();
		}
		else {
			loadAdLinkAd();
		}		
	}
	
    public void onPause(boolean multitasking) {
		if (bannerView != null) {
//		    bannerView.pause();
		}
    }
      
    public void onResume(boolean multitasking) {
		//
		String str1 = Util.md5("com.cranberrygame.cordova.plugin.: " + email);
		String str2 = Util.md5("com.cranberrygame.cordova.plugin.ad.revmob: " + email);
		if(licenseKey != null && (licenseKey.equalsIgnoreCase(str1) || licenseKey.equalsIgnoreCase(str2))) {
			Log.d(LOG_TAG, String.format("%s", "valid licenseKey"));
			revmob = RevMob.startWithListenerForWrapper(plugin.getCordova().getActivity(), mediaId, null);
		}
		else {
			Log.d(LOG_TAG, String.format("%s", "invalid licenseKey"));
			if (new Random().nextInt(100) <= 1) {//0~99					
				revmob = RevMob.startWithListenerForWrapper(plugin.getCordova().getActivity(), TEST_MEDIA_ID, null);
			}
			else {
				revmob = RevMob.startWithListenerForWrapper(plugin.getCordova().getActivity(), mediaId, null);
			}
		}	
		
        if (bannerView != null) {
//            bannerView.resume();
        }
    }
  	
    public void onDestroy() {
        if (bannerView != null) {
//            bannerView.destroy();
        }
    }	
	
	class BannerAdRevMobAdsListener implements RevMobAdsListener {
		@Override
		public void onRevMobSessionIsStarted() {
			Log.d(LOG_TAG, String.format("%s", "onRevMobSessionIsStarted"));
		}
    
		@Override
		public void onRevMobSessionNotStarted(String message) {
			Log.d(LOG_TAG, String.format("%s", "onRevMobSessionNotStarted"));
		}
    
		@Override
		public void onRevMobAdReceived() {
			Log.d(LOG_TAG, String.format("%s", "onRevMobAdReceived"));
	
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

		@Override
		public void onRevMobAdNotReceived(String message) {
			Log.d(LOG_TAG, String.format("%s", "onRevMobAdNotReceived"));
		}

		@Override
		public void onRevMobAdDisplayed() {
			Log.d(LOG_TAG, String.format("%s", "onRevMobAdDisplayed"));
		}

		@Override
		public void onRevMobAdClicked() {
			Log.d(LOG_TAG, String.format("%s", "onRevMobAdClicked"));
		}

		@Override
		public void onRevMobAdDismiss() {
			Log.d(LOG_TAG, String.format("%s", "onRevMobAdDismiss"));
		}
		
		@Override
		public void onRevMobEulaIsShown() {
			Log.d(LOG_TAG, String.format("%s", "onRevMobEulaIsShown"));
		}

		@Override
		public void onRevMobEulaWasAcceptedAndDismissed() {
			Log.d(LOG_TAG, String.format("%s", "onRevMobEulaWasAcceptedAndDismissed"));
		}

		@Override
		public void onRevMobEulaWasRejected() {
			Log.d(LOG_TAG, String.format("%s", "onRevMobEulaWasRejected"));
		}
	};
		
	class FullScreenAdRevMobAdsListener implements RevMobAdsListener {
		@Override
		public void onRevMobSessionIsStarted() {
			Log.d(LOG_TAG, String.format("%s", "onRevMobSessionIsStarted"));
		}
    
		@Override
		public void onRevMobSessionNotStarted(String message) {
			Log.d(LOG_TAG, String.format("%s", "onRevMobSessionNotStarted"));
		}
    
		@Override
		public void onRevMobAdReceived() {
			Log.d(LOG_TAG, String.format("%s", "onRevMobAdReceived"));
	
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

		@Override
		public void onRevMobAdNotReceived(String message) {
			Log.d(LOG_TAG, String.format("%s", "onRevMobAdNotReceived"));
		}

		@Override
		public void onRevMobAdDisplayed() {
			Log.d(LOG_TAG, String.format("%s", "onRevMobAdDisplayed"));
			
    		PluginResult pr = new PluginResult(PluginResult.Status.OK, "onFullScreenAdShown");
    		pr.setKeepCallback(true);
    		plugin.getCallbackContextKeepCallback().sendPluginResult(pr);
    		//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
    		//pr.setKeepCallback(true);
    		//plugin.getCallbackContextKeepCallback().sendPluginResult(pr);			
		}

		@Override
		public void onRevMobAdClicked() {
			Log.d(LOG_TAG, String.format("%s", "onRevMobAdClicked"));
		}

		@Override
		public void onRevMobAdDismiss() {
			Log.d(LOG_TAG, String.format("%s", "onRevMobAdDismiss"));
			
    		PluginResult pr = new PluginResult(PluginResult.Status.OK, "onFullScreenAdHidden");
    		pr.setKeepCallback(true);
    		plugin.getCallbackContextKeepCallback().sendPluginResult(pr);
    		//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
    		//pr.setKeepCallback(true);
    		//plugin.getCallbackContextKeepCallback().sendPluginResult(pr);			
		}
		
		@Override
		public void onRevMobEulaIsShown() {
			Log.d(LOG_TAG, String.format("%s", "onRevMobEulaIsShown"));
		}

		@Override
		public void onRevMobEulaWasAcceptedAndDismissed() {
			Log.d(LOG_TAG, String.format("%s", "onRevMobEulaWasAcceptedAndDismissed"));
		}

		@Override
		public void onRevMobEulaWasRejected() {
			Log.d(LOG_TAG, String.format("%s", "onRevMobEulaWasRejected"));
		}
	};	

	class PopupAdRevMobAdsListener implements RevMobAdsListener {
		@Override
		public void onRevMobSessionIsStarted() {
			Log.d(LOG_TAG, String.format("%s", "onRevMobSessionIsStarted"));
		}
    
		@Override
		public void onRevMobSessionNotStarted(String message) {
			Log.d(LOG_TAG, String.format("%s", "onRevMobSessionNotStarted"));
		}
    
		@Override
		public void onRevMobAdReceived() {
			Log.d(LOG_TAG, String.format("%s", "onRevMobAdReceived"));
	
    		if(popupAdPreload) {
    			PluginResult pr = new PluginResult(PluginResult.Status.OK, "onPopupAdPreloaded");
    			pr.setKeepCallback(true);
    			plugin.getCallbackContextKeepCallback().sendPluginResult(pr);
    			//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
    			//pr.setKeepCallback(true);
    			//plugin.getCallbackContextKeepCallback().sendPluginResult(pr);
    		}
    		
    		PluginResult pr = new PluginResult(PluginResult.Status.OK, "onPopupAdLoaded");
    		pr.setKeepCallback(true);
    		plugin.getCallbackContextKeepCallback().sendPluginResult(pr);
    		//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
    		//pr.setKeepCallback(true);
    		//plugin.getCallbackContextKeepCallback().sendPluginResult(pr);		
    		
    		if(!popupAdPreload) {
    			popup.show();
    		}    		
		}

		@Override
		public void onRevMobAdNotReceived(String message) {
			Log.d(LOG_TAG, String.format("%s", "onRevMobAdNotReceived"));
		}

		@Override
		public void onRevMobAdDisplayed() {
			Log.d(LOG_TAG, String.format("%s", "onRevMobAdDisplayed"));
			
    		PluginResult pr = new PluginResult(PluginResult.Status.OK, "onPopupAdShown");
    		pr.setKeepCallback(true);
    		plugin.getCallbackContextKeepCallback().sendPluginResult(pr);
    		//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
    		//pr.setKeepCallback(true);
    		//plugin.getCallbackContextKeepCallback().sendPluginResult(pr);			
		}

		@Override
		public void onRevMobAdClicked() {
			Log.d(LOG_TAG, String.format("%s", "onRevMobAdClicked"));
		}

		@Override
		public void onRevMobAdDismiss() {
			Log.d(LOG_TAG, String.format("%s", "onRevMobAdDismiss"));
			
    		PluginResult pr = new PluginResult(PluginResult.Status.OK, "onPopupAdHidden");
    		pr.setKeepCallback(true);
    		plugin.getCallbackContextKeepCallback().sendPluginResult(pr);
    		//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
    		//pr.setKeepCallback(true);
    		//plugin.getCallbackContextKeepCallback().sendPluginResult(pr);			
		}
		
		@Override
		public void onRevMobEulaIsShown() {
			Log.d(LOG_TAG, String.format("%s", "onRevMobEulaIsShown"));
		}

		@Override
		public void onRevMobEulaWasAcceptedAndDismissed() {
			Log.d(LOG_TAG, String.format("%s", "onRevMobEulaWasAcceptedAndDismissed"));
		}

		@Override
		public void onRevMobEulaWasRejected() {
			Log.d(LOG_TAG, String.format("%s", "onRevMobEulaWasRejected"));
		}
	};
	
	class AdLinkAdRevMobAdsListener implements RevMobAdsListener {
		@Override
		public void onRevMobSessionIsStarted() {
			Log.d(LOG_TAG, String.format("%s", "onRevMobSessionIsStarted"));
		}
    
		@Override
		public void onRevMobSessionNotStarted(String message) {
			Log.d(LOG_TAG, String.format("%s", "onRevMobSessionNotStarted"));
		}
    
		@Override
		public void onRevMobAdReceived() {
			Log.d(LOG_TAG, String.format("%s", "onRevMobAdReceived"));
	
    		if(adLinkAdPreload) {
    			PluginResult pr = new PluginResult(PluginResult.Status.OK, "onAdLinkAdPreloaded");
    			pr.setKeepCallback(true);
    			plugin.getCallbackContextKeepCallback().sendPluginResult(pr);
    			//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
    			//pr.setKeepCallback(true);
    			//plugin.getCallbackContextKeepCallback().sendPluginResult(pr);
    		}
    		
    		PluginResult pr = new PluginResult(PluginResult.Status.OK, "onAdLinkAdLoaded");
    		pr.setKeepCallback(true);
    		plugin.getCallbackContextKeepCallback().sendPluginResult(pr);
    		//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
    		//pr.setKeepCallback(true);
    		//plugin.getCallbackContextKeepCallback().sendPluginResult(pr);		
    		
    		if(!adLinkAdPreload) {
				link.open();
    		}    		
		}

		@Override
		public void onRevMobAdNotReceived(String message) {
			Log.d(LOG_TAG, String.format("%s", "onRevMobAdNotReceived"));
		}

		@Override
		public void onRevMobAdDisplayed() {
			Log.d(LOG_TAG, String.format("%s", "onRevMobAdDisplayed"));
			
    		PluginResult pr = new PluginResult(PluginResult.Status.OK, "onAdLinkAdShown");
    		pr.setKeepCallback(true);
    		plugin.getCallbackContextKeepCallback().sendPluginResult(pr);
    		//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
    		//pr.setKeepCallback(true);
    		//plugin.getCallbackContextKeepCallback().sendPluginResult(pr);			
		}

		@Override
		public void onRevMobAdClicked() {
			Log.d(LOG_TAG, String.format("%s", "onRevMobAdClicked"));
		}

		@Override
		public void onRevMobAdDismiss() {
			Log.d(LOG_TAG, String.format("%s", "onRevMobAdDismiss"));
			
    		PluginResult pr = new PluginResult(PluginResult.Status.OK, "onAdLinkAdHidden");
    		pr.setKeepCallback(true);
    		plugin.getCallbackContextKeepCallback().sendPluginResult(pr);
    		//PluginResult pr = new PluginResult(PluginResult.Status.ERROR);
    		//pr.setKeepCallback(true);
    		//plugin.getCallbackContextKeepCallback().sendPluginResult(pr);			
		}
		
		@Override
		public void onRevMobEulaIsShown() {
			Log.d(LOG_TAG, String.format("%s", "onRevMobEulaIsShown"));
		}

		@Override
		public void onRevMobEulaWasAcceptedAndDismissed() {
			Log.d(LOG_TAG, String.format("%s", "onRevMobEulaWasAcceptedAndDismissed"));
		}

		@Override
		public void onRevMobEulaWasRejected() {
			Log.d(LOG_TAG, String.format("%s", "onRevMobEulaWasRejected"));
		}
	};
}
