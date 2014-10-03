/*global describe beforeEach it expect */

define([
    'angular',
    'angularMocks',
    'app'
], function (angular, mocks, app) {
    'use strict';

    describe('TopCtrl', function () {
        var $q,
            $rootScope,
            $scope,
            mockProduct,
            TopCtrl,
            queryDeferred;

        beforeEach(mocks.module('ec3.top.controllers'));

        beforeEach(inject(function (_$q_, _$rootScope_) {
            $q = _$q_;
            $rootScope = _$rootScope_;
        }));

        beforeEach(inject(function ($controller) {
            $scope = $rootScope.$new();

            mockProduct = {
                query: function () {
                    queryDeferred = $q.defer();
                    return {$promise: queryDeferred.promise};
                }
            };

            spyOn(mockProduct, 'query').andCallThrough();

            $controller('TopCtrl', {
                $scope: $scope,
                Product: mockProduct
            });
        }));


        it('should set page title', function () {
            expect($rootScope.pageTitle).toEqual('Welcome');
        });
    });

});