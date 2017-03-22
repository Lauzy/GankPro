package com.freedom.lauzy.gankpro.function.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * ORM Entity
 * Created by Lauzy on 2017/1/23.
 */
@Entity
public class CollectionEntity {
    @Id
    private Long id;
    @Property(nameInDb = "DETAIL_URL")
    private String detailUrl;
    @Property(nameInDb = "DATE")
    private String date;
    @Property(nameInDb = "DESC")
    private String desc;

    @Generated(hash = 1442452034)
    public CollectionEntity(Long id, String detailUrl, String date, String desc) {
        this.id = id;
        this.detailUrl = detailUrl;
        this.date = date;
        this.desc = desc;
    }

    @Generated(hash = 1951715304)
    public CollectionEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
