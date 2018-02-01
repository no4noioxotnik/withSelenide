package com.epam.app.stepDefinition;

import com.epam.app.share.SClShare;
import cucumber.api.java.en.And;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class Asserts {

    private static SClShare b;

    public Asserts(SClShare b) {
        this.b = b;
    }

    @And("^I want to assert that the text contains (.+) in response$")
    public void iWantToAssertThatResponseIs(String respStatus) {
        assertThat(b.soapResponse).contains(respStatus);
    }

    @And("^I want to assert that response statusCode is ?(\\d+)$")
    public void iWantToAssertResponseStatusCode(int code) {
        assertThat(b.statusCode).isEqualTo(code);
    }

    @And("^I want to assert that response is (.+)$")
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
        } catch (NullPointerException e) {
        }
        return s;
    }
}

