define(['angular', './controllers', 'common/main'], function (angular, controllers) {
    'use strict';

    return angular.module('mypage.routes', ['ec3.common'])
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/mypage/login', {templateUrl: '/pages/mypage/login.html', controller: controllers.MyPageLoginCtrl});
        }]);
});