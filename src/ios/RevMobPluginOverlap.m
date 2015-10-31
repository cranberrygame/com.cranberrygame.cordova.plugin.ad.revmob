//Copyright (c) 2014 Sang Ki Kwon (Cranberrygame)
//Email: cranberrygame@yahoo.com
//Homepage: http://cranberrygame.github.io
//License: MIT (http://opensource.org/licenses/MIT)
#import "RevMobPluginOverlap.h"

@implementation RevMobPluginOverlap

@synthesize plugin;
//
@synthesize mediaId;
@synthesize isOverlap;
@synthesize isTest;
//
@synthesize bannerPreviousPosition;
@synthesize bannerPreviousSize;
@synthesize lastOrientation;
//
@synthesize bannerAdPreload;	
@synthesize interstitialAdPreload;
@synthesize popupAdPreload;
@synthesize linkAdPreload;
@synthesize rewardedVideoAdPreload;
@synthesize videoAdPreload;
//revmob
@synthesize bannerView;
@synthesize interstitialView;
@synthesize popup;
@synthesize link;
@synthesize video;
@synthesize rewardedVideo;
//
@synthesize bannerAdRevMobAdsDelegate;
@synthesize interstitialAdRevMobAdsDelegate;
@synthesize videoAdRevMobAdsDelegate;
@synthesize rewardedVideoAdRevMobAdsDelegate;
@synthesize popupAdRevMobAdsDelegate;
@synthesize linkAdRevMobAdsDelegate;

/*
- (CDVPlugin *) initWithWebView:(UIWebView *)theWebView {
    self = (Admob *)[super initWithWebView:theWebView];
    if (self) {
        // These notifications are required for re-placing the ad on orientation
        // changes. Start listening for notifications here since we need to
        // translate the Smart Banner constants according to the orientation.
        [[UIDevice currentDevice] beginGeneratingDeviceOrientationNotifications];
        [[NSNotificationCenter defaultCenter]
         addObserver:self
         selector:@selector(deviceOrientationChange___PLUGIN_NAME___:)
         name:UIDeviceOrientationDidChangeNotification
         object:nil];
    }
    return self;
}
*/

- (void) deviceOrientationChangeRevMobPlugin:(NSNotification *)notification{
    if (bannerView != nil)
    {
        CGRect bannerFrame = bannerView.frame;
        if (bannerFrame.origin.y != 0)
        {
            bannerFrame.origin.y = [self.plugin getWebView].frame.size.width - bannerView.frame.size.height;
        }
        bannerFrame.origin.x = [self.plugin getWebView].frame.size.height * 0.5f - bannerView.frame.size.width * 0.5f;
        bannerView.frame = bannerFrame;
    }
}

- (bool) _isLandscape {
    bool landscape = NO;
        
    UIDeviceOrientation currentOrientation = [[UIDevice currentDevice] orientation];
    if (UIInterfaceOrientationIsLandscape(currentOrientation)) {
        landscape = YES;
    }
    return landscape;
}

- (id) initWithPlugin:(id<Plugin>)plugin_{
    self = [super init];
    if (self) {
		self.plugin = plugin_;
    }
    return self;
}

- (void) _setLicenseKey:(NSString *)email aLicenseKey:(NSString *)licenseKey {
}
	
- (void) _setUp:(NSString *)mediaId anIsOverlap:(BOOL)isOverlap {	
	self.mediaId = mediaId;
	self.isOverlap = isOverlap;
    
	//http://sdk.revmobmobileadnetwork.com/api/ios/Classes/RevMobAds.html
	//+ (RevMobAds *)startSessionWithAppID:(id)anAppId
	//+ (RevMobAds *)startSessionWithAppID:(id)anAppId andDelegate:(id)adelegate
	//+ (RevMobAds *)startSessionWithAppID:(id)anAppId withSuccessHandler:(id)onSessionStartedHandler andFailHandler:(id)onSessionNotStartedHandler
    ////[RevMobAds startSessionWithAppID:mediaId andDelegate:self];
    [RevMobAds startSessionWithAppID:mediaId];	
	//[RevMobAds session].testingMode = RevMobAdsTestingModeOff;
	//[RevMobAds session].testingMode = RevMobAdsTestingModeWithAds;
	//[RevMobAds session].testingMode = RevMobAdsTestingModeWithoutAds;
}

