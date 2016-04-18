package com.mmontes.model.entity.TIP;

import com.mmontes.model.entity.OSM.OSMType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tiptype")
public class TIPtype {

    private static final String TIPtype_ID_GENERATOR = "TIPtypeIdGenerator";
    private Long id;
    private String name;
    private String icon;
    private Set<OSMType> osmTypes = new HashSet<>();

    public TIPtype() {
    }

    @Column(name = "id")
    @Id
    @SequenceGenerator(name = TIPtype_ID_GENERATOR, sequenceName = "tiptype_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TIPtype_ID_GENERATOR)
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

    @Column(name = "icon")
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tipType")
    public Set<OSMType> getOsmTypes() {
        return osmTypes;
    }

    public void setOsmTypes(Set<OSMType> osmTypes) {
        this.osmTypes = osmTypes;
    }
}
