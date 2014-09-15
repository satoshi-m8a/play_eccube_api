define(['angular', './routes', './controllers'], function (angular, routes, controllers) {
    'use strict';

    return angular.module('ec3.top', ['ngRoute', 'top.routes', 'common.resources.products'])
        .controller('FooterCtrl', controllers.FooterCtrl);
});