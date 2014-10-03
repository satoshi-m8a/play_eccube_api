define(['angular', './controllers', 'common/main'], function (angular, controllers) {
    'use strict';

    return angular.module('top.routes', ['ec3.common', 'ec3.top.controllers'])
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/', {templateUrl: '/pages/top.html', controller: 'TopCtrl'});
        }]);
});