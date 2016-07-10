package com.mmontes.util.dto.xml;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by Carlos on 10/07/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class EntryXml {

    @XmlAttribute(name = "k")
    private String key;

    @XmlAttribute(name = "v")
    private String value;

    public EntryXml() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
