package com.mmontes.model.entity;

import com.vividsolutions.jts.geom.Geometry;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "config")
public class Config {

    private int id;
    private Geometry boundingBox;
    private static final String CONFIG_ID_GENERATOR = "ConfigIdGenerator";

    public Config() {
    }

    public Config(int id, Geometry boundingBox) {
        this.id = id;
        this.boundingBox = boundingBox;
    }

    @Column(name = "id")
    @Id
    @SequenceGenerator(name = CONFIG_ID_GENERATOR, sequenceName = "config_id_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = CONFIG_ID_GENERATOR)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "boundingbox")
    @Type(type = "org.hibernate.spatial.GeometryType")
    public Geometry getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(Geometry boundingBox) {
        this.boundingBox = boundingBox;
    }
}
