package com.cfish.rvb.util;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;

/**
 * Created by GKX100217 on 2016/7/6.
 */
public class Selector {

    public static int shiftColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.9f;
        return Color.HSVToColor(hsv);
    }

    public static Drawable createSelector(int color, Shape shape) {
        ShapeDrawable shapeDrawable = new ShapeDrawable(shape);
        shapeDrawable.getPaint().setColor(color);
        ShapeDrawable darkerShapeDrawable = new ShapeDrawable(shape);
        darkerShapeDrawable.getPaint().setColor(shiftColor(color));

        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{-android.R.attr.state_pressed},shapeDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, darkerShapeDrawable);
        return stateListDrawable;
    }

    public static Drawable createOvalShapeSelector(int color) {
        return createSelector(color, new OvalShape());
    }

    //创建圆角矩形Selector
    public static Drawable createRoundRectShapeSelector(int color) {
        float[] roundRect = new float[] { 8, 8, 8, 8, 8, 8, 8, 8 };
        RoundRectShape roundRectShape = new RoundRectShape(roundRect, null, roundRect);
        return createSelector(color, roundRectShape);
    }

    //创建矩形Selector
    public static Drawable createRectShapeSelector(int color) {
        return createSelector(color, new RectShape());
    }
}
