define(['angular', './controllers', 'common/main'], function (angular, controllers) {
    'use strict';

    return angular.module('page.routes', ['ec3.common', 'ngRoute'])
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/:fileName*', {
                    template: '<div ng-include="templateUrl" include-replace></div>',
                    controller: controllers.PageCtrl,
                    resolve: {
                        page: ['$route', 'PageService', function ($route, PageService) {
                            return PageService.getPageInfo($route.current.params.fileName);
                        }]
                    }
                });
        }]);
});