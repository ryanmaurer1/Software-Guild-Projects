/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.freevst.service;

import com.mycompany.freevst.data.CategoryRepository;
import com.mycompany.freevst.entities.Category;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author ryanm
 */
@Service
public class CategoryService {

    private final CategoryRepository categoryRepo;

    public CategoryService(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public List<Category> findAllCategories() {
        return categoryRepo.findAll();
    }

    public Category findById(int id) {
        return categoryRepo.findById(id).orElse(null);
    }

}
