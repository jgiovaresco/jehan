var jehanApp = angular.module('jehanApp', ['ngRoute', 'angular-loading-bar', 'ngAnimate', 'notificationWidget', 'toaster', 'jehan.controllers', 'jehan.services']);

jehanApp.config(['$routeProvider', function ($routeProvider) {
  $routeProvider.when('/home', {
          templateUrl: 'view/home.html',
          controller: 'HomeCtrl' }).
      when('/instances', {
        templateUrl: 'view/instances.html',
        controller: 'InstancesListCtrl' }).
      when('/jobs/:instanceName?', {
          templateUrl: 'view/jobs.html',
          controller: 'JobsCtrl' }).
      otherwise({
          redirectTo: '/home'
      })
    ;
}]);

jehanApp.filter('jobsStatus', function() {
    return function(p_jobs, p_status) {
        var items = {
            status: p_status,
            out: []
        };

        angular.forEach(p_jobs, function (p_value) {
            if (angular.equals("ko", this.status)
                && (angular.equals(p_value.color, 'red') || angular.equals(p_value.color, 'yellow'))) {
                this.out.push(p_value);
            }
            else if (angular.equals(p_value.color, this.status)) {
                this.out.push(value);
            }
        }, items);
        return items.out;
    };
});

var jehanServices = angular.module('jehan.services', ['ngResource']);
jehanServices
    .value('restBaseUrl', '')
    .value('jehanCtx', {
        instances: []
    });