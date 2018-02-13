package com.epam.app.PageObjects;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

/**
 * Created by Rustam_Ragimov on 2/7/2018.
 */
public class GoogleSearchPage {
    public static void search(String query) {
        $(By.name("q")).setValue(query).pressEnter();
    }
}
