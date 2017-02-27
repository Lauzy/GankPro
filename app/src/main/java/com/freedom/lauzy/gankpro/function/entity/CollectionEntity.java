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
    @Property(nameInDb = "IMAGE_URL")
    private String imgUrl;
    @Property(nameInDb = "DATE")
    private String date;
    @Property(nameInDb = "DESC")
    private String desc;
    @Generated(hash = 254575603)
    public CollectionEntity(Long id, String imgUrl, String date, String desc) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.date = date;
        this.desc = desc;
    }
    @Generated(hash = 1951715304)
    public CollectionEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getImgUrl() {
        return this.imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getDesc() {
        return this.desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
