package antarit.dietgen.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import antarit.dietgen.R;
import antarit.dietgen.adapters.FoodProductCategoryAdapter;
import antarit.dietgen.datarecords.FoodProduct;
import antarit.dietgen.datarecords.FoodProductCategory;
import antarit.dietgen.helpers.FoodProductCategoryHelper;
import antarit.dietgen.helpers.FoodProductHelper;

public class FoodProductsActivity extends BaseActivity implements View.OnClickListener {

    FoodProductCategoryAdapter mFoodProductCategoryAdapter;
    ArrayList<FoodProductCategory> mFoodProductCategories;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContent(R.layout.activity_food_products);

        setFoodProductCategories();
        setFoodProductCategoryAdapter(new FoodProductCategoryAdapter(this, getFoodProductCategories()));

        ExpandableListView listView = (ExpandableListView) getActivityContent().findViewById(R.id.act_products_elv_products);
        if (listView != null) {
            listView.setAdapter(getFoodProductCategoryAdapter());
            setIndicatorBounds(listView);
        }

        FloatingActionButton floatingActionButton = (FloatingActionButton) getActivityContent().findViewById(R.id.act_products_fab);
        if (floatingActionButton != null)
            floatingActionButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        updateFoodData();
        finish();
    }

    public void setFoodProductCategoryAdapter(FoodProductCategoryAdapter foodProductCategoryAdapter) {
        this.mFoodProductCategoryAdapter = foodProductCategoryAdapter;
    }

    public void setFoodProductCategories(ArrayList<FoodProductCategory> foodProductCategories) {
        this.mFoodProductCategories = foodProductCategories;
    }

    public void setFoodProductCategories() {
        setFoodProductCategories(new ArrayList<FoodProductCategory>());

        FoodProductCategoryHelper foodProductCategoryHelper = new FoodProductCategoryHelper(this);

        Cursor foodProductCategoriesCursor = foodProductCategoryHelper.getAllTableRecordsCursor();

        while(foodProductCategoriesCursor.moveToNext()) {
            FoodProductCategory foodProductCategory = (FoodProductCategory) foodProductCategoryHelper.getRecord(foodProductCategoriesCursor);
            foodProductCategory = foodProductCategoryHelper.getFoodProductCategoryWithProducts(foodProductCategory.getId());
            getFoodProductCategories().add(foodProductCategory);
        }
    }

    public FoodProductCategoryAdapter getFoodProductCategoryAdapter() {
        return mFoodProductCategoryAdapter;
    }

    public ArrayList<FoodProductCategory> getFoodProductCategories() {
        return mFoodProductCategories;
    }



    public void updateFoodData() {
        FoodProductHelper foodProductHelper = new FoodProductHelper(this);

        for (FoodProductCategory foodProductCategory : getFoodProductCategories()) {

            for (FoodProduct product : foodProductCategory.getFoodProducts()) {
                foodProductHelper.updateRecord(product);
            }
        }
    }
}