define(['angular'], function (angular) {
    'use strict';

    return angular.module('common.services.productSearch', [])
        .factory('ProductSearch', [ '$http', function ($http) {
            return {
                preSearch: function (word) {
                    return $http.get('/api/v1/products/pre_search', {
                        params: { query: word }
                    });
                },
                search: function (word) {
                    return $http.get('/api/v1/products/search', {
                        params: { query: word }
                    });
                }

            };
        }]);
});