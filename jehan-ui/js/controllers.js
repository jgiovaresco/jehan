'use strict';

/* Controllers */

angular.module('jehan.controllers', ['jehan.services'])

    .controller('MainCtrl', function ($location) {
        $location.path('/home');
    })

    .controller('InstancesListCtrl', function ($scope, Instance) {
        $scope.instances = Instance.getInstances();
    })

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

    .controller('HomeCtrl', function ($scope, jehanCtx, Instance) {
        /**
         * @ngdoc property
         * @module HomeCtrl
         * @description Holds jenkins instances.
         */
        $scope.instances = Instance.getInstances();

        /**
         * @ngdoc method
         * @name $scope#getPanelClass
         * @function
         *
         * @description Returns the css class for the panel displaying Jobs.
         * <ul>
         *     <li>If a KO job exists, the css class is <code>panel panel-danger</code></li>
         *     <li>Otherwise, it's <code>panel panel-success</code></li>
         * </ul>
         * @param {Object} The instance.
         * @returns {String} Returns the css class for the panel displaying Jobs.
         */
        $scope.getPanelClass = function(p_instance) {
            var cssClass = "panel panel-success";
            if (p_instance.hasJobsKo()) {
                cssClass = "panel panel-danger";
            }
            return cssClass;
        }
    });
