# Overview #
show revmob popup, link, banner, full screen (including accepts video option) ad

[android, ios] [crosswalk] [cordova cli]

requires revmob account https://www.revmobmobileadnetwork.com

this is open source cordova plugin.

this has 2% ad traffic share code for supporting plugin development.
if you do not want to this, fork this github and remove this code.

# Change log #
```c
	
To-Do:

	supports ios banner
```
# Install plugin #

## Cordova cli ##
```c
cordova plugin add com.cranberrygame.cordova.plugin.ad.revmob

## Crosswalk ##
```c
XDK PORJECTS - your_xdk_project - CORDOVA 3.X HYBRID MOBILE APP SETTINGS - PLUGINS AND PERMISSIONS - Third Party Plugins - Add a Third Party Plugin - Get Plugin from the Web -

Name: revmob
Plugin ID: com.cranberrygame.cordova.plugin.ad.revmob
[v] Plugin is located in the Apache Cordova Plugins Registry
```

## Phonegap build service (config.xml) ##
```c
<gap:plugin name="com.cranberrygame.cordova.plugin.ad.revmob" source="plugins.cordova.io" />
```

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
	window.revmob.setUp(mediaId, isOverlap);

	//banner ad callback
	window.revmob.onBannerAdPreloaded = function() {
		alert('onBannerAdPreloaded');
	};
	window.revmob.onBannerAdLoaded = function() {
		alert('onBannerAdLoaded');
	};
	//full screen ad callback
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
	//popup ad callback
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
	//ad link ad callback
	window.revmob.onAdLinkAdPreloaded = function() {
		alert('onAdLinkAdPreloaded');
	};
	window.revmob.onAdLinkAdLoaded = function() {
		alert('onAdLinkAdLoaded');
	};
	window.revmob.onAdLinkAdShown = function() {
		alert('onAdLinkAdShown');
	};
	window.revmob.onAdLinkAdHidden = function() {
		alert('onAdLinkAdHidden');
	};	
}, false);

window.revmob.preloadBannerAd();
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

window.revmob.preloadFullScreenAd();
window.revmob.showFullScreenAd();

window.revmob.preloadPopupAd();
window.revmob.showPopupAd();

window.revmob.preloadAdLinkAd();
window.revmob.showAdLinkAd();
```
# Examples #
<a href="https://github.com/cranberrygame/com.cranberrygame.cordova.plugin.ad.revmob/blob/master/example/basic/index.html">example/basic/index.html</a><br>
<a href="https://github.com/cranberrygame/com.cranberrygame.cordova.plugin.ad.revmob/blob/master/example/advanced/index.html">example/advanced/index.html</a>

# Test #

# Useful links #

Cordova Admob (Ad plugin)<br>
http://plugins.cordova.io/#/package/com.cranberrygame.cordova.plugin.ad.admob<br>
https://github.com/cranberrygame/com.cranberrygame.cordova.plugin.ad.admob<br>
<br>
<br>
Cordova RevMob (Ad plugin)<br>
http://plugins.cordova.io/#/package/com.cranberrygame.cordova.plugin.ad.revmob<br>
https://github.com/cranberrygame/com.cranberrygame.cordova.plugin.ad.revmob<br>

# Credits #
