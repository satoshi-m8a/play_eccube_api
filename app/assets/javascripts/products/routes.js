define(['angular', './controllers', 'common/main'], function (angular, controllers) {
    'use strict';

    return angular.module('products.routes', ['ec3.common'])
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/products', {
                    templateUrl: '/pages/products/list.html',
                    controller: controllers.ProductListCtrl,
                    resolve: {
                        products: ['Product', function (Products) {
                            return Products.query().$promise;
                        }]
                    }
                })
                .when('/products/:productId', {
                    templateUrl: '/pages/products/detail.html',
                    controller: controllers.DetailCtrl,
                    resolve: {
                        product: ['$route', 'Product', function ($route, Products) {
                            return Products.get({productId: $route.current.params.productId}).$promise;
                        }]
                    }
                });
        }]);
});