- (void) _preloadBannerAd {
	self.bannerAdPreload = YES;

	[self _hideBannerAd];
	
    [self loadBannerAd];	
}

- (void) loadBannerAd {
    //
    if (bannerView == nil) {
        self.bannerView = [[RevMobAds session] bannerView];
        //self.bannerView.delegate = [[BannerAdRevMobAdsDelegate alloc] initWithRevMobPluginOverlap:self];//runtime error
        self.bannerAdRevMobAdsDelegate = [[BannerAdRevMobAdsDelegate alloc] initWithRevMobPluginOverlap:self];
        self.bannerView.delegate = self.bannerAdRevMobAdsDelegate;
	}
    
    [self.bannerView loadAd]; 
}
	
- (void) _showBannerAd:(NSString *)position aSize:(NSString *)size {
	
	if ([self bannerIsShowingOverlap] && [position isEqualToString:self.bannerPreviousPosition] && [size isEqualToString:self.bannerPreviousSize]) {
		return;
	}
	
	self.bannerPreviousPosition = position;
	self.bannerPreviousSize = size;

	if(bannerAdPreload) {
		bannerAdPreload = NO;
	}
	else{
		[self _hideBannerAd];
		
		[self loadBannerAd];
	}
			
	[self addBannerViewOverlap:position aSize:size];
    
/*
     CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onBannerAdShown"];
     [pr setKeepCallbackAsBool:YES];
     [[plugin getCommandDelegate] sendPluginResult:pr callbackId:[plugin getCallbackIdKeepCallback]];
     //CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
     //[pr setKeepCallbackAsBool:YES];
     //[[plugin getCommandDelegate] sendPluginResult:pr callbackId:[plugin getCallbackIdKeepCallback]];
*/
}

- (BOOL) bannerIsShowingOverlap {
	//
	BOOL bannerIsShowing = NO;
	if (bannerView != nil) {
		//if banner is showing			
		//if ([bannerView isDescendantOfView:webView]) {
		UIView* webView = [bannerView superview];
		if (webView != nil) {
			bannerIsShowing = YES;
		}
	}

	return bannerIsShowing;
}

- (void) addBannerViewOverlap:(NSString*)position aSize:(NSString*)size {
/*
    [bannerView setFrame:CGRectMake(10, 692, 200, 40)];
    [[self.plugin getWebView] addSubview:bannerView];
*/
/*
	CGRect bannerFrame = bannerView.frame;
	if ([position isEqualToString:@"top-center"]) {		    
		bannerFrame.origin.y = 0;
	}
	else {
		bannerFrame.origin.y = [self.plugin getWebView].frame.size.height - bannerView.frame.size.height;
	}
	bannerFrame.origin.x = [self.plugin getWebView].frame.size.width * 0.5f - bannerView.frame.size.width * 0.5f;
	bannerView.frame = bannerFrame;
	//https://developer.apple.com/library/ios/documentation/uikit/reference/UIView_Class/UIView/UIView.html#//apple_ref/occ/cl/UIView
	[[self.plugin getWebView] addSubview:bannerView];//////
*/
/*
     CGFloat width = [self.plugin getWebView].bounds.size.width;
     CGFloat height = [self.plugin getWebView].bounds.size.height;
     if ([position isEqualToString:@"top-center"]) {
        bannerView.frame = CGRectMake(0, 0, width, 50);
     }
     else {
        bannerView.frame = CGRectMake(0, height - 50, width, 50);
     }
     //bannerView.autoresizingMask = UIViewAutoresizingFlexibleTopMargin | UIViewAutoresizingFlexibleWidth;
     [[self.plugin getWebView] addSubview:bannerView];
*/
    //revmob: only support revmobAdDisplayed delegate
    [self.bannerView showAd];
}
 
- (void) _reloadBannerAd {
    [self loadBannerAd];
}

- (void) _hideBannerAd {
	[self _removeBannerViewOverlap];

    CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onBannerAdHidden"];
	[pr setKeepCallbackAsBool:YES];
	[[plugin getCommandDelegate] sendPluginResult:pr callbackId:[plugin getCallbackIdKeepCallback]];
    //CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
	//[pr setKeepCallbackAsBool:YES];
	//[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];	
}

