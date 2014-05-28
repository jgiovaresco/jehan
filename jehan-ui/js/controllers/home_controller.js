jehanControllers.controller('HomeCtrl', function ($scope, JehanService) {
    $scope.jobs = [];
    $scope.instances = JehanService.instances.query(
        function () {
            angular.forEach($scope.instances, function (p_instance)
            {
                $scope.jobs[p_instance.name] = JehanService.jobs.query({id: p_instance.name});
            });
        });

    $scope.getJobs = function (p_instance) {
        return $scope.jobs[p_instance.name];
    }

});