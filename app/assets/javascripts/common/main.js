define(['angular',
        './directives/header',
        './directives/footer',
        './directives/block',
        './directives/product',
        './directives/includeReplace',
        './directives/carousel',
        './resources/products',
        './resources/pages',
        './services/pages',
        './services/auth'
    ],
    function (angular) {
        'use strict';
        return angular.module('ec3.common', [
            'common.directives.header',
            'common.directives.footer',
            'common.directives.block',
            'common.directives.product',
            'common.directives.includeReplace',
            'common.directives.carousel',
            'common.resources.products',
            'common.resources.pages',
            'common.services.pages',
            'common.services.auth'
        ]);
    }
);