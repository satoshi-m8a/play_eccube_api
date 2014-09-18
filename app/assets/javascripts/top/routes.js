define(['angular', './controllers', 'common/main'], function (angular, controllers) {
    'use strict';

    return angular.module('top.routes', ['ec3.common'])
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/', {templateUrl: '/pages/top.html', controller: controllers.HomeCtrl});
        }]);
});