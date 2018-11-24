/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.controllers;

import com.miage.enums.GainRules;
import com.miage.enums.LoseRules;
import com.miage.helpers.storage.IStorageService;
import com.miage.models.Annotation;
import com.miage.models.File;
import com.miage.models.User;
import com.miage.repositories.AnnotationRepository;
import com.miage.repositories.FileRepository;
import com.miage.repositories.StatusRepository;
import com.miage.repositories.UserRepository;
import com.miage.services.NotificationService;
import com.miage.services.PointsService;
import com.miage.viewmodels.AnnotationViewModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static org.h2.util.DateTimeUtils.MILLIS_PER_DAY;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Tamer
 */
@Controller
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private AnnotationRepository annotationRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    PointsService pointService;

    private final IStorageService storageService;

    @Autowired
    public ReviewController(IStorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/file/{fileId}")
    public String reviewFile(@PathVariable int fileId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByName(username);

        File f = fileRepository.findById(fileId).get();
        Resource file = storageService.loadAsResource(f.getFileName());
        String fileLine = "";
        String content = "";
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file.getFile()));
            while ((fileLine = br.readLine()) != null) {
                content = content + fileLine + "\n";
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
        AnnotationViewModel vmodel = new AnnotationViewModel();
        vmodel.setFileId(f.getFileId());
        vmodel.setOriginContent(content);
        vmodel.setReviewerId(user.getId());
        model.addAttribute("model", vmodel);
        model.addAttribute("owner", f.getUser());
        model.addAttribute("file", f);

        return "review";
    }

    @PostMapping("/file/{fileId}")
    @ResponseBody
    public String validateReview(@RequestBody AnnotationViewModel model) {

        // process line by line based on # then number
        if (model.getAnnotations().startsWith("#")) {
            String[] content = model.getAnnotations().split("#");
            User reviewer = userRepository.findById(model.getReviewerId()).get();
            File file = fileRepository.findById(model.getFileId()).get();
            Integer annotationsNb = 0;
            for (String line : content) {
                if (!line.trim().isEmpty()) {
                    Pattern p = Pattern.compile("^[0-9]+");
                    Matcher m = p.matcher(line);
                    while (m.find()) {
                        int lineNb = Integer.parseInt(m.group());
                        Annotation annotation = new Annotation();
                        annotation.setUser(reviewer);
                        annotation.setFile(file);
                        annotation.setLineNb(lineNb);
                        annotation.setIsResolved(Boolean.FALSE);
                        annotation.setComment(line.split(String.valueOf(lineNb))[1]);
                        annotation.setTime(new Timestamp(new Date().getTime()));
                        annotationRepository.save(annotation);
                        annotationsNb++;
                    }
                }
            }
            file.setStatus(statusRepository.findById(4).get());
            pointService.decreasePointsByValue(file.getUser(), LoseRules.OWNER_ANNOTATE, annotationsNb * 2);
            pointService.increasePoints(reviewer, GainRules.REVIEWER_VALIDATE, file);
            pointService.increasePointsByValue(reviewer, GainRules.REVIEWER_VALIDATE, annotationsNb * 2);

            Timestamp now = new Timestamp(System.currentTimeMillis());
            Date date1 = new Date(now.getTime());
            Date date2 = new Date(file.getPushTime().getTime());
            boolean moreThanDay = Math.abs(date1.getTime() - date2.getTime()) > MILLIS_PER_DAY;
            if (!moreThanDay) {
                pointService.increasePoints(reviewer, GainRules.REVIEWER_VALIDATE_IN_24, file);
            } else {
                pointService.increasePoints(reviewer, GainRules.REVIEWER_VALIDATE_IN_48, file);
            }

        } else {
            User reviewer = userRepository.findById(model.getReviewerId()).get();
            File file = fileRepository.findById(model.getFileId()).get();
            Annotation annotation = new Annotation();
            annotation.setUser(reviewer);
            annotation.setFile(file);
            annotation.setIsResolved(Boolean.FALSE);
            annotation.setComment(model.getAnnotations());
            annotation.setLineNb(-1);
            annotation.setTime(new Timestamp(new Date().getTime()));
            annotationRepository.save(annotation);
            file.setStatus(statusRepository.findById(3).get());
            pointService.increasePoints(reviewer, GainRules.REVIEWER_VALIDATE, file);
        }

        notificationService.codeAnnotated(model.getReviewerId(), model.getFileId());
        return "/code/files";
    }

    @GetMapping("/annotations/{fileId}")
    public String fileAnnotations(@PathVariable int fileId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByName(username);

        File f = fileRepository.findById(fileId).get();
        Resource file = storageService.loadAsResource(f.getFileName());
        String fileLine = "";
        String content = "";
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file.getFile()));
            while ((fileLine = br.readLine()) != null) {
                content = content + fileLine + "\n";
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }

        List<Annotation> list = annotationRepository.findByFileId(f.getFileId());
        String annotations = String.join("", list.stream().map(x -> "#" + x.getLineNb() + " " + x.getComment()).collect(Collectors.toList()));
        
        AnnotationViewModel vmodel = new AnnotationViewModel();
        vmodel.setFileId(f.getFileId());
        vmodel.setOriginContent(content);
        vmodel.setReviewerId(user.getId());
        vmodel.setAnnotations(annotations);
        model.addAttribute("model", vmodel);
        model.addAttribute("owner", f.getUser());
        model.addAttribute("file", f);
        return "annotations";
    }

    // Display list of files for reviewer in the file.htlm page (exclude the owner's files)
   /* @GetMapping("/")
    public String getAllFile(Model model, @PathVariable Integer userId) {

        List<File> results = new ArrayList<>();
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "pushTime"));

        fileRepository.findAll(sort).stream().filter((file) -> (!Objects.equals(file.getUser().getId(), userId))).forEachOrdered((file) -> {
            results.add(file);
        });
        model.addAttribute("files", results);

        return "files";
    }*/
}
