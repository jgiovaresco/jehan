jehanServices.factory('jehanService', ['$resource', 'restBaseUrl',
    function ($resource, restBaseUrl) {
        return {
            /**
             * @ngdoc property
             * @name jobs
             * @module jehanService
             * @description Defines the $resource handling Jobs.
             */
            jobs: $resource(restBaseUrl + '/rest/Jobs/:id', {}, {
                query: { method: 'GET', isArray: true }
            }),
            /**
             * @ngdoc property
             * @name instances
             * @module jehanService
             * @description Defines the $resource handling Instances.
             */
            instances: $resource(restBaseUrl + '/rest/Instances/')
        };
    }]);