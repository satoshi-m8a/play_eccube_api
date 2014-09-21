define(['angular'], function (angular) {
    'use strict';

    return angular.module('common.directives.product', [])
        .directive('ec3Product', ['$log', function ($log) {
            return {
                restrict: 'E',
                replace: true,
                require: 'ngModel',
                templateUrl: function (el, attr) {
                    var base = attr.base || '';
                    var blockName = attr.blockName || 'default';
                    return base + '/blocks/product/' + blockName + '.html';
                },
                link: function (scope, el, attrs) {
                    $log.info('load product directive');
                }
            };
        }]);
});
