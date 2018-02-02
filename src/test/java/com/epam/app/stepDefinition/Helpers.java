package com.epam.app.stepDefinition;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;


public class Helpers {

    @And("^get host IP address$")
    public void ip() throws SocketException,  PendingException {
        Enumeration e = NetworkInterface.getNetworkInterfaces();
        ArrayList<String> ipAdr = new ArrayList<String>();
        while(e.hasMoreElements()) {
            NetworkInterface n = (NetworkInterface) e.nextElement();
            Enumeration ee = n.getInetAddresses();
            while (ee.hasMoreElements()) {
                InetAddress i = (InetAddress) ee.nextElement();
                if (i.getHostAddress().matches("((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)")) {
                    //System.out.println(i.getHostAddress());
                    ipAdr.add(i.getHostAddress());
                }
            }
        }
        System.out.println(ipAdr.get(2));
    }
}
