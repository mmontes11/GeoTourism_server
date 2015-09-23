package com.mmontes.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "country")
public class Country {

    private Long id;
    private String name;
    private String domain;
    private static final String COUNTRY_ID_GENERATOR = "CountryIdGenerator";

    public Country() {
    }

    public Country(Long id, String name, String domain) {
        this.id = id;
        this.name = name;
        this.domain = domain;
    }

    @Column(name = "id")
    @Id
    @SequenceGenerator(name = COUNTRY_ID_GENERATOR, sequenceName = "country_id_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = COUNTRY_ID_GENERATOR)
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
}
