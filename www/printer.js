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
  printText: function (json, onSuccess, onError) {
    exec(onSuccess, onError, "PaxPos", "printText", [json]);
  },
  printTest: function (json, onSuccess, onError) {

    exec(onSuccess, onError, "PaxPos", "printTest", [json]);
  },
  printJson: function (json, onSuccess, onError) {
    exec(onSuccess, onError, "PaxPos", "printJson", [json]);
  }
};
module.exports = printer;