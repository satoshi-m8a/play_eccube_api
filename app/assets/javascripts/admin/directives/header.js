define(['angular'], function (angular) {
    'use strict';

    return angular.module('admin.directives.header', [])
        .directive('ec3AdminHeader', ['$log', function ($log) {
            return {
                restrict: 'E',
                replace: true,
                templateUrl: '/admin/blocks/header.html',
                link: function (/*scope, el, attrs*/) {
                    $log.info('load header directive');
                }
            };
        }]);
});
