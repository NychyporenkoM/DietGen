package antarit.dietgen.activities;

import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import antarit.dietgen.R;
import antarit.dietgen.adapters.RationAdapter;
import antarit.dietgen.datarecords.DatabaseRecord;
import antarit.dietgen.helpers.RationHelper;

public class RationsActivity extends BaseActivity {
    ArrayList<DatabaseRecord> mRations;
    RationAdapter mRationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContent(R.layout.activity_food_products);

        setRations();
        setRationAdapter(new RationAdapter(this, getRations()));

        ExpandableListView listView = (ExpandableListView) getActivityContent().findViewById(R.id.act_products_elv_products);
        if (listView != null) {
            listView.setAdapter(getRationAdapter());
            setIndicatorBounds(listView);
        }
    }

    public void setRations(ArrayList<DatabaseRecord> rations) {
        this.mRations = rations;
    }

    public void setRations(){
        RationHelper rationHelper = new RationHelper(this);
        setRations(rationHelper.getCurrentDietTypeRecords(getIntent().getIntExtra(DietsActivity.EXTRA_DIET_ID, -1)));
    }

    public void setRationAdapter(RationAdapter rationAdapter) {
        this.mRationAdapter = rationAdapter;
    }

    public ArrayList<DatabaseRecord> getRations() {
        return mRations;
    }

    public RationAdapter getRationAdapter() {
        return mRationAdapter;
    }
}