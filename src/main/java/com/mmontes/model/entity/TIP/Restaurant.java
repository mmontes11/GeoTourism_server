package com.mmontes.model.entity.TIP;

import com.mmontes.model.entity.City;
import com.vividsolutions.jts.geom.Geometry;

import static com.mmontes.util.GlobalNames.RESTAURANT_DISCRIMINATOR;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = RESTAURANT_DISCRIMINATOR)
public class Restaurant extends TIP {

    public Restaurant() {
    }

    public Restaurant(String name, String type, Geometry geom, String address, String description, String photoUrl, String infoUrl, String googleMapsUrl, City city) {
        super(name, type, geom, address, description, photoUrl, infoUrl, googleMapsUrl, city);
    }
}
