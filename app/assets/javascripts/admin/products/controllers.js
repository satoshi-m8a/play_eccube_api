define([], function () {
    'use strict';

    var ProductsCtrl = function ($scope, $rootScope, $location, Products) {
        $rootScope.pageTitle = 'Product List';
        $scope.products = Products.query();
    };
    ProductsCtrl.$inject = ['$scope', '$rootScope', '$location', 'Product'];

    return {
        ProductsCtrl: ProductsCtrl
    };

});