- (void) _removeBannerViewOverlap {
    if (bannerView == nil)
		return;

	//[self.bannerView removeFromSuperview];		
	//if banner is showing
	UIView* webView = [bannerView superview];
	if (webView != nil) {
		//https://developer.apple.com/library/ios/documentation/uikit/reference/UIView_Class/UIView/UIView.html#//apple_ref/occ/cl/UIView
		[self.bannerView removeFromSuperview];
		bannerView = nil;
	}
}	
	
- (void) _preloadInterstitialAd {
	interstitialAdPreload = YES;

	[self loadInterstitialAd];	
}

- (void) loadInterstitialAd {
    self.interstitialView = [[RevMobAds session] fullscreen];
    //self.interstitialView.delegate = [[InterstitialAdRevMobAdsDelegate alloc] initWithRevMobPluginOverlap:self];//runtime error
    self.interstitialAdRevMobAdsDelegate = [[InterstitialAdRevMobAdsDelegate alloc] initWithRevMobPluginOverlap:self];
    self.interstitialView.delegate = self.interstitialAdRevMobAdsDelegate;
    
	[self.interstitialView loadAd];
}

- (void) _showInterstitialAd {
	if(interstitialAdPreload) {
		interstitialAdPreload = NO;

		[self.interstitialView showAd];
	}
	else {
		[self loadInterstitialAd];
	}
}

- (void) _preloadVideoAd {
    videoAdPreload = YES;
    
    [self loadVideoAd];
}

- (void) loadVideoAd {
    self.video = [[RevMobAds session] fullscreen];
    //self.video.delegate = [[VideoAdRevMobAdsDelegate alloc] initWithRevMobPluginOverlap:self];//runtime error
    self.videoAdRevMobAdsDelegate = [[VideoAdRevMobAdsDelegate alloc] initWithRevMobPluginOverlap:self];
    self.video.delegate = self.videoAdRevMobAdsDelegate;
    
    [self.video loadVideo];
}

- (void) _showVideoAd {
    if(videoAdPreload) {
        videoAdPreload = NO;
        
        [self.video showVideo];
    }
    else {
        [self loadVideoAd];
    }
}

- (void) _preloadRewardedVideoAd {
    rewardedVideoAdPreload = YES;
    
    [self loadRewardedVideoAd];
}

- (void) loadRewardedVideoAd {
    self.rewardedVideo = [[RevMobAds session] fullscreen];
    //self.rewardedVideo.delegate = [[RewardedVideoAdRevMobAdsDelegate alloc] initWithRevMobPluginOverlap:self];//runtime error
    self.rewardedVideoAdRevMobAdsDelegate = [[RewardedVideoAdRevMobAdsDelegate alloc] initWithRevMobPluginOverlap:self];
    self.rewardedVideo.delegate = self.rewardedVideoAdRevMobAdsDelegate;
    
    [self.rewardedVideo loadRewardedVideo];
}

- (void) _showRewardedVideoAd {
    if(rewardedVideoAdPreload) {
        rewardedVideoAdPreload = NO;
        
        [self.rewardedVideo showRewardedVideo];
    }
    else {
        [self loadRewardedVideoAd];
    }
}

- (void) _preloadPopupAd {
	popupAdPreload = YES;

	[self loadPopupAd];
}

- (void) loadPopupAd {
    self.popup = [[RevMobAds session] popup];
    //self.popup.delegate = [[PopupAdRevMobAdsDelegate alloc] initWithRevMobPluginOverlap:self];//runtime error
    self.popupAdRevMobAdsDelegate = [[PopupAdRevMobAdsDelegate alloc] initWithRevMobPluginOverlap:self];
    self.popup.delegate = self.popupAdRevMobAdsDelegate;
    
    [self.popup loadAd];
}

- (void) _showPopupAd {
	if(popupAdPreload) {
		popupAdPreload = NO;

		[self.popup showAd];
	}
	else {
		[self loadPopupAd];
	}		
}

- (void) _preloadLinkAd {
	linkAdPreload = YES;

	[self loadLinkAd];
}

