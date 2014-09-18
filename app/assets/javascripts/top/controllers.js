define([], function () {
    'use strict';

    var HomeCtrl = function ($scope, $rootScope, $location, Products) {
        $rootScope.pageTitle = 'Welcome';
        $scope.products = Products.query();
        $rootScope.hasHeader = true;
        $rootScope.hasFooter = true;
    };
    HomeCtrl.$inject = ['$scope', '$rootScope', '$location', 'Product'];

    return {
        HomeCtrl: HomeCtrl
    };

});