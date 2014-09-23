define(['angular', 'common/main', './controllers', 'admin/services/main'], function (angular, common, controllers) {
    'use strict';

    return angular.module('admin.login.routes', ['ec3.common', 'ec3.admin.services'])
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/admin/login', {
                    templateUrl: '/admin/pages/login.html',
                    controller: controllers.LoginCtrl
                }).when('/admin/logout', {
                    templateUrl: '/admin/pages/login.html',
                    controller: controllers.LogoutCtrl
                });
        }]);
});