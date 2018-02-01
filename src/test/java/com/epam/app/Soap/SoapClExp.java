package com.epam.app.Soap;

import com.eviware.soapui.impl.WsdlInterfaceFactory;
import com.eviware.soapui.impl.wsdl.*;
import com.eviware.soapui.model.iface.Request;
import com.eviware.soapui.model.iface.Response;
import com.eviware.soapui.support.SoapUIException;
import org.apache.xmlbeans.XmlException;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by Rustam_Ragimov on 1/31/2018.
 */
public class SoapClExp {

@Test
    public void SoapCl() throws IOException, SoapUIException, Request.SubmitException, XmlException {
        WsdlProject project = new WsdlProject();
        // import amazon wsdl
        WsdlInterface iface = WsdlInterfaceFactory.importWsdl(project,"https://db.1c-ksu.ru/UH_Test_Zolotarev/ws/ReportServiceFZPG?wsdl", true )[0];

// get desired operation
        WsdlOperation operation =
                (WsdlOperation) iface.getOperationByName( "MyOperation" );

// create a new empty request for that operation
        WsdlRequest request = operation.addNewRequest( "My request" );

// generate the request content from the schema
        request.setRequestContent( operation.createRequest( true ) );

// submit the request
        WsdlSubmit submit = (WsdlSubmit) request.submit( new WsdlSubmitContext(request), false );

// wait for the response
        Response response = submit.getResponse();

//	print the response
        String content = response.getContentAsString();
        System.out.println( content );
        assertNotNull( content );
        assertTrue( content.indexOf( "404 Not Found" ) > 0  );
    }

}
