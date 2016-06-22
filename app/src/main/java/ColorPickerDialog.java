import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.BundleCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.telecom.Call;
import android.view.View;

import com.cfish.rvb.R;

/**
 * Created by GKX100217 on 2016/6/22.
 */
public class ColorPickerDialog extends DialogFragment implements DialogInterface.OnClickListener {
    private Callback mCallback;
    private int[] mColor;

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
        mColor = new int[length];
        //for ()
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

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
