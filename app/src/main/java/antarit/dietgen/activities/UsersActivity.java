package antarit.dietgen.activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import antarit.dietgen.R;
import antarit.dietgen.adapters.UserAdapter;
import antarit.dietgen.helpers.UserHelper;

public class UsersActivity extends BaseActivity
        implements AdapterView.OnItemClickListener,
        View.OnClickListener {

    public static final String EXTRA_USER_ID = "user id";
    public static final int RESULT_USER = 1;

    private Cursor mUsersCursor;
    private UserAdapter mUserAdapter;
    private UserHelper mUserHelper;
    private Boolean mSelectable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContent(R.layout.activity_users);

        setSelectable();
        setUserHelper(new UserHelper(this));
        setUsersCursor(getUserHelper().getAllTableRecordsCursor());
        setUserAdapter(new UserAdapter(this, getUsersCursor()));

        ListView listView = (ListView) getActivityContent().findViewById(R.id.act_users_lv_users);
        if (listView != null) {
            listView.setAdapter(getUserAdapter());
            listView.setOnItemClickListener(this);
        }

        FloatingActionButton floatingActionButton = (FloatingActionButton) getActivityContent().findViewById(R.id.act_users_fab);
        if (floatingActionButton != null) {
            floatingActionButton.setOnClickListener(this);
            floatingActionButton.show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        getUsersCursor().moveToPosition(position);

        Integer recordId = getUsersCursor().getInt(getUsersCursor().getColumnIndexOrThrow("_id"));

        Intent intent = new Intent(this, UserDataActivity.class);
        intent.putExtra(EXTRA_USER_ID, recordId);

        if (isSelectable()) {
            setResult(RESULT_USER, intent);
            finish();
        } else
            startActivityForResult(intent, RESULT_USER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateCursor();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, UserDataActivity.class);
        startActivityForResult(intent, RESULT_USER);
    }

    public void setUsersCursor (Cursor usersCursor) {
        this.mUsersCursor = usersCursor;
    }

    public void setUserHelper (UserHelper userHelper) {
        this.mUserHelper = userHelper;
    }

    public void setUserAdapter (UserAdapter userAdapter) {
        this.mUserAdapter = userAdapter;
    }

    public void setSelectable (Boolean selectable) {
        this.mSelectable = selectable;
    }

    public void setSelectable() {
        if(getIntent()!=null)
            setSelectable(getIntent().getBooleanExtra("selectable", false));
        else
            setSelectable(false);
    }

    public Cursor getUsersCursor() {
        return mUsersCursor;
    }

    public UserHelper getUserHelper() {
        return mUserHelper;
    }

    public UserAdapter getUserAdapter() {
        return mUserAdapter;
    }

    public Boolean isSelectable() {
        return mSelectable;
    }

    public void updateCursor() {
        Cursor userCursor = getUserHelper().getAllTableRecordsCursor();
        getUserAdapter().changeCursor(userCursor);
        setUsersCursor(userCursor);
    }
}