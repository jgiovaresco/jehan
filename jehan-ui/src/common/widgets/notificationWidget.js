'use strict';

// NotificationWidget from
// https://github.com/lavinjj/angularjs-spinner/blob/master/source/notificationWidget.js
// http://www.kvetis.com/2014/01/angularjs-loading-widget.html

// Declare module which depends on filters, and services
angular.module('notificationWidget', [])
    .provider('requestNotification', function () {
        // This is where we keep subscribed listeners
        var onRequestStartedListeners = [];
        var onRequestEndedListeners = [];

        // This is a utility to easily increment the request count
        var count = 0;
        var requestCounter = {
            increment: function () {
                count++;
            },
            decrement: function () {
                if (count > 0)
                    count--;
            },
            getCount: function () {
                return count;
            }
        };
        // Subscribe to be notified when request starts
        this.subscribeOnRequestStarted = function (listener) {
            onRequestStartedListeners.push(listener);
        };
        // Tell the provider, that the request has started.
        this.fireRequestStarted = function (request) {
            // Increment the request count
            requestCounter.increment();
            //run each subscribed listener
            angular.forEach(onRequestStartedListeners, function (listener) {
                // call the listener with request argument
                listener(request);
            });
            return request;
        };
        // this is a complete analogy to the Request START
        this.subscribeOnRequestEnded = function (listener) {
            onRequestEndedListeners.push(listener);
        };
        this.fireRequestEnded = function () {
            requestCounter.decrement();
            var passedArgs = arguments;
            angular.forEach(onRequestEndedListeners, function (listener) {
                listener.apply(this, passedArgs);
            });
            return arguments[0];
        };
        this.getRequestCount = requestCounter.getCount;
        //This will be returned as a service
        this.$get = function () {
            var that = this;
            // just pass all the functions
            return {
                subscribeOnRequestStarted: that.subscribeOnRequestStarted,
                subscribeOnRequestEnded: that.subscribeOnRequestEnded,
                fireRequestEnded: that.fireRequestEnded,
                fireRequestStarted: that.fireRequestStarted,
                getRequestCount: that.getRequestCount
            };
        };
    })
    .config(function ($httpProvider, requestNotificationProvider) {
        $httpProvider
            .defaults
            .transformRequest
            .push(function (data) {
                requestNotificationProvider.fireRequestStarted(data);
                return data;
            });

        $httpProvider
            .defaults
            .transformResponse
            .push(function (data) {
                requestNotificationProvider.fireRequestEnded(data);
                return data;
            });
    })
    // declare the directive that will show and hide the loading widget
    .directive('loadingWidget', ['requestNotification', function (requestNotification) {
        return {
            restrict: "A",
            link: function (scope, element) {
                // hide the element initially
                element.hide();

                //subscribe to listen when a request starts
                requestNotification
                    .subscribeOnRequestStarted(function () {
                        // show the spinner!
                        element.show();
                    });

                requestNotification
                    .subscribeOnRequestEnded(function () {
                        // hide the spinner if there are no more pending requests
                        if (requestNotification.getRequestCount() === 0)
                            element.hide();
                    });
            }
        };
    }])
    // declare the directive that will hide an element when a request starts.
    .directive('hideWhileLoading', ['requestNotification', function (requestNotification) {
        return {
            restrict: "A",
            link: function (scope, element) {
                // hide the element initially
                element.hide();

                //subscribe to listen when a request starts
                requestNotification
                    .subscribeOnRequestStarted(function () {
                        element.hide();
                    });

                requestNotification
                    .subscribeOnRequestEnded(function () {
                        if (requestNotification.getRequestCount() === 0)
                            element.show();
                    });
            }
        };
    }]);