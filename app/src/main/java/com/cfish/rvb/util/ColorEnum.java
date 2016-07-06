package com.cfish.rvb.util;

import android.graphics.Color;
import android.support.annotation.ColorRes;

/**
 * Created by GKX100217 on 2016/7/6.
 */
public enum ColorEnum {

    PURPLE(0x00),
    PEAR(0x01),
    EYE(0x02),
    CHEEK(0x03),
    GRASS(0x04),
    MALUS(0x05),
    SKY(0x06),
    MUM(0x07),
    DARK(0x08);

    private int value;
    ColorEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ColorEnum mapValueToTheme(final int value) {
        for (ColorEnum theme : ColorEnum.values()) {
            if (value == theme.getValue()) {
                return theme;
            }
        }
        return getDefault();
    }

    static ColorEnum getDefault() {
        return PURPLE;
    }
}
