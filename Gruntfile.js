'use strict';

module.exports = function (grunt) {
    // Load Grunt tasks declared in the package.json file
    require('matchdep').filterDev('grunt-*').forEach(grunt.loadNpmTasks);

    // Configure Grunt
    grunt.initConfig({

        express: {
            all: {
                options: {
                    port: 8080,
                    hostname: "0.0.0.0",
                    bases: ['jehan-ui'],
                    livereload: true,
                    middleware: [
                        require('json-proxy').initialize({
                            proxy: {
                                forward: {
                                    '/rest': 'http://localhost:8081/rest'
                                }
                            }
                        })]
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
        'express',
        'watch'
    ]);
};