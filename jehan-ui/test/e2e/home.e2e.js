describe('home', function () {

    var width = 1280,
        height = 800;
    browser.driver.manage().window().setSize(width, height);

    beforeEach(function () {
        browser.get('#/home');
        //browser.debugger(); //launch protractor with debug option and use 'c' in console to continue test execution
    });

    describe('job link', function () {
        it('should redirect on the job console when click on', function () {
            var jobsList = element.all(by.repeater('job in instance.jobs | jobsStatus:\'ko\''));
            var jobLink = jobsList.all(by.css('.ng-binding'));
            expect(jobLink.get(0).getAttribute("href")).toContain('/lastBuild/console');

            jobLink.get(0).click();
            expect(element.all(by.cssContainingText('Jenkins'))).toBeTruthy();
        });
    });

});