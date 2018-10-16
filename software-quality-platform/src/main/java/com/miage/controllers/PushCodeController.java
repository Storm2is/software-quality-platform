/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.controllers;

import com.miage.helpers.EmailNotificationHelper;
import com.miage.repositories.FileRepository;
import com.miage.repositories.UserRepository;
import java.io.IOException;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.miage.helpers.storage.IStorageService;
import com.miage.models.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.mail.MessagingException;
import org.springframework.core.env.Environment;

/**
 *
 * @author Tamer
 */
@Controller
public class PushCodeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileRepository fileRepository;

    private final IStorageService storageService;

    @Autowired
    private Environment env;

    @Autowired
    public PushCodeController(IStorageService storageService) {
        this.storageService = storageService;              
    }

    /*
    * Redirect to Upload page 
     */
    @GetMapping("/upload")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(PushCodeController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));

        return "pushCode";
    }

    /*
    * This method will take care of uploading a file and storing it into local directory called: "upload-dir"
     */
    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
            @RequestParam("tags") String tags,
            RedirectAttributes redirectAttributes) throws MessagingException {

        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        File f = new File();
        f.setFileName(file.getOriginalFilename());
        f.setExtension(file.getContentType());
        f.setTags(tags);
        f.setFilePath(System.getProperty("user.dir") + "/" + env.getProperty("storage.localfolder") + "/" + file.getOriginalFilename());
        fileRepository.save(f);        
        sendNotification(f.getFileName());
        return "redirect:/";
    }
    private void sendNotification(String fName) throws MessagingException
    {
        ArrayList<String> to = new ArrayList<>();        
        userRepository.findAll().forEach(user -> to.add(user.getEmail())); //Populate list of recepicents
        String fileName = fName; //Need file name
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date());
        String username=null; //userRepository.findById(0).get().getUsername() Substitute ID        
        String subjectMsg="New code avalible";
        String link = "<a href='www.google.com'>link</a>";
        String bodyMsg="User "+username+" at "+ timeStamp+
                " added a new code file "+fileName +
                " into the platform." +
                "\n To get access check this "+ link;
        EmailNotificationHelper.generateAndSendEmail(subjectMsg, bodyMsg, to);        
    }
    
    @PostMapping("/readypush")
    public String markReadyPushCode(@RequestParam("fileId") Integer fileId) {
        File f = fileRepository.findById(fileId).get();
        f.setStatusId(1);
        f.setPushTime(new Timestamp(new Date().getTime()));
        fileRepository.save(f);
        return "redirect:/";
    }

    /*
    * To download a file just redirect to this method, localhost:8080/files/[FILE_NAME]
     */
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
