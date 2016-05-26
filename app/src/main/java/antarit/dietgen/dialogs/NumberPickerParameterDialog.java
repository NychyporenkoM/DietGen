package antarit.dietgen.dialogs;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import antarit.dietgen.R;

public class NumberPickerParameterDialog extends DialogFragment implements Button.OnClickListener {

    public static final String argument1 = "numberType";
    public static final String argument2 = "curValue";
    public Integer numberType;
    public NumberPicker numberPicker;
    public OnCompleteListener onCompleteListener;

    public static NumberPickerParameterDialog newInstance(Integer numberType, Integer curValue) {
        NumberPickerParameterDialog numberPickerParameterDialog = new NumberPickerParameterDialog();

        Bundle args = new Bundle();
        args.putInt(argument1, numberType);
        args.putInt(argument2, curValue);

        numberPickerParameterDialog.setArguments(args);

        return numberPickerParameterDialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dlg_np_parameter_bt_complete:
                onCompleteListener.OnComplete(numberType, numberPicker.getValue());
                dismiss();
                break;
            default:
                dismiss();
                break;
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        numberType = getArguments().getInt(argument1);
        getDialog().setTitle(getTitle());

        View view = inflater.inflate(R.layout.dialog_np_parameter, null);

        numberPicker = (NumberPicker) view.findViewById(R.id.dlg_np_parameter);
        customiseNumberPicker();

        onCompleteListener = (OnCompleteListener) getActivity();

        Button cancelButton = (Button) view.findViewById(R.id.dlg_np_parameter_bt_cancel);
        cancelButton.setOnClickListener(this);
        Button okButton = (Button) view.findViewById(R.id.dlg_np_parameter_bt_complete);
        okButton.setOnClickListener(this);

        return view;
    }

    public String getTitle() {
        switch (numberType){
            case 1:
                return "Chose your age in years";
            case 2:
                return "Chose your height in centimeters";
            case 3:
                return "Chose your weight in kgs";
            case 4:
                return "Chose how many meals you want to have";
            default:
                return "Something went wrong";
        }
    }

    public void customiseNumberPicker() {
        switch (numberType){
            case 1:
                numberPicker.setMinValue(18);
                numberPicker.setMaxValue(120);
                break;
            case 2:
                numberPicker.setMinValue(140);
                numberPicker.setMaxValue(270);
                break;
            case 3:
                numberPicker.setMinValue(40);
                numberPicker.setMaxValue(300);
                break;
            case 4:
                numberPicker.setMinValue(1);
                numberPicker.setMaxValue(6);
                break;
            default:
                break;
        }
        numberPicker.setValue(getArguments().getInt(argument2));
    }

    public interface OnCompleteListener {
        void OnComplete(Integer numberType, Integer newValue);
    }
}