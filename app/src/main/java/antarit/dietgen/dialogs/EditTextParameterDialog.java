package antarit.dietgen.dialogs;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import antarit.dietgen.R;

public class EditTextParameterDialog extends DialogFragment implements Button.OnClickListener {

    public static final String title = "title";
    public static final String previousText = "oldName";

    private EditText mEditText;
    private OnCompleteListener mOnCompleteListener;

    public static EditTextParameterDialog newInstance(String dialogTitle, String oldName) {
        EditTextParameterDialog editTextParameterDialog = new EditTextParameterDialog();

        Bundle args = new Bundle();
        args.putString(title, dialogTitle);
        args.putString(previousText, getStringSafely(oldName));

        editTextParameterDialog.setArguments(args);

        return editTextParameterDialog;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle(getArguments().getString(title));

        View view = inflater.inflate(R.layout.dialog_et_parameter, null);

        setEditText ((EditText) view.findViewById(R.id.dlg_et_parameter));
        getEditText().setText(getArguments().getString(previousText));

        setOnCompleteListener ((OnCompleteListener) getActivity());

        Button cancelButton = (Button) view.findViewById(R.id.dlg_et_parameter_bt_cancel);
        cancelButton.setOnClickListener(this);
        
        Button okButton = (Button) view.findViewById(R.id.dlg_et_parameter_bt_complete);
        okButton.setOnClickListener(this);

        return view;
    }

    public static String getStringSafely(String someText) {
        return someText == null ? "" : someText;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dlg_et_parameter_bt_complete:
                String name = getEditText().getText().toString();
                getOnCompleteListener().OnComplete(name);
                dismiss();
                break;
            default:
                dismiss();
                break;
        }
    }

    public interface OnCompleteListener {
        void OnComplete(String name);
    }

    public void setEditText(EditText editText) {
        this.mEditText = editText;
    }

    public void setOnCompleteListener(OnCompleteListener onCompleteListener) {
        this.mOnCompleteListener = onCompleteListener;
    }

    public EditText getEditText() {
        return mEditText;
    }

    public OnCompleteListener getOnCompleteListener() {
        return mOnCompleteListener;
    }
}