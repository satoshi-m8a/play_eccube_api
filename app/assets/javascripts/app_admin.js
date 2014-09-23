define(['angular', './admin/dashboard/main', './admin/login/main', './admin/products/main', './admin/services/main'], function (angular) {
    'use strict';

    return angular.module('ec3', ['ec3.admin.dashboard', 'ec3.admin.login', 'ec3.admin.products', 'ec3.admin.services'])
        .config(['$locationProvider', '$routeProvider', function ($locationProvider, $routeProvider) {
            $locationProvider.html5Mode(true);
            $routeProvider.otherwise({ templateUrl: '/pages/404.html' });
        }])
        .run(['$rootScope', '$location', 'AdminAuthService', function ($rootScope, $location, AdminAuthService) {
            $rootScope.$on('$routeChangeStart', function (event, next) {
                if (next.originalPath === '/admin/login') {
                    $rootScope.hasHeader = false;
                    $rootScope.hasFooter = false;
                } else {
                    $rootScope.hasHeader = true;
                    $rootScope.hasFooter = true;
                }

                if (!AdminAuthService.isLoggedIn() && next.originalPath !== '/admin/login') {
                    $location.path('/admin/login');
                }

                if (AdminAuthService.isLoggedIn() && next.originalPath === '/admin/login') {
                    $location.path('/admin');
                }

            });


            $rootScope.$on('$routeChangeError', function (event, current, previous, error) {
                if (error.status !== '200') {
                    $rootScope.errorTemplateUrl = '/pages/404.html';
                }
            });
        }]);
});