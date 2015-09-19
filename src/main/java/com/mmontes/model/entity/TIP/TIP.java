package com.mmontes.model.entity.TIP;

import com.mmontes.model.entity.City;
import com.vividsolutions.jts.geom.Geometry;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "tip")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name="type",
        discriminatorType = DiscriminatorType.STRING
)
@DiscriminatorValue(value = "TIP")
public class TIP {

    private Long id;
    private String type;
    private String name;
    private Geometry geom;
    private String address;
    private String description;
    private String photoUrl;
    private String infoUrl;
    private String googleMapsUrl;
    private City city;

    public TIP() {
    }

    @Column(name = "id")
    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "increment")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "photourl")
    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Column(name = "infourl")
    public String getInfoUrl() {
        return infoUrl;
    }

    public void setInfoUrl(String infoUrl) {
        this.infoUrl = infoUrl;
    }

    @Column(name = "googlemapsurl")
    public String getGoogleMapsUrl() {
        return googleMapsUrl;
    }

    public void setGoogleMapsUrl(String googleMapsUrl) {
        this.googleMapsUrl = googleMapsUrl;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cityid")
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
