
module.exports = {

	setLicenseKey: function(email, licenseKey) {
		var self = this;	
        cordova.exec(
            null,
            null,
            'RevMobPlugin',
            'setLicenseKey',			
            [email, licenseKey]
        ); 
    },	
	setUp: function(mediaId, isOverlap) {
		var self = this;	
        cordova.exec(
            function (result) {
				if (typeof result == "string") {
					if (result == "onBannerAdPreloaded") {
						if (self.onBannerAdPreloaded)
							self.onBannerAdPreloaded();
					}
					else if (result == "onBannerAdLoaded") {
						if (self.onBannerAdLoaded)
							self.onBannerAdLoaded();
					}
					//
					if (result == "onFullScreenAdPreloaded") {
						if (self.onFullScreenAdPreloaded)
							self.onFullScreenAdPreloaded();
					}
					else if (result == "onFullScreenAdLoaded") {
						if (self.onFullScreenAdLoaded)
							self.onFullScreenAdLoaded();
					}
					else if (result == "onFullScreenAdShown") {
						if (self.onFullScreenAdShown)
							self.onFullScreenAdShown();
					}
					else if (result == "onFullScreenAdHidden") {
						 if (self.onFullScreenAdHidden)
							self.onFullScreenAdHidden();
					}					
					if (result == "onPopupAdPreloaded") {
						if (self.onPopupAdPreloaded)
							self.onPopupAdPreloaded();
					}
					else if (result == "onPopupAdLoaded") {
						if (self.onPopupAdLoaded)
							self.onPopupAdLoaded();
					}
					else if (result == "onPopupAdShown") {
						if (self.onPopupAdShown)
							self.onPopupAdShown();
					}
					else if (result == "onPopupAdHidden") {
						 if (self.onPopupAdHidden)
							self.onPopupAdHidden();
					}
					if (result == "onAdLinkAdPreloaded") {
						if (self.onAdLinkAdPreloaded)
							self.onAdLinkAdPreloaded();
					}
					else if (result == "onAdLinkAdLoaded") {
						if (self.onAdLinkAdLoaded)
							self.onAdLinkAdLoaded();
					}
					else if (result == "onAdLinkAdShown") {
						if (self.onAdLinkAdShown)
							self.onAdLinkAdShown();
					}
					else if (result == "onAdLinkAdHidden") {
						 if (self.onAdLinkAdHidden)
							self.onAdLinkAdHidden();
					}
				}
				else {
					//if (result["event"] == "onXXX") {
					//	//result["message"]
					//	if (self.onXXX)
					//		self.onXXX(result);
					//}
				}			
			}, 
			function (error) {
			},
            'RevMobPlugin',
            'setUp',			
            [mediaId, isOverlap]
        ); 
    },
	preloadBannerAd: function() {
		var self = this;	
        cordova.exec(
            null,
            null,
            'RevMobPlugin',
            'preloadBannerAd',
            []
        ); 
    },
    showBannerAd: function(position, size) {
		var self = this;	
        cordova.exec(
            null,
            null,
            'RevMobPlugin',
            'showBannerAd',
            [position, size]
        ); 
    },
	reloadBannerAd: function() {
		var self = this;	
        cordova.exec(
            null,
            null,
            'RevMobPlugin',
            'reloadBannerAd',
            []
        ); 
    },
    hideBannerAd: function() {
		var self = this;	
        cordova.exec(
            null,
            null,
            'RevMobPlugin',
            'hideBannerAd',
            []
        ); 
    },
	//
	preloadFullScreenAd: function() {
		var self = this;	
        cordova.exec(
            null,
            null,
            'RevMobPlugin',
            'preloadFullScreenAd',
            []
        ); 
    },
    showFullScreenAd: function() {
		var self = this;
		cordova.exec(
            null,
            null,
            'RevMobPlugin',
            'showFullScreenAd',
            []
        ); 
    },
	preloadPopupAd: function() {
		var self = this;	
        cordova.exec(
            null,
            null,
            'RevMobPlugin',
            'preloadPopupAd',
            []
        ); 
    },
    showPopupAd: function() {
		var self = this;
		cordova.exec(
            null,
            null,
            'RevMobPlugin',
            'showPopupAd',
            []
        ); 
    },
	preloadAdLinkAd: function() {
		var self = this;	
        cordova.exec(
            null,
            null,
            'RevMobPlugin',
            'preloadAdLinkAd',
            []
        ); 
    },
    showAdLinkAd: function() {
		var self = this;
		cordova.exec(
            null,
            null,
            'RevMobPlugin',
            'showAdLinkAd',
            []
        ); 
    },
	onBannerAdPreloaded: null,
	onBannerAdLoaded: null,
	//
	onFullScreenAdPreloaded: null,
	onFullScreenAdLoaded: null,
	onFullScreenAdShown: null,
	onFullScreenAdHidden: null,
	onPopupAdPreloaded: null,
	onPopupAdLoaded: null,
	onPopupAdShown: null,
	onPopupAdHidden: null,
	onAdLinkAdPreloaded: null,
	onAdLinkAdLoaded: null,
	onAdLinkAdShown: null,
	onAdLinkAdHidden: null
};
