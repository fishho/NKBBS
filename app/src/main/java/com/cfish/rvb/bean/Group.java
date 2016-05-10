package com.cfish.rvb.bean;

/**
 * Created by GKX100217 on 2016/2/26.
 */
public class Group implements Comparable<Group> {
    private String gid;
    private String name;
    private String label;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public int compareTo(Group another) {
        return this.getLabel().compareTo(another.getLabel());
    }
}
