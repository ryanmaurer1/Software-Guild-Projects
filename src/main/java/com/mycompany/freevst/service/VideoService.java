/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.freevst.service;

import com.mycompany.freevst.data.VideoRepository;
import com.mycompany.freevst.entities.Video;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author ryanm
 */
@Service
public class VideoService {

    private final VideoRepository repo;

    public VideoService(VideoRepository repo) {
        this.repo = repo;
    }

    public List<Video> findAll() {
        return repo.findAll();
    }

    public List<Video> findByPluginId(int pluginId) {

        List<Video> allVideos = repo.findAll();
        List<Video> videosForPlugin = new ArrayList<>();

        for (Video v : allVideos) {
            if (v.getPlugin().getPluginId() == pluginId) {
                videosForPlugin.add(v);
            }
        }

        return videosForPlugin;

    }
    
    public void deleteVideo(Video v){
        repo.delete(v);
    }

}
