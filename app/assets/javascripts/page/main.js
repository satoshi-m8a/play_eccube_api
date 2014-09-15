define(['angular', './routes', './controllers'], function (angular, routes, controllers) {
    'use strict';

    return angular.module('ec3.page', ['ngRoute', 'page.routes'])
        .controller('PageCtrl', controllers.PageCtrl);
});