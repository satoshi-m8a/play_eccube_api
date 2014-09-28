define(['angular'], function (angular) {
    'use strict';

    return angular.module('common.directives.header', [])
        .directive('ec3Header', ['$log', '$location', function ($log, $location) {
            return {
                restrict: 'E',
                replace: true,
                templateUrl: '/blocks/header.html',
                link: function (scope) {
                    $log.info('load header directive');
                    scope.searchProduct = function (word) {
                        $location.path('/products/search').search('query', word);
                    };
                }
            };
        }]);
});
