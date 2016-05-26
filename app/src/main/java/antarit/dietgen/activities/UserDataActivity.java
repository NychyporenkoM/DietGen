package antarit.dietgen.activities;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import antarit.dietgen.dialogs.EditTextParameterDialog;
import antarit.dietgen.dialogs.NumberPickerParameterDialog;
import antarit.dietgen.custom.views.ParameterRadioGroup;
import antarit.dietgen.custom.views.ParameterTextView;
import antarit.dietgen.R;
import antarit.dietgen.datarecords.User;
import antarit.dietgen.helpers.UserHelper;

public class UserDataActivity extends BaseActivity
        implements RadioGroup.OnCheckedChangeListener,
        NumberPickerParameterDialog.OnCompleteListener,
        EditTextParameterDialog.OnCompleteListener,
        View.OnClickListener {
    private User mUser;
    private UserHelper mUserHelper;
    private Boolean mEditable;

    private TextView tvName;
    private TextView tvAge;
    private TextView tvHeight;
    private TextView tvWeight;
    private TextView tvMealsCount;
    private TextView tvPhysicalActivityDescription;
    private RadioButton rbMale;
    private RadioButton rbFemale;
    private RadioButton rbSedentary;
    private RadioButton rbLight;
    private RadioButton rbModerate;
    private RadioButton rbActive;
    private RadioButton rbExtreme;
    private FloatingActionButton fab;
    private ArrayList<ParameterTextView> editViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContent(R.layout.activity_user_data);

        editViews = new ArrayList<>();
        setViews();

        setUserHelper(new UserHelper(this));

        Integer intentValue = getIntent().getIntExtra(UsersActivity.EXTRA_USER_ID, -1);

        if (intentValue != -1) {
            setUser((User) getUserHelper().getRecordById(intentValue));
            setUserNameAsTitle();
            setEditable(Boolean.FALSE);
            updateViewsContent();
        } else {
            setUser(new User());
            setEditable(Boolean.TRUE);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.act_user_data_rb_male:
                getUser().setGender(User.Gender.Male);
                break;
            case R.id.act_user_data_rb_female:
                getUser().setGender(User.Gender.Female);
                break;
            case R.id.act_user_data_rb_sedentary:
                getUser().setPhysicalActivityLevel(User.PhysicalActivityLevel.Sedentary);
                tvPhysicalActivityDescription.setText(getString(R.string.description_sedentary));
                break;
            case R.id.act_user_data_rb_light:
                getUser().setPhysicalActivityLevel(User.PhysicalActivityLevel.Light);
                tvPhysicalActivityDescription.setText(getString(R.string.description_light));
                break;
            case R.id.act_user_data_rb_moderate:
                getUser().setPhysicalActivityLevel(User.PhysicalActivityLevel.Moderate);
                tvPhysicalActivityDescription.setText(getString(R.string.description_moderate));
                break;
            case R.id.act_user_data_rb_active:
                getUser().setPhysicalActivityLevel(User.PhysicalActivityLevel.Active);
                tvPhysicalActivityDescription.setText(getString(R.string.description_active));
                break;
            case R.id.act_user_data_rb_extreme:
                getUser().setPhysicalActivityLevel(User.PhysicalActivityLevel.Extreme);
                tvPhysicalActivityDescription.setText(getString(R.string.description_extreme));
                break;
            default: break;
        }
    }

    @Override
    public void OnComplete(Integer numberType, Integer newValue) {
        switch (numberType) {
            case 1:
                getUser().setAge(newValue);
                break;
            case 2:
                getUser().setHeight(newValue);
                break;
            case 3:
                getUser().setWeight(newValue);
                break;
            case 4:
                getUser().setMealsCount(newValue);
                break;
            default:
                break;
        }
        updateViewsContent();
    }

    @Override
    public void OnComplete(String name) {
        getUser().setName(name);
        updateViewsContent();
    }

    @Override
    public void onClick(View v) {
        DialogFragment dialog;
        switch (v.getId()) {
            case R.id.act_user_data_fab:
                saveUser();
                break;
            case R.id.act_user_data_cv_edit_age:
                dialog = NumberPickerParameterDialog.newInstance(1, getUser().getAge());
                dialog.show(getFragmentManager(), "Age");
                break;
            case R.id.act_user_data_cv_edit_height:
                dialog = NumberPickerParameterDialog.newInstance(2, getUser().getHeight());
                dialog.show(getFragmentManager(), "Height");
                break;
            case R.id.act_user_data_cv_edit_weight:
                dialog = NumberPickerParameterDialog.newInstance(3, getUser().getWeight());
                dialog.show(getFragmentManager(), "Weight");
                break;
            case R.id.act_user_data_cv_edit_meals_count:
                dialog = NumberPickerParameterDialog.newInstance(4, getUser().getMealsCount());
                dialog.show(getFragmentManager(), "Meals");
                break;
            case R.id.act_user_data_cv_edit_name:
                dialog = EditTextParameterDialog.newInstance("Enter user name", tvName.getText().toString());
                dialog.show(getFragmentManager(), "Name");
                break;
            default:
                break;
        }
    }

    @Override
    public void processException(String message) {
        super.processException(message);
        setEditable(Boolean.TRUE);
    }

    public void setUser(User user) {
        this.mUser = user;
    }

    public void setEditable(Boolean editable) {
        this.mEditable = editable;

        updateFloatingActionButton();
        setEditableViews(getVisibility(isEditable()));
        setEditableRadioGroups(isEditable());
    }

    public void setUserHelper(UserHelper userHelper) {
        this.mUserHelper = userHelper;
    }

    public User getUser() {
        return mUser;
    }

    public Boolean isEditable() {
        return mEditable;
    }

    public UserHelper getUserHelper() {
        return mUserHelper;
    }

    private Boolean isNewUser() {
        return getUser().getId() == null;
    }

    private void setViews() {

        ParameterTextView editName = (ParameterTextView) getActivityContent().findViewById(R.id.act_user_data_cv_edit_name);
        if (editName != null) {
            editName.setParameterName(R.string.name);
            tvName = editName.getTvValue();
            editViews.add(editName);
        }

        ParameterTextView editAge = (ParameterTextView) getActivityContent().findViewById(R.id.act_user_data_cv_edit_age);
        if (editAge != null) {
            editAge.setParameterName(R.string.age);
            tvAge = editAge.getTvValue();
            editViews.add(editAge);
        }

        ParameterTextView editHeight = (ParameterTextView) getActivityContent().findViewById(R.id.act_user_data_cv_edit_height);
        if (editHeight != null) {
            editHeight.setParameterName(R.string.height);
            tvHeight = editHeight.getTvValue();
            editViews.add(editHeight);
        }

        ParameterTextView editWeight = (ParameterTextView) getActivityContent().findViewById(R.id.act_user_data_cv_edit_weight);
        if (editWeight != null) {
            editWeight.setParameterName(R.string.weight);
            tvWeight = editWeight.getTvValue();
            editViews.add(editWeight);
        }

        ParameterTextView editMealsCount = (ParameterTextView) getActivityContent().findViewById(R.id.act_user_data_cv_edit_meals_count);
        if (editMealsCount != null) {
            editMealsCount.setParameterName(R.string.meals);
            tvMealsCount = editMealsCount.getTvValue();
            editViews.add(editMealsCount);
        }

        rbMale = ParameterRadioGroup.getRadioButton(this, R.id.act_user_data_rb_male, R.string.gender_male);
        rbFemale = ParameterRadioGroup.getRadioButton(this, R.id.act_user_data_rb_female, R.string.gender_female);

        ParameterRadioGroup editGender = (ParameterRadioGroup) getActivityContent().findViewById(R.id.act_user_data_cv_edit_gender);
        if (editGender!=null) {
            editGender.setParameterName(R.string.gender);
            RadioGroup rgGender = editGender.getRgValue();
            rgGender.addView(rbMale);
            rgGender.addView(rbFemale);
            rgGender.setOnCheckedChangeListener(this);
        }

        rbSedentary = ParameterRadioGroup.getRadioButton(this, R.id.act_user_data_rb_sedentary, R.string.physical_activity_sedentary);
        rbLight = ParameterRadioGroup.getRadioButton(this, R.id.act_user_data_rb_light, R.string.physical_activity_light);
        rbModerate = ParameterRadioGroup.getRadioButton(this, R.id.act_user_data_rb_moderate, R.string.physical_activity_moderate);
        rbActive = ParameterRadioGroup.getRadioButton(this, R.id.act_user_data_rb_active, R.string.physical_activity_active);
        rbExtreme = ParameterRadioGroup.getRadioButton(this, R.id.act_user_data_rb_extreme, R.string.physical_activity_extreme);

        ParameterRadioGroup editPhysicalActivity = (ParameterRadioGroup) getActivityContent().findViewById(R.id.act_user_data_cv_edit_physical_activity);
        if (editPhysicalActivity!=null) {
            editPhysicalActivity.setParameterName(R.string.physical_activity);
            RadioGroup rgPhysicalActivity = editPhysicalActivity.getRgValue();
            rgPhysicalActivity.addView(rbSedentary);
            rgPhysicalActivity.addView(rbLight);
            rgPhysicalActivity.addView(rbModerate);
            rgPhysicalActivity.addView(rbActive);
            rgPhysicalActivity.addView(rbExtreme);
            rgPhysicalActivity.setOnCheckedChangeListener(this);
        }

        tvPhysicalActivityDescription = (TextView) getActivityContent().findViewById(R.id.act_user_data_tv_physical_activity_description);

        fab = (FloatingActionButton) getActivityContent().findViewById(R.id.act_user_data_fab);
        if (fab != null)
            fab.setOnClickListener(this);
    }

    private void updateViewsContent() {
        updateTextViewContent(tvName, getUser().getName());
        updateTextViewContent(tvAge, getUser().getAge());
        updateTextViewContent(tvHeight, getUser().getHeight());
        updateTextViewContent(tvWeight, getUser().getWeight());
        updateTextViewContent(tvMealsCount, getUser().getMealsCount());
        updateRadioGroupGenderContent(getUser().getGender());
        updateRadioGroupPhysicalActivityContent(getUser().getPhysicalActivityLevel());
    }

    private void updateTextViewContent(TextView view, Integer value) {
        view.setText(String.valueOf(value));
    }

    private void updateTextViewContent(TextView view, String value) {
        view.setText(value);
    }

    private void updateRadioGroupPhysicalActivityContent(User.PhysicalActivityLevel PhysicalActivityLevel) {
        switch (PhysicalActivityLevel) {
            case Sedentary:
                rbSedentary.setChecked(true);
                break;
            case Light:
                rbLight.setChecked(true);
                break;
            case Moderate:
                rbModerate.setChecked(true);
                break;
            case Active:
                rbActive.setChecked(true);
                break;
            case Extreme:
                rbExtreme.setChecked(true);
                break;
            default:
                break;
        }
    }

    private void updateRadioGroupGenderContent(User.Gender gender) {
        switch(gender) {
            case Male:
                rbMale.setChecked(true);
                break;
            case Female:
                rbFemale.setChecked(true);
                break;
            default:
                break;
        }
    }

    private void updateFloatingActionButton() {
        if(!isEditable())
            fab.setImageResource(R.drawable.new_edit);
        else
            fab.setImageResource(R.drawable.new_complete);
    }

    private void setEditableViews (int visibility) {

        for (ParameterTextView editView : editViews) {

            editView.getIvEdit().setVisibility(visibility);

            if(isEditable())
                editView.setOnClickListener(this);
            else
                editView.setOnClickListener(null);

            if(editView.getId() == R.id.act_user_data_cv_edit_name && (!isNewUser()))
                editView.setVisibility(getEditNameViewVisibility(visibility));
        }
    }

    private void setEditableRadioGroups(Boolean isEnabled) {
        rbMale.setEnabled(isEnabled);
        rbFemale.setEnabled(isEnabled);
        rbSedentary.setEnabled(isEnabled);
        rbLight.setEnabled(isEnabled);
        rbModerate.setEnabled(isEnabled);
        rbActive.setEnabled(isEnabled);
        rbExtreme.setEnabled(isEnabled);
    }

    private int getEditNameViewVisibility(int visibility) {
        return (visibility == View.INVISIBLE) ? View.GONE : View.VISIBLE;
    }

    private int getVisibility(Boolean isEditable) {
        return isEditable ? View.VISIBLE : View.INVISIBLE;
    }

    private void saveUser() {
        try {
            setEditable(!isEditable());
            checkUser();
            getUserHelper().saveRecord(getUser());
            setUserNameAsTitle();

            if(isNewUser())
                this.finish();

        } catch (NullNameException e) {
            processException("Enter the Name");
        } catch (NullAgeException e) {
            processException("Enter the Age");
        } catch (NullHeightException e) {
            processException("Enter the Height");
        } catch (NullWeightException e) {
            processException("Enter the Weight");
        } catch (NullMealsCountException e) {
            processException("Enter the Meals count");
        } catch (NullGenderException e) {
            processException("Choose the Gender");
        } catch (NullPhysicalActivityException e) {
            processException("Choose the Physical activity");
        } catch (Exception e) {
            processException("Something went wrong");
        }
    }

    private void setUserNameAsTitle() {
        setTitle(getUser().getName());
    }

    private void checkUser() throws Exception {
        if (getUser().getName() == null)
            throw new NullNameException();
        if (getUser().getAge() == 0)
            throw new NullAgeException();
        if (getUser().getHeight() == 0)
            throw new NullHeightException();
        if (getUser().getWeight() == 0)
            throw new NullWeightException();
        if (getUser().getMealsCount() == 0)
            throw new NullMealsCountException();
        if (getUser().getGender() == null || getUser().getGender() == User.Gender.UnChosen)
            throw new NullGenderException();
        if (getUser().getPhysicalActivityLevel() == null || getUser().getPhysicalActivityLevel() == User.PhysicalActivityLevel.UnChosen)
            throw new NullPhysicalActivityException();
    }

    class NullAgeException extends Exception {}

    class NullHeightException extends Exception {}

    class NullWeightException extends Exception {}

    class NullMealsCountException extends Exception {}

    class NullGenderException extends Exception {}

    class NullPhysicalActivityException extends Exception {}

    class NullNameException extends Exception {}

}