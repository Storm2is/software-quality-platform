/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.services;

import com.miage.models.File;
import com.miage.models.User;
import com.miage.repositories.FileRepository;
import com.miage.repositories.UserRepository;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mikhail
 */
@Service
public class NotificationService {
    @Autowired
    private Environment env;
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private FileRepository fileRepository;
    
    private Map<Integer,InternetAddress> allUsersAdreses;
    private Properties mailServerProperties;
    private Session getMailSession;
    private MimeMessage generateMailMessage;
    
    private String login;
    private String password;
    
    private String serviceAdress;        
    
    @Autowired
    public NotificationService()
    {        
        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");        
        getMailSession = Session.getDefaultInstance(mailServerProperties, null);                
    }
    
    @PostConstruct
    private void setup()
    {
        login=env.getProperty("emailNotification.login");
        password=env.getProperty("emailNotification.password");                
        serviceAdress=env.getProperty("emailNotification.serviceAdress");
        
        List<User> allUsers= userRepository.findAll();
        
        allUsersAdreses= new HashMap<>();
        
        allUsers.forEach(user -> {
            InternetAddress newAdress = new InternetAddress();
            newAdress.setAddress(user.getEmail());
            try {
                newAdress.setPersonal(user.getUsername());
            } catch (UnsupportedEncodingException ex) {
                System.out.println(ex.getMessage());
            }
            allUsersAdreses.put(user.getId(),newAdress);
        });
    }
    
    public void newCodeUploaded(Integer userId, String fileName){            
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date());        
        String subjectMsg = "New code avalible";
        String link = "<a href='"+serviceAdress+"/code/files'>link</a>";
        String userName=allUsersAdreses.get(userId).getPersonal();
        String bodyMsg = "User " + userName + " at " + timeStamp
                + " added a new code file " + fileName
                + " into the platform."
                + "\n To get access check this " + link;
        Map<Integer,InternetAddress> to = new HashMap<>(allUsersAdreses);
        to.remove(userId);        
        try {
            SendEmail(subjectMsg, bodyMsg, to.values() );
        } catch (MessagingException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void codeAnnotated(Integer userId, Integer fileId){
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date());        
        String subjectMsg = "Your code was annotated";
        String link = "<a href='"+serviceAdress+"/review/file/"+fileId+"'>link</a>";
        String userName=allUsersAdreses.get(userId).getPersonal();
        File file = fileRepository.findById(fileId).get();
        String fileName=file.getFileName();
        String bodyMsg = "User " + userName + " at " + timeStamp
                + " anotated your code file " + fileName                
                + ".\n To get access check this " + link;
        InternetAddress fileOwnerAdress=allUsersAdreses.get(file.getUser().getId());        
        List<InternetAddress> to = new ArrayList<>();
        to.add(fileOwnerAdress);
        try {
            SendEmail(subjectMsg, bodyMsg, to );
            //System.out.println(subjectMsg + "/n" + bodyMsg+"/n"+to.size());
        } catch (MessagingException ex) {
            System.out.println(ex.getMessage());
        }
    }                       
    
    private void SendEmail(String subject, String messageBody,
            Collection<InternetAddress> recipients)
            throws AddressException, MessagingException {                              
                        
        InternetAddress[] arrayOfRecipients = recipients.toArray(new InternetAddress[recipients.size()]);                        
        
        generateMailMessage = new MimeMessage(getMailSession);        
        generateMailMessage.addRecipients(Message.RecipientType.TO, arrayOfRecipients);
        generateMailMessage.setSubject(subject);        
        generateMailMessage.setContent(messageBody, "text/html");

        Transport transport = getMailSession.getTransport("smtp");

        transport.connect("smtp.gmail.com", login, password);
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }
    
    
}
