/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.freevst.service;

import com.mycompany.freevst.data.TypeRepository;
import com.mycompany.freevst.entities.Type;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author ryanm
 */
@Service
public class TypeService {

    private final TypeRepository repo;

    public TypeService(TypeRepository repo) {
        this.repo = repo;
    }

    public List<Type> findAllTypes() {
        return repo.findAll();
    }

    public Type findById(int id) {
        return repo.findById(id).orElse(null);
    }

    public List<Type> findByCategory(String categoryName) {
        List<Type> allTypes = repo.findAll();

        List<Type> typeByCategory = new ArrayList<>();

        for (Type t : allTypes) {
            if (t.getCategory().getName().equals(categoryName)) {
                typeByCategory.add(t);
            }
        }
        return typeByCategory;

    }

    public boolean typeAlreadyExists(Type t) {
        List<Type> allTypes = repo.findAll();

        for (Type type : allTypes) {
            if (type.getName().equals(t.getName())) {
                return true;
            } else {
                return false;
            }

        }
        return false;

    }

    public void addType(Type t) {
        repo.save(t);
        
    }
    
    public Type findByName(String name) {
        return repo.findByName(name);
    }
    
    
}
