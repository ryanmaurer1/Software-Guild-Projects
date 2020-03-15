/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.freevst.entities;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.Data;

/**
 *
 * @author ryanm
 */
@Data
@Embeddable
public class RatingId implements Serializable {

    private int pluginId;

    private int userId;

    public RatingId() {

    }

    public RatingId(int pluginId, int uderId) {
        this.pluginId = pluginId;
        this.userId = uderId;
    }

}
