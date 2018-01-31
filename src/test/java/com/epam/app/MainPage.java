
package com.epam.app;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class MainPage {

    @Given("^browser: (.+)$")
    public static void setupClass(String browser) {
        System.setProperty("chromedriver.exe", "/src/test/resources");
        System.setProperty("selenide.browser", browser);
    }


    @When("^open webpage: (.+)$")
    public void openMainPage(String webpage) throws InterruptedException {
        clearBrowserCache();
        open(webpage);
    }

    @Then("^search for: (.+)$")
    public void searchFor(String word) {
        $(By.name("q")).setValue(word).pressEnter();
    }

    @Then("^click on (\\d+) -rd link$")
    public void clickOn(int num) {
        $(By.xpath("//*[@id='rso']//div/div[" + num + "]//h3/a")).followLink();
    }

    @And("^wait: (\\d+) milliseconds$")
    public void wait(int per) throws InterruptedException {
        Thread.sleep(per);
    }

    @And("^set login as: (.+) and password as: (.+)$")
    public void setLoginPassword(String login, String password) {
        $(By.name("username")).pressEnter();
        $(By.name("username")).setValue(login);
        $(By.name("password")).setValue(password);
        $(By.xpath("/html/body/table/tbody//tbody/tr[4]/td/input")).followLink();
    }

    @And("^switch to the previous tab$")
    public void toPreviousTab() {
        Set<String> handles = getWebDriver().getWindowHandles();
        List<String> tabs = new ArrayList<String>(handles);
        switchTo().window(tabs.get(1));
        getWebDriver().close();
        switchTo().window(tabs.get(0));
    }

    @And("^assert that login error occured$")
    public void loginError() {
        $(By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td[2]/div/a")).exists();
    }

    @And("^chose to register from inside of error message$")
    public void erroRegister() {
        $(By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td[2]/div/a")).followLink();
    }
}
