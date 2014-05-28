jehanServices
    .factory('JehanService', function ($resource) {
        return {
            jobs: $resource('/rest/Jobs/:id', {}, {
                query: { method: 'GET', isArray: true }
            }),
            instances: $resource('/rest/Instances/')
        };
    });