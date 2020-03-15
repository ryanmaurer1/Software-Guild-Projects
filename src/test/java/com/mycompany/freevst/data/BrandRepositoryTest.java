/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.freevst.data;

import com.mycompany.freevst.entities.Brand;
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
public class BrandRepositoryTest {

    @Autowired
    BrandRepository repo;

    public BrandRepositoryTest() {
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

        assertTrue(repo.findAll().size() > 0);

    }
    
    @Test
    public void testFindById(){
        
        Brand brand = repo.findById(1).orElse(null);
        
        String name = brand.getName();
        
        assertEquals("Valhalla", name);
        
    }
    
    @Test
    public void testAddBrand() {
        
        Brand brand = new Brand();
        
        brand.setName("u-he");
        brand.setUrl("https://u-he.com/");
        
        repo.save(brand);
        assertNotNull(brand);
        
        repo.delete(brand);
        
    }
    
    

}
