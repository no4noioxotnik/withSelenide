package com.epam.app.cucumberTest;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features"
        ,glue = {"src/test/java/com/epam/app/stepDefinition"}
)

public class TestRunner {

}