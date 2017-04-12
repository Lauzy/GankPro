package com.freedom.lauzy.gankpro.function.entity;

public class AuthorEntity {
    private String tip;
    private String url;

    public AuthorEntity(String tip, String url) {
        this.tip = tip;
        this.url = url;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
