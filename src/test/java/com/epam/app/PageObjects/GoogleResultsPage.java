package com.epam.app.PageObjects;

import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Selenide.$$;

/**
 * Created by Rustam_Ragimov on 2/7/2018.
 */
public class GoogleResultsPage {
    public ElementsCollection results() {
        return $$("#ires");
    }
}