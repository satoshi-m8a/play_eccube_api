define(['angular'], function (angular) {
    'use strict';

    return angular.module('common.directives.footer', [])
        .directive('ec3Footer', ['$log', function ($log) {
            return {
                restrict: 'E',
                templateUrl: '/blocks/footer.html',
                link: function (/*scope, el, attrs*/) {
                    $log.info('load footer directive');
                }
            };
        }]);
});
