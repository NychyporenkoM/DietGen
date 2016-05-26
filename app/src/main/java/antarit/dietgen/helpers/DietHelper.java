package antarit.dietgen.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import antarit.dietgen.datarecords.DatabaseRecord;
import antarit.dietgen.datarecords.Diet;
import antarit.dietgen.datarecords.DietType;

public class DietHelper extends DietTypeHelper {

    public static final String TABLE_DIET = "Diet";
    public static final String DIET_ID = "_id";
    public static final String DIET_USER_ID = "user_id";
    public static final String DIET_PURPOSE = "purpose";

    public DietHelper(Context context) {
        super(context);
        setTable(TABLE_DIET);
    }

    @Override
    public void updateRecord(ContentValues contentValues, DatabaseRecord record) {

    }

    @Override
    public ContentValues getContentValues(DatabaseRecord record) {

        ContentValues contentValues = new ContentValues();
        Diet diet = getDiet(record);

        contentValues.put(DIET_ID, diet.getId());
        contentValues.put(DIET_USER_ID, diet.getUserId());
        Integer purpose = getAsInt(diet.getPurpose());
        contentValues.put(DIET_PURPOSE, purpose);

        return contentValues;
    }

    @Override
    public DatabaseRecord getRecord(Cursor cursor) {
        Diet diet = new Diet();

        diet.setId(getInteger(cursor, DIET_ID));
        diet.setUserId(getInteger(cursor, DIET_USER_ID));

        Diet.Purpose purpose = getPurposeFromInt(getInteger(cursor, DIET_PURPOSE));
        diet.setPurpose(purpose);

        RationHelper rationHelper = new RationHelper(getContext());
        diet.setRecords(rationHelper.getCurrentDietTypeRecords(diet.getId()));

        return diet;
    }

    @Override
    public String getSQLStatement(Integer value) {
        return null;
    }

    @Override
    public void insertRecord(ContentValues contentValues, DietType dietType) {
        Long id = database.insert(getTable(), null, contentValues);

        dietType.setId(id.intValue());
        dietType.saveRecords(new RationHelper(getContext()));
    }

    @Override
    public void deleteChildRecords(DietType dietType) {
        RationHelper rationHelper = new RationHelper(getContext());
        for (DatabaseRecord record : dietType.getRecords()) {
            rationHelper.deleteRecord(record);
        }
    }

    public Diet getDiet(DatabaseRecord record) {
        return (Diet) record;
    }

    public Integer getAsInt(Diet.Purpose purpose) {
        switch (purpose) {
            case GainMuscle:
                return 1;
            case BalancedNutrition:
                return 2;
            case FatLoss:
                return 3;
            default:
                return -1;
        }
    }

    public Diet.Purpose getPurposeFromInt(Integer value) {
        switch (value) {
            case 1:
                return Diet.Purpose.GainMuscle;
            case 2:
                return Diet.Purpose.BalancedNutrition;
            case 3:
                return Diet.Purpose.FatLoss;
            default:
                return null;
        }
    }
}