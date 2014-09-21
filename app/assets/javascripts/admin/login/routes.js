define(['angular', 'common/main'], function (angular) {
    'use strict';

    return angular.module('admin.login.routes', ['ec3.common'])
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/admin/login', {templateUrl: '/admin/pages/login.html'});
        }]);
});