//Copyright (c) 2014 Sang Ki Kwon (Cranberrygame)
//Email: cranberrygame@yahoo.com
//Homepage: http://www.github.com/cranberrygame
//License: MIT (http://opensource.org/licenses/MIT)
#import "RevMobPlugin.h"
#import "MainViewController.h"
//
#import "RevMobPluginOverlap.h"

@implementation RevMobPlugin

@synthesize callbackIdKeepCallback;
//
@synthesize pluginDelegate;
//
@synthesize email;
@synthesize licenseKey_;

- (void) setLicenseKey: (CDVInvokedUrlCommand*)command {
    NSString *email = [command.arguments objectAtIndex: 0];
    NSString *licenseKey = [command.arguments objectAtIndex: 1];
    NSLog(@"%@", email);
    NSLog(@"%@", licenseKey);
    
    //[self.commandDelegate runInBackground:^{
        [self _setLicenseKey:email aLicenseKey:licenseKey];
    //}];
}

- (void) setUp: (CDVInvokedUrlCommand*)command {
    //self.viewController
	//NSString *adUnit = [command.arguments objectAtIndex: 0];
	//NSString *adUnitFullScreen = [command.arguments objectAtIndex: 1];
	//BOOL isOverlap = [[command.arguments objectAtIndex: 2] boolValue];
	//BOOL isTest = [[command.arguments objectAtIndex: 3] boolValue];
	//NSLog(@"%@", adUnit);
	//NSLog(@"%@", adUnitFullScreen);
	//NSLog(@"%d", isOverlap);
	//NSLog(@"%d", isTest);
	NSString *mediaId = [command.arguments objectAtIndex: 0];
	NSLog(@"%@", mediaId);
	BOOL isOverlap = [[command.arguments objectAtIndex: 1] boolValue];
	NSLog(@"%d", isOverlap);
	
    self.callbackIdKeepCallback = command.callbackId;
	
    //if(isOverlap)
        pluginDelegate = [[RevMobPluginOverlap alloc] initWithPlugin:self];
    //else
    //    pluginDelegate = [[RevMobPluginSplit alloc] initWithPlugin:self];
			
    //[self.commandDelegate runInBackground:^{
		[self _setUp:mediaId anIsOverlap:isOverlap];	
    //}];
}

- (void) preloadBannerAd: (CDVInvokedUrlCommand*)command {
    //[self.commandDelegate runInBackground:^{
		[self _preloadBannerAd];
    //}];
}

- (void) showBannerAd: (CDVInvokedUrlCommand*)command {
	NSString *position = [command.arguments objectAtIndex: 0];
	NSString *size = [command.arguments objectAtIndex: 1];
	NSLog(@"%@", position);
	NSLog(@"%@", size);
			
    //[self.commandDelegate runInBackground:^{
		[self _showBannerAd:position aSize:size];
    //}];
}

- (void) reloadBannerAd: (CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
		[self _reloadBannerAd];
    }];
}

- (void) hideBannerAd: (CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
		[self _hideBannerAd];
    }];		
}

- (void) preloadFullScreenAd: (CDVInvokedUrlCommand*)command {
    //[self.commandDelegate runInBackground:^{
		[self _preloadFullScreenAd];
    //}];
}

- (void) showFullScreenAd: (CDVInvokedUrlCommand*)command {
    //[self.commandDelegate runInBackground:^{
		[self _showFullScreenAd];
    //}];
}

- (void) preloadPopupAd: (CDVInvokedUrlCommand*)command {
    //[self.commandDelegate runInBackground:^{
		[self _preloadPopupAd];
    //}];
}

- (void) showPopupAd: (CDVInvokedUrlCommand*)command {
    //[self.commandDelegate runInBackground:^{
		[self _showPopupAd];
    //}];
}

- (void) preloadAdLinkAd: (CDVInvokedUrlCommand*)command {
    //[self.commandDelegate runInBackground:^{
		[self _preloadAdLinkAd];
    //}];
}

- (void) showAdLinkAd: (CDVInvokedUrlCommand*)command {
    //[self.commandDelegate runInBackground:^{
		[self _showAdLinkAd];
    //}];
}

//cranberrygame start: Plugin

- (UIWebView*) getWebView {
    return self.webView;
}

- (UIViewController*) getViewController {
    return self.viewController;
}

- (id<CDVCommandDelegate>) getCommandDelegate {
    return self.commandDelegate;
}

- (NSString*) getCallbackIdKeepCallback {
    return callbackIdKeepCallback;
}

//cranberrygame end: Plugin

//cranberrygame start: PluginDelegate

- (void) _setLicenseKey:(NSString *)email aLicenseKey:(NSString *)licenseKey {
	//[pluginDelegate _setLicenseKey:email aLicenseKey:licenseKey];	
	self.email = email;
	self.licenseKey_ = licenseKey;
}

- (void) _setUp:(NSString *)mediaId anIsOverlap:(BOOL)isOverlap {
	[pluginDelegate _setUp:mediaId anIsOverlap:isOverlap];
}

- (void) _preloadBannerAd {
	[pluginDelegate _preloadBannerAd];
}

- (void) _showBannerAd:(NSString *)position aSize:(NSString *)size {
	[pluginDelegate _showBannerAd:position aSize:size];
}

- (void)_reloadBannerAd {
    [pluginDelegate _reloadBannerAd];
}

- (void) _hideBannerAd {
    [pluginDelegate _hideBannerAd];
}

- (void) _preloadFullScreenAd {
	[pluginDelegate _preloadFullScreenAd];
}

- (void) _showFullScreenAd {
	[pluginDelegate _showFullScreenAd];	
}

- (void) _preloadPopupAd {
	[pluginDelegate _preloadPopupAd];
}

- (void) _showPopupAd {
	[pluginDelegate _showPopupAd];	
}

- (void) _preloadAdLinkAd {
	[pluginDelegate _preloadAdLinkAd];
}

- (void) _showAdLinkAd {
	[pluginDelegate _showAdLinkAd];
}
	
//cranberrygame end: PluginDelegate
	
@end
