package com.cfish.rvb.bean;

/**
 * Created by dfish on 2016/11/19.
 */


public class Section {

    private final String name;

    public boolean isExpanded;

    public Section(String name) {
        this.name = name;
        isExpanded = false;
    }

    public String getName() {
        return name;
    }
}

