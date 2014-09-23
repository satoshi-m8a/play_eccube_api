define([], function () {
    'use strict';

    var ProductsCtrl = function ($scope, $rootScope, $location, Products) {
        $rootScope.pageTitle = 'Product List';
        $scope.products = Products.query();
    };
    ProductsCtrl.$inject = ['$scope', '$rootScope', '$location', 'Product'];


    var ProductCtrl = function ($scope, $rootScope, $location, Products) {
        $rootScope.pageTitle = 'Product List';
        //$scope.products = Products.query();
    };
    ProductCtrl.$inject = ['$scope', '$rootScope', '$location', 'Product'];


    var CategoryCtrl = function ($scope, $rootScope, $location, Category) {
        var rootCategory = {name: 'ホーム', id: 0};

        $rootScope.pageTitle = 'Category List';
        $scope.categoryTree = Category.tree();
        $scope.selectedCategory = rootCategory;
        $scope.children = $scope.categoryTree;

        $scope.selectCategoryItem = function (category) {
            $scope.selectedCategory = category;
            $scope.children = $scope.selectedCategory.children;

            $scope.$broadcast('selectCategoryItem', category);
        };

        $scope.selectRoot = function () {
            $scope.selectedCategory = rootCategory;
            $scope.children = $scope.categoryTree;
            $scope.$broadcast('selectCategoryItem', rootCategory);
        };
    };
    CategoryCtrl.$inject = ['$scope', '$rootScope', '$location', 'Category'];

    return {
        ProductsCtrl: ProductsCtrl,
        ProductCtrl: ProductCtrl,
        CategoryCtrl: CategoryCtrl
    };

});