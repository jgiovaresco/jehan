jehanServices.factory('Instance', ['$filter', 'jehanService', 'jehanCtx',
    function ($filter, jehanService, jehanCtx) {
        /**
         * @ngdoc method
         * @name getKoJobsNb
         * @function
         *
         * @description Calculates the number of jobs which are KO for an instance.
         * <p>A job is KO if its color is 'red' or 'yellow' ie, if its last build failed or if the tests failed.</p>
         *
         * @param {Object} p_instance A Jenkins instance.
         * @returns {int} The number of jobs which are KO.
         */
        var getKoJobsNb = function (p_instance) {
            var nb = 0;
            angular.forEach(p_instance.jobs, function (p_job) {
                if (angular.equals("red", p_job.color) || angular.equals("yellow", p_job.color)) {
                    nb++;
                }
            });
            return nb;
        };

        return {
            /**
             * @ngdoc method
             * @name getInstances
             * @function
             *
             * @description Retreives the jenkins instances from the REST service.
             * <p>The instances are store in <code>jehanCtx</code>. When the data are received, for each instances,
             * <ul>
             *     <li>it fetchs their jobs.</li>
             *     <li>it calculates the number of jobs which are KO.</li>
             * </ul>
             * </p>
             *
             * @returns {Array<Object>} The instances of Jenkins.
             */
            getInstances: function () {
                jehanCtx.instances = jehanService.instances.query({}, function () {
                    angular.forEach(jehanCtx.instances, function (p_instance) {
                        // retrieves the jobs
                        p_instance.jobs = jehanService.jobs.query({id: p_instance.name}, function () {
                            // calculates the number of KO jobs
                            p_instance.koJobsNb = getKoJobsNb(p_instance);
                            p_instance.hasJobsKo = function() {
                                return this.koJobsNb > 0;
                            }
                        });
                    });
                });
                return jehanCtx.instances;
            }
        };
    }]);