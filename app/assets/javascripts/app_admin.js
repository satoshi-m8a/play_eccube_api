define(['angular', './admin/dashboard/main', './admin/login/main', './admin/products/main'], function (angular) {
    'use strict';

    return angular.module('ec3', ['ec3.admin.dashboard', 'ec3.admin.login', 'ec3.admin.products'])
        .config(['$locationProvider', '$routeProvider', function ($locationProvider, $routeProvider) {
            $locationProvider.html5Mode(true);
            $routeProvider.otherwise({ templateUrl: '/pages/404.html' });
        }])
        .run(['$rootScope', '$location', 'AuthService', function ($rootScope, $location, AuthService) {
            $rootScope.$on('$routeChangeStart', function (event, next) {
                if (next.originalPath === '/admin/login') {
                    $rootScope.hasHeader = false;
                    $rootScope.hasFooter = false;
                } else {
                    $rootScope.hasHeader = true;
                    $rootScope.hasFooter = true;
                }

                if (!AuthService.isLoggedIn() && next.originalPath !== '/admin/login') {
                    $location.path('/admin/login');
                }
            });


            $rootScope.$on('$routeChangeError', function (event, current, previous, error) {
                if (error.status !== '200') {
                    $rootScope.errorTemplateUrl = '/pages/404.html';
                }
            });
        }]);
});