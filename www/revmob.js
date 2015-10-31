
module.exports = {
	_loadedBannerAd: false,
	_loadedInterstitialAd: false,	
	_loadedVideoAd: false,
	_loadedRewardedVideoAd: false,
	_loadedPopupAd: false,
	_loadedLinkAd: false,
	_isShowingBannerAd: false,
	_isShowingInterstitialAd: false,	
	_isShowingVideoAd: false,
	_isShowingRewardedVideoAd: false,
	_isShowingPopupAd: false,
	_isShowingLinkAd: false,
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
						self._loadedBannerAd = true;

						if (self.onBannerAdLoaded)
							self.onBannerAdLoaded();
					}
					else if (result == "onBannerAdShown") {
						self._loadedBannerAd = false;
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
					else if (result == "onInterstitialAdPreloaded") {
//cranberrygame start; deprecated					
						if (self.onFullScreenAdPreloaded)
							self.onFullScreenAdPreloaded();
//cranberrygame end							
						if (self.onInterstitialAdPreloaded)
							self.onInterstitialAdPreloaded();							
					}
					else if (result == "onInterstitialAdLoaded") {
						self._loadedInterstitialAd = true;

//cranberrygame start; deprecated						
						if (self.onFullScreenAdLoaded)
							self.onFullScreenAdLoaded();
//cranberrygame end							
						if (self.onInterstitialAdLoaded)
							self.onInterstitialAdLoaded();								
					}
					else if (result == "onInterstitialAdShown") {
						self._loadedInterstitialAd = false;					
						self._isShowingInterstitialAd = true;
					
//cranberrygame start; deprecated					
						if (self.onFullScreenAdShown)
							self.onFullScreenAdShown();
//cranberrygame end						
						if (self.onInterstitialAdShown)
							self.onInterstitialAdShown();						
					}
					else if (result == "onInterstitialAdHidden") {
						self._isShowingInterstitialAd = false;
					
//cranberrygame start; deprecated					
						 if (self.onFullScreenAdHidden)
							self.onFullScreenAdHidden();
//cranberrygame end							
						 if (self.onInterstitialAdHidden)
							self.onInterstitialAdHidden();							
					}
					//
					else if (result == "onVideoAdPreloaded") {
						if (self.onVideoAdPreloaded)
							self.onVideoAdPreloaded();
					}
					else if (result == "onVideoAdLoaded") {
						self._loadedVideoAd = true;

						if (self.onVideoAdLoaded)
							self.onVideoAdLoaded();
					}
					else if (result == "onVideoAdShown") {
						self._loadedVideoAd = false;
						self._isShowingVideoAd = true;
					
						if (self.onVideoAdShown)
							self.onVideoAdShown();
					}
					else if (result == "onVideoAdHidden") {
						self._isShowingVideoAd = false;
					
						 if (self.onVideoAdHidden)
							self.onVideoAdHidden();
					}					
					//
					else if (result == "onRewardedVideoAdPreloaded") {
						if (self.onRewardedVideoAdPreloaded)
							self.onRewardedVideoAdPreloaded();
					}
					else if (result == "onRewardedVideoAdLoaded") {
						self._loadedRewardedVideoAd = true;

						if (self.onRewardedVideoAdLoaded)
							self.onRewardedVideoAdLoaded();
					}
					else if (result == "onRewardedVideoAdShown") {
						self._loadedRewardedVideoAd = false;
						self._isShowingRewardedVideoAd = true;
					
						if (self.onRewardedVideoAdShown)
							self.onRewardedVideoAdShown();
					}
					else if (result == "onRewardedVideoAdHidden") {
						self._isShowingRewardedVideoAd = false;
					
						 if (self.onRewardedVideoAdHidden)
							self.onRewardedVideoAdHidden();
					}					
					else if (result == "onRewardedVideoAdCompleted") {
						 if (self.onRewardedVideoAdCompleted)
							self.onRewardedVideoAdCompleted();
					}
					//
					else if (result == "onPopupAdPreloaded") {
						if (self.onPopupAdPreloaded)
							self.onPopupAdPreloaded();
					}
					else if (result == "onPopupAdLoaded") {
						self._loadedPopupAd = true;

						if (self.onPopupAdLoaded)
							self.onPopupAdLoaded();
					}
					else if (result == "onPopupAdShown") {
						self._loadedPopupAd = false;
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
					else if (result == "onLinkAdPreloaded") {
						if (self.onLinkAdPreloaded)
							self.onLinkAdPreloaded();
					}
					else if (result == "onLinkAdLoaded") {
						self._loadedLinkAd = true;

						if (self.onLinkAdLoaded)
							self.onLinkAdLoaded();
					}
					else if (result == "onLinkAdShown") {
						self._loadedLinkAd = false;
						self._isShowingLinkAd = true;
						
						if (self.onLinkAdShown)
							self.onLinkAdShown();
					}
					else if (result == "onLinkAdHidden") {
						self._isShowingLinkAd = false;
					
						 if (self.onLinkAdHidden)
							self.onLinkAdHidden();
					}					
				}
				else {
					//var event = result["event"];
					//var location = result["message"];				
					//if (event == "onXXX") {
					//	if (self.onXXX)
					//		self.onXXX(location);
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
	//
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
//cranberrygame start; deprecated		
	preloadFullScreenAd: function() {
		var self = this;	
        cordova.exec(
            null,
            null,
            'RevMobPlugin',
            'preloadInterstitialAd',
            []
        ); 
    },
    showFullScreenAd: function() {
		var self = this;
		cordova.exec(
            null,
            null,
            'RevMobPlugin',
            'showInterstitialAd',
            []
        ); 
    },
//cranberrygame end		
	preloadInterstitialAd: function() {
		var self = this;	
        cordova.exec(
            null,
            null,
            'RevMobPlugin',
            'preloadInterstitialAd',
            []
        ); 
    },
    showInterstitialAd: function() {
		var self = this;
		cordova.exec(
            null,
            null,
            'RevMobPlugin',
            'showInterstitialAd',
            []
        ); 
    },
	//
	preloadVideoAd: function() {
		var self = this;	
        cordova.exec(
            null,
            null,
            'RevMobPlugin',
            'preloadVideoAd',
            []
        ); 
    },
    showVideoAd: function() {
		var self = this;
		cordova.exec(
            null,
            null,
            'RevMobPlugin',
            'showVideoAd',
            []
        ); 
    },
	//	
	preloadRewardedVideoAd: function() {
		var self = this;	
        cordova.exec(
            null,
            null,
            'RevMobPlugin',
            'preloadRewardedVideoAd',
            []
        ); 
    },
    showRewardedVideoAd: function() {
		var self = this;
		cordova.exec(
            null,
            null,
            'RevMobPlugin',
            'showRewardedVideoAd',
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
	preloadLinkAd: function() {
		var self = this;	
        cordova.exec(
            null,
            null,
            'RevMobPlugin',
            'preloadLinkAd',
            []
        ); 
    },
    showLinkAd: function() {
		var self = this;
		cordova.exec(
            null,
            null,
            'RevMobPlugin',
            'showLinkAd',
            []
        ); 
    },
	//
	loadedBannerAd: function() {
		return this._loadedBannerAd;
	},
//cranberrygame start; deprecated	
	loadedFullScreenAd: function() {
		return this._loadedInterstitialAd;
	},
//cranberrygame end	
	loadedInterstitialAd: function() {
		return this._loadedInterstitialAd;
	},	
	loadedVideoAd: function() {
		return this._loadedVideoAd;
	},		
	loadedRewardedVideoAd: function() {
		return this._loadedRewardedVideoAd;
	},		
	loadedPopupAd: function() {
		return this._loadedPopupAd;
	},		
	loadedLinkAd: function() {
		return this._loadedLinkAd;
	},		
	//
	isShowingBannerAd: function() {
		return this._isShowingBannerAd;
	},
//cranberrygame start; deprecated	
	isShowingFullScreenAd: function() {
		return this._isShowingInterstitialAd;
	},
//cranberrygame end	
	isShowingInterstitialAd: function() {
		return this._isShowingInterstitialAd;
	},	
	isShowingVideoAd: function() {
		return this._isShowingVideoAd;
	},		
	isShowingRewardedVideoAd: function() {
		return this._isShowingRewardedVideoAd;
	},		
	isShowingPopupAd: function() {
		return this._isShowingPopupAd;
	},		
	isShowingLinkAd: function() {
		return this._isShowingLinkAd;
	},		
	//
	onBannerAdPreloaded: null,
	onBannerAdLoaded: null,
	onBannerAdShown: null,
	onBannerAdHidden: null,
	//
//cranberrygame start; deprecated	
	onFullScreenAdPreloaded: null,
	onFullScreenAdLoaded: null,
	onFullScreenAdShown: null,
	onFullScreenAdHidden: null,
//cranberrygame end
	onInterstitialAdPreloaded: null,
	onInterstitialAdLoaded: null,
	onInterstitialAdShown: null,
	onInterstitialAdHidden: null	
	//
	onVideoAdPreloaded: null,
	onVideoAdLoaded: null,
	onVideoAdShown: null,
	onVideoAdHidden: null,	
	//
	onRewardedVideoAdPreloaded: null,
	onRewardedVideoAdLoaded: null,
	onRewardedVideoAdShown: null,
	onRewardedVideoAdHidden: null,
	onRewardedVideoAdCompleted: null,
	//
	onPopupAdPreloaded: null,
	onPopupAdLoaded: null,
	onPopupAdShown: null,
	onPopupAdHidden: null,
	//
	onLinkAdPreloaded: null,
	onLinkAdLoaded: null,
	onLinkAdShown: null,
	onLinkAdHidden: null
};
