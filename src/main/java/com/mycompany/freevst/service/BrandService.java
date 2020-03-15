/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.freevst.service;

import com.mycompany.freevst.data.BrandRepository;
import com.mycompany.freevst.entities.Brand;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author ryanm
 */
@Service
public class BrandService {

    private final BrandRepository repo;

    public BrandService(BrandRepository repo) {
        this.repo = repo;
    }

    public List<Brand> getAllBrands() {
        return repo.findAll();

    }
    
    public Brand findById(int id){
        return repo.findById(id).orElse(null);
    }
    
    public Brand findByName(String name) {
        return repo.findByName(name);
    }
    
    public void addBrand(Brand b){
        repo.save(b);
    }
}
