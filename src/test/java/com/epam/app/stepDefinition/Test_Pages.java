package com.epam.app.stepDefinition;

import com.epam.app.PageObjects.GoogleResultsPage;
import com.epam.app.PageObjects.GoogleSearchPage;
import com.epam.app.share.SClShare;
import cucumber.api.java.en.When;

import static com.codeborne.selenide.Selenide.open;


public class Test_Pages {
    private SClShare b;

    public Test_Pages(SClShare b) {
        this.b = b;
    }
    private GoogleResultsPage resultsPage;

    @When("^go to page: (.*)$")
            public void search(String query) {
        GoogleSearchPage searchPage = open(b.webpage + query, GoogleSearchPage.class);
        GoogleResultsPage resultsPage = searchPage.search(query);
        b.resultsPage = resultsPage;
    }
}
