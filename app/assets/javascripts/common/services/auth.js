define(['angular'], function (angular) {
    'use strict';

    return angular.module('common.services.auth', [])
        .factory('AuthService', [ '$http', function ($http) {
            return {
                isLoggedIn: function () {
                    console.log('isloggedin');
                    return true;
                }
            };
        }]);
});