/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.freevst.entities;

import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 *
 * @author ryanm
 */
@Entity
@Data
public class Plugin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pluginId;

    @NotBlank(message = "The plugin must have a name")
    private String name;

    @Size(max = 256, message = "The description must be less than 256 characters")
    private String description;

    @NotBlank(message = "Please provide a donwload link")
    private String downloadLink;

    @NotBlank(message = "Please provide a link to a picture of the plugin")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;

    @Transient
    @ElementCollection
    @OneToMany
    @JoinColumn(name = "plugin_id")
    private List<Rating> ratings;

    @Transient
    @ElementCollection
    @OneToMany
    @JoinColumn(name = "plugin_id")
    private List<Video> videos;

    @Transient
    private double average;

}
