/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.freevst.controllers;

import com.mycompany.freevst.entities.Brand;
import com.mycompany.freevst.entities.Category;
import com.mycompany.freevst.entities.Plugin;
import com.mycompany.freevst.entities.Rating;
import com.mycompany.freevst.entities.RatingId;
import com.mycompany.freevst.entities.Type;
import com.mycompany.freevst.entities.Video;
import com.mycompany.freevst.security.MyUserDetailsService;
import com.mycompany.freevst.service.BrandService;
import com.mycompany.freevst.service.CategoryService;
import com.mycompany.freevst.service.PluginService;
import com.mycompany.freevst.service.RatingService;
import com.mycompany.freevst.service.TypeService;
import com.mycompany.freevst.service.VideoService;
import com.mycompany.freevst.service.UserService;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author ryanm
 */
@Controller
public class vstController {

    @Autowired
    BrandService brandService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    @Autowired
    TypeService typeService;

    @Autowired
    PluginService pluginService;

    @Autowired
    RatingService ratingService;

    @Autowired
    VideoService videoService;

    @Autowired
    MyUserDetailsService userDetailsService;

    // HOME PAGE
    @GetMapping("/")
    public String index(Model model) {

        HashMap pluginAverages = new HashMap();

        List<Plugin> allPlugins = pluginService.findAll();

        for (Plugin p : allPlugins) {
            p = pluginService.ratingsForAPlugin(p); // gives each plugin a list of ratings;
            double average = pluginService.calculateAverageRating(p);
            pluginAverages.put(p.getPluginId(), average);

        }
        List<Type> allTypes = typeService.findAllTypes();
        model.addAttribute("types", allTypes);

        model.addAttribute("averages", pluginAverages);
        model.addAttribute("plugins", allPlugins);
        return "home";

    }

    // ADD A NEW PLUGIN
    @GetMapping("/addPlugin")
    public String addPlugin(Plugin plugin, Type type, Model model, Brand brand) {

        List<Category> allCategories = categoryService.findAllCategories();
        List<Type> allTypes = typeService.findAllTypes();
        List<Brand> allBrands = brandService.getAllBrands();

        model.addAttribute("categories", allCategories);
        model.addAttribute("types", allTypes);
        model.addAttribute("brands", allBrands);

        return "add-plugin";
    }

    @PostMapping("/addPlugin")
    public String saveNewPlugin(@Valid Plugin plugin, BindingResult result, Model model) {

        // if the type is existing in the DB, it only has an id
        // if it is not existing already, then it only has a name
        // need this if to figure out how to add the right type to the DB
        // and how to know which type to give to the plugin
        if (!result.hasErrors()) {

            if (plugin.getType().getName().isEmpty()) {
                Type type = typeService.findById(plugin.getType().getTypeId());
                Category c = categoryService.findById(plugin.getType().getCategory().getCategoryId());
                type.setCategory(c);
                typeService.addType(type);
                Type t = typeService.findById(plugin.getType().getTypeId());
                plugin.setType(t);

            } else {

                String typeName = plugin.getType().getName();
                Type type = new Type();
                type.setName(typeName);
                Category c = categoryService.findById(plugin.getType().getCategory().getCategoryId());
                type.setCategory(c);
                typeService.addType(type);
                Type t = typeService.findByName(typeName);
                plugin.setType(t);
            }

            // this is needed for the same reasons as above
            if (plugin.getBrand().getName().isEmpty()) {
                Brand brand = brandService.findById(plugin.getBrand().getBrandId());
                plugin.setBrand(brand);
            } else {
                String brandName = plugin.getBrand().getName();
                String brandUrl = plugin.getBrand().getUrl();
                Brand brand = new Brand();
                brand.setName(brandName);
                brand.setUrl(brandUrl);
                brandService.addBrand(brand);
                Brand b = brandService.findByName(brandName);
                plugin.setBrand(b);
            }

            pluginService.savePlugin(plugin);

            return "redirect:/";
        } else {
            List<Category> allCategories = categoryService.findAllCategories();
            List<Type> allTypes = typeService.findAllTypes();
            List<Brand> allBrands = brandService.getAllBrands();

            model.addAttribute("categories", allCategories);
            model.addAttribute("types", allTypes);
            model.addAttribute("brands", allBrands);
            return "add-plugin";
        }
    }

