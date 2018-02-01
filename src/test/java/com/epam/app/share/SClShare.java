package com.epam.app.share;

import org.shai.xmodifier.XModifier;
import org.w3c.dom.Document;

public class SClShare {
    public byte[] body = null;

    public int statusCode = 0;
    public Document domdoc;
    public Document domresp;
    public XModifier modifier;
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
