define(['angular'], function (angular) {
    'use strict';

    return angular.module('common.resources.categories', ['ngResource'])
        .factory('Category', ['$resource',
            function ($resource) {
                return $resource('/api/v1/categories/:categoryId/:method', {categoryId: '@categoryId', method: '@method'}, {
                    query: {method: 'GET', isArray: true},
                    tree: {method: 'GET', params: {categoryId: 'tree'}, isArray: true},
                    save: {method: 'POST'},
                    update: {method: 'PUT'},
                    up: {method: 'POST', params: {method: 'up'}},
                    down: {method: 'POST', params: {method: 'down'}},
                    remove: {method: 'DELETE'}
                });
            }
        ]);
});
