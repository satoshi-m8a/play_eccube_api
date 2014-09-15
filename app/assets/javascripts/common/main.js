define(['angular', './directives/header', './directives/block', './directives/product', './resources/products'],
    function (angular) {
        'use strict';
        return angular.module('ec3.common', [
            'common.directives.header',
            'common.directives.block',
            'common.directives.product',
            'common.resources.products'
        ]);
    }
);