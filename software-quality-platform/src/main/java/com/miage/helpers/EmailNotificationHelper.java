/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Mikhail
 */
public class EmailNotificationHelper {

    static Properties mailServerProperties;
    static Session getMailSession;
    static MimeMessage generateMailMessage;

    /**
     * Create and send MimeMessage using the parameters provided.
     *
     * @param subject subject of the email
     * @param messageBody message body of email
     * @param recipients list of recipients
     * @throws MessagingException, AddressException
     */
    public static void generateAndSendEmail(String subject, String messageBody,
            ArrayList<String> recipients)
            throws AddressException, MessagingException {
        List<InternetAddress> to = new ArrayList<>(recipients.size());

        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");
        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);
        recipients.forEach(adress -> {
            InternetAddress newAdress = null;
            newAdress = new InternetAddress();
            newAdress.setAddress(adress);
            if (newAdress != null) {
                to.add(newAdress);
            }
        });
        InternetAddress[] arrayOfRecipients = to.toArray(new InternetAddress[to.size()]);
        generateMailMessage.addRecipients(Message.RecipientType.TO, arrayOfRecipients);
        generateMailMessage.setSubject(subject);
        generateMailMessage.setContent(messageBody, "text/html");

        Transport transport = getMailSession.getTransport("smtp");

        // Enter your correct gmail UserID and Password and do not forget Allow less secure apps in google setups
        //https://support.google.com/accounts/answer/6010255?p=lsa_blocked&hl=en&visit_id=636746096287423049-1895637013&rd=1
        transport.connect("smtp.gmail.com", "USERID", "PASSWORD");
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }
}
