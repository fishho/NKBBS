package com.cfish.rvb.adapter;

import com.cfish.rvb.bean.Item;
import com.cfish.rvb.bean.Section;

/**
 * Created by dfish on 2016/11/19.
 */
public interface ItemClickListener {
    void itemClicked(Item item);
    void itemClicked(Section section);
}
