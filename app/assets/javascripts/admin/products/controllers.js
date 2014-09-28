define(['angular'], function (angular) {
    'use strict';

    var ProductsCtrl = function ($scope, $rootScope, $location, Products) {
        $rootScope.pageTitle = 'Product List';
        $scope.products = Products.query();
    };
    ProductsCtrl.$inject = ['$scope', '$rootScope', '$location', 'Product'];


    var ProductCtrl = function ($scope, $rootScope, $location, Products, Category) {
        $rootScope.pageTitle = 'Product List';
        //$scope.products = Products.query();
        $scope.categories = Category.query();

    };
    ProductCtrl.$inject = ['$scope', '$rootScope', '$location', 'Product', 'Category'];


    var CategoryCtrl = function ($scope, $rootScope, $location, Category) {
        var rootCategory = {name: 'ホーム', id: 0};

        $rootScope.pageTitle = 'Category List';
        $scope.categoryTree = Category.tree();
        $scope.selectedCategory = rootCategory;
        $scope.children = $scope.categoryTree;
        rootCategory.children = $scope.categoryTree;

        var findCategoryByParentId = function (parentCategoryId) {
            var search = function (items) {
                var findItem = null;
                angular.forEach(items, function (item) {
                    if (item.id === parentCategoryId) {
                        findItem = item;
                    }
                });
                if (findItem) {
                    return findItem;
                } else {
                    angular.forEach(items, function (item) {
                        var s = search(item.children);
                        if (s) {
                            findItem = s;
                        }
                    });
                    return findItem;
                }
            };

            return search($scope.categoryTree);
        };

        $scope.selectCategoryItem = function (category) {
            $scope.selectedCategory = category;
            $scope.children = $scope.selectedCategory.children;
            $scope.parent = findCategoryByParentId($scope.selectedCategory.parentCategoryId);
            $scope.$broadcast('selectCategoryItem', category);
        };

        $scope.selectRoot = function () {
            $scope.selectedCategory = rootCategory;
            $scope.children = $scope.categoryTree;
            $scope.parent = null;
            $scope.$broadcast('selectCategoryItem', rootCategory);
        };

        $scope.$on('selectCategoryItem', function () {
            $scope.editing = false;
        });

        $scope.categoryName = '';
        $scope.addEnabled = function () {
            return $scope.categoryName.length > 0;
        };

        $scope.addCategory = function (name) {
            var newCategory = Category.save({name: name, parentCategoryId: $scope.selectedCategory.id});
            $scope.children.push(newCategory);
            $scope.categoryName = '';
        };

        var swap = function (cat1, cat2) {
            var tmp = cat1.rank;
            cat1.rank = cat2.rank;
            cat2.rank = tmp;
            $scope.children.sort(function (a, b) {
                return b.rank - a.rank;
            });
        };

        $scope.up = function (cat1, cat2) {
            swap(cat1, cat2);
            Category.up({categoryId: cat1.id});
        };

        $scope.down = function (cat1, cat2) {
            swap(cat1, cat2);
            Category.down({categoryId: cat1.id});
        };

        $scope.remove = function (cat) {
            var remove = function (cat) {
                if (cat.children && cat.children.length) {
                    angular.forEach(cat.children, function (cat) {
                        remove(cat.children);
                    });
                    cat.children = [];
                } else {
                    cat = null;
                }
            };

            $scope.selectedCategory.children.splice($scope.selectedCategory.children.indexOf(cat), 1);
            remove(cat);

            Category.remove({categoryId: cat.id});
        };

        $scope.editing = false;
        $scope.editedCategoryName = $scope.selectedCategory.name;
        $scope.edit = function () {
            if ($scope.selectedCategory.id < 1) {
                return;
            }

            $scope.editing = !$scope.editing;
            if ($scope.editing) {
                $scope.editedCategoryName = $scope.selectedCategory.name;
                $scope.$broadcast('onEditStart');
            }
        };

        $scope.submitEnabled = function () {
            return $scope.editedCategoryName.length > 0 && (!hasSameName() || $scope.editedCategoryName === $scope.selectedCategory.name);
        };

        $scope.hasInput = function () {
            return $scope.editedCategoryName.length > 0;
        };

        $scope.hasSameName = function () {
            return hasSameName() && $scope.editedCategoryName !== $scope.selectedCategory.name;
        };


        var hasSameName = function () {
            var parent = findCategoryByParentId($scope.selectedCategory.parentCategoryId);

            var children = parent ? parent.children : $scope.categoryTree;

            var ret = false;
            angular.forEach(children, function (item) {
                if (item.name === $scope.editedCategoryName) {
                    ret = true;
                }
            });

            return ret;
        };


        $scope.updateCategoryName = function () {
            if ($scope.selectedCategory.name === $scope.editedCategoryName) {
                $scope.editing = false;
                return;
            }
            if (hasSameName()) {
                return;
            }

            $scope.editing = false;
            $scope.selectedCategory.name = $scope.editedCategoryName;
            Category.update({categoryId: $scope.selectedCategory.id, name: $scope.selectedCategory.name});
        };

    };
    CategoryCtrl.$inject = ['$scope', '$rootScope', '$location', 'Category'];

    return {
        ProductsCtrl: ProductsCtrl,
        ProductCtrl: ProductCtrl,
        CategoryCtrl: CategoryCtrl
    };

});