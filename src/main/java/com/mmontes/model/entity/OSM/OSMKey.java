package com.mmontes.model.entity.OSM;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "osmkey")
public class OSMKey {

    private static final String OSM_KEY_ID_GENERATOR = "OSMKeyIdGenerator";
    private Long id;
    private String key;
    private Set<OSMValue> osmValues = new HashSet<>();

    public OSMKey() {
    }

    public OSMKey(Long id, String key) {
        this.id = id;
        this.key = key;
    }

    @Column(name = "id")
    @Id
    @SequenceGenerator(name = OSM_KEY_ID_GENERATOR, sequenceName = "osmkey_id_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = OSM_KEY_ID_GENERATOR)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "key")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "osmKey")
    public Set<OSMValue> getOsmValues() {
        return osmValues;
    }

    public void setOsmValues(Set<OSMValue> osmValues) {
        this.osmValues = osmValues;
    }
}
