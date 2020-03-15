/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.freevst.service;

import com.mycompany.freevst.data.PluginRepository;
import com.mycompany.freevst.data.RatingRepository;
import com.mycompany.freevst.data.TypeRepository;
import com.mycompany.freevst.entities.Plugin;
import com.mycompany.freevst.entities.Rating;
import com.mycompany.freevst.entities.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 *
 * @author ryanm
 */
@Service
public class PluginService {

    private final PluginRepository pluginRepo;
    private final RatingRepository ratingRepo;
    private final TypeRepository typeRepo;

    public PluginService(PluginRepository pluginRepo, RatingRepository ratingRepo, TypeRepository typeRepo) {
        this.pluginRepo = pluginRepo;
        this.ratingRepo = ratingRepo;
        this.typeRepo = typeRepo;

    }

    public List<Plugin> findAll() {
        return pluginRepo.findAll();
    }

    public void savePlugin(Plugin p) {
        pluginRepo.save(p);
    }

    public Plugin findById(int id) {
        return pluginRepo.findById(id).orElse(null);
    }

    public List<Plugin> findByType(Type t) {

        List<Plugin> allPlugins = pluginRepo.findAll().stream()
                .filter(i -> i.getType().getName().equals(t.getName()))
                .collect(Collectors.toList());

        return allPlugins; // all of the plugins with a specific "type"

    }

    public Plugin ratingsForAPlugin(Plugin plugin) {

        List<Rating> ratings = ratingRepo.findAll();

        List<Rating> ratingsForAPlugin = ratings.stream()
                .filter(i -> i.getPlugin().getPluginId() == plugin.getPluginId()).collect(Collectors.toList());

        plugin.setRatings(ratingsForAPlugin);

        return plugin;

    }

    public double calculateAverageRating(Plugin plugin) {

        List<Rating> ratings = plugin.getRatings();

        double numOfRatings = ratings.size();

        double totalRatings = 0;
        double average = 0;

        for (Rating r : ratings) {
            totalRatings += r.getRating();
        }

        average = totalRatings / numOfRatings;

        return average;

    }

    public List<Plugin> sortedPluginsByType() {

        List<Plugin> allPlugins = pluginRepo.findAll();

        List<Plugin> sortedPlugins = allPlugins.stream().sorted((a, b) -> a.getType().getName()
                .compareTo(b.getType().getName())).collect(Collectors.toList());

        return sortedPlugins;

    }

    public void deletePlugin(Plugin p) {
        pluginRepo.delete(p);
    }

    public List<Plugin> listTopPlugins() {

        List<Plugin> allPlugins = pluginRepo.findAll();

        for (Plugin p : allPlugins) {
            ratingsForAPlugin(p);

            p.setAverage(calculateAverageRating(p));

        }

        // at this point, each plugin has a list of ratings and an average rating
        List<Plugin> sortedByAverage = allPlugins.parallelStream()
                .sorted(Comparator.comparingDouble(p -> p.getAverage())).collect(Collectors.toList());

        Collections.reverse(sortedByAverage); // the above stream didn't sort it in the right order

        List<Plugin> sortedByAverageAndType = new ArrayList<>();

        List<Type> allTypes = typeRepo.findAll();

        for (Plugin p : sortedByAverage) {

            if (!p.getRatings().isEmpty()) {

                if (allTypes.contains(p.getType())) {
                    sortedByAverageAndType.add(p);
                    allTypes.remove(p.getType());
                }
            }
        }

        return sortedByAverageAndType;

    }

}
