package com.epam.app.stepDefinition;

import com.epam.app.share.SClShare;
import com.eviware.soapui.impl.WsdlInterfaceFactory;
import com.eviware.soapui.impl.wsdl.*;
import com.eviware.soapui.model.iface.Request;
import com.eviware.soapui.support.SoapUIException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.xmlbeans.XmlException;
import org.shai.xmodifier.XModifier;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;



public class SoapClExp  {

    private String proxyUsername;
    private String proxyPassword;
    private WsdlProject project;
    private WsdlInterface iface;
    private Document domdoc;
    private XModifier modifier;
    private String endpUri;
    private String username;
    private String password;

    private SClShare b;

    public SoapClExp(SClShare b) {
        this.b = b;
    }


    @Given("^Soap client with endpointURI: (.*) username: (.*) and password (.*)$")
    public void soapInstance(String endpUri, String username, String password) throws XmlException, IOException, SoapUIException {
        WsdlProject project = new WsdlProject();
        this.proxyUsername = username + ":";
        this.proxyPassword = password + "@";
        this.username = username;
        this.password = password;
        this.project = project;
        this.endpUri = endpUri;
    }

        // import amazon wsdl
    @And("^Wsdl with url: (http|https)://(.*)$")
    public void wsdlUrl (String protocol, String url) throws SoapUIException {
        this.iface = WsdlInterfaceFactory
                .importWsdl(project, protocol + "://" + proxyUsername + proxyPassword + url, true)[0];
    }

    @When("^I get wsdl, I want to get a new empty request for the operation$")
    public void emptyRequest() throws IOException, SAXException, ParserConfigurationException, UnirestException {
        String w = iface.getOperationList().get(0).getRequestList().get(0).getRequestContent();
        WsdlOperation operation = (WsdlOperation) iface.getOperationList().get(0);
        WsdlRequest request = operation.addNewRequest("My request");
        request.setRequestContent(operation.createRequest(true));
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        this.domdoc = builder.parse(new ByteArrayInputStream(w.getBytes()));
        this.modifier = new XModifier(domdoc);
    }

    @Then("^set namespace: (.+) to value: (.+)$")
    public void setNamespace(String namespace, String value) {
        modifier.setNamespace(namespace, value);
    }

    @And("^set SOAP header: (.+) to value: (.+)$")
    public void setHeaderToValue(String header, String value) {
        modifier.setNamespace(header, value);
    }

    @And("^set XML body via any xpath: //(.+) to value: (.+)$")
    public void setXmlBodyViaAnyXpath(String xpath, String value) throws InterruptedException {
        if (value.equals("timestamp")) {
            long ts = System.currentTimeMillis() / 1000L;
            value = ("00000000-0000-0000-0000-"+String.valueOf(ts));
            Thread.sleep(1000);
        }
        String xPath = "//" + xpath;
        modifier.addModify(xPath, value);
    }

    @Then("^send modified message to host service$")
    public void prepareSoapMessage() throws TransformerException, UnirestException, NullPointerException, Request.SubmitException {
        modifier.modify();
        DOMSource domSource = new DOMSource(domdoc);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.transform(domSource, result);
        String requestFinalGenerated = writer.toString();

        HttpResponse<String> soapResponse = Unirest.post(endpUri)
                .basicAuth(username, password)
                .header("Content-Type", "application/xml")
                .body(requestFinalGenerated)
                .asString();
        b.soapResponse = soapResponse.getBody();
        b.statusCode = soapResponse.getStatus();
    }
}
