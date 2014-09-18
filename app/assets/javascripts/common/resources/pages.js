define(['angular'], function (angular) {
    'use strict';

    return angular.module('common.resources.pages', ['ngResource'])
        .factory('Page', ['$resource',
            function ($resource) {
                return $resource('/api/v1/pages/:fileName', {}, {
                    query: {method: 'GET', transformResponse: function (data) {
                        return angular.fromJson(data).pages;
                    }, isArray: true},
                    save: {method: 'POST'},
                    remove: {method: 'DELETE'}
                });
            }
        ]);
});
