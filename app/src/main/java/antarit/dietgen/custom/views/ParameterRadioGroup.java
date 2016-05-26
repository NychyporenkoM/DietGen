package antarit.dietgen.custom.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import antarit.dietgen.R;

public class ParameterRadioGroup extends LinearLayout{

    private TextView tvParameterName;
    private RadioGroup rgValue;
    private ImageView ivEdit;

    public ParameterRadioGroup(Context context) {
        super(context);
        setViews(context);
    }

    public ParameterRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        setViews(context);
    }

    public ParameterRadioGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setViews(context);
    }

    public void setViews(final Context context) {
        final View rootView = inflate(context, R.layout.cv_parameter_rg, this);
        tvParameterName = (TextView) rootView.findViewById(R.id.cv_parameter_rg_parameter_name);
        rgValue = (RadioGroup) rootView.findViewById(R.id.cv_parameter_rg);
        ivEdit = (ImageView) rootView.findViewById(R.id.cv_parameter_rg_iv_select);
    }

    public TextView getTvParameterName() {
        return tvParameterName;
    }

    public RadioGroup getRgValue() {
        return rgValue;
    }

    public ImageView getIvEdit() {
        return ivEdit;
    }

    public void setParameterName(int stringId) {
        getIvEdit().setVisibility(View.INVISIBLE);
        getTvParameterName().setText(stringId);
    }

    public static RadioButton getRadioButton(Context context, int resId, int stringId) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        RadioButton radioButton = new RadioButton(context);
        radioButton.setId(resId);
        radioButton.setText(stringId);
        radioButton.setLayoutParams(layoutParams);

        return radioButton;
    }
}