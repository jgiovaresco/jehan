var gulp = require('gulp');
var useref = require('gulp-useref');
var gulpif = require('gulp-if');
var watch = require('gulp-watch');
var connect = require('gulp-connect');
var filter = require('gulp-filter');
var clean = require('gulp-clean');
var header = require('gulp-header');

var pkg = require('./package.json');
var paths = {
	appdir          : 'jehan-ui',
	dist            : 'jehan-ui/dist',
	distJs          : 'jehan-ui/dist/js',
	distCss         : 'jehan-ui/dist/css',
	srcDir          : 'jehan-ui/src',
	srcJs           : 'jehan-ui/src/**/*.js',
	srcCss          : 'jehan-ui/src/css/*.css',
	srcHtml         : 'jehan-ui/src/**/*.html',
	srcTpl          : 'jehan-ui/src/**/*.tpl.html',
	bower_components: 'jehan-ui/src/bower_components'
};

var banner = ['/**',
    ' * <%= pkg.title || pkg.name %>',
    ' * @version v<%= pkg.version %>',
    ' * @link <%= pkg.homepage %>',
	' * @license <%= pkg.licenses[0].type %>, <%= pkg.licenses[0].url %>',
	'*/',
    ''].join('\n');


gulp.task('clean', function () {
	return gulp.src(paths.dist, {read: false})
		.pipe(clean());
})

gulp.task("copy",function () {

	gulp.src(paths.srcTpl)
		.pipe(gulp.dest(paths.dist));
});

gulp.task('dist', ['clean'], function () {
	gulp.src(paths.bower_components + '/bootstrap/dist/fonts/*.{ttf,woff,eof,svg}')
		.pipe(gulp.dest(paths.dist + '/fonts'));

	gulp.src(paths.srcTpl)
		.pipe(gulp.dest(paths.dist));


	var assets = useref.assets();
	gulp.src(paths.srcDir + '/index.html')
		.pipe(assets)
		.pipe(gulpif('*.js', header(banner, { pkg : pkg } )))
		.pipe(assets.restore())
		.pipe(useref())
		.pipe(gulp.dest(paths.dist));

});

/**
 * Livereload
 */
gulp.task('watch', function () {
	watch([paths.srcHtml, paths.srcCss, paths.srcJs]).pipe(connect.reload());
});

/**
 * Lance un serveur pour tester l'application, l'api REST est mockée.
 */
gulp.task('dev', function () {
	require('./dev/mockRest.js');

	connect.server({
		               root      : paths.srcDir,
		               livereload: true,
		               middleware: function (connect, opt) {
			               return [
				               require('json-proxy').initialize({
					                                                proxy: {
						                                                forward: {
							                                                '/rest': 'http://localhost:8280/'
						                                                }
					                                                }
				                                                })]
		               }
	               });
});


gulp.task('default', ['dev', 'watch'], function () {
});