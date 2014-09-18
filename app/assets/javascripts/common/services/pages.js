define(['angular'], function (angular) {
    'use strict';

    return angular.module('common.services.pages', [])
        .factory('PageService', [ '$http', function ($http) {
            return {
                getPageInfo: function (pageName) {
                    return $http.get('/api/v1/pages/' + pageName);
                }
            };
        }]);
});