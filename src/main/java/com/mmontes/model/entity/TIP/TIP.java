package com.mmontes.model.entity.TIP;

import com.mmontes.model.entity.City;
import com.mmontes.model.entity.Comment;
import com.mmontes.model.entity.Rating;
import com.mmontes.model.entity.UserAccount;
import com.mmontes.model.entity.route.RouteTIP;
import com.vividsolutions.jts.geom.Geometry;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tip")
public class TIP {

    private static final String TIP_ID_GENERATOR = "TIPIdGenerator";
    private Long id;
    private TIPtype type;
    private String name;
    private Geometry geom;
    private String address;
    private String description;
    private String photoUrl;
    private String infoUrl;
    private String googleMapsUrl;
    private Long osmId;
    private City city;
    private Set<UserAccount> favouritedBy = new HashSet<>();
    private Set<RouteTIP> routeTIPs = new HashSet<>();

    public TIP() {
    }

    @Column(name = "id")
    @Id
    @SequenceGenerator(name = TIP_ID_GENERATOR, sequenceName = "tip_id_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = TIP_ID_GENERATOR)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "typeid")
    public TIPtype getType() {
        return type;
    }

    public void setType(TIPtype type) {
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

    @Column(name = "osmid")
    public Long getOsmId() {
        return osmId;
    }

    public void setOsmId(Long osmId) {
        this.osmId = osmId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cityid")
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "tipuseraccount",
            joinColumns = {
                    @JoinColumn(name = "tipid", nullable = false, updatable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "userid", nullable = false, updatable = false)
            }
    )
    public Set<UserAccount> getFavouritedBy() {
        return favouritedBy;
    }

    public void setFavouritedBy(Set<UserAccount> favouritedBy) {
        this.favouritedBy = favouritedBy;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.tip", orphanRemoval=true, cascade=CascadeType.ALL)
    public Set<RouteTIP> getRouteTIPs() {
        return routeTIPs;
    }

    public void setRouteTIPs(Set<RouteTIP> routeTIPs) {
        this.routeTIPs = routeTIPs;
    }
}
