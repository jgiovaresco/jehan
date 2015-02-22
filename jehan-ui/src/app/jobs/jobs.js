angular.module('jehan.jobs-list', [ 'ngRoute', 'jehan.resources.jobs' ])

	.config([ '$routeProvider', function ($routeProvider) {
		$routeProvider.when('/jobs/:instanceName?', {
			templateUrl: 'app/jobs/jobs.tpl.html',
			controller: 'JobsListCtrl'
		})
		;
	} ])

	.controller('JobsListCtrl', [ '$scope', '$routeParams', 'Instances', function ($scope, $routeParams, Instances) {
		/**
		 * @ngdoc property
		 * @module JobsListCtrl
		 * @returns {Array.<Object>} List of Jenkins instances.
		 * @description
		 * Holds the list of Jenkins instances.
		 */
		$scope.instances = Instances.query({}, function () {
			// Loads the jobs if the instance name is in the $routeParams
			$scope.instanceChanged();
		});

		/**
		 * @ngdoc property
		 * @module JobsListCtrl
		 * @returns {string} The selected instance. Set by the routeParams "instanceParams" or by the user with the select input.
		 * @description
		 * Holds the selected instance.
		 */
		$scope.instanceSelected = $routeParams.instanceName;

		/**
		 * @ngdoc property
		 * @module JobsListCtrl
		 * @returns {Array.<Object>} Jobs list of the selected instance.
		 * @description
		 * Holds the jobs list of the selected instance.
		 */
		$scope.jobs = null;

		/**
		 * @ngdoc method
		 * @module JobsListCtrl
		 * @name $scope#instanceChanged
		 * @function
		 *
		 * @description Retrieves the jobs of an instance when the user change the value in the select input.
		 *
		 * @returns {Array.<Object>} An array containing the jobs.
		 */
		$scope.instanceChanged = function () {
			if (null != $scope.instanceSelected)
			{
				var i = 0;
				var found = false;
				while (!found && i < $scope.instances.length)
				{
					if ($scope.instances[ i ].name == $scope.instanceSelected)
					{
						$scope.jobs = $scope.instances[ i ].jobs;
						found = true;
					}
					i++;
				}
			}
		};
	} ]);
