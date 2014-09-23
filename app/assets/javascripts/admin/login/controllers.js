define([], function () {
    'use strict';

    var LoginCtrl = function ($scope, $rootScope, $location, AdminAuthService) {
        $rootScope.pageTitle = 'Login';

        $scope.disabled = false;
        $scope.error = false;

        $scope.login = function () {
            $scope.disabled = true;
            AdminAuthService.login($scope.loginId, $scope.password).then(function () {

                //Session Cookieが即時に反映されないため
                setTimeout(function () {
                    $location.path('/admin');
                }, 1);
            }).catch(function () {
                $scope.error = true;

                //フォーカスを当てる。
                $scope.$broadcast('onError');

            }).finally(function () {
                $scope.disabled = false;
            });
        };

    };
    LoginCtrl.$inject = ['$scope', '$rootScope', '$location', 'AdminAuthService'];

    var LogoutCtrl = function ($scope, $rootScope, $location, Session) {
        $rootScope.pageTitle = 'Logout';

        Session.clear();

        $location.path('/admin/login');
    };

    LogoutCtrl.$inject = ['$scope', '$rootScope', '$location', 'Session'];


    return {
        LoginCtrl: LoginCtrl,
        LogoutCtrl: LogoutCtrl
    };

});