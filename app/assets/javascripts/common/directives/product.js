define(['angular'], function (angular) {
    'use strict';

    return angular.module('common.directives.product', [])
        .directive('ec3Product', ['$log', function ($log) {
            return {
                restrict: 'E',
                require: 'ngModel',
                templateUrl: function (el, attr) {
                    var blockName = attr.blockName || 'product';
                    return '/blocks/' + blockName + '.html';
                },
                link: function (/*scope, el, attrs*/) {
                    $log.info('load product directive');
                }
            };
        }]);
});
