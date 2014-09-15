define(['angular'], function (angular) {
    'use strict';

    return angular.module('common.directives.header', [])
        .directive('ec3Header', ['$log', function ($log) {
            return {
                restrict: 'E',
                templateUrl: '/blocks/header.html',
                link: function (/*scope, el, attrs*/) {
                    $log.info('load header directive');
                }
            };
        }]);
});
