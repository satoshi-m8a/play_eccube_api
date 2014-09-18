define(['angular', './routes', './controllers'], function (angular, routes) {
    'use strict';

    return angular.module('ec3.products', ['ngRoute', 'products.routes', 'common.resources.products']);
});