// A reference configuration file.
'use strict';
exports.config = {
	directConnect : true,
	specs : [ '../e2e/*.e2e.js' ],

	suites : {
		'home' : [ '../e2e/home.e2e.js' ]
	},
	capabilities : {
		'browserName' : 'chrome'
	},
	baseUrl : 'http://localhost:8080/',
	rootElement : 'body',
	onPrepare : function() {
		// The require statement must be down here, since jasmine-reporters needs jasmine to be 
		// in the global and protractor does not guarantee this until inside the onPrepare 
		// function.
		require('jasmine-reporters');
		jasmine.getEnv().addReporter(
				new jasmine.JUnitXmlReporter('target/test/e2e-reports/'));
	}
};