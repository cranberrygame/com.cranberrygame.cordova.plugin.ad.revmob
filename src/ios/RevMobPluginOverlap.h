//Copyright (c) 2014 Sang Ki Kwon (Cranberrygame)
//Email: cranberrygame@yahoo.com
//Homepage: http://www.github.com/cranberrygame
//License: MIT (http://opensource.org/licenses/MIT)
#import <Foundation/Foundation.h>
#import <Cordova/CDV.h>
//
#import <RevMobAds/RevMobAds.h>
#import <RevMobAds/RevMobAdsDelegate.h>
//
#import "RevMobPlugin.h"

@interface RevMobPluginOverlap : NSObject <PluginDelegate, RevMobAdsDelegate>

@property id<Plugin> plugin;
//
@property NSString *mediaId;
@property BOOL isOverlap;
@property BOOL isTest;
//
@property NSString *email;
@property NSString *licenseKey_;
//
@property NSString *bannerPreviousPosition;
@property NSString *bannerPreviousSize;
@property NSInteger lastOrientation;
//
@property BOOL bannerAdPreload;	
@property BOOL fullScreenAdPreload;	
@property BOOL popupAdPreload;	
@property BOOL adLinkAdPreload;	
//revmob
@property RevMobBannerView *bannerView;
@property RevMobFullscreen *interstitialView;
@property RevMobPopup *popup;
@property RevMobAdLink *link;
@property id bannerViewDelegate;
@property id interstitialViewDelegate;
@property id popupDelegate;
@property id linkDelegate;

- (id) initWithPlugin:(id<Plugin>)plugin_;

@end

@interface BannerAdRevMobAdsDelegate : NSObject <RevMobAdsDelegate>
@property RevMobPluginOverlap *revMobPluginOverlap;
- (id) initWithRevMobPluginOverlap:(RevMobPluginOverlap*)revMobPluginOverlap_;
@end

@interface FullScreenAdRevMobAdsDelegate : NSObject <RevMobAdsDelegate>
@property RevMobPluginOverlap *revMobPluginOverlap;
- (id) initWithRevMobPluginOverlap:(RevMobPluginOverlap*)revMobPluginOverlap_;
@end

@interface PopupAdRevMobAdsDelegate : NSObject <RevMobAdsDelegate>
@property RevMobPluginOverlap *revMobPluginOverlap;
- (id) initWithRevMobPluginOverlap:(RevMobPluginOverlap*)revMobPluginOverlap_;
@end

@interface AdLinkAdRevMobAdsDelegate : NSObject <RevMobAdsDelegate>
@property RevMobPluginOverlap *revMobPluginOverlap;
- (id) initWithRevMobPluginOverlap:(RevMobPluginOverlap*)revMobPluginOverlap_;
@end
