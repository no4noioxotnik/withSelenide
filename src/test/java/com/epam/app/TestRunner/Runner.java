package com.epam.app.TestRunner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "pretty",
                "html:target/cucumber-results/html",
                "html:target/cucumber-html-report"
        },
        tags = {"~@wip", "~@ignored"},
        features = {"target/test-classes/features"},
        glue = {"classpath:com.epam.app.stepDefinition"},
        strict = true)
public class Runner {
}
