package antarit.dietgen.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import antarit.dietgen.datarecords.DatabaseRecord;
import antarit.dietgen.datarecords.FoodProduct;
import antarit.dietgen.datarecords.FoodProductCategory;

public class FoodProductCategoryHelper extends DatabaseRecordHelper {

    public static final String TABLE_FOOD_CATEGORY = "Food_Product_Category";
    public static final String FOOD_CATEGORY_ID = "_id";
    public static final String FOOD_CATEGORY_NAME = "name";

    private Context mContext;

    public FoodProductCategoryHelper(Context context) {
        super(context);
        setContext(context);
        setTable(TABLE_FOOD_CATEGORY);
    }

    @Override
    public void updateRecord(ContentValues contentValues, DatabaseRecord record) {

    }

    @Override
    public ContentValues getContentValues(DatabaseRecord record) {
        return null;
    }

    @Override
    public DatabaseRecord getRecord(Cursor cursor) {
        FoodProductCategory foodProductCategory = new FoodProductCategory();
        foodProductCategory.setId(getInteger(cursor, FOOD_CATEGORY_ID));
        foodProductCategory.setName(getString(cursor, FOOD_CATEGORY_NAME));
        foodProductCategory.setChecked(Boolean.FALSE);
        return foodProductCategory;
    }

    @Override
    public String getSQLStatement(Integer value) {
        return "Select * from " + TABLE_FOOD_CATEGORY + " where main_meal_item = " + value + " order by random() limit 1";
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    public FoodProductCategory getFoodProductCategoryWithProducts(Integer categoryId) {
        ArrayList<FoodProduct> foodProducts = new ArrayList<>();

        FoodProductHelper productHelper = new FoodProductHelper(getContext());
        productHelper.setWhere("category_id = " + categoryId.toString());

        Cursor productsCursor = productHelper.getAllTableRecordsCursor();
        while (productsCursor.moveToNext()) {
            FoodProduct foodProduct = (FoodProduct) productHelper.getRecord(productsCursor);
            foodProducts.add(foodProduct);
        }

        FoodProductCategory category = (FoodProductCategory) getRecordById(categoryId);
        category.setFoodProducts(foodProducts);

        return category;
    }

}