angular.module('jehan.resources.jobs', [ 'ngResource' ]);
angular.module('jehan.resources.jobs').factory('Jobs', [ '$resource', function ($resource) {

	var Jobs = $resource('/rest/Jobs/:id', {}, {
		query: { method: 'GET', isArray: true }
	});

	return Jobs;
} ]);