    // RATE A PLUGIN
    @GetMapping("/rate-plugin/{pluginId}")
    public String goToRatePlugin(@PathVariable int pluginId, Model model, Rating rating) {

        Rating r = new Rating();
        model.addAttribute("rating", r);

        Plugin p = pluginService.findById(pluginId);
        model.addAttribute("plugin", p);

        List<Type> allTypes = typeService.findAllTypes();
        model.addAttribute("types", allTypes);

        return "rate-plugin";
    }

    @PostMapping("/rate-plugin/{pluginId}")
    public String ratePlugin(@Valid Rating rating, BindingResult result, @PathVariable int pluginId, Model model) {

        if (!result.hasErrors()) {

            Rating r = new Rating();
            r.setPlugin(pluginService.findById(pluginId));
            r.setRating(rating.getRating());
            r.setNote(rating.getNote());
            r.setUser(userDetailsService.getCurrentUser());
            r.setRatingDate(LocalDate.now());
            RatingId ratingId = new RatingId(pluginService.findById(pluginId).getPluginId(),
                    userDetailsService.getCurrentUser().getUserId());
            r.setRatingId(ratingId);

            ratingService.addRating(r);

            return "redirect:/view-plugin/" + pluginId;
        } else {

            Plugin p = pluginService.findById(pluginId);
            model.addAttribute("plugin", p);
            return "rate-plugin";
        }
    }

    // VIEW ALL PLUGINS FOR A TYPE
    @GetMapping("/plugins-by-type/{name}")
    public String viewPluginsByType(@PathVariable String name, Model model) {

        HashMap pluginAverages = new HashMap();

        List<Type> allTypes = typeService.findAllTypes();
        model.addAttribute("types", allTypes);

        Type t = typeService.findByName(name);
        List<Plugin> pluginsByType = pluginService.findByType(t);

        for (Plugin p : pluginsByType) {
            p = pluginService.ratingsForAPlugin(p); // gives each plugin a list of ratings;
            double average = pluginService.calculateAverageRating(p);
            pluginAverages.put(p.getPluginId(), average);

        }

        model.addAttribute("plugins", pluginsByType);
        model.addAttribute("averages", pluginAverages);
        model.addAttribute("name", name);

        return "plugins-by-type";

    }

    // VIEW ALL
    @GetMapping("/view-all-plugins")
    public String viewAllPlugins(Model model) {

        HashMap pluginAverages = new HashMap();

        List<Plugin> sortedPlugins = pluginService.sortedPluginsByType();
        model.addAttribute("plugins", sortedPlugins);

        for (Plugin p : sortedPlugins) {
            p = pluginService.ratingsForAPlugin(p); // gives each plugin a list of ratings;
            double average = pluginService.calculateAverageRating(p);
            pluginAverages.put(p.getPluginId(), average);

        }
        List<Type> allTypes = typeService.findAllTypes();

        model.addAttribute("types", allTypes);
        model.addAttribute("averages", pluginAverages);

        return "view-all-plugins";

    }

    // DELETE
    @GetMapping("/delete/{pluginId}")
    public String goToDeletePage(@PathVariable int pluginId, Model model) {

        Plugin p = pluginService.findById(pluginId);

        model.addAttribute("plugin", p);

        return "delete";

    }

    @PostMapping("/delete/{pluginId}")
    public String deletePlugin(@PathVariable int pluginId) {
        Plugin p = pluginService.findById(pluginId);

        Plugin plugin = pluginService.ratingsForAPlugin(p);

        List<Video> videosForPlugin = videoService.findByPluginId(pluginId);

        if (!videosForPlugin.isEmpty()) {
            videosForPlugin.forEach((v) -> {
                videoService.deleteVideo(v);
            });
        }

        List<Rating> ratings = plugin.getRatings();

        if (!ratings.isEmpty()) {
            ratings.forEach((r) -> {
                ratingService.deleteRating(r);
            });
        }

        pluginService.deletePlugin(plugin);

        return "redirect:/view-all-plugins";

    }

