package com.mmontes.model.entity.TIP;

import com.mmontes.model.entity.OSMType;

import javax.persistence.*;

@Entity
@Table(name = "tiptype")
public class TIPtype {

    private static final String TIPtype_ID_GENERATOR = "TIPtypeIdGenerator";
    private Long id;
    private String name;
    private String icon;
    private OSMType osmType;

    public TIPtype() {
    }

    @Column(name = "id")
    @Id
    @SequenceGenerator(name = TIPtype_ID_GENERATOR, sequenceName = "tiptype_id_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = TIPtype_ID_GENERATOR)
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

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "TIPType", cascade = CascadeType.ALL)
    public OSMType getOsmType() {
        return osmType;
    }

    public void setOsmType(OSMType osmType) {
        this.osmType = osmType;
    }
}
