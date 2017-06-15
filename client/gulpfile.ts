const gulp = require('gulp');
const del = require('del');
const filter = require('gulp-filter');
const typescript = require('gulp-typescript');
const tscConfig = require('./tsconfig.json');

// Typescript clean the client
gulp.task('clean-client', function() {
  return del('dist/app/*');
});

// Typescript clean the server
gulp.task('clean-server', function() {
  return del('dist/server/*');
});

// TypeScript compile the client
gulp.task('compile-client', function () {
  return gulp
    .src('src/client/**/*.ts')
    .pipe(typescript(tscConfig.compilerOptions))
    .pipe(gulp.dest('dist/app'));
});

gulp.task('copy-libraries', function(){
  const libs = filter(['**', '!*testing.umd.min.js'], {restore: false, passthrough: true})
  return gulp
    .src('node_modules/@angular/**/bundles/*.umd.min.js')
    .pipe(libs)
    .pipe(gulp.dest('dist/app/libs'));
});

gulp.task('copy-assests', function(){
  return gulp
    .src('src/client/**/*.html')
    .pipe(gulp.dest('dist/app'));
});

// TypeScript compile the server
gulp.task('compile-server', function () {
  return gulp
    .src('src/server/**/*.ts')
    .pipe(typescript(tscConfig.compilerOptions))
    .pipe(gulp.dest('dist/server'));
});

gulp.task('build-client', ['clean-client', 'compile-client', 'copy-libraries', 'copy-assests']);
gulp.task('build-server', ['clean-server', 'compile-server']);
gulp.task('build', ['build-server', 'build-client']);
gulp.task('default', ['build']);
