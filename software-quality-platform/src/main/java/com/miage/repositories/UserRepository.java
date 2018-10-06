/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.repositories;

import com.miage.models.User;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Tamer
 */
public interface UserRepository extends CrudRepository<User, Integer> {

}
