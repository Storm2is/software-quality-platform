/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.services;

import com.miage.enums.GainRules;
import com.miage.enums.LoseRules;
import com.miage.models.File;
import com.miage.models.Point;
import com.miage.models.User;
import com.miage.repositories.FileRepository;
import com.miage.repositories.PointRepository;
import com.miage.repositories.UserRepository;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tamer
 */
@Service
public class PointsService {

    @Autowired
    private Environment env;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PointRepository pointsRepository;
    @Autowired
    private FileRepository fileRepository;
            
    @Autowired
    public PointsService() {
    }

    public void increasePoints(User user, GainRules rule, File file) {
        Point p = user.getPoint();
        switch (rule) 
        {
            case OWNER_UPLOAD:
            {
                if (file.getFileLength() <=50)
                {
                    p.setValue(p.getValue() + 50);
                    break;
                }else
                {
                    int count = (int)Math.round((file.getFileLength() - 50) / 10);
                    p.setValue(p.getValue() + 50 + 2*count);
                    break;
                }
            }
            // A reviewer click validate button to validates a file 
            // The nb of annotation will be count later 
            // and the points +/- are recalculated when the reviewer validate the file with /without annotation.
            case REVIEWER_VALIDATE: {
                if (file.getFileLength() <=50)
                {
                    p.setValue(p.getValue() + 10);
                    break;
                }else
                {
                    int count = (int)Math.round((file.getFileLength() - 50) / 10);
                    p.setValue(p.getValue() + 10 + count*2);
                    break;
                }
            }
            //A reviewer opens a file to review (<= 24hrs)
            case REVIEWER_VALIDATE_IN_24: {
                p.setValue(p.getValue() + 10);
                break;
            }
            //A reviewer opens a file to review (>= 48hrs)
            case REVIEWER_VALIDATE_IN_48: {
                p.setValue(p.getValue() + 25);
                break;
            }
        }
        pointsRepository.save(p);
    }

    //    REVIEWER_LATE,
   // REVIEWER_NONE
    public void  scanAndUpadtePoint()
    {
        long currentTime = System.currentTimeMillis();

        for (File file : fileRepository.findAll()) {
            // Rule 1: A code is left unreviewed for more than 24 hours 
            // -10 points each member of the team excluding the code owner
            if ((file.getStatus().getStatusName()== "Ready") && (Math.abs(currentTime -  (long)file.getPushTime().getTime()) > TimeUnit.DAYS.toMillis(2)))
            {
                for (User user: userRepository.findAll())
                {
                    if (user.getId() != file.getUser().getId())
                    {
                        //int point = user.getPoint().getValue() - 10;
                        //user.getPoint().setValue(point); 
                        decreasePointsByValue(user,LoseRules.REVIEWER_NONE,10); 
                    }
                }
                // The files causing penalty for members are just counted once
                //file.setPenaltyOff(1);
            }
            
            // Rule 2: A reviewer does not finish review in 24 hours: -10 points
            if ((file.getStatus().getStatusName()== "InProgress") && (Math.abs(currentTime -  (long)file.getPushTime().getTime()) > TimeUnit.DAYS.toMillis(2)))
            {
                //int point = file.getUser().getPoint().getValue() - 10;
                //file.getUser().getPoint().setValue(point); 
                decreasePointsByValue(file.getUser(),LoseRules.REVIEWER_LATE,10);
            }
        }        
    }
        
    public void increasePointsByValue(User user, GainRules rule, Integer value) {
        Point p = user.getPoint();
        switch (rule) {
            case REVIEWER_VALIDATE: {
                p.setValue(p.getValue() + value);
                break;
            }
        }
        pointsRepository.save(p);
    }

    public void decreasePointsByValue(User user, LoseRules rule, Integer value) {
        Point p = user.getPoint();
        switch (rule) {
            case OWNER_ANNOTATE:
            case REVIEWER_NONE: 
            case REVIEWER_LATE:
            {
                if (p.getValue() > value) {
                    p.setValue(p.getValue() - value);
                } else {
                    p.setValue(0);
                }
                break;
            }
        }
        pointsRepository.save(p);
    }
}
