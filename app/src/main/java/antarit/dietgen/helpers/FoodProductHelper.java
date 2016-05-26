package antarit.dietgen.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import antarit.dietgen.datarecords.DatabaseRecord;
import antarit.dietgen.datarecords.FoodProduct;

public class FoodProductHelper extends DatabaseRecordHelper {

    public static final String TABLE_PRODUCT = "Food_Product";
    public static final String PRODUCT_ID = "_id";
    public static final String PRODUCT_CATEGORY_ID = "category_id";
    public static final String PRODUCT_NAME = "name";
    public static final String PRODUCT_PROTEINS = "proteins";
    public static final String PRODUCT_CARBOHYDRATES = "carbohydrates";
    public static final String PRODUCT_FATS = "fats";
    public static final String PRODUCT_IS_JOINED = "is_joined";

    public FoodProductHelper(Context context) {
        super(context);
        setTable(TABLE_PRODUCT);
    }

    @Override
    public void updateRecord(ContentValues contentValues, DatabaseRecord record) {

    }

    @Override
    public ContentValues getContentValues(DatabaseRecord record) {
        ContentValues contentValues = new ContentValues();

        FoodProduct product = (FoodProduct) record;

        contentValues.put(PRODUCT_ID, product.getId());
        Integer isJoined = getAsInt(product.isSelected());
        contentValues.put(PRODUCT_IS_JOINED, isJoined);

        return contentValues;
    }

    @Override
    public DatabaseRecord getRecord(Cursor cursor) {
        FoodProduct food = new FoodProduct();

        if (cursor.getCount() != 0) {
            food.setId(getInteger(cursor, PRODUCT_ID));
            food.setCategoryId(getInteger(cursor, PRODUCT_CATEGORY_ID));
            food.setName(getString(cursor, PRODUCT_NAME));
            food.setFats(getFloat(cursor, PRODUCT_FATS));
            food.setProteins(getFloat(cursor, PRODUCT_PROTEINS));
            food.setCarbohydrates(getFloat(cursor, PRODUCT_CARBOHYDRATES));
            food.setSelected(getBoolean(cursor, PRODUCT_IS_JOINED));
            return food;
        } else
            return null;
    }

    @Override
    public String getSQLStatement(Integer value) {
        return "Select * from " + TABLE_PRODUCT + " where category_id = " + value + " and is_joined = 1 order by random() limit 1";
    }

    public void updateRecord(DatabaseRecord record) {
        database.update(getTable(), getContentValues(record), "_id = ?", new String[]{record.getId().toString()});
    }

    public Integer getAsInt(Boolean isJoined) {
        return isJoined ? 1 : 0;
    }
}