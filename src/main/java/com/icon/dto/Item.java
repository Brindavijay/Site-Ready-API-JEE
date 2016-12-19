package com.icon.dto;

import com.icon.annotation.MongoCollection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@MongoCollection(name="items")
@XmlRootElement
public class Item {

    @XmlElement
    private Long itemId;

    @XmlElement
    private String shortDesc;

    @XmlElement
    private String longDesc;

    @XmlElement
    private String imageUrl;

    @XmlElement
    private boolean siteReady;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isSiteReady() {
        return siteReady;
    }

    public void setSiteReady(boolean siteReady) {
        this.siteReady = siteReady;
    }
}
