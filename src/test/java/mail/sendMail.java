package mail;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class sendMail {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String from = "huyci13xl@gmail.com";
        String to = "chien.pd@evotek.vn";
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.ssl.trust", "smtp.host");
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.debug", "false");
        // get Session
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("huyci13xl@gmail.com", "oiuyuxyehvfzhthh");
            }
        });
        session.setDebug(true);
        try {

            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            msg.setFrom(new InternetAddress("huyci13xl@gmail.com", "huyci13xl@gmail.com"));
            msg.setReplyTo(InternetAddress.parse("to mail id", false));

            msg.setSubject("Test Report by Automation", "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(to)));

            // Create the message body part
            BodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart.setText("Send mail report with Cumcumber with Serenity");

            // Create a multipart message for attachment
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Second part is image attachment
            messageBodyPart = new MimeBodyPart();
            String filename = System.getProperty("user.dir")
                    + "/target/site/serenity/index.html";

            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            // Trick is to add the content-id header here
            messageBodyPart.setHeader("Content-ID", "image_id");

            // zip the cucumber html report--

            File f = new File(System.getProperty("user.dir") + "/target/site");

            // Populates the array with names of files and directories
            String[] myFiles = f.list();
            String path = System.getProperty("user.dir") + "/target/site/";
            for (int i = 0; i < myFiles.length; i++) {
                myFiles[i] = path + myFiles[i];
            }

            String zipFile = System.getProperty("user.dir") + "/target/site.zip";
            ZipTheFolder zipfolder = new ZipTheFolder();
            try {
                zipfolder.zip(myFiles, zipFile);
            } catch (Exception ex) {
                // some errors occurred
                ex.printStackTrace();
            }
            // Second Attachement

            MimeBodyPart messageBodyPart2 = new MimeBodyPart();
            String filename2 = System.getProperty("user.dir") + "/target/site.zip";

            try {
                messageBodyPart2.attachFile(filename2, "application/zip", "base64");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            multipart.addBodyPart(messageBodyPart);
            // multipart.addBodyPart(messageBodyPart2);

            // third part for displaying image in the email body
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent("<h1>Attached is the zip</h1>", "text/html");
            multipart.addBodyPart(messageBodyPart);

            // Set the multipart message to the email message
            msg.setContent(multipart);

            // Send message
            Transport.send(msg);
            System.out.println("EMail Sent Successfully with image!!");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}


