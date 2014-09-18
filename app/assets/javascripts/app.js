define(['angular', './top/main', './products/main', './page/main'], function (angular) {
    'use strict';

    return angular.module('ec3', ['ec3.top', 'ec3.products', 'ec3.page'])
        .config(['$locationProvider', '$routeProvider', function ($locationProvider, $routeProvider) {
            $locationProvider.html5Mode(true);
            $routeProvider.otherwise({ templateUrl: '/pages/404.html' });
        }])
        .run(['$rootScope', function ($rootScope) {
            $rootScope.$on('$routeChangeError', function (event, current, previous, error) {
                if (error.status !== '200') {
                    $rootScope.errorTemplateUrl = '/pages/404.html';
                }
            });
        }]);
});