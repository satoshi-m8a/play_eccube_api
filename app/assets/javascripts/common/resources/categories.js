define(['angular'], function (angular) {
    'use strict';

    return angular.module('common.resources.categories', ['ngResource'])
        .factory('Category', ['$resource',
            function ($resource) {
                return $resource('/api/v1/categories/:categoryId', {}, {
                    query: {method: 'GET', isArray: true},
                    tree: {method: 'GET', params: {categoryId: 'tree'}, isArray: true},
                    save: {method: 'POST'},
                    remove: {method: 'DELETE'}
                });
            }
        ]);
});
