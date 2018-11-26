/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.services;

import com.miage.models.Annotation;
import com.miage.models.File;
import com.miage.models.Point;
import com.miage.models.User;
import com.miage.repositories.AnnotationRepository;
import com.miage.repositories.FileRepository;
import com.miage.repositories.PointRepository;
import com.miage.repositories.UserRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mikhail
 */
@Service
public class LeaderBoardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private AnnotationRepository annotationRepository;

    public LeaderBoardService() {
    }

    public List<User> getAllUsers() {
        Iterable<User> IUsers = userRepository.findAll();
        Stream<User> usersStream = StreamSupport.stream(IUsers.spliterator(), false);

        List<User> users = usersStream.collect(Collectors.toList());
        return users;
    }

    public Point getUserPoints(User user) {
        Iterable<Point> points = pointRepository.findAll();
        Stream<Point> pointsStream = StreamSupport.stream(points.spliterator(), false);

        Optional<Point> result = pointsStream.filter(u -> Objects.equals(user.getId(), u.getUserId()))
                .findFirst();

        if (result.isPresent()) {
            return result.get();
        } else {
            return new Point(user.getId(), 0);
        }
    }

    public List<String> getAllBadges(User user) {
        List<String> badges = new ArrayList<>();

        if (getUsersWithHighestPoint().contains(user)) {
            badges.add("Super Star");
        }

        if (getUsersWithHighestNumberOfUploads().contains(user)) {
            badges.add("Best Coder");
        }

        if (getUsersWithHighestNumberOfReviews().contains(user)) {
            badges.add("Master Reviewer");
        }

        if (getUsersWithHighestNumberOfFilesWithoutAnnotations().contains(user)) {
            badges.add("Master Coder");
        }

        return badges;
    }

    public boolean isHappy(User user) {
        int allUserPoints = getAllUsersPoints();
        int numOfUsers = userRepository.findAll().size();
        Point point = getUserPoints(user);
        int userPoints = point != null ? point.getValue() : 0;//pointRepository.findById(user.getId()).get().getValue();
        return (userPoints > allUserPoints / numOfUsers);
    }

    private Integer getAllUsersPoints() {
        Iterable<Point> points = pointRepository.findAll();
        Stream<Point> pointsStream = StreamSupport.stream(points.spliterator(), false);
        return pointsStream.mapToInt(p -> p.getValue()).sum();
    }

    private List<User> getUsersWithHighestPoint() {
        Iterable<Point> points = pointRepository.findAll();
        Stream<Point> pointsStream = StreamSupport.stream(points.spliterator(), false);

        int maxPoint = pointsStream.max(Comparator.comparing(Point::getValue)).get().getValue();
        List<Integer> usersIds = StreamSupport.stream(points.spliterator(), false)
                .filter(p -> p.getValue() == maxPoint)
                .map(p -> p.getUserId())
                .collect(Collectors.toList());

        Iterable<User> users = userRepository.findAll();
        Stream<User> usersStream = StreamSupport.stream(users.spliterator(), false);

        List<User> topUsers = usersStream
                .filter(u -> usersIds.contains(u.getId()))
                .collect(Collectors.toList());
        return topUsers;
    }

    private List<User> getUsersWithHighestNumberOfUploads() {
        Iterable<File> files = fileRepository.findAll();
        Stream<File> filesStream = StreamSupport.stream(files.spliterator(), false);
        Map<User, Long> groupedFiles = filesStream.collect(
                Collectors.groupingBy(File::getUser, Collectors.counting())
        );
        long highestNumberOfUploads = groupedFiles.entrySet()
                .stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                .get()
                .getValue();

        List<User> topUsers = groupedFiles.entrySet()
                .stream()
                .filter(u -> u.getValue() == highestNumberOfUploads)
                .map(u -> u.getKey())
                .collect(Collectors.toList());

        return topUsers;
    }

    private List<User> getUsersWithHighestNumberOfReviews() {
        Iterable<Annotation> annotations = annotationRepository.findAll();
        Stream<Annotation> annotationsStream = StreamSupport.stream(annotations.spliterator(), false);

        Map<User, Long> grouppedAnnotations = annotationsStream.collect(
                Collectors.groupingBy(Annotation::getUser, Collectors.counting())
        );

        long highestNumberOfReviews = grouppedAnnotations.entrySet()
                .stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                .get()
                .getValue();

        List<User> topUsers = grouppedAnnotations.entrySet()
                .stream()
                .filter(u -> u.getValue() == highestNumberOfReviews)
                .map(u -> u.getKey())
                .collect(Collectors.toList());

        return topUsers;
    }

    private List<User> getUsersWithHighestNumberOfFilesWithoutAnnotations() {
        Iterable<File> files = fileRepository.findAll();
        Stream<File> filesStream = StreamSupport.stream(files.spliterator(), false);

        Iterable<Annotation> annotations = annotationRepository.findAll();
        Stream<Annotation> annotationsStream = StreamSupport.stream(annotations.spliterator(), false);
        //List<Annotation> annotationsList = annotationsStream.collect(Collectors.toList());
        List<Integer> annotatedFilesId = annotationsStream
                .map(a -> a.getFile().getFileId())
                .collect(Collectors.toList());

        List<File> filesWithoutAnnotations = filesStream.filter(f -> !annotatedFilesId.contains(f.getFileId()))
                .collect(Collectors.toList());

        Map<User, Long> grouppedFilesWithoutAnnotations = filesWithoutAnnotations
                .stream()
                .collect(
                        Collectors.groupingBy(File::getUser, Collectors.counting())
                );

        long highestNumberOfFilesWithoutAnnotations = grouppedFilesWithoutAnnotations.entrySet()
                .stream()
                .max((entry1, entry2) -> entry1.getValue() < entry2.getValue() ? 1 : -1)
                .get()
                .getValue();

        List<User> topUsers = grouppedFilesWithoutAnnotations.entrySet()
                .stream()
                .filter(u -> u.getValue() == highestNumberOfFilesWithoutAnnotations)
                .map(u -> u.getKey())
                .collect(Collectors.toList());

        return topUsers;
    }
}
