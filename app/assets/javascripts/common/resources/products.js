define(['angular'], function (angular) {
    'use strict';

    return angular.module('common.resources.products', ['ngResource'])
        .factory('Product', ['$resource',
            function ($resource) {
                return $resource('/api/v1/products/:id', {}, {
                    query: {method: 'GET', transformResponse: function (data) {
                        return angular.fromJson(data).products;
                    }, isArray: true},
                    save: {method: 'POST'},
                    remove: {method: 'DELETE'}
                });
            }
        ]);
});
