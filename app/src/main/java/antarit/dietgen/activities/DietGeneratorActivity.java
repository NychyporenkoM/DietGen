package antarit.dietgen.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import antarit.dietgen.R;
import antarit.dietgen.custom.views.ParameterRadioGroup;
import antarit.dietgen.custom.views.ParameterTextView;
import antarit.dietgen.custom.views.UserView;
import antarit.dietgen.datarecords.Diet;
import antarit.dietgen.datarecords.User;
import antarit.dietgen.Generator;
import antarit.dietgen.helpers.DietHelper;
import antarit.dietgen.helpers.UserHelper;

public class DietGeneratorActivity extends BaseActivity
        implements RadioGroup.OnCheckedChangeListener,
        View.OnClickListener {

    public static final String EXTRA_SELECTABLE = "selectable";

    private User mUser;
    private Diet mDiet;
    private UserView mUserView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContent(R.layout.activity_diet_generator);
        setDiet(new Diet());
        setViews();
        FloatingActionButton floatingActionButton = (FloatingActionButton) getActivityContent().findViewById(R.id.act_diet_generator_fab);
        if (floatingActionButton != null)
            floatingActionButton.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.act_diet_generator_rb_gain_muscle:
                getDiet().setPurpose(Diet.Purpose.GainMuscle);
                break;
            case R.id.act_diet_generator_rb_balanced_nutrition:
                getDiet().setPurpose(Diet.Purpose.BalancedNutrition);
                break;
            case R.id.act_diet_generator_rb_fat_loss:
                getDiet().setPurpose(Diet.Purpose.FatLoss);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_diet_generator_fab:
                saveDiet();
                break;
            default:
                Intent intent = new Intent(this, UsersActivity.class);
                intent.putExtra(EXTRA_SELECTABLE, true);
                startActivityForResult(intent, UsersActivity.RESULT_USER);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case UsersActivity.RESULT_USER:
                UserHelper userHelper = new UserHelper(this);
                User user = (User) userHelper.getRecordById(data.getIntExtra(UsersActivity.EXTRA_USER_ID, -1));
                setUser(user);
                setVisibility();
                break;
            default:
                break;
        }
    }

    public void setUser(User user) {
        this.mUser = user;
    }

    public void setDiet(Diet diet) {
        this.mDiet = diet;
    }

    public void setUserView(UserView mUserView) {
        this.mUserView = mUserView;
    }

    public User getUser() {
        return mUser;
    }

    public Diet getDiet() {
        return mDiet;
    }

    public UserView getUserView() {
        return mUserView;
    }

    public void setViews() {
        ParameterTextView parameterTextView = (ParameterTextView) getActivityContent().findViewById(R.id.act_diet_generator_cv_not_selected_user);
        if(parameterTextView != null)
            parameterTextView.setParameterName(R.string.user);

        setUserView ((UserView) getActivityContent().findViewById(R.id.act_diet_generator_cv_selected_user));
        getUserView().setVisibility(View.GONE);

        LinearLayout selectUser = (LinearLayout) getActivityContent().findViewById(R.id.act_diet_generator_select_user);
        if (selectUser != null)
            selectUser.setOnClickListener(this);

        RadioButton rbGainMuscle = ParameterRadioGroup.getRadioButton(this, R.id.act_diet_generator_rb_gain_muscle, R.string.purpose_gain_muscle);
        RadioButton rbBalancedNutrition = ParameterRadioGroup.getRadioButton(this, R.id.act_diet_generator_rb_balanced_nutrition, R.string.purpose_balanced_nutrition);
        RadioButton rbFatLoss = ParameterRadioGroup.getRadioButton(this, R.id.act_diet_generator_rb_fat_loss, R.string.purpose_fat_loss);

        ParameterRadioGroup editPurpose = (ParameterRadioGroup) getActivityContent().findViewById(R.id.act_diet_generator_cv_edit_diet_purpose);
        if (editPurpose != null) {
            editPurpose.setParameterName(R.string.purpose);
            RadioGroup rgPurpose = editPurpose.getRgValue();
            rgPurpose.addView(rbGainMuscle);
            rgPurpose.addView(rbBalancedNutrition);
            rgPurpose.addView(rbFatLoss);
            rgPurpose.setOnCheckedChangeListener(this);
        }
    }

    public void setVisibility() {
        ParameterTextView editUser = (ParameterTextView) getActivityContent().findViewById(R.id.act_diet_generator_cv_not_selected_user);
        if (editUser != null)
            editUser.setVisibility(View.GONE);
        getUserView().setVisibility(View.VISIBLE);
        getUserView().customizeView(getUser());
    }

    public void saveDiet() {
        try {
            checkDiet();
            getUser().setDiet(getDiet());
            Generator generator = new Generator(this, getUser());
            setDiet(generator.getDiet());
            DietHelper helper = new DietHelper(this);
            helper.saveRecord(getDiet());
            setResult(DietsActivity.RESULT_DIET);
            finish();
        } catch (NullUserException e) {
            processException("Select the User");
        } catch (NullPurposeException e) {
            processException("Choose the Purpose");
        } catch (Generator.EmptyRecordException e) {
            processException(e.getMessage());
        } catch (Exception e) {
            processException("Something went wrong");
        }
    }

    public void checkDiet() throws Exception {
        if(getUser() == null)
            throw new NullUserException();
        if(getDiet().getPurpose() == null)
            throw  new NullPurposeException();
    }

    class NullUserException extends Exception {}

    class NullPurposeException extends Exception {}

}