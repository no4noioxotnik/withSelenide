package com.epam.app.cucumberTest;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "features.UiTests"
        ,glue={"stepDefinition"}
)

public class TestRunner {

}