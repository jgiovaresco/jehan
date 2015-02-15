angular.module('jehan.home', ['jehan.services'])

.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/home', {
        templateUrl: 'app/home/home.tpl.html',
        controller: 'HomeCtrl' })
    ;
}])

.controller('HomeCtrl', function ($scope, jehanCtx, Instance) {
    /**
     * @ngdoc property
     * @module HomeCtrl
     * @description Holds jenkins instances.
     */
    $scope.instances = Instance.getInstancesWithJobs();

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
     * @param {Object} p_instance  The instance.
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
