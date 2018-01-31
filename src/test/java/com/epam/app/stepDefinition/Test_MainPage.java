
package com.epam.app.stepDefinition;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class Test_MainPage {

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

    @And("^set login as: \"(.*)\" and password as: \"(.*)\"$")
    public void setLoginPassword(String login, String password) {
        $(By.name("username")).pressEnter();
        $(By.name("username")).setValue(login);
        $(By.name("password")).setValue(password);
        $(By.xpath("/html/body/table/tbody//tbody/tr[4]/td/input")).followLink();
    }

    @And("^user enters Credentials to LogIn$")
    public void user_enters_testuser__and_Test(DataTable usercredentials) throws Throwable {
        List<List<String>> data = usercredentials.raw();
        $(By.name("username")).pressEnter();
        $(By.name("username")).setValue(data.get(0).get(0));
        $(By.name("password")).setValue(data.get(0).get(1));
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

    @And("^choose to register from inside of error message$")
    public void erroRegister() {
        try {
            $(By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td[2]/div/a")).followLink();
        } catch (NoSuchElementException e) {
            System.out.println($(By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td[2]/div/a")).getText());
        }
    }
}
