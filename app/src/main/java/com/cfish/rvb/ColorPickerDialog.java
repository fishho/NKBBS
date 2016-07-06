package com.cfish.rvb;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.BundleCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.telecom.Call;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;

import com.cfish.rvb.R;
import com.cfish.rvb.util.Selector;

/**
 * Created by GKX100217 on 2016/6/22.
 */
public class ColorPickerDialog extends DialogFragment implements View.OnClickListener {
    private Callback mCallback;
    private int[] mColors;

    public interface Callback {
        void onColorSelection(int index, int color, int darker);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AppCompatDialog dialog = new AppCompatDialog(getActivity());
        dialog.setContentView(R.layout.dialog_color_picker);
        dialog.setTitle(R.string.colorPicker);

        final TypedArray array = getActivity().getResources().obtainTypedArray(R.array.themeColors);

        int length = array.length();
        mColors = new int[length];
        for (int i = 0; i < length; i++)
            mColors[i] = array.getColor(i, 0);
        array.recycle();
        final GridLayout list = (GridLayout)dialog.findViewById(R.id.grid);
        final int preselect = getArguments().getInt("preselect", -1);
        for (int i = 0; i < list.getChildCount(); i++) {
            FrameLayout child = (FrameLayout) list.getChildAt(i);
            child.setTag(i);
            child.setOnClickListener(this);
            child.getChildAt(0).setVisibility(preselect == i ? View.VISIBLE : View.GONE);

            Drawable selector = Selector.createOvalShapeSelector(mColors[i]);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int[][] states = new int[][] {
                        new int[]{-android.R.attr.state_pressed},
                        new int[]{android.R.attr.state_pressed}
                };
                int[] colors = new int[] {
                        Selector.shiftColor(mColors[i]),
                        mColors[i]
                };
                ColorStateList rippleColors = new ColorStateList(states, colors);
                setBackgroundCompat(child, new RippleDrawable(rippleColors, selector, null));
            } else {
                setBackgroundCompat(child, selector);
            }
        }
        return dialog;
    }

    @Override
    public void onClick(View v) {
        if (v.getTag() != null) {
            Integer index = (Integer) v.getTag();
            mCallback.onColorSelection(index, mColors[index], Selector.shiftColor(mColors[index]));
            dismiss();
        }
    }

    private void setBackgroundCompat(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackground(drawable);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callback))
            throw new RuntimeException("The Activity must implement Callback to be used by ColorChooserPicker.");
        mCallback = (Callback) activity;
    }

    public void show(AppCompatActivity context, int preselect) {
        Bundle args = new Bundle();
        args.putInt("preselect", preselect);
        setArguments(args);
        show(context.getSupportFragmentManager(), "COLOR_SELECTOR");
    }
}
