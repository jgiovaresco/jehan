describe("InstancesList controller", function () {
	var scope, mockInstances;

	beforeEach(function () {
		// Create mock Instance
		mockInstances = jasmine.createSpyObj('Instances', [ 'query' ]);

		angular.mock.module('jehan.instances-list');

		angular.mock.inject(function ($rootScope, $controller) {

			mockInstances.query
				.andReturn([ {
					             "name": "jenkins1",
					             "jobs": [
						             { "name": "job1", "color": "blue" },
						             { "name": "job2", "color": "red" }
					             ]
				             } ]);

			scope = $rootScope.$new();
			$controller('InstancesListCtrl', {
				$scope: scope,
				Instances: mockInstances
			})
		});
	});

	it("should retrieve instances on load", function () {
		expect(mockInstances.query).toHaveBeenCalledWith();
		expect(scope.instances.length).toEqual(1);
		expect(scope.instances[ 0 ].name).toEqual("jenkins1");
		expect(scope.instances[ 0 ].jobs.length).toEqual(2);
		expect(scope.instances[ 0 ].jobs[ 0 ].name).toEqual("job1");
		expect(scope.instances[ 0 ].jobs[ 0 ].color).toEqual("blue");
		expect(scope.instances[ 0 ].jobs[ 1 ].name).toEqual("job2");
		expect(scope.instances[ 0 ].jobs[ 1 ].color).toEqual("red");
	});
});