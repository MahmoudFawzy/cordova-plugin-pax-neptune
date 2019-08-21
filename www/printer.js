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
  printTest: function (json, onSuccess, onError) {
    exec(onSuccess, onError, "PaxPos", "print_test", [JSON.stringify(json)]);
  },
  printText: function (json, onSuccess, onError) {
    exec(onSuccess, onError, "PaxPos", "print_text", [JSON.stringify(json)]);
  },
  printJson: function (json, onSuccess, onError) {
    exec(onSuccess, onError, "PaxPos", "print_json", [JSON.stringify(json)]);
  }
};
module.exports = printer;