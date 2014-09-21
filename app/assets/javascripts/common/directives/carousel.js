define(['angular'], function (angular) {
    'use strict';

    return angular.module('common.directives.carousel', [])
        .directive('ec3Carousel', ['$log', function ($log) {
            return {
                restrict: 'E',
                replace: true,
                templateUrl: function (el, attr) {
                    var blockName = attr.blockName || 'default';
                    return '/blocks/carousel/' + blockName + '.html';
                },
                link: function (scope, el, attrs) {
                    $log.info('load carousel directive');
                    scope.id = attrs.id;
                }
            };
        }]);
});
