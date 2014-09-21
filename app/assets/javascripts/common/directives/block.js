define(['angular', './controllers'], function (angular, controllers) {
    'use strict';

    return angular.module('common.directives.block', [])
        .directive('ec3Block', ['$log', function ($log) {
            return {
                restrict: 'E',
                replace: true,
                controller: ['$scope', '$element', '$attrs', function ($scope, $element, $attrs) {
                }],
                templateUrl: function (el, attr) {
                    return '/blocks/' + attr.blockName + '.html';
                },
                link: function (/*scope, element, attrs, controller, trancludeFn*/) {
                    $log.info('load block directive');

                }
            };
        }]);
});
