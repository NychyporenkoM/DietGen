package antarit.dietgen.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import antarit.dietgen.datarecords.CompatibilityRecord;
import antarit.dietgen.datarecords.DatabaseRecord;

public class FoodProductCompatibilityHelper extends DatabaseRecordHelper {

    public static final String TABLE_FOOD_PRODUCT_COMPATIBILITY = "Food_Product_Compatibility";
    public static final String FOOD_PRODUCT_COMPATIBILITY_ID = "_id";
    public static final String FOOD_PRODUCT_COMPATIBILITY_CATEGORY_ID = "category_id_2";

    public FoodProductCompatibilityHelper(Context context) {
        super(context);
        setTable(TABLE_FOOD_PRODUCT_COMPATIBILITY);
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
        CompatibilityRecord record = new CompatibilityRecord();

        record.setId(getInteger(cursor, FOOD_PRODUCT_COMPATIBILITY_ID));
        record.setCategoryId(getInteger(cursor, FOOD_PRODUCT_COMPATIBILITY_CATEGORY_ID));

        return record;
    }

    @Override
    public String getSQLStatement(Integer value) {
        return "Select * from " + TABLE_FOOD_PRODUCT_COMPATIBILITY + " where category_id_1 = " + value + " order by random() limit 1";
    }
}