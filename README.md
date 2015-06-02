Cordova RevMob plugin
====================
# Overview #
Show revmob popup, link, banner, full screen (including accepts video option) ad

[android, ios] [cordova cli] [xdk]

Requires revmob account https://www.revmobmobileadnetwork.com

revmob-android-sdk-9.0.0
revmob-ios-sdk-9.0.0

This is open source cordova plugin.

You can see Plugins For Cordova in one page: http://cranberrygame.github.io?referrer=github

# Change log #
```c
	
To-Do:

	supports ios banner
```
# Install plugin #

## Cordova cli ##
https://cordova.apache.org/docs/en/edge/guide_cli_index.md.html#The%20Command-Line%20Interface - npm install -g cordova@4.1.2
```c
cordova plugin add com.cranberrygame.cordova.plugin.ad.revmob
```

## Xdk ##
https://software.intel.com/en-us/intel-xdk - Download XDK - XDK PORJECTS - [specific project] - CORDOVA 3.X HYBRID MOBILE APP SETTINGS - PLUGINS - Third Party Plugins - Add a Third Party Plugin - Get Plugin from the Web -
```c
Name: revmob
Plugin ID: com.cranberrygame.cordova.plugin.ad.revmob
[v] Plugin is located in the Apache Cordova Plugins Registry
```

## Cocoon ##
https://cocoon.io - Create project - [specific project] - Setting - Plugins - Search - cranberrygame - revmob - Save

## Phonegap build service (config.xml) ##
https://build.phonegap.com/ - Apps - [specific project] - Update code - Zip file including config.xml
```c
<gap:plugin name="com.cranberrygame.cordova.plugin.ad.revmob" source="plugins.cordova.io" />
```

## Construct2 ##
Download construct2 plugin: http://www.paywithapost.de/pay?id=4ef3f2be-26e8-4a04-b826-6680db13a8c8
<br>
Now all the native plugins are installed automatically: https://plus.google.com/102658703990850475314/posts/XS5jjEApJYV
# Server setting #
```c
```

