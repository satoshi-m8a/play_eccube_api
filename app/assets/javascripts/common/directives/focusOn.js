define(['angular'], function (angular) {
    'use strict';

    return angular.module('common.directives.focusOn', [])
        .directive('focusOn', function () {
            return {
                restrict: 'A',
                link: function (scope, elem, attrs) {
                    scope.$on(attrs.focusOn, function (e) {
                        elem[0].focus();
                        elem.select();
                    });
                }
            };
        });
});
