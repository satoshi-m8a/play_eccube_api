define(['angular', './routes' ], function (angular, routes) {
    'use strict';

    return angular.module('ec3.admin.login', ['ngRoute', 'admin.login.routes']);
});