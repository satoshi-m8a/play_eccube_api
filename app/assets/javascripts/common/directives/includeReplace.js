define(['angular'], function (angular) {
    'use strict';

    return angular.module('common.directives.includeReplace', [])
        .directive('includeReplace', function () {
            return {
                require: 'ngInclude',
                restrict: 'A',
                link: function (scope, el) {
                    el.replaceWith(el.children());
                }
            };
        });
});
