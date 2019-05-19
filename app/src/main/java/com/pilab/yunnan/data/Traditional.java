package com.pilab.yunnan.data;

import java.util.List;

public class Traditional {
    private String id;
    private String image;
    private String title;
    private String info;
    private String type;
    private String detail;
    private List<String> star;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<String> getStar() {
        return star;
    }

    public void setStar(List<String> star) {
        this.star = star;
    }

}
