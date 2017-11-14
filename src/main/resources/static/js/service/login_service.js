'use strict';

angular.module('myApp').factory('LoginService', ['$http', '$q', function ($http, $q) {
    var REST_SERVICE_URI = '/api/user/login/';

    var factory = {
        verifyUser: verifyUser
    };

    return factory;

    function verifyUser(user) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI, user)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('Error while login');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }
}]);