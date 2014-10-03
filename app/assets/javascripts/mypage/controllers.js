define([], function () {
    'use strict';

    var MyPageLoginCtrl = function ($scope, $rootScope, $location) {
        $rootScope.pageTitle = 'MyPage Login';
        $rootScope.hasHeader = true;
        $rootScope.hasFooter = true;
    };
    MyPageLoginCtrl.$inject = ['$scope', '$rootScope', '$location'];

    return {
        MyPageLoginCtrl: MyPageLoginCtrl
    };

});