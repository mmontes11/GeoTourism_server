package com.mmontes.util.dto.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Carlos on 10/07/2016.
 */
@XmlRootElement(name = "osm")
@XmlAccessorType(XmlAccessType.FIELD)
public class TIPXml {

    @XmlElement(name = "node")
    private List<NodeXml> node;

    public List<NodeXml> getNode() {
        return node;
    }

    public void setNode(List<NodeXml> node) {
        this.node = node;
    }
}

