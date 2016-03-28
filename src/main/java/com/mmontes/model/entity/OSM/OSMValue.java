package com.mmontes.model.entity.OSM;

import javax.persistence.*;

@Entity
@Table(name = "osmvalue")
public class OSMValue {

    private static final String OSM_VALUE_ID_GENERATOR = "OSMValueIdGenerator";
    private Long id;
    private OSMKey osmKey;
    private String value;

    public OSMValue() {
    }

    public OSMValue(Long id, OSMKey osmKey, String value) {
        this.id = id;
        this.osmKey = osmKey;
        this.value = value;
    }

    @Column(name = "id")
    @Id
    @SequenceGenerator(name = OSM_VALUE_ID_GENERATOR, sequenceName = "osmvalue_id_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = OSM_VALUE_ID_GENERATOR)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "osmkeyid")
    public OSMKey getOsmKey() {
        return osmKey;
    }

    public void setOsmKey(OSMKey osmKey) {
        this.osmKey = osmKey;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
