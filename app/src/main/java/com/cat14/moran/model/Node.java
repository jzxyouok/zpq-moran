package com.cat14.moran.model;

import java.util.List;

/**
 * 广场页面信息类
 */
public class Node {

    private String address;
    private List<ImageItem> images;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ImageItem> getImages() {
        return images;
    }

    public void setImages(List<ImageItem> images) {
        this.images = images;
    }
}
