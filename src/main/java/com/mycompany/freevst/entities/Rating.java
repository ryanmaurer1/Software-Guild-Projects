/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.freevst.entities;

import java.time.LocalDate;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author ryanm
 */
@Entity
@Data
public class Rating {

    @NotNull(message = "Please provide a rating")
    private int rating;

    @EmbeddedId
    RatingId ratingId;

    @Size(max = 256, message = "The review must not be longer than a tweet (256 characters)")
    private String note;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate ratingDate;

    @MapsId("pluginId")
    @ManyToOne
    @JoinColumn(name = "plugin_id")
    Plugin plugin;

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

}
