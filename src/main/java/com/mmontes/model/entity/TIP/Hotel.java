package com.mmontes.model.entity.TIP;

import static com.mmontes.util.GlobalNames.HOTEL_DISCRIMINATOR;

import com.mmontes.model.entity.City;
import org.postgis.Geometry;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = HOTEL_DISCRIMINATOR)
public class Hotel extends TIP{

    public Hotel() {
    }

    public Hotel(String name, String type, Geometry geom, String address, String description, String photoUrl, String infoUrl, String googleMapsUrl, City city) {
        super(name, type, geom, address, description, photoUrl, infoUrl, googleMapsUrl, city);
    }
}
