angular.module('jehan.jobs', ['jehan.services'])

.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/jobs/:instanceName?', {
        templateUrl: 'app/jobs/jobs.tpl.html',
        controller: 'JobsCtrl' })
    ;
}])

.controller('JobsCtrl', function ($scope, $routeParams, jehanService, Instance) {
    /**
     * @ngdoc property
     * @module JobsCtrl
     * @returns {Array.<Object>} List of Jenkins instances.
     * @description
     * Holds the list of Jenkins instances.
     */
    $scope.instances = Instance.getInstances();

    /**
     * @ngdoc property
     * @module JobsCtrl
     * @returns {string} The selected instance. Set by the routeParams "instanceParams" or by the user with the select input.
     * @description
     * Holds the selected instance.
     */
    $scope.instanceSelected = $routeParams.instanceName;

    /**
     * @ngdoc method
     * @name $scope#instanceChanged
     * @function
     *
     * @description Retrieves the jobs of an instance when the user change the value in the select input.
     *
     * @returns {Array.<Object>} An array containing the jobs.
     */
    $scope.instanceChanged = function() {
        if (null != $scope.instanceSelected) {
            $scope.jobs = jehanService.jobs.query({id: $scope.instanceSelected});
        }
    };

    // Loads the jobs if the instance name is in the $routeParams
    $scope.instanceChanged();
})
;
