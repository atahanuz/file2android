package com.atahanuz;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class FtpFileUpload {

    public static void main(String[] args) {
        // Example usage
        String filePath = "/path/to/your/file.txt";
        String serverUrl = "192.168.1.44:2221";
        uploadFileViaFTP(filePath, serverUrl);
    }

    public static void uploadFileViaFTP(String filePath, String serverUrl) {
        // Default FTP user and password
        String user = "android";
        String pass = "android";

        FTPClient ftpClient = new FTPClient();
        try {
            // Parse server URL and port
            String[] serverDetails = serverUrl.split(":");
            String server = serverDetails[0];
            int port = serverDetails.length > 1 ? Integer.parseInt(serverDetails[1]) : 21; // default FTP port is 21

            // Handle interruptions
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Caught Ctrl+C... stopping transfer.");
                try {
                    if (ftpClient.isConnected()) {
                        ftpClient.logout();
                        ftpClient.disconnect();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));

            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            File localFile = new File(filePath);
            FileInputStream inputStream = new FileInputStream(localFile);

            String remoteFileName = URLEncoder.encode(localFile.getName(), StandardCharsets.UTF_8.toString());

            System.out.println("Starting file upload...");

            boolean done = ftpClient.storeFile(remoteFileName, inputStream);
            inputStream.close();

            if (done) {
                System.out.println("The file was uploaded successfully.");
            } else {
                System.out.println("Could not upload the file.");
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
