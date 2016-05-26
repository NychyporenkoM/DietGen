package antarit.dietgen.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import antarit.dietgen.R;
import antarit.dietgen.adapters.DietAdapter;
import antarit.dietgen.helpers.DietHelper;

public class DietsActivity extends BaseActivity
        implements AdapterView.OnItemClickListener,
        View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener {

    public static final String EXTRA_DIET_ID = "diet id";
    public static final int RESULT_DIET = 1;

    Cursor mDietsCursor;
    DietAdapter mDietAdapter;
    DietHelper mDietHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContent(R.layout.activity_diets);

        setDietHelper(new DietHelper(this));
        setDietsCursor(getDietHelper().getAllTableRecordsCursor());
        setDietAdapter(new DietAdapter(this, getDietsCursor()));

        ListView listView = (ListView) getActivityContent().findViewById(R.id.act_diets_lv_diets);
        if (listView != null) {
            listView.setAdapter(getDietAdapter());
            listView.setOnItemClickListener(this);
        }

        FloatingActionButton floatingActionButton = (FloatingActionButton) getActivityContent().findViewById(R.id.act_diets_fab);
        if (floatingActionButton != null)
            floatingActionButton.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        getDietsCursor().moveToPosition(position);
        Integer recordId = getDietsCursor().getInt(getDietsCursor().getColumnIndexOrThrow("_id"));
        Intent intent = new Intent(this, RationsActivity.class);
        intent.putExtra(EXTRA_DIET_ID, recordId);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateCursor();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, DietGeneratorActivity.class);
        startActivityForResult(intent, RESULT_DIET);
    }

    public void setDietsCursor (Cursor dietsCursor) {
        this.mDietsCursor = dietsCursor;
    }

    public void setDietAdapter (DietAdapter dietAdapter) {
        this.mDietAdapter = dietAdapter;
    }

    public void setDietHelper (DietHelper dietHelper) {
        this.mDietHelper = dietHelper;
    }

    public Cursor getDietsCursor() {
        return mDietsCursor;
    }

    public DietAdapter getDietAdapter() {
        return mDietAdapter;
    }

    public DietHelper getDietHelper() {
        return mDietHelper;
    }

    public void updateCursor() {
        Cursor cursor = getDietHelper().getAllTableRecordsCursor();
        getDietAdapter().changeCursor(cursor);
        setDietsCursor(cursor);
    }

}