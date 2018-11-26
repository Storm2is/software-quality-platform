/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.controllers;

import com.miage.enums.CodeQualification;
import com.miage.models.Annotation;
import com.miage.models.File;
import com.miage.models.Sprint;
import com.miage.repositories.AnnotationRepository;
import com.miage.repositories.FileRepository;
import com.miage.repositories.SprintRepository;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Mikhail
 */
@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private AnnotationRepository annotationRepository;

    @Autowired
    private SprintRepository sprintRepository;

    @GetMapping("/member")
    public String member_dashboard(Model model) throws IOException {
        return "member_dashboard";
    }

    @GetMapping("/master")
    public String master_dashboard(Model model) throws IOException {
        return "sm_dashboard";
    }

    //With each sprint:
    //count nb_anno by files (reviewed / validated
    //Ignore file haven't reviewed yet (in progress or ready)
    public Map<Integer, Integer> countAnnotationByFile(Model model, int sprintID) {
        Timestamp startTime = sprintRepository.findById(sprintID).get().getStartTime();
        Timestamp endTime = sprintRepository.findById(sprintID).get().getEndTime();

        Map<Integer, Integer> file_anno = new HashMap<Integer, Integer>();

        for (File file : fileRepository.findAll()) {
            int count = 0;
            if ((file.getStatus().getStatusName() == "Validated" || file.getStatus().getStatusName() == "Reviewed")
                    && file.getPushTime().after(startTime) && file.getPushTime().before(endTime)) {
                int fileID = file.getFileId();

                for (Annotation ann : annotationRepository.findByFileId(fileID)) {
                    count++;
                }
                file_anno.put(fileID, count);
            }
        }
        return file_anno;
    }

    //Count by files: 
    //1. Calculate the number of annotation of each reviewed files 
    //2. Calculate the percentage (group by the nb of annotation) of files in their range / the total number of file reviewed
    //Range: Files having nb_of annoatation = 0-2, 3-5, > 5
    @GetMapping("/master/getPercentage/{springId}/{typeId}")
    @ResponseBody
    public float getPercentageAnnoBySprint(Model model, @PathVariable int typeId, @PathVariable int springId) {
        float result = 0;
        float range_1 = 0;
        float range_2 = 0;
        float range_3 = 0;

        Map<Integer, Integer> anno_count = countAnnotationByFile(model, springId);

        int nb_file_total = anno_count.values().size();

        //Count nb of files according to their range (having nb_of annoatation = 0-2, 3-5, > 5)
        for (int count : anno_count.values()) {
            if (count <= 2) {
                range_1++;
            } else if (count > 2 && count <= 5) {
                range_2++;
            } else {
                range_3++;
            }
        }

        CodeQualification type = CodeQualification.values()[typeId];
        // return the percentage
        switch (type) {
            case GOOD_CODE: {
                result = (range_1 / nb_file_total);
            }
            case SPAGHETTI_CODE: {
                result = (range_2 / nb_file_total);
            }
            case BAD_CODE: {
                result = (range_3 / nb_file_total);
            }
        }
        return result;
    }

    @GetMapping("/master/getGoal/{sprintId}")
    @ResponseBody
    public int getGoal(Model model, @PathVariable int sprintId) {
        return sprintRepository.findById(sprintId).get().getGoal();
    }

    @PostMapping("/master/setGoal/{sprintId}/{goal}")
    @ResponseBody
    public void setGoal(Model model, @PathVariable int sprintId, @PathVariable int goal) {
        Sprint s = sprintRepository.findById(sprintId).get();
        s.setGoal(goal);
        sprintRepository.save(s);
    }

    @GetMapping("/master/getSprintList")
    @ResponseBody
    public List<Sprint> getSprintList(Model model) {
        return (List<Sprint>) sprintRepository.findAll();
    }

    // Return a map of sprintID - percentage line accepted
    @GetMapping("/master/getAcceptedList")
    @ResponseBody
    public Map<Integer, Float> getAcceptedPercentage(Model model) {
        Map<Integer, Float> result = new HashMap<Integer, Float>();

        for (Sprint sp : sprintRepository.findAll()) {
            int total_line_by_sprint = 0;
            int total_ann_by_sprint = 0;
            int total_accepted_by_sprint = 0;

            int sprintID = sp.getSprintId();

            Timestamp startTime = sprintRepository.findById(sprintID).get().getStartTime();
            Timestamp endTime = sprintRepository.findById(sprintID).get().getEndTime();

            //Explore all files reviewed in this sprint and count the total annotation
            for (File f : fileRepository.findAll()) {
                if ((f.getStatus().getStatusName() == "Validated" || f.getStatus().getStatusName() == "Reviewed")
                        && f.getPushTime().after(startTime) && f.getPushTime().before(endTime)) {
                    int fileID = f.getFileId();
                    total_ann_by_sprint = total_ann_by_sprint + annotationRepository.findByFileId(fileID).size();
                }

                total_line_by_sprint = total_line_by_sprint + f.getFileLength();
            }
            total_accepted_by_sprint = total_line_by_sprint - total_ann_by_sprint;
            float perc_accepted_by_sprint = total_accepted_by_sprint / total_line_by_sprint;
            result.put(sprintID, perc_accepted_by_sprint);
        }
        return result;
    }
}
