package com.epam.app.stepDefinition;

import com.epam.app.share.SClShare;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
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
import java.util.concurrent.TimeUnit;


public class Helpers {
    private SClShare b;

    public Helpers(SClShare b) {
        this.b = b;
    }

    @And("^get localhost IP address$")
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

    @And("^compare file with pathname \"(.*)\" to file \"(.*)\"$")
    public void compareTwoFiles(String actualFilePath, String expectedFilePath) throws IOException {
        compare2Files(actualFilePath, expectedFilePath);
    }

    MD5EncoderUtility md5EncoderUtil = new MD5EncoderUtility();

    public boolean compare2Files(String actualFilePath, String expectedFilePath) {
        if ((md5EncoderUtil.encodeToMd5(actualFilePath)).equals(md5EncoderUtil.encodeToMd5(expectedFilePath))) {
            System.out.println("The files- "+actualFilePath+" and "+expectedFilePath+" are same");
            return true;
        } else {
            System.out.println("The files- "+actualFilePath+" and "+expectedFilePath+" are NOT same");
            return false;
        }
    }

    @And("^i want to connect via ssh to host \"(.*)\" port \"?(\\d+)\" with username \"(.*)\" and password \"(.*)\"$")
    public void sshConnect(String host, int port, String username, String password) throws IOException {
        final SSHClient sshClient = new SSHClient();
            sshClient.addHostKeyVerifier(new PromiscuousVerifier());
//            KeyProvider keys = sshClient.loadKeys("path_to_private_key.ppk");
//          sshClient.addHostKeyVerifier("ca:0b:b3:7f:53:5a:e3:bc:bf:44:63:d8:2d:26:c0:41");
            sshClient.connect(host, port);
            sshClient.authPassword(username, password);
            Session session = sshClient.startSession();
            session.allocateDefaultPTY();
            Session.Shell shell = session.startShell();
            //NEXT IN DEBUG
        final Session.Command cmd = session.exec("ping -c 1 google.com");
        System.out.println(IOUtils.readFully(cmd.getInputStream()).toString());
        cmd.join(5, TimeUnit.SECONDS);
        System.out.println("\n** exit status: " + cmd.getExitStatus());
    }
}
