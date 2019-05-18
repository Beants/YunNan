package com.pilab.yunnan;

public class Traditional {
    //类型
    private String type;
    //图片资源 ID
    private int imgId;
    //简略介绍
    private String info;

    public Traditional(String type, int imgId, String info) {
        this.type = type;
        this.imgId = imgId;
        this.info = info;
    }

    public String getType() {
        return type;
    }

    public int getImgId() {
        return imgId;
    }

    public String getInfo() {
        return info;
    }
}
