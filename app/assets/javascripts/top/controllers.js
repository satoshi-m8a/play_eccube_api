define([], function () {
    'use strict';

    var HomeCtrl = function ($scope, $rootScope, $location, Products) {
        $rootScope.pageTitle = 'Welcome';
        $scope.products = Products.query();
    };
    HomeCtrl.$inject = ['$scope', '$rootScope', '$location', 'Product'];

    /** Controls the footer */
    var FooterCtrl = function (/*$scope*/) {
    };
    //FooterCtrl.$inject = ['$scope'];

    return {
        FooterCtrl: FooterCtrl,
        HomeCtrl: HomeCtrl
    };

});