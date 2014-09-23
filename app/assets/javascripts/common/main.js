define(['angular',
        './directives/header',
        './directives/footer',
        './directives/block',
        './directives/product',
        './directives/categoryTree',
        './directives/categoryTreeItem',
        './directives/includeReplace',
        './directives/focusOn',
        './directives/carousel',
        './resources/categories',
        './resources/products',
        './resources/pages',
        './services/pages',
        './services/auth',
        './services/session'
    ],
    function (angular) {
        'use strict';
        return angular.module('ec3.common', [
            'common.directives.header',
            'common.directives.footer',
            'common.directives.block',
            'common.directives.product',
            'common.directives.categoryTree',
            'common.directives.categoryTreeItem',
            'common.directives.includeReplace',
            'common.directives.focusOn',
            'common.directives.carousel',
            'common.resources.categories',
            'common.resources.products',
            'common.resources.pages',
            'common.services.pages',
            'common.services.auth',
            'common.services.session'
        ]);
    }
);