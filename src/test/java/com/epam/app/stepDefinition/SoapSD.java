package com.epam.app.stepDefinition;

import com.epam.app.share.SClShare;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.io.FileUtils;
import org.reficio.ws.SoapContext;
import org.reficio.ws.builder.SoapBuilder;
import org.reficio.ws.builder.SoapOperation;
import org.reficio.ws.builder.core.Wsdl;
import org.reficio.ws.client.core.Security;
import org.reficio.ws.client.core.SoapClient;
import org.reficio.ws.common.ResourceUtils;
import org.shai.xmodifier.XModifier;
import org.xml.sax.SAXException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Rustam_Ragimov on 2/1/2018.
 */
public class SoapSD {
    private SClShare b;

    public SoapSD(SClShare b) {
        this.b = b;
    }

    @Given("^SOAP builder with login: (.+) and password: (.+) and host: (.+) port: (.+) endpoint: (.+)$")
    public void soapBuilder(String login, String password, String host, String port, String endpoint) {
        port = ":" + port;

        Security security = Security.builder()
                .authBasic(login, password)
                .build();

        SoapClient client = SoapClient.builder()
                .endpointUri(host + port + endpoint)
                .endpointSecurity(security)
                .build();

        b.security = security;
        b.client = client;
        b.path = (host + port + endpoint);
        b.wsdlPath = (b.path + "?wsdl");
        b.login = login;
        b.password = password;
        b.host = host;
        b.destination = endpoint;
        b.port1 = port;
    }

    @When("^save wsdl file from url: (.+)$")
    public void saveWsdlFileFromUrl(String url) throws IOException, UnirestException {
        HttpResponse<String> response = Unirest.get(url)
                .basicAuth(b.login, b.password)
                .asString();

        b.body = response.getBody().getBytes();
        FileUtils.touch(new File("src/test/resources/wsdl/parameters.wsdl"));
        Files.write(Paths.get("src/test/resources/wsdl/parameters.wsdl"), b.body);
    }

    @Then("^generate empty SOAP message$")
    public void generateEmptySoap() throws IOException, SAXException, ParserConfigurationException, UnirestException {
        Wsdl wsdl = Wsdl.parse(ResourceUtils.getResource("wsdl/parameters.wsdl"));
        List<QName> qNames = wsdl.getBindings();
        SoapBuilder soapBuilder = wsdl.binding().name(qNames.get(0)).find();
        SoapOperation operation = soapBuilder.operation().name("Request").find();
        SoapContext soapContext = SoapContext.builder()
                .alwaysBuildHeaders(true)
                .buildOptional(true)
                .exampleContent(true)
                .typeComments(false)
                .valueComments(false)
                .build();

        String requestString = soapBuilder.buildInputMessage(operation, soapContext);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        b.domdoc = builder.parse(new ByteArrayInputStream(requestString.getBytes()));
        b.soapBuilder = soapBuilder;
        b.modifier = new XModifier(b.domdoc);
    }

    //TODO: Delete wsdl file IF exists
    @And("^delete wsdl file (.+)$")
    public void deleteWSDLFile(File file) throws IOException {
        file = new File("src/test/resources/wsdl/parameters.wsdl");
        FileUtils.forceDelete(file);//deleteDirectory(file);
    }

    @And("^set namespace: (.+) to value: (.+)$")
    public void setNamespace(String namespace, String value) {
        b.modifier.setNamespace(namespace, value);
    }

    @And("^set SOAP header: (.+) to value: (.+)$")
    public void setHeaderToValue(String header, String value) {
        b.modifier.setNamespace(header, value);
    }

    @And("^set XML body via any xpath: //(.+) to value: (.+)$")
    public void setXmlBodyViaAnyXpath(String xpath, String value) throws InterruptedException {
        if (value.equals("timestamp")) {
            long ts = System.currentTimeMillis() / 1000L;
            value = ("00000000-0000-0000-0000-"+String.valueOf(ts));
            Thread.sleep(1000);
        }
        String xPath = "//" + xpath;
        b.modifier.addModify(xPath, value);
    }

    @Then("^prepare SOAP message$")
    public void prepareSoapMessage() throws TransformerException {
        b.modifier.modify();
        DOMSource domSource = new DOMSource(b.domdoc);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.transform(domSource, result);
        b.requestFinalGenerated = writer.toString();
    }

    @And("^send modified message to host service$")
    public void sendModifiedMessage() throws UnirestException, NullPointerException {
        HttpResponse <String> soapResponse = Unirest.post(b.path)
                .basicAuth(b.login, b.password)
                .header("Content-Type", "application/xml")
                .body(b.requestFinalGenerated)
                .asString();
        b.soapResponse = soapResponse.getBody();
        b.statusCode = soapResponse.getStatus();
    }
}
