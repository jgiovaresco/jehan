describe("JobsList controller", function () {
	var $httpBackend = null;
	var scope;

	beforeEach(module('jehanApp'));

	beforeEach(inject(function ($rootScope, $controller, _$httpBackend_) {
		$httpBackend = _$httpBackend_;

		scope = $rootScope.$new();
	}));

	it("should retrieve instances on load but none selected", function () {
		$httpBackend.expectGET('rest/Instances')
			.respond([ {
				           "name": "jenkins1",
				           "jobs": [
					           { "name": "job1", "color": "blue" }
				           ]
			           } ]);

		inject(function ($controller, Instances) {
			$controller('JobsListCtrl', {
				$scope: scope,
				$routeParams: {},
				Instances: Instances
			})
		});

		$httpBackend.flush();

		expect(scope.instances.length).toEqual(1);
		expect(scope.instances[ 0 ].name).toEqual("jenkins1");
		expect(scope.jobs).toBeNull();
	});

	it("should retrieve instances on load and one is selected", function () {
		$httpBackend.expectGET('rest/Instances')
			.respond([ {
				           "name": "jenkins1",
				           "jobs": [
					           { "name": "job1", "color": "blue" }
				           ]
			           } ]);

		inject(function ($controller, Instances) {
			$controller('JobsListCtrl', {
				$scope: scope,
				$routeParams: { "instanceName": "jenkins1" },
				Instances: Instances
			})
		});

		$httpBackend.flush();

		expect(scope.instances.length).toEqual(1);
		expect(scope.instances[ 0 ].name).toEqual("jenkins1");
		expect(scope.jobs).not.toBeNull();
		expect(scope.jobs.length).toEqual(1);
		expect(scope.jobs[ 0 ].name).toEqual("job1");
	});
});