- (void) loadLinkAd {

    self.link = [[RevMobAds session] adLink];
    //self.link.delegate = [[LinkAdRevMobAdsDelegate alloc] initWithRevMobPluginOverlap:self];//runtime error
    self.linkAdRevMobAdsDelegate = [[LinkAdRevMobAdsDelegate alloc] initWithRevMobPluginOverlap:self];
    self.link.delegate = self.linkAdRevMobAdsDelegate;
 
    [self.link loadAd];
}

- (void) _showLinkAd {
	if(linkAdPreload) {
		linkAdPreload = NO;

		[self.link openLink];
	}
	else {
		[self loadLinkAd];
	}		
}

@end

@implementation BannerAdRevMobAdsDelegate

@synthesize revMobPluginOverlap;

- (id) initWithRevMobPluginOverlap:(RevMobPluginOverlap*)revMobPluginOverlap_ {
    self = [super init];
    if (self) {
		self.revMobPluginOverlap = revMobPluginOverlap_;
    }
    return self;
}

- (void) revmobSessionIsStarted {
    NSLog(@"revmobSessionIsStarted");
}

- (void) revmobSessionNotStarted:(NSError *)error {
    NSLog(@"revmobSessionNotStarted");
}

- (void) revmobAdDidReceive {
    NSLog(@"revmobAdDidReceive");
	
	if(revMobPluginOverlap.bannerAdPreload) {
		CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onBannerAdPreloaded"];
		[pr setKeepCallbackAsBool:YES];
		[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
		//CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
		//[pr setKeepCallbackAsBool:YES];
		//[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
	}
	
	CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onBannerAdLoaded"];
	[pr setKeepCallbackAsBool:YES];
	[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
	//CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
	//[pr setKeepCallbackAsBool:YES];
	//[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
}

- (void) revmobAdDidFailWithError:(NSError *)error {
    NSLog(@"revmobAdDidFailWithError");
}

- (void) revmobAdDisplayed {
    NSLog(@"revmobAdDisplayed");
	
    CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onBannerAdShown"];
	[pr setKeepCallbackAsBool:YES];
	[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
    //CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
	//[pr setKeepCallbackAsBool:YES];
	//[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
}

- (void) revmobUserClickedInTheAd {
    NSLog(@"revmobUserClickedInTheAd");
}

- (void) revmobUserClosedTheAd {
    NSLog(@"revmobUserClosedTheAd");//Not implemented.
}

- (void) installDidReceive {
    NSLog(@"installDidReceive");
}

- (void) installDidFail {
    NSLog(@"installDidFail");
}

@end

@implementation InterstitialAdRevMobAdsDelegate

@synthesize revMobPluginOverlap;

- (id) initWithRevMobPluginOverlap:(RevMobPluginOverlap*)revMobPluginOverlap_ {
    self = [super init];
    if (self) {
		self.revMobPluginOverlap = revMobPluginOverlap_;
    }
    return self;
}

- (void) revmobSessionIsStarted {
    NSLog(@"revmobSessionIsStarted");
}

- (void) revmobSessionNotStarted:(NSError *)error {
    NSLog(@"revmobSessionNotStarted");
}

- (void) revmobAdDidReceive {
    NSLog(@"revmobAdDidReceive");
	
	if(revMobPluginOverlap.interstitialAdPreload) {
		CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onInterstitialAdPreloaded"];
		[pr setKeepCallbackAsBool:YES];
		[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
		//CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
		//[pr setKeepCallbackAsBool:YES];
		//[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
	}
	
	CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onInterstitialAdLoaded"];
	[pr setKeepCallbackAsBool:YES];
	[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
	//CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
	//[pr setKeepCallbackAsBool:YES];
	//[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
	
    if(!revMobPluginOverlap.interstitialAdPreload) {
        [revMobPluginOverlap.interstitialView showAd];
    }
}

- (void) revmobAdDidFailWithError:(NSError *)error {
    NSLog(@"revmobAdDidFailWithError");
}

- (void) revmobAdDisplayed {
    NSLog(@"revmobAdDisplayed");
	
    CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onInterstitialAdShown"];
	[pr setKeepCallbackAsBool:YES];
	[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
    //CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
	//[pr setKeepCallbackAsBool:YES];
	//[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
}

- (void) revmobUserClickedInTheAd {
    NSLog(@"revmobUserClickedInTheAd");
}

- (void) revmobUserClosedTheAd {
    NSLog(@"revmobUserClosedTheAd");
	
    CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onInterstitialAdHidden"];
	[pr setKeepCallbackAsBool:YES];
	[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
    //CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
	//[pr setKeepCallbackAsBool:YES];
	//[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
}

- (void) installDidReceive {
    NSLog(@"installDidReceive");
}

- (void) installDidFail {
    NSLog(@"installDidFail");
}

@end

@implementation VideoAdRevMobAdsDelegate

@synthesize revMobPluginOverlap;

- (id) initWithRevMobPluginOverlap:(RevMobPluginOverlap*)revMobPluginOverlap_ {
    self = [super init];
    if (self) {
        self.revMobPluginOverlap = revMobPluginOverlap_;
    }
    return self;
}

- (void) revmobSessionIsStarted {
    NSLog(@"revmobSessionIsStarted");
}

- (void) revmobSessionNotStarted:(NSError *)error {
    NSLog(@"revmobSessionNotStarted");
}

- (void) revmobAdDidReceive {
    NSLog(@"revmobAdDidReceive");
}

- (void) revmobAdDidFailWithError:(NSError *)error {
    NSLog(@"revmobAdDidFailWithError");
}

- (void) revmobAdDisplayed {
    NSLog(@"revmobAdDisplayed");
}

- (void) revmobUserClickedInTheAd {
    NSLog(@"revmobUserClickedInTheAd");
}

- (void) revmobUserClosedTheAd {
    NSLog(@"revmobUserClosedTheAd");
    
    CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onVideoAdHidden"];
    [pr setKeepCallbackAsBool:YES];
    [[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
    //CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    //[pr setKeepCallbackAsBool:YES];
    //[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
}

- (void) installDidReceive {
    NSLog(@"installDidReceive");
}

- (void) installDidFail {
    NSLog(@"installDidFail");
}

//video ad callback
-(void)revmobVideoDidLoad{
    NSLog(@"revmobVideoDidLoad");
	
    if(revMobPluginOverlap.videoAdPreload) {
        CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onVideoAdPreloaded"];
        [pr setKeepCallbackAsBool:YES];
        [[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
        //CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
        //[pr setKeepCallbackAsBool:YES];
        //[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
    }
    
    CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onVideoAdLoaded"];
    [pr setKeepCallbackAsBool:YES];
    [[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
    //CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    //[pr setKeepCallbackAsBool:YES];
    //[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
    
    if(!revMobPluginOverlap.videoAdPreload) {
        [revMobPluginOverlap.video showVideo];
    }	
}

-(void)revmobVideoNotCompletelyLoaded{
    NSLog(@"revmobVideoNotCompletelyLoaded");
}

-(void)revmobVideoDidStart{
    NSLog(@"revmobVideoDidStart");
	
    CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onVideoAdShown"];
    [pr setKeepCallbackAsBool:YES];
    [[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
    //CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    //[pr setKeepCallbackAsBool:YES];
    //[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];	
}

-(void)revmobVideoDidFinish{
    NSLog(@"revmobVideoDidFinish");
}

//rewarded video ad callback
-(void)revmobRewardedVideoDidLoad{
    NSLog(@"revmobRewardedVideoDidLoad");
}

-(void)revmobRewardedVideoNotCompletelyLoaded{
    NSLog(@"revmobRewardedVideoNotCompletelyLoaded");
}

-(void)revmobRewardedPreRollDisplayed{
    NSLog(@"revmobRewardedPreRollDisplayed");
}

-(void)revmobRewardedVideoDidStart{
    NSLog(@"revmobRewardedVideoDidStart");
}

-(void)revmobRewardedVideoDidFinish{
    NSLog(@"revmobRewardedVideoDidFinish");
}

-(void)revmobRewardedVideoComplete {
    NSLog(@"revmobRewardedVideoComplete");
}

@end

@implementation RewardedVideoAdRevMobAdsDelegate

@synthesize revMobPluginOverlap;

- (id) initWithRevMobPluginOverlap:(RevMobPluginOverlap*)revMobPluginOverlap_ {
    self = [super init];
    if (self) {
        self.revMobPluginOverlap = revMobPluginOverlap_;
    }
    return self;
}

- (void) revmobSessionIsStarted {
    NSLog(@"revmobSessionIsStarted");
}

- (void) revmobSessionNotStarted:(NSError *)error {
    NSLog(@"revmobSessionNotStarted");
}

- (void) revmobAdDidReceive {
    NSLog(@"revmobAdDidReceive");
}

- (void) revmobAdDidFailWithError:(NSError *)error {
    NSLog(@"revmobAdDidFailWithError");
}

- (void) revmobAdDisplayed {
    NSLog(@"revmobAdDisplayed");
}

- (void) revmobUserClickedInTheAd {
    NSLog(@"revmobUserClickedInTheAd");
}

- (void) revmobUserClosedTheAd {
    NSLog(@"revmobUserClosedTheAd");
    
    CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onRewardedVideoAdHidden"];
    [pr setKeepCallbackAsBool:YES];
    [[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
    //CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    //[pr setKeepCallbackAsBool:YES];
    //[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]]; 
}

- (void) installDidReceive {
    NSLog(@"installDidReceive");
}

- (void) installDidFail {
    NSLog(@"installDidFail");
}

//video ad callback
-(void)revmobVideoDidLoad{
    NSLog(@"revmobVideoDidLoad");
}

-(void)revmobVideoNotCompletelyLoaded{
    NSLog(@"revmobVideoNotCompletelyLoaded");
}

-(void)revmobVideoDidStart{
    NSLog(@"revmobVideoDidStart");
}

-(void)revmobVideoDidFinish{
    NSLog(@"revmobVideoDidFinish");
}

//rewarded video ad callback
-(void)revmobRewardedVideoDidLoad{
    NSLog(@"revmobRewardedVideoDidLoad");
	
    if(revMobPluginOverlap.rewardedVideoAdPreload) {
        CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onRewardedVideoAdPreloaded"];
        [pr setKeepCallbackAsBool:YES];
        [[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
        //CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
        //[pr setKeepCallbackAsBool:YES];
        //[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
    }
    
    CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onRewardedVideoAdLoaded"];
    [pr setKeepCallbackAsBool:YES];
    [[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
    //CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    //[pr setKeepCallbackAsBool:YES];
    //[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
    
    if(!revMobPluginOverlap.rewardedVideoAdPreload) {
        [revMobPluginOverlap.rewardedVideo showRewardedVideo];
    }
}

-(void)revmobRewardedVideoNotCompletelyLoaded{
    NSLog(@"revmobRewardedVideoNotCompletelyLoaded");
}

-(void)revmobRewardedPreRollDisplayed{
    NSLog(@"revmobRewardedPreRollDisplayed");
}

-(void)revmobRewardedVideoDidStart{
    NSLog(@"revmobRewardedVideoDidStart");
	
    CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onRewardedVideoAdShown"];
    [pr setKeepCallbackAsBool:YES];
    [[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
    //CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    //[pr setKeepCallbackAsBool:YES];
    //[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];	
}

-(void)revmobRewardedVideoDidFinish{
    NSLog(@"revmobRewardedVideoDidFinish");
}

-(void)revmobRewardedVideoComplete {
    NSLog(@"revmobRewardedVideoComplete");
    
    CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onRewardedVideoAdCompleted"];
    [pr setKeepCallbackAsBool:YES];
    [[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
    //CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    //[pr setKeepCallbackAsBool:YES];
    //[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
}

@end

@implementation PopupAdRevMobAdsDelegate

@synthesize revMobPluginOverlap;

- (id) initWithRevMobPluginOverlap:(RevMobPluginOverlap*)revMobPluginOverlap_ {
    self = [super init];
    if (self) {
		self.revMobPluginOverlap = revMobPluginOverlap_;
    }
    return self;
}

- (void) revmobSessionIsStarted {
    NSLog(@"revmobSessionIsStarted");
}

- (void) revmobSessionNotStarted:(NSError *)error {
    NSLog(@"revmobSessionNotStarted");
}

- (void) revmobAdDidReceive {
    NSLog(@"revmobAdDidReceive");
	
	if(revMobPluginOverlap.popupAdPreload) {
		CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onPopupAdPreloaded"];
		[pr setKeepCallbackAsBool:YES];
		[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
		//CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
		//[pr setKeepCallbackAsBool:YES];
		//[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
	}
	
	CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onPopupAdLoaded"];
	[pr setKeepCallbackAsBool:YES];
	[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
	//CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
	//[pr setKeepCallbackAsBool:YES];
	//[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
	
    if(!revMobPluginOverlap.popupAdPreload) {
        [revMobPluginOverlap.popup showAd];
    }
}

- (void) revmobAdDidFailWithError:(NSError *)error {
    NSLog(@"revmobAdDidFailWithError");
}

- (void) revmobAdDisplayed {
    NSLog(@"revmobAdDisplayed");
  
    //android: triggered, ios: not triggered
    CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onPopupAdShown"];
    [pr setKeepCallbackAsBool:YES];
    [[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
    //CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    //[pr setKeepCallbackAsBool:YES];
    //[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
}

- (void) revmobUserClickedInTheAd {
    NSLog(@"revmobUserClickedInTheAd");
}

- (void) revmobUserClosedTheAd {
    NSLog(@"revmobUserClosedTheAd");
	
	//android: not triggered, ios: triggered
    CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onPopupAdHidden"];
	[pr setKeepCallbackAsBool:YES];
	[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
    //CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
	//[pr setKeepCallbackAsBool:YES];
	//[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
}

- (void) installDidReceive {
    NSLog(@"installDidReceive");
}

- (void) installDidFail {
    NSLog(@"installDidFail");
}

@end

@implementation LinkAdRevMobAdsDelegate

@synthesize revMobPluginOverlap;

- (id) initWithRevMobPluginOverlap:(RevMobPluginOverlap*)revMobPluginOverlap_ {
    self = [super init];
    if (self) {
		self.revMobPluginOverlap = revMobPluginOverlap_;
    }
    return self;
}

- (void) revmobSessionIsStarted {
    NSLog(@"revmobSessionIsStarted");
}

- (void) revmobSessionNotStarted:(NSError *)error {
    NSLog(@"revmobSessionNotStarted");
}

- (void) revmobAdDidReceive {
    NSLog(@"revmobAdDidReceive");
	
	if(revMobPluginOverlap.linkAdPreload) {
		CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onLinkAdPreloaded"];
		[pr setKeepCallbackAsBool:YES];
		[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
		//CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
		//[pr setKeepCallbackAsBool:YES];
		//[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
	}
	
	CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onLinkAdLoaded"];
	[pr setKeepCallbackAsBool:YES];
	[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
	//CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
	//[pr setKeepCallbackAsBool:YES];
	//[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
	
    if(!revMobPluginOverlap.linkAdPreload) {
        [revMobPluginOverlap.link openLink];
    }
}

- (void) revmobAdDidFailWithError:(NSError *)error {
    NSLog(@"revmobAdDidFailWithError");
}

- (void) revmobAdDisplayed {
    NSLog(@"revmobAdDisplayed");
}

- (void) revmobUserClickedInTheAd {
    NSLog(@"revmobUserClickedInTheAd");

    CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onLinkAdShown"];
    [pr setKeepCallbackAsBool:YES];
    [[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
    //CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    //[pr setKeepCallbackAsBool:YES];
    //[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
}

- (void) revmobUserClosedTheAd {
    NSLog(@"revmobUserClosedTheAd");

    //android: not triggered, ios: not triggered
    CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"onLinkAdHidden"];
	[pr setKeepCallbackAsBool:YES];
	[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
    //CDVPluginResult* pr = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
	//[pr setKeepCallbackAsBool:YES];
	//[[revMobPluginOverlap.plugin getCommandDelegate] sendPluginResult:pr callbackId:[revMobPluginOverlap.plugin getCallbackIdKeepCallback]];
}

- (void) installDidReceive {
    NSLog(@"installDidReceive");
}

- (void) installDidFail {
    NSLog(@"installDidFail");
}

@end
