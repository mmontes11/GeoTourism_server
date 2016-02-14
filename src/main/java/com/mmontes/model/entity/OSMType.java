package com.mmontes.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "osmtype")
public class OSMType {

    private static final String OSM_TYPE_ID_GENERATOR = "OSMTypeIdGenerator";
    private Long id;
    private String keyName;
    private String value;
    private Long TIPTypeId;

    public OSMType() {
    }

    public OSMType(Long id, String keyName, String value, Long TIPTypeId) {
        this.id = id;
        this.keyName = keyName;
        this.value = value;
        this.TIPTypeId = TIPTypeId;
    }

    @Column(name = "id")
    @Id
    @SequenceGenerator(name = OSM_TYPE_ID_GENERATOR, sequenceName = "osmtype_id_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = OSM_TYPE_ID_GENERATOR)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "keyname")
    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    @Column(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Column(name = "tiptypeid")
    public Long getTIPTypeId() {
        return TIPTypeId;
    }

    public void setTIPTypeId(Long TIPTypeId) {
        this.TIPTypeId = TIPTypeId;
    }
}
