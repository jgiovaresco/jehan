angular.module('jehanApp', [
    'ngRoute',
    'angular-loading-bar',
    'ngAnimate',
    'notificationWidget',
    'toaster',
    'jehan.services',
    'jehan.home',
    'jehan.instances',
    'jehan.jobs'
]);

angular.module('jehanApp').config(['$routeProvider', function ($routeProvider) {
  $routeProvider.otherwise({
      redirectTo: '/home'
  });
}]);

angular.module('jehanApp').controller('MainCtrl', function ($location) {
    $location.path('/home');
});

angular.module('jehanApp').filter('jobsStatus', function() {
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
                this.out.push(p_value);
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