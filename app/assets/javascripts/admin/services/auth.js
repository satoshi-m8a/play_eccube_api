define(['angular'], function (angular) {
    'use strict';

    return angular.module('admin.services.auth', [])
        .factory('AdminAuthService', [ '$http', 'Session', function ($http, Session) {
            return {
                isLoggedIn: function () {
                    var session = Session.getSessionCookie();
                    return session.mid ? true : false;
                },
                login: function (loginId, password) {
                    return $http.post('/api/v1/members/login', {loginId: loginId, password: password});
                }
            };
        }]);
});