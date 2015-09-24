package com.mmontes.model.entity;

import com.vividsolutions.jts.geom.Geometry;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "city")
public class City {

    private Long id;
    private String name;
    private Geometry geom;
    private Region region;
    private static final String CITY_ID_GENERATOR = "CityIdGenerator";

    public City() {
    }

    @Column(name = "id")
    @Id
    @SequenceGenerator(name = CITY_ID_GENERATOR, sequenceName = "city_id_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = CITY_ID_GENERATOR)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "geom")
    @Type(type = "org.hibernate.spatial.GeometryType")
    public Geometry getGeom() {
        return geom;
    }

    public void setGeom(Geometry geom) {
        this.geom = geom;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "regionid")
    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}
