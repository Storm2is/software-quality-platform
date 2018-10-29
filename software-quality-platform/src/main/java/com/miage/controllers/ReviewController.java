/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.controllers;

import com.miage.helpers.storage.IStorageService;
import com.miage.models.Annotation;
import com.miage.models.File;
import com.miage.repositories.AnnotationRepository;
import com.miage.repositories.FileRepository;
import com.miage.repositories.StatusRepository;
import com.miage.repositories.UserRepository;
import com.miage.viewmodels.AnnotationViewModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
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

    private final IStorageService storageService;

    @Autowired
    public ReviewController(IStorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/file/{fileId}")
    public String reviewFile(@PathVariable int fileId, Model model) {

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
        vmodel.setReviewerId(1);
        model.addAttribute("model", vmodel);

        return "review";
    }

    @PostMapping("/file/{fileId}")
    @ResponseBody
    public String validateReview(@RequestBody AnnotationViewModel model) {

        // process line by line based on # then number
        if (model.getAnnotations().startsWith("#")) {
            String[] content = model.getAnnotations().split("#");
            System.out.println("content:" + model.getAnnotations());
            for (String line : content) {
                System.out.println("test:" + line);
            }

            for (String line : content) {
                if (!line.trim().isEmpty()) {
                    System.out.println("test:" + line);
                    Pattern p = Pattern.compile("^[0-9]+");
                    Matcher m = p.matcher(line);
                    while (m.find()) {
                        System.out.println("parsed:" + m.group());
                        int lineNb = Integer.parseInt(m.group());

                        Annotation annotation = new Annotation();
                        annotation.setUser(userRepository.findById(model.getReviewerId()).get());
                        annotation.setFile(fileRepository.findById(model.getFileId()).get());
                        annotation.setLineNb(lineNb);
                        annotation.setIsResolved(Boolean.FALSE);
                        annotation.setComment(line.split(String.valueOf(lineNb))[1]);
                        annotationRepository.save(annotation);
                    }
                }
            }
        } else {
            Annotation annotation = new Annotation();
            annotation.setUser(userRepository.findById(model.getReviewerId()).get());
            annotation.setFile(fileRepository.findById(model.getFileId()).get());
            annotation.setIsResolved(Boolean.FALSE);
            annotation.setComment(model.getAnnotations());
            annotation.setLineNb(-1);
            annotationRepository.save(annotation);
        }

        return "/code/files";
    }
}
