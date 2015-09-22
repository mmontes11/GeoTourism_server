package com.mmontes.model.entity.TIP;

import com.mmontes.model.entity.City;
import com.vividsolutions.jts.geom.Geometry;

import static com.mmontes.util.GlobalNames.MONUMENT_DISCRIMINATOR;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = MONUMENT_DISCRIMINATOR)
public class Monument extends TIP{

    public Monument() {
    }

    public Monument(String name, String type, Geometry geom, String address, String description, String photoUrl, String infoUrl, String googleMapsUrl, City city) {
        super(name, type, geom, address, description, photoUrl, infoUrl, googleMapsUrl, city);
    }
}
