jehanControllers.controller('InstancesListCtrl', function ($scope, JehanService) {

    $scope.instances = JehanService.instances.query();

});