package com.epam.app.stepDefinition;

import com.epam.app.PageObjects.GoogleResultsPage;
import com.epam.app.share.SClShare;
import cucumber.api.java.en.And;
import org.openqa.selenium.By;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static org.assertj.core.api.Assertions.assertThat;

public class AssertsCommon {

    private static SClShare b;

    public AssertsCommon(SClShare b) {
        this.b = b;
    }

    @And("^I want to assert that the text contains \"(.+)\" in response$")
    public void iWantToAssertThatResponseIs(String respStatus) {
        assertThat(b.soapResponse).contains(respStatus);
    }

    @And("^I want to assert that response statusCode is \"?(\\d+)\"$")
    public void iWantToAssertResponseStatusCode(int code) {
        assertThat(b.statusCode).isEqualTo(code);
    }

    @And("^I want to assert that response is \"(.+)\"$")
    public String updateSOAPMessage(String s) throws Exception {
        MessageFactory msgFactory = MessageFactory.newInstance();
        SOAPMessage message = msgFactory.createMessage();
        SOAPPart soapPart = message.getSOAPPart();
        byte[] buffer = b.soapResponse.getBytes();
        ByteArrayInputStream stream = new ByteArrayInputStream(buffer);
        StreamSource source = new StreamSource(stream);
        soapPart.setContent(source);
        soapPart.getContent();
        try {
            soapPart.getEnvelope().getBody().getFault().getFaultCode();
        } catch (SOAPException e) {
        }
        return s;
    }

    @And("^assert that result page contains text: \"(.*)\"$")
    public void resultPage(String resQuery) {
        assertThat($(By.linkText(resQuery)));
    }

    @And("^assert that request element contains text: \"(.*)\"$")
    public void resultCorrect(String elementText) {
        GoogleResultsPage.results().get(0).shouldHave(text(elementText));
    }

    @And("^assert that login error occured$")
    public void loginError() {
        $(By.xpath("/html/body/table/tbody/tr[2]/td/table/tbody/tr/td[2]/div/a")).exists();
    }

    public static void assertContainsOrEquals(String matchType, String v, String key) {
        switch (matchType) {
            case "equals":
                assertThat(key).isEqualTo(v);
                break;
            case "contains":
                assertThat(key).contains(v);
                break;
        }
    }
}

