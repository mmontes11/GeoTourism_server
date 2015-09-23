package com.mmontes.model.entity;


import org.hibernate.annotations.Type;
import org.postgis.Geometry;

import javax.persistence.*;

@Entity
@Table(name = "region")
public class Region {

    private static final String REGION_ID_GENERATOR = "RegionIdGenerator";
    private Long id;
    private String name;
    private String domain;
    private Country country;

    public Region() {
    }

    public Region(Long id, String name, String domain) {
        this.id = id;
        this.name = name;
        this.domain = domain;
    }

    @Column(name = "id")
    @Id
    @SequenceGenerator(name = REGION_ID_GENERATOR, sequenceName = "region_id_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = REGION_ID_GENERATOR)
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

    @Column(name = "domain")
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "countryid")
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
