"use strict";

module.exports = function (config) {
	config.set({
		// base path, that will be used to resolve files and exclude
		basePath: '../..',
		// use jasmine
		frameworks: [ 'jasmine' ],
		// list of files / patterns to load in the browser
		files: [
			'src/bower_components/jquery/dist/jquery.js',
			'src/bower_components/angular/angular.js',
			'src/bower_components/angular-route/angular-route.js',
			'src/bower_components/angular-resource/angular-resource.js',
			'src/bower_components/angular-animate/angular-animate.js',
			'src/bower_components/angular-loading-bar/build/loading-bar.js',
			'src/bower_components/angular-notify-toaster/toaster.js',
			'src/bower_components/angular-mocks/angular-mocks.js',
			'src/app/**/*.js',
			'src/common/**/*.js',
			'test/unit/**/*.spec.js'
		],
		// use dots reporter, as travis terminal does not support escaping sequences
		// possible values: 'dots' || 'progress'
		reporters: 'progress',
		// these are default values, just to show available options web server port
		port: 8089,
		// cli runner port
		runnerPort: 9109,
		urlRoot: '/__test/',
		// enable / disable colors in the output (reporters and logs)
		colors: true,
		// level of logging
		// possible values: LOG_DISABLE || LOG_ERROR || LOG_WARN || LOG_INFO || LOG_DEBUG
		logLevel: config.LOG_INFO,
		// enable / disable watching file and executing tests whenever any file changes
		autoWatch: false,
		// polling interval in ms (ignored on OS that support inotify)
		autoWatchInterval: 0,
		// Start these browsers, currently available:
		// - Chrome
		// - ChromeCanary
		// - Firefox
		// - Opera
		// - Safari
		// - PhantomJS
		browsers: [ 'PhantomJS' ],
		// Continuous Integration mode
		// if true, it capture browsers, run tests and exit
		singleRun: true,
		// Plugins
		plugins: [
			'karma-phantomjs-launcher',
			'karma-jasmine'
		]
	});
};