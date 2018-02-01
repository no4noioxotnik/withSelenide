package com.epam.app.stepDefinition;

import cucumber.api.java.en.And;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;

import java.io.IOException;
import java.net.ServerSocket;

import static com.google.common.collect.ComparisonChain.start;


public class MockFtp {

    @And("^setup ftp mock server set port: (\\d+) username: (.*) password: (.*) homeDir: (.*)$")
    public void mock(int port, String userName, String password, String homeDir) throws FtpException, IOException, InterruptedException {
        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
        UserManager userManager = userManagerFactory.createUserManager();
        BaseUser user = new BaseUser();
        user.setName(userName);
        user.setPassword(password);
        user.setHomeDirectory(homeDir);
        userManager.save(user);

        ListenerFactory listenerFactory = new ListenerFactory();
        listenerFactory.setPort(port);

        FtpServerFactory factory = new FtpServerFactory();
        factory.setUserManager(userManager);
        factory.addListener("default", listenerFactory.createListener());

        FtpServer server = factory.createServer();
        server.start();
        Thread.sleep(50000);
    }


    private ServerSocket serverSocket;
    private Thread thread = null;

    public void runThread() throws IOException {
        serverSocket = new ServerSocket(21);
        System.out.println("server started");
        start();
        //serverSocket.setSoTimeout(10000);
    }


    public void newThread(){
        if (thread == null)
        {  thread = new Thread(String.valueOf(this));
            thread.start();
        }
    }
}