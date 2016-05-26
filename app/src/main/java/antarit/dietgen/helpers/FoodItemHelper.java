package antarit.dietgen.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import antarit.dietgen.datarecords.DatabaseRecord;
import antarit.dietgen.datarecords.DietType;
import antarit.dietgen.datarecords.FoodItem;
import antarit.dietgen.datarecords.FoodProduct;

public class FoodItemHelper extends DietTypeHelper {

    public static final String TABLE_FOOD_ITEM = "Food_Product_Item";
    public static final String FOOD_ITEM_ID = "_id";
    public static final String FOOD_ITEM_MEAL_ID = "meal_id";
    public static final String FOOD_ITEM_PRODUCT_ID = "food_product_id";
    public static final String FOOD_ITEM_WEIGHT = "weight";

    FoodItemHelper (Context context) {
        super(context);
        setTable(TABLE_FOOD_ITEM);
    }

    @Override
    public void updateRecord(ContentValues contentValues, DatabaseRecord record) {

    }

    @Override
    public ContentValues getContentValues(DatabaseRecord record) {
        ContentValues contentValues = new ContentValues();
        FoodItem foodItem = getFoodItem(record);

        contentValues.put(FOOD_ITEM_ID, foodItem.getId());
        contentValues.put(FOOD_ITEM_MEAL_ID, foodItem.getParent().getId());
        contentValues.put(FOOD_ITEM_PRODUCT_ID, foodItem.getProduct().getId());
        contentValues.put(FOOD_ITEM_WEIGHT, foodItem.getWeight());

        return contentValues;
    }

    @Override
    public DatabaseRecord getRecord(Cursor cursor) {
        FoodProductHelper foodProductHelper = new FoodProductHelper(getContext());
        Integer productId = getInteger(cursor, FOOD_ITEM_PRODUCT_ID);

        FoodItem foodItem = new FoodItem();
        foodItem.setId(getInteger(cursor, FOOD_ITEM_ID));
        foodItem.setProduct((FoodProduct) foodProductHelper.getRecordById(productId));
        foodItem.setWeight(getInteger(cursor, FOOD_ITEM_WEIGHT));

        return foodItem;
    }

    @Override
    public String getSQLStatement(Integer value) {
        return "Select * from " + TABLE_FOOD_ITEM + " where " + FOOD_ITEM_MEAL_ID + "= " + value;
    }

    @Override
    public void insertRecord(ContentValues contentValues, DietType dietType) {
        insertRecord(contentValues);
    }

    @Override
    public void deleteChildRecords(DietType dietType) {

    }

    public FoodItem getFoodItem(DatabaseRecord record) {
        return (FoodItem) record;
    }
}