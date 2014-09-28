define(['angular'], function (angular) {
    'use strict';

    return angular.module('common.directives.focusOn', [])
        .directive('focusOn', function () {
            return {
                restrict: 'A',
                link: function (scope, elem, attrs) {
                    scope.$on(attrs.focusOn, function (e) {
                        setTimeout(function () {
                            elem[0].focus();
                            elem.select();
                        }, 1);
                    });
                }
            };
        });
});
