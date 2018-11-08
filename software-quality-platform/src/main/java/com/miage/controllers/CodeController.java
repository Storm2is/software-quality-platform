package com.miage.controllers;

import com.miage.enums.GainRules;
import com.miage.services.NotificationService;
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
import com.miage.models.Point;
import com.miage.models.User;
import com.miage.repositories.StatusRepository;
import com.miage.repositories.UserRepository;
import com.miage.services.PointsService;
import com.miage.viewmodels.FileViewModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.mail.MessagingException;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    NotificationService notificationService;

    @Autowired
    PointsService pointService;

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
            RedirectAttributes redirectAttributes) throws MessagingException, IOException {

        storageService.store(file);

        File f = new File();
        f.setFileName(file.getOriginalFilename());
        f.setExtension(file.getContentType());
        f.setTags(tags);

        // try to get the number of line from file objectt
        // lenght = fgdgdfgdfg
        FileReader fr = new FileReader(System.getProperty("user.dir") + "/" + env.getProperty("storage.localfolder") + "/" + file.getOriginalFilename());
        LineNumberReader lnr = new LineNumberReader(fr);
        int lines = 0;
        while (lnr.readLine() != null) {
            lines++;
        }
        lnr.close();
        f.setFileLength(lines);

        f.setFilePath(System.getProperty("user.dir") + "/" + env.getProperty("storage.localfolder") + "/" + file.getOriginalFilename());
        fileRepository.save(f);
       // sendNotification(f.getFileName());
        return new ResponseEntity<File>(f, HttpStatus.OK);
    }

    @PostMapping("/push")
    @ResponseBody
    public String pushCode(@RequestBody FileViewModel file) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User owner = userRepository.findByName(username);
        File f = fileRepository.findById(file.getFileid()).get();
        f.setStatus(statusRepository.findById(1).get());
        f.setUser(owner);
        f.setPushTime(new Timestamp(new Date().getTime()));
        fileRepository.save(f);
        notificationService.newCodeUploaded(file.getUserid(), f.getFileName());
        pointService.increasePoints(owner, GainRules.OWNER_UPLOAD,f);
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

    @GetMapping("/upload/{userId}")
    public String getUserFiles(Model model, @PathVariable Integer userId) {
        List<File> results = new ArrayList<>();

        for (File file : fileRepository.findAll()) {
            if (Objects.equals(file.getUser().getId(), userId)) {
                results.add(file);
            }
        }
        model.addAttribute("files", results);

        return "upload";
    }

    /*
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
