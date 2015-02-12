'use strict';

module.exports = function (grunt) {
    // Load Grunt tasks declared in the package.json file
    require('matchdep').filterDev('grunt-*').forEach(grunt.loadNpmTasks);

    // Configure Grunt
    grunt.initConfig({
        appdir: 'jehan-ui',
        //distdir: '<%= appdir %>/dist',
        pkg: grunt.file.readJSON('package.json'),
        banner: '/*! <%= pkg.title || pkg.name %> - v<%= pkg.version %> - <%= grunt.template.today("yyyy-mm-dd") %>\n' +
        '<%= pkg.homepage ? " * " + pkg.homepage + "\\n" : "" %>' +
        ' * Copyright (c) <%= grunt.template.today("yyyy") %> <%= pkg.author %>;\n' +
        ' * Licensed <%= _.pluck(pkg.licenses, "type").join(", ") %>\n */\n',

        dist: {
            dir: '<%= appdir %>/dist',
            js: '<%= appdir %>/dist/js',
            css: '<%= appdir %>/dist/css'
        },

        src: {
            js: ['<%= appdir %>/src/**/*.js'],
            css: ['<%= appdir %>/src/css/*.css'],
            html: ['<%= appdir %>/src/index.html'],
            tpl: {
                app: ['<%= appdir %>/src/app/**/*.tpl.html']
            },
            bower_components: '<%= appdir %>/bower_components'
        },

        clean: ['<%= dist.dir %>/*'],

        copy: {
            assets: {
                files: [{dest: '<%= dist.dir %>', src: '**', expand: true, cwd: '<%= appdir %>/src/assets/'}]
            },
            templates: {
                files: [{dest: '<%= dist.dir %>/view', src: ['**/*.tpl.html'], expand: true, cwd: '<%= appdir %>/src/app/'}]
            },
            fonts: {
                files: [{dest: '<%= dist.dir %>/fonts', src: ['**'], expand: true, cwd: '<%= src.bower_components %>/bootstrap/dist/fonts'}]
            }
        },

        concat: {
            dist: {
                options: {
                    banner: "<%= banner %>"
                },
                src: ['<%= src.js %>'],
                dest: '<%= dist.js %>/<%= pkg.name %>.js'
            },
            index: {
                src: ['<%= src.html %>'],
                dest: '<%= dist.dir %>/index.html',
                options: {
                    process: true
                }
            },
            angular: {
                src: ['<%= src.bower_components %>/angular/angular.js', '<%= src.bower_components %>/angular-animate/angular-animate.js',
                    '<%= src.bower_components %>/angular-loading-bar/build/loading-bar.js', '<%= src.bower_components %>/angular-notify-toaster/toaster.js',
                    '<%= src.bower_components %>/angular-resource/angular-resource.js', '<%= src.bower_components %>/angular-route/angular-route.js'],
                dest: '<%= dist.js %>/angular.js'
            },
            jquery: {
                src: ['<%= src.bower_components %>/jquery/dist/jquery.js'],
                dest: '<%= dist.js %>/jquery.js'
            },
            css: {
                src: ['<%= src.bower_components %>/bootstrap/dist/css/bootstrap.css', '<%= src.bower_components %>/angular-loading-bar/build/loading-bar.css',
                    '<%= src.bower_components %>/angular-notify-toaster/toaster.css', '<%= src.css %>'],
                dest: '<%= dist.css %>/<%= pkg.name %>.css'
            }
        },

        express: {
            server: {
                options: {
                    port: 8080,
                    bases: ['jehan-ui/dist'],
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
                    bases: ['jehan-ui/dist'],
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

    grunt.registerTask('build', ['clean', 'concat', 'copy']);
};