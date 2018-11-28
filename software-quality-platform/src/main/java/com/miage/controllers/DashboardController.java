/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.controllers;

import com.miage.enums.CodeQualification;
import com.miage.models.Annotation;
import com.miage.models.File;
import com.miage.models.Quality;
import com.miage.models.Sprint;
import com.miage.models.User;
import com.miage.repositories.AnnotationRepository;
import com.miage.repositories.FileRepository;
import com.miage.repositories.QualityRepository;
import com.miage.repositories.SprintRepository;
import com.miage.repositories.UserRepository;
import com.miage.services.LeaderBoardService;
import com.miage.viewmodels.SMDashboardViewModel;
import com.miage.viewmodels.QualityViewModel;
import com.miage.viewmodels.SprintProgressViewModel;
import com.miage.viewmodels.UserDashboardViewModel;
import com.miage.viewmodels.UserViewModel;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private UserRepository userRepository;

    @Autowired
    private QualityRepository qualityRepository;

    @Autowired
    private AnnotationRepository annotationRepository;

    @Autowired
    private SprintRepository sprintRepository;

    @Autowired
    private LeaderBoardService leaderBoardService;

    @GetMapping("/member")
    public String member_dashboard(Model model) throws IOException {
        return "member_dashboard";
    }

    @GetMapping("/master")
    public String master_dashboard(Model model) throws IOException {
        return "sm_dashboard";
    }

    @GetMapping("/getScrumMasterData")
    @ResponseBody
    public List<SMDashboardViewModel> get_scrum_master_data(Model model) throws IOException {
        List<SMDashboardViewModel> data = new ArrayList<>();

        List<Sprint> sprints = sprintRepository.findAll();
        List<User> users = userRepository.findAll();
        List<Quality> qualities = qualityRepository.findAll();

        SprintProgressViewModel init = new SprintProgressViewModel();
        init.setDate(sprints.get(0).getStart());
        init.setValue((float) 0);

        for (Sprint s : sprints) {
            SMDashboardViewModel item = new SMDashboardViewModel();

            List<UserViewModel> uvml = new ArrayList<UserViewModel>();
            List<File> listFiles = fileRepository.findAll();
            for (User u : users) {
                UserViewModel uvm = new UserViewModel();
                uvm.setUser(u);
                uvm.setPoints(leaderBoardService.getUserPoints(u).getValue());
                uvm.setBadges(leaderBoardService.getAllBadges(u));
                uvm.setUploadedFiles(listFiles.stream().filter(x -> x.getUser().getId() == u.getId()).collect(Collectors.toList()).size());

                uvm.setReviewedFiles(getNbFileReviewed(u.getId(), s.getId()));
                uvml.add(uvm);
            }
            item.setUsers(uvml);

            item.setPeriod(s);
            List<Quality> qlist = qualities.stream().filter(x -> Objects.equals(x.getSprintId(), s.getId())).collect(Collectors.toList());
            item.setQuality(qlist);
            List<SprintProgressViewModel> spm = new ArrayList<>();
            spm.add(init);

            List<SprintProgressViewModel> spvm2 = new ArrayList<>();
            spvm2.add(init);

            for (int i = 1; i <= s.getId(); i++) {
                SprintProgressViewModel spvm = new SprintProgressViewModel();
                int a = i;
                Sprint s1 = sprints.stream().filter(x -> a == x.getId()).findFirst().get();
                spvm.setDate(s1.getEnd());
                spvm.setValue((float) s1.getGoal());
                spm.add(spvm);

                SprintProgressViewModel usvm = new SprintProgressViewModel();
                usvm.setDate(s1.getEnd());

                List<File> files = fileRepository.findAll();
                files = files.stream().filter(x -> x.getPushTime().getTime() <= s1.getEnd().getTime()).collect(Collectors.toList());
                float totalNumberOfLines = 0;
                float totalNumberAccepted = 0;
                for (File f : files) {
                    List<Annotation> fileAnnotations = annotationRepository.findByFileId(f.getFileId());
                    totalNumberOfLines += f.getFileLength();
                    totalNumberAccepted += f.getFileLength() - (Math.pow(fileAnnotations.size() + 2, 2) * 3);
                }
                usvm.setValue((totalNumberAccepted / totalNumberOfLines) * 100);

                spvm2.add(usvm);
            }
            item.setSprintGoal(spm);
            item.setAcceptedCode(spvm2);
            data.add(item);
        }
        return data;
    }

    @GetMapping("/getUserData")
    @ResponseBody
    public List<UserDashboardViewModel> get_user_data(Model model) throws IOException {
        List<UserDashboardViewModel> list = new ArrayList<>();
        List<Sprint> sprints = sprintRepository.findAll();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByName(username);

        List<Quality> qualities = qualityRepository.findAll();
        SprintProgressViewModel init = new SprintProgressViewModel();
        init.setDate(sprints.get(0).getStart());
        init.setValue((float) 0);

        for (Sprint s : sprints) {
            UserDashboardViewModel usvm = new UserDashboardViewModel();
            usvm.setPeriod(s);

            List<SprintProgressViewModel> spm = new ArrayList<>();
            spm.add(init);

            List<SprintProgressViewModel> spvm2 = new ArrayList<>();
            spvm2.add(init);

            for (int i = 1; i <= s.getId(); i++) {
                SprintProgressViewModel spvm = new SprintProgressViewModel();
                int a = i;
                Sprint s1 = sprints.stream().filter(x -> a == x.getId()).findFirst().get();
                spvm.setDate(s1.getEnd());
                spvm.setValue((float) s1.getGoal());
                spm.add(spvm);

                List<File> files = fileRepository.findAll();
                files = files.stream().filter(x -> x.getPushTime().getTime() <= s1.getEnd().getTime()).collect(Collectors.toList());
                float totalNumberOfLines = 0;
                float totalNumberAccepted = 0;
                for (File f : files) {
                    List<Annotation> fileAnnotations = annotationRepository.findByFileId(f.getFileId());
                    totalNumberOfLines += f.getFileLength();
                    totalNumberAccepted += f.getFileLength() - (Math.pow(fileAnnotations.size() + 2, 2) * 3);
                    if (totalNumberAccepted < 0) {
                        totalNumberAccepted = totalNumberAccepted * -1;
                    }
                }
                SprintProgressViewModel usvm2 = new SprintProgressViewModel();
                usvm2.setDate(s1.getEnd());
                usvm2.setValue((totalNumberAccepted / totalNumberOfLines) * 100);

                spvm2.add(usvm2);
            }
            usvm.setSprintGoal(spm);

            usvm.setAcceptedCode(spvm2);

            List<Quality> qlist = qualities.stream().filter(x -> Objects.equals(x.getSprintId(), s.getId())).collect(Collectors.toList());
            usvm.setQuality(qlist);
            usvm.setBadges(leaderBoardService.getAllBadges(user));
            usvm.setUploadedFiles(10);
            usvm.setReviewedFiles(15);
            List<File> files = fileRepository.findAll();
            usvm.setUploadedFiles(files.stream().filter(x -> x.getUser().getId() == user.getId()).collect(Collectors.toList()).size());
            usvm.setReviewedFiles(getNbFileReviewed(user.getId(), s.getId()));

            list.add(usvm);
        }
        return list;
    }

    //With each sprint:
    //count nb_anno by files (reviewed / validated
    //Ignore file haven't reviewed yet (in progress or ready)
    public Map<Integer, Integer> countAnnotationByFile(Model model, int sprintID) {
        Timestamp startTime = sprintRepository.findById(sprintID).get().getStart();
        Timestamp endTime = sprintRepository.findById(sprintID).get().getEnd();

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

            int sprintID = sp.getId();

            Timestamp startTime = sprintRepository.findById(sprintID).get().getStart();
            Timestamp endTime = sprintRepository.findById(sprintID).get().getEnd();

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

    public int getNbFileReviewed(int userId, int sprintID) {
        Timestamp startTime = sprintRepository.findById(sprintID).get().getStart();
        Timestamp endTime = sprintRepository.findById(sprintID).get().getEnd();

        int count = 0;

        for (Annotation an : annotationRepository.findAll()) {

            if (an.getTime().after(startTime) && an.getTime().before(endTime)) {
                if (annotationRepository.findByUserId(userId).contains(an.getAnnotationId())) {

                    count++;
                }
            }
        }

        return count;
    }

}
