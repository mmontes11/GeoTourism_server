package com.mmontes.model.entity.OSM;

import com.mmontes.model.entity.TIP.TIPtype;

import javax.persistence.*;

@Entity
@Table(name = "osmtype")
public class OSMType {

    private static final String OSM_VALUE_ID_GENERATOR = "OSMTypeIdGenerator";
    private Long id;
    private OSMKey osmKey;
    private TIPtype tipType;
    private String value;

    public OSMType() {
    }

    public OSMType(Long id, OSMKey osmKey, String value, TIPtype tipType) {
        this.id = id;
        this.osmKey = osmKey;
        this.tipType = tipType;
        this.value = value;
    }

    @Column(name = "id")
    @Id
    @SequenceGenerator(name = OSM_VALUE_ID_GENERATOR, sequenceName = "osmtype_id_seq")
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

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "tiptypeid")
    public TIPtype getTipType() {
        return tipType;
    }

    public void setTipType(TIPtype tipType) {
        this.tipType = tipType;
    }

    @Column(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
