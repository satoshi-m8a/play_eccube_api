(function (requirejs) {
    'use strict';
    requirejs.config({
        paths: {
            jquery: '../lib/jquery/jquery',
            bootstrap: '../lib/bootstrap/js/bootstrap',
            angular: '../lib/angularjs/angular',
            'angular-route': '../lib/angularjs/angular-route',
            'angular-resource': '../lib/angularjs/angular-resource'
        },
        shim: {
            jquery: {
                exports: '$'
            },
            bootstrap: {
                deps: ['jquery']
            },
            angular: {
                deps: ['jquery'],
                exports: 'angular'
            },
            'angular-route': ['angular'],
            'angular-resource': ['angular']
        }
    });

    require(['angular', 'angular-route', 'angular-resource', 'jquery', 'bootstrap', './app'], function (angular) {
        angular.bootstrap(document, ['ec3']);
    });

})(requirejs);