/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.services;

import com.miage.enums.GainRules;
import com.miage.enums.LoseRules;
import com.miage.models.Point;
import com.miage.models.User;
import com.miage.repositories.PointRepository;
import com.miage.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
    public PointsService() {
    }

    public void increasePoints(User user, GainRules rule) {
        Point p = user.getPoint();
        switch (rule) {
            case OWNER_UPLOAD: {
                p.setValue(p.getValue() + 50);
                break;
            }
            case REVIEWER_VALIDATE: {
                p.setValue(p.getValue() + 10);
                break;
            }
            case REVIEWER_VALIDATE_IN_24: {
                p.setValue(p.getValue() + 25 + 10);
                break;
            }
            case REVIEWER_VALIDATE_IN_48: {
                p.setValue(p.getValue() + 10 + 10);
                break;
            }
        }
        pointsRepository.save(p);
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
            case OWNER_ANNOTATE: {
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
