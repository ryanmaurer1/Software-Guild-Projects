/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.freevst.data;

import com.mycompany.freevst.entities.Category;
import com.mycompany.freevst.entities.Type;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author ryanm
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TypeRepositoryTest {

    @Autowired
    TypeRepository typeRepo;

    @Autowired
    CategoryRepository categoryRepo;

    public TypeRepositoryTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testFindAll() {

        assertTrue(typeRepo.findAll().size() > 0);

    }

    @Test
    public void testFindById() {

        assertNotNull(typeRepo.findById(1).orElse(null));

    }

    @Test
    public void testAddAType() {

        Category category = categoryRepo.findById(1).orElse(null);

        Type type = new Type();

        type.setName("EQ");
        type.setCategory(category);

        typeRepo.save(type);
        assertNotNull(type);

        typeRepo.delete(type);

    }

    @Test
    public void testFindByName() {

        assertNotNull(typeRepo.findByName("Synth"));

    }

}
