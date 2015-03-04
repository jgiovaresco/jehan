angular.module('jehan.resources.instances', [ 'ngResource' ]);
angular.module('jehan.resources.instances').factory('Instances', [ '$resource', function ($resource) {

	var Instances = $resource('rest/Instances');

	Instances.prototype.getNumberOfKoJobs = function () {
		var nb = 0;
		angular.forEach(this.jobs, function (p_job) {
			if (angular.equals("red", p_job.color) || angular.equals("yellow", p_job.color))
			{
				nb++;
			}
		});
		return nb;
	};

    Instances.prototype.getNumberOfOkJobs = function () {
        var nb = 0;
        angular.forEach(this.jobs, function (p_job) {
            if (angular.equals("green", p_job.color) || angular.equals("blue", p_job.color))
            {
                nb++;
            }
        });
        return nb;
    };

	Instances.prototype.hasKoJobs = function () {
		return this.getNumberOfKoJobs() > 0;
	};

	return Instances;
} ]);
