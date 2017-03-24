package com.freedom.lauzy.gankpro.function.entity;

/**
 * Created by Lauzy on 2017/3/24.
 */

public class LibEntity {
    private String name;
    private String link;

    public LibEntity(String name, String link) {
        this.name = name;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
