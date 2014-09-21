define(['angular', 'common/main', 'admin/directives/main'], function (angular) {
    'use strict';

    return angular.module('admin.dashboard.routes', ['ec3.common', 'ec3.admin'])
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/admin', {
                    templateUrl: '/admin/pages/dashboard.html',
                    controller: ['$scope', '$rootScope', function ($scope, $rootScope) {
                    }]
                });
        }]);
});