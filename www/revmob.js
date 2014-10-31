//function RevMob(appId) { //cranberrygame
function RevMob() { //cranberrygame
	//this.appId = appId; //cranberrygame
	this.TEST_DISABLED = 0;
	this.TEST_WITH_ADS = 1;
	this.TEST_WITHOUT_ADS = 2;

//cranberrygame start	
	this.setUp = function(appId) {
		this.appId = appId;
	}
//cranberrygame end
	
	this.startSession = function(successCallback, errorCallback) {
		//cordova.exec(successCallback, errorCallback, "RevMobPlugin", "startSession", [appId]); //cranberrygame
		cordova.exec(successCallback, errorCallback, "RevMobPlugin", "startSession", [this.appId]); //cranberrygame
	}

	this.showFullscreen = function(successCallback, errorCallback) {
		cordova.exec(successCallback, errorCallback, "RevMobPlugin", "showFullscreen", []);
	}

	this.openAdLink = function(successCallback, errorCallback) {
		cordova.exec(successCallback, errorCallback, "RevMobPlugin", "openAdLink", []);
	}

	this.showPopup = function(successCallback, errorCallback) {
		cordova.exec(successCallback, errorCallback, "RevMobPlugin", "showPopup", []);
	}

	this.showBanner = function(successCallback, errorCallback) {
		cordova.exec(successCallback, errorCallback, "RevMobPlugin", "showBanner", []);
	}

	this.hideBanner = function(successCallback, errorCallback) {
		cordova.exec(successCallback, errorCallback, "RevMobPlugin", "hideBanner", []);
	}

	this.setTestingMode = function(testingMode) {
		cordova.exec(null, null, "RevMobPlugin", "setTestingMode", [testingMode]);
	}

	this.printEnvironmentInformation = function() {
		cordova.exec(null, null, "RevMobPlugin", "printEnvironmentInformation", []);
	}

	this.setTimeoutInSeconds = function(seconds) {
		cordova.exec(null, null, "RevMobPlugin", "setTimeoutInSeconds", [seconds]);
	}
}

//cranberrygame start
module.exports = new RevMob();
//cranberrygame end
