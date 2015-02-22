angular.module('jehan.instances-list', [ 'ngRoute', 'jehan.resources.instances' ])

	.config([ '$routeProvider', function ($routeProvider) {
		$routeProvider.when('/instances', {
			templateUrl: 'app/instances/instances.tpl.html',
			controller: 'InstancesListCtrl'
		})
		;
	} ])

	.controller('InstancesListCtrl', [ '$scope', 'Instances', function ($scope, Instances) {
		$scope.instances = Instances.query();
	} ]);

