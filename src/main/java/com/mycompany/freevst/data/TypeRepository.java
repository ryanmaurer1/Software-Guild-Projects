/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.freevst.data;

import com.mycompany.freevst.entities.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ryanm
 */
@Repository
public interface TypeRepository
        extends JpaRepository<Type, Integer> {

    Type findByName (String name);
    
}