    // EDIT A PLUGIN
    @GetMapping("/edit-plugin/{pluginId}")
    public String editPlugin(@PathVariable int pluginId, Model model) {

        Plugin p = pluginService.findById(pluginId);

        List<Category> allCategories = categoryService.findAllCategories();
        List<Type> allTypes = typeService.findAllTypes();
        List<Brand> allBrands = brandService.getAllBrands();

        model.addAttribute("categories", allCategories);
        model.addAttribute("types", allTypes);
        model.addAttribute("brands", allBrands);
        model.addAttribute("plugin", p);

        return "edit-plugin";
    }

    @PostMapping("/edit-plugin/{pluginId}")
    public String saveEditedPlugin(@Valid Plugin plugin, BindingResult result, Model model) {

        // if the type is existing in the DB, it only has an id
        // if it is not existing already, then it only has a name
        // need this if to figure out how to add the right type to the DB
        // and how to know which type to give to the plugin
        if (!result.hasErrors()) {
            if (plugin.getType().getName().isEmpty()) {
                Type type = typeService.findById(plugin.getType().getTypeId());
                Category c = categoryService.findById(plugin.getType().getCategory().getCategoryId());
                type.setCategory(c);
                typeService.addType(type);
                Type t = typeService.findById(plugin.getType().getTypeId());
                plugin.setType(t);

            } else {

                String typeName = plugin.getType().getName();
                Type type = new Type();
                type.setName(typeName);
                Category c = categoryService.findById(plugin.getType().getCategory().getCategoryId());
                type.setCategory(c);
                typeService.addType(type);
                Type t = typeService.findByName(typeName);
                plugin.setType(t);
            }

            // this is needed for the same reasons as above
            if (plugin.getBrand().getName().isEmpty()) {
                Brand brand = brandService.findById(plugin.getBrand().getBrandId());
                plugin.setBrand(brand);
            } else {
                String brandName = plugin.getBrand().getName();
                String brandUrl = plugin.getBrand().getUrl();
                Brand brand = new Brand();
                brand.setName(brandName);
                brand.setUrl(brandUrl);
                brandService.addBrand(brand);
                Brand b = brandService.findByName(brandName);
                plugin.setBrand(b);
            }

            pluginService.savePlugin(plugin);

            return "redirect:/view-all-plugins";
        } else {
            List<Category> allCategories = categoryService.findAllCategories();
            List<Type> allTypes = typeService.findAllTypes();
            List<Brand> allBrands = brandService.getAllBrands();

            model.addAttribute("categories", allCategories);
            model.addAttribute("types", allTypes);
            model.addAttribute("brands", allBrands);
            return "add-plugin";
        }
    }

    @GetMapping("/top-plugins")
    public String viewTopPlugins(Model model) {

        List<Plugin> plugins = pluginService.listTopPlugins();

        HashMap pluginAverages = new HashMap();

        List<Type> allTypes = typeService.findAllTypes();
        model.addAttribute("types", allTypes);

        for (Plugin p : plugins) {

            double average = p.getAverage();
            pluginAverages.put(p.getPluginId(), average);

        }

        model.addAttribute("plugins", plugins);
        model.addAttribute("averages", pluginAverages);

        return "top-plugins";

    }

    @GetMapping("/view-plugin/{pluginId}")
    public String viewOnePlugin(@PathVariable int pluginId, Model model) {

        HashMap pluginAverages = new HashMap();

        List<Type> allTypes = typeService.findAllTypes();
        model.addAttribute("types", allTypes);

        Plugin p = pluginService.findById(pluginId);

        p = pluginService.ratingsForAPlugin(p);
        double average = pluginService.calculateAverageRating(p);
        pluginAverages.put(p.getPluginId(), average);

        List<Rating> ratingsForPlugin = ratingService.findAllRatingsForAPlugin(pluginId);
        int size = ratingsForPlugin.size() - 1;

        for (int i = size; i >= 0; i--) {
            if (ratingsForPlugin.get(i).getRatingDate() == null
                    || ratingsForPlugin.get(i).getNote() == null
                    || ratingsForPlugin.get(i).getNote().isEmpty()) {
                ratingsForPlugin.remove(i);
            }
        }

        model.addAttribute("p", p);
        model.addAttribute("averages", pluginAverages);
        model.addAttribute("ratings", ratingsForPlugin);

        return "view-plugin";
    }

}