# API #
```javascript
var mediaId = "REPLACE_THIS_WITH_YOUR_MEDIA_ID";
var isOverlap = true; //true: overlap, false: split
/*
var mediaId;
var isOverlap = true; //true: overlap, false: split
//android
if (navigator.userAgent.match(/Android/i)) {
	mediaId = "REPLACE_THIS_WITH_YOUR_ANDROID_MEDIA_ID";
}
//ios
else if (navigator.userAgent.match(/iPhone/i) || navigator.userAgent.match(/iPad/i)) {
	mediaId = "REPLACE_THIS_WITH_YOUR_IOS_MEDIA_ID";
}
*/

document.addEventListener("deviceready", function(){
	//if no license key, 2% ad traffic share for dev support.
	//you can get free license key from https://play.google.com/store/apps/details?id=com.cranberrygame.pluginsforcordova
	//window.revmob.setLicenseKey("yourEmailId@yourEmaildDamin.com", "yourFreeLicenseKey");
	
	window.revmob.setUp(mediaId, isOverlap);

	//
	window.revmob.onBannerAdPreloaded = function() {
		alert('onBannerAdPreloaded');
	};
	window.revmob.onBannerAdLoaded = function() {
		alert('onBannerAdLoaded');
	};
	window.revmob.onBannerAdShown = function() {
		alert('onBannerAdShown');
	};
	window.revmob.onBannerAdHidden = function() {
		alert('onBannerAdHidden');
	};	
	//
	window.revmob.onFullScreenAdPreloaded = function() {
		alert('onFullScreenAdPreloaded');
	};
	window.revmob.onFullScreenAdLoaded = function() {
		alert('onFullScreenAdLoaded');
	};
	window.revmob.onFullScreenAdShown = function() {
		alert('onFullScreenAdShown');
	};
	window.revmob.onFullScreenAdHidden = function() {
		alert('onFullScreenAdHidden');
	};
	//
	window.revmob.onPopupAdPreloaded = function() {
		alert('onPopupAdPreloaded');
	};
	window.revmob.onPopupAdLoaded = function() {
		alert('onPopupAdLoaded');
	};
	window.revmob.onPopupAdShown = function() {
		alert('onPopupAdShown');
	};
	window.revmob.onPopupAdHidden = function() {
		alert('onPopupAdHidden');
	};
	//
	window.revmob.onLinkAdPreloaded = function() {
		alert('onLinkAdPreloaded');
	};
	window.revmob.onLinkAdLoaded = function() {
		alert('onLinkAdLoaded');
	};
	window.revmob.onLinkAdShown = function() {
		alert('onLinkAdShown');
	};
	window.revmob.onLinkAdHidden = function() {
		alert('onLinkAdHidden');
	};
	//
	window.revmob.onVideoAdPreloaded = function() {
		alert('onVideoAdPreloaded');
	};
	window.revmob.onVideoAdLoaded = function() {
		alert('onVideoAdLoaded');
	};
	window.revmob.onVideoAdShown = function() {
		alert('onVideoAdShown');
	};
	window.revmob.onVideoAdHidden = function() {
		alert('onVideoAdHidden');
	};
	//
	window.revmob.onRewardedVideoAdPreloaded = function() {
		alert('onRewardedVideoAdPreloaded');
	};
	window.revmob.onRewardedVideoAdLoaded = function() {
		alert('onRewardedVideoAdLoaded');
	};
	window.revmob.onRewardedVideoAdShown = function() {
		alert('onRewardedVideoAdShown');
	};
	window.revmob.onRewardedVideoAdHidden = function() {
		alert('onRewardedVideoAdHidden');
	};
	window.revmob.onRewardedVideoAdCompleted = function() {
		alert('onRewardedVideoAdCompleted');
	};
}, false);

window.revmob.preloadBannerAd();//option, download ad previously for fast show
/*
position: 'top-left', 'top-center', 'top-right', 'left', 'center', 'right', 'bottom-left', 'bottom-center', 'bottom-right'
size: 	'BANNER' (320x50, Phones and Tablets)
		'LARGE_BANNER' (320x100, Phones and Tablets)
		'MEDIUM_RECTANGLE' (300x250, Phones and Tablets)
		'FULL_BANNER' (468x60, Tablets)
		'LEADERBOARD' (728x90, Tablets)
		'SKYSCRAPER' (120x600, Tablets, ipad only)
		'SMART_BANNER' (Auto size, Phones and Tablets, recommended)
*/
window.revmob.showBannerAd('top-center', 'SMART_BANNER');
window.revmob.showBannerAd('bottom-center', 'SMART_BANNER');
window.revmob.reloadBannerAd();
window.revmob.hideBannerAd();

window.revmob.preloadFullScreenAd();//option, download ad previously for fast show
window.revmob.showFullScreenAd();

window.revmob.preloadPopupAd();//option, download ad previously for fast show
window.revmob.showPopupAd();

window.revmob.preloadLinkAd();//option, download ad previously for fast show
window.revmob.showLinkAd();

window.revmob.preloadVideoAd();
window.revmob.showVideoAd();

window.revmob.preloadRewardedVideoAd();
window.revmob.showRewardedVideoAd();

alert(window.revmob.loadedBannerAd());//boolean: true or false
alert(window.revmob.loadedFullScreenAd());//boolean: true or false
alert(window.revmob.loadedPopupAd());//boolean: true or false
alert(window.revmob.loadedLinkAd());//boolean: true or false
alert(window.revmob.loadedVideoAd());//boolean: true or false
alert(window.revmob.loadedRewardedVideoAd());//boolean: true or false

alert(window.revmob.isShowingBannerAd());//boolean: true or false
alert(window.revmob.isShowingFullScreenAd());//boolean: true or false
alert(window.revmob.isShowingPopupAd());//boolean: true or false
alert(window.revmob.isShowingLinkAd());//boolean: true or false
alert(window.revmob.isShowingVideoAd());//boolean: true or false
alert(window.revmob.isShowingRewardedVideoAd());//boolean: true or false
```
# Examples #
<a href="https://github.com/cranberrygame/cordova-plugin-ad-revmob/blob/master/example/basic/index.html">example/basic/index.html</a><br>
<a href="https://github.com/cranberrygame/cordova-plugin-ad-revmob/blob/master/example/advanced/index.html">example/advanced/index.html</a>

# Test #

[![](http://img.youtube.com/vi/fThTXn88dNw/0.jpg)](https://www.youtube.com/watch?v=fThTXn88dNw&feature=youtu.be "Youtube")

You can also run following test apk.
https://dl.dropboxusercontent.com/u/186681453/pluginsforcordova/revmob/apk.html

# Useful links #

Plugins For Cordova<br>
http://cranberrygame.github.io?referrer=github

# Credits #
