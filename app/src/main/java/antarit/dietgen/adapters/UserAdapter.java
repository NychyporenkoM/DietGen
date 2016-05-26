package antarit.dietgen.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import antarit.dietgen.R;
import antarit.dietgen.custom.views.UserView;
import antarit.dietgen.datarecords.User;
import antarit.dietgen.helpers.UserHelper;

public class UserAdapter extends CursorAdapter {

    LayoutInflater mLayoutInflater;

    public UserAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        setLayoutInflater ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return getLayoutInflater().inflate(R.layout.row_user, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        UserHelper userHelper = new UserHelper(context);
        User user = (User) userHelper.getRecord(cursor);

        UserView userView = (UserView) view.findViewById(R.id.row_user_cv_user_view);
        userView.customizeView(user);
    }

    public void setLayoutInflater(LayoutInflater layoutInflater) {
        this.mLayoutInflater = layoutInflater;
    }

    public LayoutInflater getLayoutInflater() {
        return mLayoutInflater;
    }
}