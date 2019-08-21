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
  printTest: function (txt, onSuccess, onError) {
    exec(onSuccess, onError, "PaxPos", "print_test", [txt]);
  },
  printText: function (txt, onSuccess, onError) {
    exec(onSuccess, onError, "PaxPos", "print_text", [txt]);
  },
  printJson: function (json, onSuccess, onError) {
    exec(onSuccess, onError, "PaxPos", "print_json", [json]);
  }
};
module.exports = printer;