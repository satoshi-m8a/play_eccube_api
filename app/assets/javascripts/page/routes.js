define(['angular', './controllers', 'common/main'], function (angular, controllers) {
    'use strict';

    return angular.module('page.routes', ['ec3.common', 'ngRoute'])
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/:pageId', {templateUrl: function (attr) {
                    return '/' + attr.pageId + '.html';
                }, controller: controllers.PageCtrl});
        }]);
});