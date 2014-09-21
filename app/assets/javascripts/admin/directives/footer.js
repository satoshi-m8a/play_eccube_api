define(['angular'], function (angular) {
    'use strict';

    return angular.module('admin.directives.footer', [])
        .directive('ec3AdminFooter', ['$log', function ($log) {
            return {
                restrict: 'E',
                replace: true,
                templateUrl: '/admin/blocks/footer.html',
                link: function (/*scope, el, attrs*/) {
                    $log.info('load header directive');
                }
            };
        }]);
});
