package com.epam.app.share;

import com.mashape.unirest.http.HttpResponse;
import org.reficio.ws.builder.SoapBuilder;
import org.reficio.ws.client.core.Security;
import org.reficio.ws.client.core.SoapClient;
import org.shai.xmodifier.XModifier;
import org.w3c.dom.Document;

public class SClShare {
    public byte[] body = null;

    public int statusCode = 0;
    public Document domdoc;
    public Document domresp;
    public XModifier modifier;
    public HttpResponse soapResponseBody;
    public SoapBuilder soapBuilder = null;
    public Security security = null;
    public SoapClient client = null;
    public String login = null;
    public String destination = null;
    public String password = null;
    public String port1 = null;
    public String host = null;
    public String requestFinalGenerated = null;
    public String path = null;
    public String wsdlPath = null;
    public String soapResponse = null;
}
