define(['angular'], function (angular) {
    'use strict';

    return angular.module('common.directives.header', [])
        .directive('ec3Header', ['$log', '$location', 'ProductSearch', function ($log, $location, ProductSearch) {
            return {
                restrict: 'E',
                replace: true,
                templateUrl: '/blocks/header.html',
                link: function ($scope) {
                    $log.info('load header directive');
                    $scope.searchProduct = function (word) {
                        $location.path('/products/search').search('query', word);
                    };

                    $scope.preSearchProduct = function (word) {
                        ProductSearch.preSearch(word).success(function (data) {
                            $scope.hits = data.hits;
                        });
                    };

                }
            };
        }]);
});
