package com.cfish.rvb.bean;

/**
 * Created by dfish on 2016/11/19.
 */
public class Item {

    private final String name;
    private final int id;

    public Item(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
