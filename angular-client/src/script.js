'use strict';
  var sApp;

  sApp = angular.module('sApp', ['ngRoute']);

  sApp.config(function($routeProvider) {
    return $routeProvider
    .when('/', {templateUrl: 'h.html'})
    .when('/1', {templateUrl: '1.html'})
    .when('/2', {templateUrl: '2.html'})
    .otherwise({redirectTo: '/'});
  });

  sApp.controller('NavCtrl', function($scope, $location) {
    $scope.isActive = function(viewLocation) {
      return viewLocation === $location.path();
    };

    $scope.classActive = function( viewLocation ) {
      if( $scope.isActive(viewLocation) ) {
        return 'active-btn';
      }
      else {
        return 'inactive-btn';
      }
    }
  });