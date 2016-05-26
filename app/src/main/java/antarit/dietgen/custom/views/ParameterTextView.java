package antarit.dietgen.custom.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import antarit.dietgen.R;

public class ParameterTextView extends LinearLayout {

    private TextView tvParameterName;
    private TextView tvValue;
    private ImageView ivEdit;

    public ParameterTextView (Context context) {
        super(context);
        setViews(context);
    }

    public ParameterTextView (Context context, AttributeSet attrs) {
        super(context, attrs);
        setViews(context);
    }

    public ParameterTextView (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setViews(context);
    }

    public void setViews (final Context context) {
        final View rootView = inflate(context, R.layout.cv_parameter_tv, this);
        tvParameterName = (TextView) rootView.findViewById(R.id.cv_parameter_tv_parameter_name);
        tvValue = (TextView) rootView.findViewById(R.id.cv_parameter_tv);
        ivEdit = (ImageView) rootView.findViewById(R.id.cv_parameter_tv_iv_select);
    }

    public TextView getTvParameterName() {
        return tvParameterName;
    }

    public TextView getTvValue() {
        return tvValue;
    }

    public ImageView getIvEdit() {
        return ivEdit;
    }

    public void setParameterName (int stringId) {
        getTvParameterName().setText(stringId);
    }

}
