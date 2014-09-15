define([], function () {
    'use strict';

    var PageCtrl = function ($scope, $rootScope) {
        $rootScope.pageTitle = 'Welcome';
    };
    PageCtrl.$inject = ['$scope', '$rootScope', '$location'];

    return {
        PageCtrl: PageCtrl
    };

});