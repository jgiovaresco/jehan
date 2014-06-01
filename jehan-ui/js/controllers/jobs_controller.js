jehanControllers.controller('JobsCtrl', function ($scope, $routeParams, JehanService) {

    // console.debug("JobsCtrl : " + $routeParams.instanceName);

    /**
     * @ngdoc property
     * @module JobsCtrl
     * @returns {Array.<Object>} List of Jenkins instances.
     * @description
     * Holds the list of Jenkins instances.
     */
    $scope.instances = JehanService.instances.query();

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
     * @name $scope#$instanceChanged
     * @function
     *
     * @description Retrieves the jobs of an instance when the user change the value in the select input.
     *
     * @returns {Array.<Object>} An array containing the jobs.
     */
    $scope.instanceChanged = function() {
        if (null != $scope.instanceSelected) {
            // /console.debug("Instance Changed : " + $scope.instanceSelected + ' : ' + angular.isString($scope.instanceSelected));
            $scope.jobs = JehanService.jobs.query({id: $scope.instanceSelected});
        }
    }

    // Loads the jobs if the instance name is in the $routeParams
    $scope.instanceChanged();
});