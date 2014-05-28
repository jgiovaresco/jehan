var jehanApp = angular.module('jehanApp', ['ngRoute', 'jehanApp.controllers', 'jehanApp.services']);

jehanApp.config(['$routeProvider', function ($routeProvider) {
  $routeProvider.
      when('/home', {
          templateUrl: '/view/home.html',
          controller: 'HomeCtrl' }).
      when('/instances', {
        templateUrl: '/view/instances.html',
        controller: 'InstancesListCtrl' })
    ;
}]);

var jehanControllers = angular.module('jehanApp.controllers', []);
var jehanServices = angular.module('jehanApp.services', ['ngResource']);