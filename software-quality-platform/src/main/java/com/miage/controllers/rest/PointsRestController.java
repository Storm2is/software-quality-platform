/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.controllers.rest;

import com.miage.models.Point;
import com.miage.models.User;
import com.miage.services.LeaderBoardService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Mikhail
 */
@RestController
@RequestMapping("/leaderBoard")
public class PointsRestController {

    @Autowired
    private LeaderBoardService leaderBoardService;

    @GetMapping("/getUserInfo/{id}")
    public List<UserInfo> getUserInfo(@PathVariable Integer id) {
        List<UserInfo> userInfo = new ArrayList<>();

        User selectedUser = leaderBoardService.getAllUsers()
                .stream()
                .filter(user -> Objects.equals(user.getId(), id))
                .findFirst()
                .get();
        Point point = leaderBoardService.getUserPoints(selectedUser);

        System.out.println(point);
        int pointVal = point != null ? point.getValue() : 0;
        boolean isHappy = leaderBoardService.isHappy(selectedUser);
        List<String> badges = leaderBoardService.getAllBadges(selectedUser);

        if (!badges.isEmpty()) {
            badges.forEach(b -> userInfo.add(new UserInfo(b, pointVal, isHappy, selectedUser)));
        } else {
            userInfo.add(new UserInfo(null, pointVal, isHappy, selectedUser));
        }

        return userInfo;
    }

    @GetMapping("/getUserPoints")
    public List<UserPoints> getUserPoints() {
        List<UserPoints> userPoints = new ArrayList<>();

        leaderBoardService.getAllUsers()
                .forEach(user -> {
                    Point point = leaderBoardService.getUserPoints(user);
                    System.out.println(point.toString());
                    int pointVal = point != null ? point.getValue() : 0;
                    boolean isHappy = leaderBoardService.isHappy(user);
                    userPoints.add(new UserPoints(pointVal, isHappy, user));
                });
        return userPoints;
    }

    @GetMapping("/getBadges")
    public List<UserBadge> getAllUsersWithBadges() {
        List<UserBadge> usersWithBages = new ArrayList<>();

        leaderBoardService.getAllUsers()
                .forEach(user
                        -> {
                    leaderBoardService.getAllBadges(user)
                            .forEach(badge -> usersWithBages.add(new UserBadge(badge, user)));
                });

        return usersWithBages;
    }

    class UserWrapper {

        private User user;

        public UserWrapper(User user) {
            this.user = user;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }

    public class UserBadge extends UserWrapper {

        private String badge;

        public UserBadge(String badge, User user) {
            super(user);
            this.badge = badge;
        }

        public String getBadge() {
            return badge;
        }

        public void setBadge(String badge) {
            this.badge = badge;
        }
    }

    public class UserPoints extends UserWrapper {

        private Integer points;
        private boolean isHappy;

        public UserPoints(Integer points, boolean isHappy, User user) {
            super(user);
            this.points = points;
            this.isHappy = isHappy;
        }

        public Integer getPoints() {
            return points;
        }

        public boolean isIsHappy() {
            return isHappy;
        }

        public void setPoints(Integer points) {
            this.points = points;
        }

        public void setIsHappy(boolean isHappy) {
            this.isHappy = isHappy;
        }
    }

    public class UserInfo extends UserPoints {

        private String badge;

        public UserInfo(String badge, Integer points, boolean isHappy, User user) {
            super(points, isHappy, user);
            this.badge = badge;
        }

        public String getBadge() {
            return badge;
        }

        public void setBadge(String badge) {
            this.badge = badge;
        }
    }

}
