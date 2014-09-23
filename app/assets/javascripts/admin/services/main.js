define(['angular',
        'admin/services/auth'
    ],
    function (angular) {
        'use strict';
        return angular.module('ec3.admin.services', [
            'admin.services.auth'
        ]);
    }
);