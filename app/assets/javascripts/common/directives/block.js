define(['angular', './controllers'], function (angular, controllers) {
    'use strict';

    return angular.module('common.directives.block', [])
        .directive('ec3Block', ['$log', function ($log) {
            return {
                restrict: 'E',
                controller: controllers.BlockCtrl,
                templateUrl: function (el, attr) {
                    return '/blocks/' + attr.blockName + '.html';
                },
                link: function (/*scope, el, attrs*/) {
                    $log.info('load block directive');
                }
            };
        }]);
});
