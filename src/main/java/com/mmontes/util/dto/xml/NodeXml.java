package com.mmontes.util.dto.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by Carlos on 10/07/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class NodeXml {

    @XmlAttribute
    private Long id;

    @XmlAttribute
    private String lat;

    @XmlAttribute
    private String lon;

    @XmlElement(name = "tag")
    private List<EntryXml> tags;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public List<EntryXml> getTags() {
        return tags;
    }

    public void setTags(List<EntryXml> tags) {
        this.tags = tags;
    }

}
