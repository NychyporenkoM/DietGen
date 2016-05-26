package antarit.dietgen.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import antarit.dietgen.R;
import antarit.dietgen.datarecords.Diet;
import antarit.dietgen.datarecords.User;
import antarit.dietgen.Generator;
import antarit.dietgen.helpers.DietHelper;
import antarit.dietgen.helpers.UserHelper;

public class DietAdapter extends CursorAdapter {

    LayoutInflater mLayoutInflater;

    public DietAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        setLayoutInflater ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return getLayoutInflater().inflate(R.layout.row_diet, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        DietHelper helper = new DietHelper(context);
        Diet diet = (Diet) helper.getRecord(cursor);

        TextView tvDietName = (TextView) view.findViewById(R.id.row_diet_tv_diet_name);
        String text = "Diet #" + diet.getId().toString();
        tvDietName.setText(text);

        UserHelper userHelper = new UserHelper(context);
        User user = (User) userHelper.getRecordById(diet.getUserId());
        user.setDiet(diet);

        TextView tvUserName = (TextView) view.findViewById(R.id.row_diet_tv_user_name);
        tvUserName.setText(user.getName());

        Generator generator = new Generator(context, user);

        TextView tvDailyCalories = (TextView) view.findViewById(R.id.row_diet_tv_daily_calories);
        String dailyCalories = generator.getDailyCalories(diet.getPurpose()).toString();
        tvDailyCalories.setText(dailyCalories);
    }

    public void setLayoutInflater(LayoutInflater layoutInflater) {
        this.mLayoutInflater = layoutInflater;
    }

    public LayoutInflater getLayoutInflater() {
        return mLayoutInflater;
    }
}