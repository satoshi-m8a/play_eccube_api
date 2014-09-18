define(['angular', './routes', './controllers'], function (angular, routes) {
    'use strict';

    return angular.module('ec3.top', ['ngRoute', 'top.routes', 'common.resources.products']);
});