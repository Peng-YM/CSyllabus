'use strict';
angular.module('myApp', ['ngCookies']).controller('IndexController', ['$cookieStore', function ($cookieStore) {
    var self = this;
    self.userName = $cookieStore.get('id');
}]);