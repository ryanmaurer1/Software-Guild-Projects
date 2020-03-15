/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.freevst.data;

import com.mycompany.freevst.entities.Brand;
import com.mycompany.freevst.entities.Plugin;
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
public class PluginRepositoryTest {

    @Autowired
    PluginRepository pluginRepo;

    @Autowired
    BrandRepository brandRepo;

    @Autowired
    TypeRepository typeRepo;

    public PluginRepositoryTest() {
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

        assertTrue(pluginRepo.findAll().size() > 0);

    }

    @Test
    public void testFindById() {

        Plugin plugin = pluginRepo.findById(1).orElse(null);

        String name = plugin.getName();

        assertEquals("Valhalla Frequency Echo", name);

    }

    @Test
    public void testAddPlugin() {

        Type type = typeRepo.findById(1).orElse(null);

        Brand brand = brandRepo.findById(1).orElse(null);

        Plugin plugin = new Plugin();

        plugin.setType(type);
        plugin.setBrand(brand);

        plugin.setDescription("very good");
        plugin.setDownloadLink("https://valhalladsp.com/shop/reverb/valhalla-vintage-verb/");
        plugin.setName("Valhalla Vintage Verb");
        plugin.setImageUrl("https://valhalladsp.com/wp-content/uploads/2016/06/valhalla-gui_0002_VV-80s.png");

        pluginRepo.save(plugin);

        assertNotNull(plugin);

        pluginRepo.delete(plugin);

    }

}
