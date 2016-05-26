package antarit.dietgen.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import antarit.dietgen.datarecords.DatabaseRecord;
import antarit.dietgen.datarecords.DietType;
import antarit.dietgen.datarecords.Ration;

public class RationHelper extends DietTypeHelper {

    public static final String TABLE_RATION = "Ration";
    public static final String RATION_ID = "_id";
    public static final String RATION_DIET_ID = "diet_id";

    public RationHelper(Context context) {
        super(context);
        setTable(TABLE_RATION);
    }

    @Override
    public void updateRecord(ContentValues contentValues, DatabaseRecord record) {

    }

    @Override
    public ContentValues getContentValues(DatabaseRecord record) {
        ContentValues contentValues = new ContentValues();

        Ration ration = getRation(record);

        contentValues.put(RATION_ID, ration.getId());
        contentValues.put(RATION_DIET_ID, ration.getParent().getId());

        return contentValues;
    }

    @Override
    public DatabaseRecord getRecord(Cursor cursor) {
        Ration ration = new Ration();

        ration.setId(getInteger(cursor, RATION_ID));

        MealHelper mealHelper = new MealHelper(getContext());
        ration.setRecords(mealHelper.getCurrentDietTypeRecords(ration.getId()));

        return ration;
    }

    @Override
    public String getSQLStatement(Integer value) {
        return "Select * from " + TABLE_RATION + " where " + RATION_DIET_ID + "= " + value;
    }

    @Override
    public void insertRecord(ContentValues contentValues, DietType dietType) {
        Long id = database.insert(getTable(), null, contentValues);
        dietType.setId(id.intValue());
        dietType.saveRecords(new MealHelper(getContext()));
    }

    @Override
    public void deleteChildRecords(DietType dietType) {
        MealHelper mealHelper = new MealHelper(getContext());

        for (DatabaseRecord record : dietType.getRecords()) {
            mealHelper.deleteRecord(record);
        }
    }

    public Ration getRation(DatabaseRecord record) {
        return (Ration) record;
    }

}