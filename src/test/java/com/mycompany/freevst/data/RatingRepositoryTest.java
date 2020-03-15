/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.freevst.data;

import com.mycompany.freevst.entities.Plugin;
import com.mycompany.freevst.entities.Rating;
import com.mycompany.freevst.entities.RatingId;
import com.mycompany.freevst.entities.User;
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
public class RatingRepositoryTest {

    @Autowired
    RatingRepository ratingRepo;

    @Autowired
    PluginRepository pluginRepo;

    @Autowired
    UserRepository userRepo;

    public RatingRepositoryTest() {
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
    public void findAllRatings() {
        assertTrue(ratingRepo.findAll().size() > 0);
    }
//
//    @Test
//    public void testFindRatingById() {
//
//        Rating rating = ratingRepo.findById(new RatingId(1, 1)).orElse(null);
//
//        assertEquals(8, rating.getRating());
//
//    }

    @Test
    public void testAddRating() {

        User user = userRepo.findById(1).orElse(null);
        Plugin plugin = pluginRepo.findById(1).orElse(null);

        Rating rating = new Rating();

        rating.setUser(user);
        rating.setPlugin(plugin);
        rating.setRating(4);
        rating.setRatingId(new RatingId(plugin.getPluginId(), user.getUserId()));

        ratingRepo.save(rating);

        assertNotNull(rating);

        ratingRepo.delete(rating);

    }

}
