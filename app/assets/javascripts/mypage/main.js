define(['angular', './routes', './controllers'], function (angular, routes) {
    'use strict';

    return angular.module('ec3.mypage', ['ngRoute', 'mypage.routes']);
});