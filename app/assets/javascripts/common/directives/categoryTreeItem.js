define(['angular'], function (angular) {
    'use strict';

    return angular.module('common.directives.categoryTreeItem', [])
        .directive('ec3CategoryTreeItem', ['$compile', function ($compile) {
            return {
                restrict: 'E',
                replace: true,
                scope: {
                    category: '='
                },
                templateUrl: function (el, attr) {
                    var base = attr.base || 'common';
                    var blockName = attr.blockName || 'tree_item';
                    return base + '/blocks/category/' + blockName + '.html';
                },
                link: function (scope, element, attrs) {
                    element.append($compile('<ul><ec3-category-tree-item ng-repeat="child in category.children | orderBy:\'rank\':true" category="child"></ec3-category-tree-item></ul>')(scope));
                    scope.selectCategoryItem = function (category) {
                        if (scope.$parent.selectCategoryItem) {
                            scope.$parent.selectCategoryItem(category);
                        }
                    };

                    scope.$on('selectCategoryItem', function (event, selectedCategory) {
                        scope.category.selected = selectedCategory.id === scope.category.id;
                    });
                }
            };
        }]);
});
