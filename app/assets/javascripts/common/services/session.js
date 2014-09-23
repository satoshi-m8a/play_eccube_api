define(['angular'], function (angular) {
    'use strict';

    return angular.module('common.services.session', ['ngCookies'])
        .factory('Session', [ '$cookies', function ($cookies) {

            var SESSION_KEY = 'PLAY_SESSION';

            var getSessionCookie = function () {
                var rawCookie = $cookies[SESSION_KEY];
                if (!rawCookie) {
                    return {};
                }
                var rawData = rawCookie.substring(rawCookie.indexOf('-') + 1, rawCookie.length - 1);
                var session = {};
                angular.forEach(rawData.split("&"), function (rawPair) {
                    var pair = rawPair.split('=');
                    session[pair[0]] = pair[1];
                });

                return session;
            };

            var clear = function () {
                delete $cookies[SESSION_KEY];
            };

            return {
                getSessionCookie: getSessionCookie,
                clear: clear
            };
        }]);
});