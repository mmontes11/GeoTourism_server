package com.mmontes.model.entity.route;

import com.vividsolutions.jts.geom.Geometry;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "route")
public class Route {

    private static final String Route_ID_GENERATOR = "RouteIdGenerator";
    private Long id;
    private String name;
    private String description;
    private Geometry geom;
    private String googleMapsUrl;
    private Set<RouteTIP> routeTIPs = new HashSet<>();

    public Route() {
    }

    @Column(name = "id")
    @Id
    @SequenceGenerator(name = Route_ID_GENERATOR, sequenceName = "route_id_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = Route_ID_GENERATOR)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @Column(name = "name")
    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "geom")
    @Type(type = "org.hibernate.spatial.GeometryType")
    public Geometry getGeom() {
        return geom;
    }

    public void setGeom(Geometry geom) {
        this.geom = geom;
    }

    @Column(name = "googlemapsurl")
    public String getGoogleMapsUrl() {
        return googleMapsUrl;
    }

    public void setGoogleMapsUrl(String googleMapsUrl) {
        this.googleMapsUrl = googleMapsUrl;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.route")
    public Set<RouteTIP> getRouteTIPs() {
        return routeTIPs;
    }

    public void setRouteTIPs(Set<RouteTIP> routeTIPs) {
        this.routeTIPs = routeTIPs;
    }
}
