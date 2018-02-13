package com.epam.app.PageObjects;

import com.codeborne.selenide.ElementsCollection;
import com.epam.app.share.SClShare;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/**
 * Created by Rustam_Ragimov on 2/7/2018.
 */
public class GoogleResultsPage {
    private static SClShare b;

    public GoogleResultsPage(SClShare b) {
        this.b = b;
    }

    public static ElementsCollection results() {
        return $$("#ires");
    }

    public static void selectSearchResultNumber(int num) {
        $(By.xpath("//*[@id='rso']//div/div[" + num + "]//h3/a")).click();
    }
}