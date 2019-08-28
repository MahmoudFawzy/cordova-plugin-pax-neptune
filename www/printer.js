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
  printTest: function (onSuccess, onError) {
    exec(onSuccess, onError, "PaxPos", "print_test", []);
  },
  printText: function (onSuccess, onError) {
    exec(onSuccess, onError, "PaxPos", "print_text", []);
  },
  printJson: function (onSuccess, onError) {
    exec(onSuccess, onError, "PaxPos", "print_json", []);
  }
};
module.exports = printer;