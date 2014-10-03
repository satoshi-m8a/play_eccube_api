module.exports = function (config) {
    config.set({
        basePath: '',
        frameworks: ['jasmine', 'requirejs'],
        files: [
            {pattern: 'app/assets/javascripts/**/*.js', included: false},
            {pattern: 'test/javascripts/unit/**/*.js', included: false},
            {pattern: 'bower_components/**/*.js', included: false},
            'test/javascripts/main-test.js'
        ],

        autoWatch: true,

        LogLevel: config.LOG_DEBUG,

        browsers: ['Chrome'],

        reporters: ['progress', 'junit'],

        junitReporter: {
            outputFile: 'target/test-reports/jsUnit.xml',
            suite: 'unit'
        }
    });
};
