'use strict';
angular.module('myApp').controller('LoginController', ['$scope', '$window', 'LoginService', function ($scope, $window, LoginService) {
    var self = this;
    self.user = {
        name: '',
        password: ''
    };

    self.submit = login;
    self.reset = reset;

    function login() {
        LoginService.verifyUser(self.user)
            .then(
                function (response) {
                    console.log("Login Successfully");
                    $window.location.href = '/index.html';
                },
                function (errResponse) {
                    alert(errResponse.data.errorMessage);
                    console.error('Error while Login');
                }
            )
    }

    function reset() {
        self.user = {
            name: '',
            password: ''
        };

        $scope.loginForm.$setPristine();
    }

}]);