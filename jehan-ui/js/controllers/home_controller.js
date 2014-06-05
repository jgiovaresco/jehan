jehanControllers.controller('HomeCtrl', function ($scope, JehanService) {

    /**
     * @ngdoc property
     * @module HomeCtrl
     * @returns {Array.<Object>} List of jobs grouped by the name of the instance.
     * @description
     * Holds the list of jobs grouped by the name of the instance.
     */
    $scope.jobs = [];

    /**
     * @ngdoc property
     * @module HomeCtrl
     * @returns {Array.<Object>} List of Jenkins instances.
     * @description
     * Holds the list of Jenkins instances.
     */
    $scope.instances = JehanService.instances.query({filter: "jobsKO"},
        function () {
            angular.forEach($scope.instances, function (p_instance)
            {
                $scope.jobs[p_instance.name] = JehanService.jobs.query({id: p_instance.name});
            });
        });

    /**
     * @ngdoc method
     * @name $scope#$getJobs
     * @function
     *
     * @description Retrieves the jobs of an instance.
     *
     * @param {Object} p_instance The instance.
     *
     * @returns {Array.<Object>} An array containing the jobs.
     */
    $scope.getJobs = function (p_instance) {
        // console.debug("getJobs " + p_instance.name);
        return $scope.jobs[p_instance.name];
    };

    /**
     * @ngdoc method
     * @name $scope#$isLastBuildOk
     * @function
     *
     * @description Determines if the last build of the job is OK (compile and test successful).
     *
     * @param {Object} p_job The job.
     *
     * @returns {boolean} true if the last build of the job is OK, false otherwise.
     */
    $scope.isLastBuildOk = function (p_job) {
        // console.debug("isLastBuildOk " + p_job.name + " : " + angular.equals("blue", p_job.color));
        return angular.equals("blue", p_job.color);
    };

    /**
     * @ngdoc method
     * @name $scope#$isLastBuildDisabled
     * @function
     *
     * @description Determines if the last build of the job is disabled.
     *
     * @param {Object} p_job The job.
     *
     * @returns {boolean} true if the last build of the job is disabled, false otherwise.
     */
    $scope.isLastBuildDisabled = function (p_job) {
        // console.debug("isLastBuildOk " + p_job.name + " : " + angular.equals("blue", p_job.color));
        return angular.equals("disabled", p_job.color) || angular.equals("grey", p_job.color);
    };
});