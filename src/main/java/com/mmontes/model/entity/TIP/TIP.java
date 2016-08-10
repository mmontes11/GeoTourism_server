package com.mmontes.model.entity.TIP;

import com.mmontes.model.entity.City;
import com.mmontes.model.entity.Comment;
import com.mmontes.model.entity.Rating;
import com.mmontes.model.entity.UserAccount;
import com.mmontes.model.entity.route.RouteTIP;
import com.vividsolutions.jts.geom.Geometry;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Calendar;
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
    private boolean reviewed;
    private City city;
    private Calendar creationDate;
    private UserAccount userAccount;
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

    @Column(name = "reviewed")
    public boolean isReviewed() {
        return reviewed;
    }

    public void setReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cityid")
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Column(name = "creationDate")
    @Temporal(TemporalType.TIMESTAMP)
    public Calendar getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Calendar creationDate) {
        this.creationDate = creationDate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.tip", orphanRemoval=true, cascade=CascadeType.ALL)
    public Set<RouteTIP> getRouteTIPs() {
        return routeTIPs;
    }

    public void setRouteTIPs(Set<RouteTIP> routeTIPs) {
        this.routeTIPs = routeTIPs;
    }
}
