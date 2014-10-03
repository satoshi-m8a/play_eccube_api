define(['angular'], function (angular) {
    'use strict';

    return angular.module('ec3.top.controllers', []).
        controller("TopCtrl", ['$scope', '$rootScope', '$location', 'Product', function ($scope, $rootScope, $location, Products) {
            $rootScope.pageTitle = 'Welcome';
            $scope.products = Products.query();
            $rootScope.hasHeader = true;
            $rootScope.hasFooter = true;
        }]
    );

});