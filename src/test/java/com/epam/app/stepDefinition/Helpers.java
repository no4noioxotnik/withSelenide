package com.epam.app.stepDefinition;

import com.epam.app.share.SClShare;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import org.apache.commons.io.FileUtils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;


public class Helpers {
    private SClShare b;

    public Helpers(SClShare b) {
        this.b = b;
    }

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

    @And("^Set timeout \"?(\\d+)\" milliseconds$")
    public void timeout (int time) throws InterruptedException {
        Thread.sleep(time);
    }

    @And("^get file from url \"(.*)\" in bytes$")
    public void getFileFromUrl(String url) throws UnirestException {
            HttpResponse<String> response = Unirest.get(url)//    .basicAuth(b.login, b.password)
            .asString();
    b.body = response.getBody().getBytes();
    }

    @And("^create file with filename \"(.*)\" to path \"(.*)\"$")
    public void createNewFile(File file, String path) throws IOException, UnirestException, ParserConfigurationException, SAXException {
        FileUtils.touch(new File(path + file));//"src/test/resources/test_data/parameters.wsdl"
        b.path = path;
        b.file = file;
    }

    @And("^fill local file with bytes recieved$")
    public void fillFileBytes() throws IOException {
        Files.write(Paths.get(b.path + b.file), b.body);//body in bytes
    }

    @And("^delete file \"(.+)\" from path \"(.*)\"$")
    public void deleteLocalFile(File file, String path) throws IOException {
        file = new File(path + file);//"src/test/resources/test_data/parameters.wsdl"
        FileUtils.forceDelete(file);//or .deleteDirectory(file);
    }

    @And("^create sparse file to path \"(.*)\" with size \"(.*)\" and filename \"(.*)\"$")
    public void createSparsFile(String filePath, Long fileSize, String filename) throws Exception {
        create(filePath, fileSize, filename);
    }

    public void create (String filepath, Long filesize, String filename) throws Exception {
//        length = 0x8FFFFFF;
        MappedByteBuffer out = new RandomAccessFile(filepath + filename, "rw")
                .getChannel().map(FileChannel.MapMode.READ_WRITE, 0, filesize);
        for (int i = 0; i < filesize; i++)
            out.put((byte) 'x');
        System.out.println("Finished writing");
        for (long i = filesize / 2; i < filesize / 2 + 6; i++)
            System.out.print((char) out.get((int) i));
    }
}
