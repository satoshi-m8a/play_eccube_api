define([], function () {
    'use strict';

    var ProductListCtrl = function ($scope, $rootScope, products) {
        $rootScope.pageTitle = 'Product List';
        $scope.products = products;
        $rootScope.hasHeader = true;
        $rootScope.hasFooter = true;
    };
    ProductListCtrl.$inject = ['$scope', '$rootScope', 'products'];

    var DetailCtrl = function ($scope, $rootScope, $routeParams, product) {
        $rootScope.pageTitle = 'Product Detail';
        $scope.product = product;
        $rootScope.hasHeader = true;
        $rootScope.hasFooter = true;
    };
    DetailCtrl.$inject = ['$scope', '$rootScope', '$routeParams', 'product'];

    var ProductSearchCtrl = function ($scope, $rootScope, $routeParams, ProductSearch) {
        $rootScope.pageTitle = 'Search Results';
        $rootScope.hasHeader = true;
        $rootScope.hasFooter = true;

        $scope.searchWord = $routeParams.query;
        ProductSearch.search($scope.searchWord).success(function (data) {
            $scope.products = data;
        });
    };
    ProductSearchCtrl.$inject = ['$scope', '$rootScope', '$routeParams', 'ProductSearch'];

    return {
        ProductListCtrl: ProductListCtrl,
        DetailCtrl: DetailCtrl,
        ProductSearchCtrl: ProductSearchCtrl
    };

});