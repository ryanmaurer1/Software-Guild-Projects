/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.freevst.data;

import com.mycompany.freevst.entities.Plugin;
import com.mycompany.freevst.entities.Video;
import java.util.List;
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
public class VideoRepositoryTest {
    
    @Autowired
    VideoRepository repo;
    
    @Autowired
    PluginRepository pluginRepo;
    
    public VideoRepositoryTest() {
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
    public void testAdd() {
        
        Plugin plugin = pluginRepo.findById(1).orElse(null);
        
        Video video = new Video();
        
        video.setPlugin(plugin);
        video.setLink("https://www.youtube.com/watch?v=L0z7u4j3Jfg");
        
        repo.save(video);
        assertNotNull(video);
        
        repo.delete(video);
        
    }
    
}
