package com.epam.app.Soap;

import com.eviware.soapui.impl.WsdlInterfaceFactory;
import com.eviware.soapui.impl.wsdl.*;
import com.eviware.soapui.model.iface.Request;
import com.eviware.soapui.model.iface.Response;
import com.eviware.soapui.model.iface.SubmitContext;
import com.eviware.soapui.support.SoapUIException;
import org.apache.xmlbeans.XmlException;
import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;


public class SoapClExp  {

@Test
    public void SoapCl() throws IOException, SoapUIException, Request.SubmitException, NotImplementedException, XmlException {
        WsdlProject project = new WsdlProject();
        String proxyUsername = "UserReportServiceFZPG";
        String proxyPassword = "s1CeB5Ams";

        // import amazon wsdl
        WsdlInterface iface = WsdlInterfaceFactory
                .importWsdl(project,"https://" + proxyUsername + ":" + proxyPassword + "@" + "db.1c-ksu.ru/UH_Test_Zolotarev/ws/ReportServiceFZPG?wsdl", true )[0];

// get desired operation
    String w = iface.getOperationList().get(0).getRequestList().get(0).getRequestContent();//.get(0).getRequestList().get().getRequestContent();

//        WsdlOperation operation = (WsdlOperation) iface.getOperationByName( "MyOperation" );
    WsdlOperation operation = (WsdlOperation) iface.getOperationList().get(0);

// create a new empty request for that operation
        WsdlRequest request = operation.addNewRequest( "My request" );

// generate the request content from the schema
        request.setRequestContent( operation.createRequest( true ) );

// submit the request
        //WsdlSubmit submit = (WsdlSubmit) request.submit( new WsdlSubmitContext(request), false );

// wait for the response
    //    Response response = submit.getResponse();
    Response response = request.submit((SubmitContext) request, true).getResponse();

//	print the response
        String content = response.getContentAsString();
        System.out.println( content );
        assertNotNull( content );
        assertTrue( content.indexOf( "404 Not Found" ) > 0  );
    }

}
