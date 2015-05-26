
module.exports = {
	_isShowingBannerAd: false,
	_isShowingFullScreenAd: false,
	_isShowingPopupAd: false,
	_isShowingAdLinkAd: false,
	//
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
					else if (result == "onBannerAdShown") {
						self._isShowingBannerAd = true;
					
						if (self.onBannerAdShown)
							self.onBannerAdShown();
					}
					else if (result == "onBannerAdHidden") {
						self._isShowingBannerAd = false;
					
						 if (self.onBannerAdHidden)
							self.onBannerAdHidden();
					}
					//
					else if (result == "onFullScreenAdPreloaded") {
						if (self.onFullScreenAdPreloaded)
							self.onFullScreenAdPreloaded();
					}
					else if (result == "onFullScreenAdLoaded") {
						if (self.onFullScreenAdLoaded)
							self.onFullScreenAdLoaded();
					}
					else if (result == "onFullScreenAdShown") {
						self._isShowingFullScreenAd = true;
					
						if (self.onFullScreenAdShown)
							self.onFullScreenAdShown();
					}
					else if (result == "onFullScreenAdHidden") {
						self._isShowingFullScreenAd = false;
					
						 if (self.onFullScreenAdHidden)
							self.onFullScreenAdHidden();
					}
					//
					else if (result == "onPopupAdPreloaded") {
						if (self.onPopupAdPreloaded)
							self.onPopupAdPreloaded();
					}
					else if (result == "onPopupAdLoaded") {
						if (self.onPopupAdLoaded)
							self.onPopupAdLoaded();
					}
					else if (result == "onPopupAdShown") {
						self._isShowingPopupAd = true;
					
						if (self.onPopupAdShown)
							self.onPopupAdShown();
					}
					else if (result == "onPopupAdHidden") {
						self._isShowingPopupAd = false;
					
						 if (self.onPopupAdHidden)
							self.onPopupAdHidden();
					}
					//
					else if (result == "onAdLinkAdPreloaded") {
						if (self.onAdLinkAdPreloaded)
							self.onAdLinkAdPreloaded();
					}
					else if (result == "onAdLinkAdLoaded") {
						if (self.onAdLinkAdLoaded)
							self.onAdLinkAdLoaded();
					}
					else if (result == "onAdLinkAdShown") {
						self._isShowingAdLinkAd = true;
						
						if (self.onAdLinkAdShown)
							self.onAdLinkAdShown();
					}
					else if (result == "onAdLinkAdHidden") {
						self._isShowingAdLinkAd = false;
					
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
	//
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
	//
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
	isShowingBannerAd: function() {
		return this._isShowingBannerAd;
	},
	isShowingFullScreenAd: function() {
		return this._isShowingFullScreenAd;
	},		
	isShowingPopupAd: function() {
		return this._isShowingPopupAd;
	},		
	isShowingAdLinkAd: function() {
		return this._isShowingAdLinkAd;
	},		
	onBannerAdPreloaded: null,
	onBannerAdLoaded: null,
	//
	onFullScreenAdPreloaded: null,
	onFullScreenAdLoaded: null,
	onFullScreenAdShown: null,
	onFullScreenAdHidden: null,
	//
	onPopupAdPreloaded: null,
	onPopupAdLoaded: null,
	onPopupAdShown: null,
	onPopupAdHidden: null,
	//
	onAdLinkAdPreloaded: null,
	onAdLinkAdLoaded: null,
	onAdLinkAdShown: null,
	onAdLinkAdHidden: null
};
