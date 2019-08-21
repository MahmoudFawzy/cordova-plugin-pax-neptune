var exec = require("cordova/exec");

var printer = {
  platforms: ["android"],

  isSupported: function () {
    if (window.device) {
      var platform = window.device.platform;
      if (platform !== undefined && platform !== null) {
        return this.platforms.indexOf(platform.toLowerCase()) >= 0;
      }
    }
    return false;
  },
  printText: function (parms, onSuccess, onError) {
    exec(onSuccess, onError, "PaxPos", "printText", parms);
  },
  printTest: function (parms, onSuccess, onError) {
    exec(onSuccess, onError, "PaxPos", "printTest", parms);
  },
  printJson: function (parms, onSuccess, onError) {
    exec(onSuccess, onError, "PaxPos", "printJson", parms);
  }
};
module.exports = printer;