'use strict';

angular.module('myApp').controller('RegisteredController', ['$scope', '$window', 'RegisteredService', function ($scope, $window, RegisteredService) {
    var self = this;
    self.user = {
      name: '',
      email: '',
      password: ''
    };

    self.submit = registered;
    self.reset = reset;

    function createUser(user) {
        RegisteredService.createUser(user)
            .then(
                function (response) {
                    console.log("Registered Successfully");
                    $window.location.href = "/index.html";
                },
                function (errResponse) {
                    alert(errResponse.data.errorMessage);
                    console.error('Error while creating User');
                }
            )
    }

    function registered() {
        createUser(self.user);
    }

    function reset() {
        self.user = {
            name: '',
            email: '',
            password: ''
        };

        // reset form
        $scope.myForm.$setPristine();

    }
}]);