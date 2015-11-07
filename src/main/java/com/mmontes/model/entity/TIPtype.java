package com.mmontes.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "tiptype")
public class TIPtype {

    private static final String TIPtype_ID_GENERATOR = "TIPtypeIdGenerator";
    private Long id;
    private String name;

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
}
