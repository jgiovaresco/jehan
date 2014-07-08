'use strict';

module.exports = function (grunt) {
    // Load Grunt tasks declared in the package.json file
    require('matchdep').filterDev('grunt-*').forEach(grunt.loadNpmTasks);

    // Configure Grunt
    grunt.initConfig({

        express: {
            server: {
                options: {
                    port: 8080,
                    bases: ['jehan-ui'],
                    middleware: [
                        require('json-proxy').initialize({
                            proxy: {
                                forward: {
                                    '/rest': 'http://localhost:8081/rest'
                                }
                            }
                        })]
                }
            },
            dev: {
                options: {
                    port: 8080,
                    bases: ['jehan-ui'],
                    middleware: [
                        require('json-proxy').initialize({
                            proxy: {
                                forward: {
                                    '/rest': 'http://localhost:8280/rest'
                                }
                            }
                        })]
                }
            },
            mockApi: {
                options: {
                    port: 8280,
                    bases: '.tmp',
                    server: require('path').resolve('./dev/mockRest')

                }
            }
        },

        // grunt-watch will monitor the projects files
        watch: {
            all: {
                files: ['index.html', 'view/*.html', 'js/**/*.js'],
                options: {
                    livereload: true
                }
            }
        }
    });

    // Creates the `server` task
    grunt.registerTask('server', [
        'express:server',
        'watch'
    ]);
    // Creates the `server` task
    grunt.registerTask('dev', [
        'express:dev',
        'express:mockApi',
        'watch'
    ]);
};