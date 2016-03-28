package com.mmontes.model.entity.OSM;

import com.mmontes.model.entity.TIP.TIPtype;

import javax.persistence.*;

@Entity
@Table(name = "osmtype")
public class OSMType {

    private static final String OSM_TYPE_ID_GENERATOR = "OSMTypeIdGenerator";
    private Long id;
    private OSMValue osmValue;
    private TIPtype TIPtype;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "osmvalueid")
    public OSMValue getOsmValue() {
        return osmValue;
    }

    public void setOsmValue(OSMValue osmValue) {
        this.osmValue = osmValue;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tiptypeid")
    public TIPtype getTIPtype() {
        return TIPtype;
    }

    public void setTIPtype(com.mmontes.model.entity.TIP.TIPtype TIPtype) {
        this.TIPtype = TIPtype;
    }
}
