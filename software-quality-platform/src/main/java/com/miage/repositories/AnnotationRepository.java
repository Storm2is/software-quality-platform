/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.repositories;

import com.miage.models.Annotation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Tamer
 */
public interface AnnotationRepository extends JpaRepository<Annotation, Integer> {

    @Query("SELECT a FROM Annotation a WHERE a.file.fileId = ?1")
    List<Annotation> findByFileId(int fileId);

}
