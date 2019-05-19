package com.pilab.yunnan;

public class Traditional {
    //类型
    private String type;
    //图片资源 ID
    private String imgUrl;
    //简略介绍
    private String info;

    public Traditional(String type, String imgId, String info) {
        this.type = type;
        this.imgUrl = imgId;
        this.info = info;
    }

    public String getType() {
        return type;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getInfo() {
        return info;
    }
}
