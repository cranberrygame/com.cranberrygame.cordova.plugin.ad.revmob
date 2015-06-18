//Copyright (c) 2014 Sang Ki Kwon (Cranberrygame)
//Email: cranberrygame@yahoo.com
//Homepage: http://cranberrygame.github.io
//License: MIT (http://opensource.org/licenses/MIT)
#import "RevMobPlugin.h"
//
#import <RevMobAds/RevMobAds.h>

@interface RevMobPluginOverlap : NSObject <PluginDelegate>

@property id<Plugin> plugin;
//
@property NSString *mediaId;
@property BOOL isOverlap;
@property BOOL isTest;
//
@property NSString *bannerPreviousPosition;
@property NSString *bannerPreviousSize;
@property NSInteger lastOrientation;
//
@property BOOL bannerAdPreload;	
@property BOOL fullScreenAdPreload;
@property BOOL videoAdPreload;
@property BOOL rewardedVideoAdPreload;
@property BOOL popupAdPreload;	
@property BOOL linkAdPreload;
//revmob
@property RevMobBannerView *bannerView;
@property RevMobFullscreen *interstitialView;
@property RevMobFullscreen *video;
@property RevMobFullscreen *rewardedVideo;
@property RevMobPopup *popup;
@property RevMobAdLink *link;
//
@property id bannerAdRevMobAdsDelegate;
@property id fullScreenAdRevMobAdsDelegate;
@property id videoAdRevMobAdsDelegate;
@property id rewardedVideoAdRevMobAdsDelegate;
@property id popupAdRevMobAdsDelegate;
@property id linkAdRevMobAdsDelegate;

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

@interface VideoAdRevMobAdsDelegate : NSObject <RevMobAdsDelegate>
@property RevMobPluginOverlap *revMobPluginOverlap;
- (id) initWithRevMobPluginOverlap:(RevMobPluginOverlap*)revMobPluginOverlap_;
@end

@interface RewardedVideoAdRevMobAdsDelegate : NSObject <RevMobAdsDelegate>
@property RevMobPluginOverlap *revMobPluginOverlap;
- (id) initWithRevMobPluginOverlap:(RevMobPluginOverlap*)revMobPluginOverlap_;
@end

@interface PopupAdRevMobAdsDelegate : NSObject <RevMobAdsDelegate>
@property RevMobPluginOverlap *revMobPluginOverlap;
- (id) initWithRevMobPluginOverlap:(RevMobPluginOverlap*)revMobPluginOverlap_;
@end

@interface LinkAdRevMobAdsDelegate : NSObject <RevMobAdsDelegate>
@property RevMobPluginOverlap *revMobPluginOverlap;
- (id) initWithRevMobPluginOverlap:(RevMobPluginOverlap*)revMobPluginOverlap_;
@end
