package antarit.dietgen.custom.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import antarit.dietgen.R;
import antarit.dietgen.datarecords.User;

public class UserView extends LinearLayout {

    public UserView (Context context) {
        super(context);
        setUserViewLayout(context);
    }

    public UserView (Context context, AttributeSet attrs) {
        super(context, attrs);
        setUserViewLayout(context);
    }

    public UserView (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setUserViewLayout(context);
    }

    public void setUserViewLayout (final Context context) {
        inflate(context, R.layout.cv_user_view, this);
    }

    public void customizeView(User user){
        TextView tvUserName = (TextView) findViewById(R.id.cv_user_view_tv_name);
        tvUserName.setText(user.getName());

        ImageView ivGenderIcon = (ImageView) findViewById(R.id.cv_user_view_iv_gender);
        ivGenderIcon.setImageResource(getGenderResourceId(user));

        ImageView ivPhysicalActivityIcon = (ImageView) findViewById(R.id.cv_user_view_iv_physical_activity);
        ivPhysicalActivityIcon.setImageResource(getPhysicalActivityResourceId(user));

        TextView tvUserAge = (TextView) findViewById(R.id.cv_user_view_tv_age);
        tvUserAge.setText(String.valueOf(user.getAge()));
        TextView tvUserHeight = (TextView) findViewById(R.id.cv_user_view_tv_height);
        tvUserHeight.setText(String.valueOf(user.getHeight()));
        TextView tvUserWeight = (TextView) findViewById(R.id.cv_user_view_tv_weight);
        tvUserWeight.setText(String.valueOf(user.getWeight()));
        TextView tvUserMealsCount = (TextView) findViewById(R.id.cv_user_view_tv_meals_count);
        tvUserMealsCount.setText(String.valueOf(user.getMealsCount()));
    }

    public int getGenderResourceId (User user) {
        switch (user.getGender()){
            case Male:
                return R.drawable.male;
            case Female:
                return R.drawable.female;
            default:
                return 0;
        }
    }

    public int getPhysicalActivityResourceId (User user) {
        switch (user.getPhysicalActivityLevel()){
            case Sedentary:
                return R.drawable.sedentary;
            case Light:
                return R.drawable.light;
            case Moderate:
                return R.drawable.moderate;
            case Active:
                return R.drawable.active;
            case Extreme:
                return R.drawable.extreme;
            default:
                return 0;
        }
    }
}
