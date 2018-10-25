package com.miage.controllers;

import com.miage.repositories.FileRepository;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.miage.helpers.storage.IStorageService;
import com.miage.models.File;
import com.miage.repositories.StatusRepository;
import com.miage.repositories.UserRepository;
import com.miage.viewmodels.FileViewModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Timestamp;
import java.util.Date;
import javax.mail.MessagingException;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Tamer
 */
@Controller
@RequestMapping("/code")
public class CodeController {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusRepository statusRepository;

    private final IStorageService storageService;

    @Autowired
    private Environment env;

    @Autowired
    public CodeController(IStorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/upload")
    public String uploadPage(Model model) throws IOException {
        model.addAttribute("files", fileRepository.findAll());
        /*
        model.addAttribute("username", username);
        int userid = userRepository.getUserIdByUsername(username);
        model.addAttribute("userid", userid);
         */
        return "upload";
    }

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<File> uploadCodeHandler(
            @RequestParam("file") MultipartFile file,
            @RequestParam("tags") String tags,
            RedirectAttributes redirectAttributes) throws MessagingException {

        storageService.store(file);

        File f = new File();
        f.setFileName(file.getOriginalFilename());
        f.setExtension(file.getContentType());
        f.setTags(tags);
        f.setFilePath(System.getProperty("user.dir") + "/" + env.getProperty("storage.localfolder") + "/" + file.getOriginalFilename());
        fileRepository.save(f);
        //sendNotification(f.getFileName());
        return new ResponseEntity<File>(f, HttpStatus.OK);
    }

    @PostMapping("/push")
    @ResponseBody
    public String pushCode(@RequestBody FileViewModel file) {
        File f = fileRepository.findById(file.getFileid()).get();
        f.setStatus(statusRepository.findById(1).get());
        f.setUser(userRepository.findById(file.getUserid()).get());
        f.setPushTime(new Timestamp(new Date().getTime()));
        fileRepository.save(f);
        return "/files/all";
    }

    @RequestMapping(value = "/filecontent/{filename}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getFileContent(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        String fileLine = "";
        String content = "";
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file.getFile()));
            while ((fileLine = br.readLine()) != null) {
                content = content + fileLine + "<br>";
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
        return content;
    }

    @GetMapping("/files")
    public String getAllFile(Model model) {
        model.addAttribute("files", fileRepository.findAll());
        return "files";
    }
    /*

    private void sendNotification(String fName) throws MessagingException {
        ArrayList<String> to = new ArrayList<>();
        userRepository.findAll().forEach(user -> to.add(user.getEmail())); //Populate list of recepicents
        String fileName = fName; //Need file name
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date());
        String username = null; //userRepository.findById(0).get().getUsername() Substitute ID        
        String subjectMsg = "New code avalible";
        String link = "<a href='www.google.com'>link</a>";
        String bodyMsg = "User " + username + " at " + timeStamp
                + " added a new code file " + fileName
                + " into the platform."
                + "\n To get access check this " + link;
        EmailNotificationHelper.generateAndSendEmail(subjectMsg, bodyMsg, to);
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @RequestMapping("/files/GetAllFile")
    public ResponseEntity<Resource> getAllFile() {
        return (ResponseEntity<Resource>) fileRepository.findAll();
    }
     */
}
