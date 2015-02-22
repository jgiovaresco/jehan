describe('Instances resources test', function () {
	var $httpBackend = null;
	var service;

	beforeEach(module('jehanApp'));

	beforeEach(inject(function (_$httpBackend_, Instances) {
		$httpBackend = _$httpBackend_;
		service = Instances;
	}));

	it('should returns instances with only blue jobs', function () {
		$httpBackend.expectGET('rest/Instances')
			.respond([ {
				           "jobs": [
					           { "name": "job1", "color": "blue" }
				           ]
			           } ]);

		var result = service.query();

		$httpBackend.flush();

		expect(result.length).toBe(1);
		expect(result[ 0 ].getKoJobsNb()).toBe(0);
		expect(result[ 0 ].hasJobsKo()).toBe(false);
	});

	it('should returns instances with only red jobs', function () {
		$httpBackend.expectGET('rest/Instances')
			.respond([ {
				           "jobs": [
					           { "name": "job1", "color": "red" }
				           ]
			           } ]);

		var result = service.query();

		$httpBackend.flush();

		expect(result.length).toBe(1);
		expect(result[ 0 ].getKoJobsNb()).toBe(1);
		expect(result[ 0 ].hasJobsKo()).toBe(true);
	});

	it('should returns instances with only yellow jobs', function () {
		$httpBackend.expectGET('rest/Instances')
			.respond([ {
				           "jobs": [
					           { "name": "job1", "color": "yellow" }
				           ]
			           } ]);

		var result = service.query();

		$httpBackend.flush();

		expect(result.length).toBe(1);
		expect(result[ 0 ].getKoJobsNb()).toBe(1);
		expect(result[ 0 ].hasJobsKo()).toBe(true);
	});

	it('should returns instances with jobs red and yellow', function () {
		$httpBackend.expectGET('rest/Instances')
			.respond([ {
				           "jobs": [
					           { "name": "job1_", "color": "blue" },
					           { "name": "job2_", "color": "red" },
					           { "name": "job3_", "color": "yellow" }
				           ]
			           } ]);

		var result = service.query();

		$httpBackend.flush();

		expect(result.length).toBe(1);
		expect(result[ 0 ].getKoJobsNb()).toBe(2);
		expect(result[ 0 ].hasJobsKo()).toBe(true);
	});

});