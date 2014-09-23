define(['angular'], function (angular) {
    'use strict';

    return angular.module('common.directives.categoryTree', [])
        .directive('ec3CategoryTree', ['$log', function ($log) {
            return {
                restrict: 'E',
                replace: true,
                scope: {
                    categories: '='
                },
                templateUrl: function (el, attr) {
                    var base = attr.base || '';
                    var blockName = attr.blockName || 'default';
                    return base + '/blocks/category/' + blockName + '.html';
                },
                link: function (scope, el, attrs) {
                    $log.info('load category tree directive');
                    scope.selectCategoryItem = function (category) {
                        if (scope.$parent.selectCategoryItem) {
                            scope.$parent.selectCategoryItem(category);
                        }
                    };
                }
            };
        }]);
});
