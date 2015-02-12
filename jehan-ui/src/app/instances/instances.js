angular.module('jehan.instances', ['jehan.services'])

.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/instances', {
        templateUrl: 'view/instances/instances.tpl.html',
        controller: 'InstancesListCtrl' })
    ;
}])

.controller('InstancesListCtrl', function ($scope, Instance) {
    $scope.instances = Instance.getInstances();
});

