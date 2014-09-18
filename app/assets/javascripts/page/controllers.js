define([], function () {
    'use strict';

    var PageCtrl = function ($scope, $rootScope, $location, page) {
        $rootScope.pageTitle = page.data.name;
        $scope.templateUrl = '/pages/' + page.data.fileName + '.html';
        $rootScope.hasHeader = page.data.hasHeader;
        $rootScope.hasFooter = page.data.hasFooter;
    };
    PageCtrl.$inject = ['$scope', '$rootScope', '$location', 'page'];

    return {
        PageCtrl: PageCtrl
    };

});