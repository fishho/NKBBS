package com.cfish.rvb.adapter;

import com.cfish.rvb.bean.Section;

/**
 * Created by dfish on 2016/11/19.
 */
public interface SectionStateChangeListener {
    void onSectionStateChanged(Section section, boolean isOpen);
}
