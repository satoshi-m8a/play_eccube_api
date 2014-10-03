(function (requirejs) {
    'use strict';
    requirejs.config({
        paths: {
            jquery: '../lib/jquery/jquery',
            bootstrap: '../lib/bootstrap/js/bootstrap',
            angular: '../lib/angularjs/angular',
            'angular-route': '../lib/angularjs/angular-route',
            'angular-resource': '../lib/angularjs/angular-resource',
            'angular-cookies': '../lib/angularjs/angular-cookies'
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
            'angular-resource': ['angular'],
            'angular-cookies': ['angular']
        }
    });

    require(['angular', 'angular-route', 'angular-resource', 'angular-cookies', 'jquery', 'bootstrap', './app'], function (angular) {
        angular.bootstrap(document, ['ec3']);

    });

})(requirejs);