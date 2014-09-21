define(['angular',
        'admin/directives/header',
        'admin/directives/footer'
    ],
    function (angular) {
        'use strict';
        return angular.module('ec3.admin', [
            'admin.directives.header',
            'admin.directives.footer'
        ]);
    }
);