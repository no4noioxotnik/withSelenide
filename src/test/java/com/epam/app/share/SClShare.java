package com.epam.app.share;

import com.epam.app.PageObjects.GoogleResultsPage;
import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import org.shai.xmodifier.XModifier;
import org.w3c.dom.Document;

public class SClShare {
    public byte[] body = null;

    public int statusCode = 0;
    public Document domdoc;
    public XModifier modifier;
    public String password = null;
    public String soapResponse = null;
    public String webpage;
    public GoogleResultsPage resultsPage;
    public String proxyUsername;
    public String proxyPassword;
    public String username;
    public WsdlProject project;
    public String endpUri;
    public WsdlInterface iface;
}
