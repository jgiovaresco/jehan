angular.module('jehanApp', [
	'ngRoute',
	'angular-loading-bar',
	'ngAnimate',
	'notificationWidget',
	'toaster',
	'jehan.resources.jobs',
	'jehan.resources.instances',
	'jehan.home',
	'jehan.instances-list',
	'jehan.jobs-list'
]);

angular.module('jehanApp').config([ '$routeProvider', function ($routeProvider) {
	$routeProvider.otherwise({
		redirectTo: '/home'
	});
} ]);

angular.module('jehanApp').controller('MainCtrl', function ($location) {
	$location.path('/home');
});

angular.module('jehanApp').filter('jobsStatus', function () {
	return function (p_jobs, p_status) {
		var items = {
			status: p_status,
			out: []
		};

		angular.forEach(p_jobs, function (p_value) {
			if (angular.equals("ko", this.status)
			    && (angular.equals(p_value.color, 'red') || angular.equals(p_value.color, 'yellow')))
			{
				this.out.push(p_value);
			}
			else if (angular.equals(p_value.color, this.status))
			{
				this.out.push(p_value);
			}
		}, items);
		return items.out;
	};
});
