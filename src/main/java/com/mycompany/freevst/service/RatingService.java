/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.freevst.service;

import com.mycompany.freevst.data.RatingRepository;
import com.mycompany.freevst.entities.Rating;
import com.mycompany.freevst.entities.RatingId;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author ryanm
 */
@Service
public class RatingService {

    private final RatingRepository repo;

    public RatingService(RatingRepository repo) {
        this.repo = repo;
    }

    public List<Rating> findAll() {

        return repo.findAll();
    }

    public Rating findById(int pluginId, int userId) {

        return repo.findById(new RatingId(pluginId, userId)).orElse(null);
    }

    public void addRating(Rating r) {
        repo.save(r);
    }

    public void deleteRating(Rating r) {
        repo.delete(r);
    }

    public List<Rating> findAllRatingsForAPlugin(int pluginId) {

        List<Rating> allRatings = repo.findAll();

        List<Rating> ratingsForPlugin = new ArrayList<>();

        for (Rating r : allRatings) {
            if (r.getPlugin().getPluginId() == pluginId) {
                ratingsForPlugin.add(r);
            }
        }

        return ratingsForPlugin;

    }

}
