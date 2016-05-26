package antarit.dietgen.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import antarit.dietgen.datarecords.DatabaseRecord;
import antarit.dietgen.datarecords.DietType;
import antarit.dietgen.datarecords.Meal;

public class MealHelper extends DietTypeHelper {

    public static final String TABLE_MEAL = "Meal";
    public static final String MEAL_ID = "_id";
    public static final String MEAL_RATION_ID = "ration_id";

    public MealHelper(Context context) {
        super(context);
        setTable(TABLE_MEAL);
    }

    @Override
    public void updateRecord(ContentValues contentValues, DatabaseRecord record) {

    }

    @Override
    public ContentValues getContentValues(DatabaseRecord record) {
        ContentValues contentValues = new ContentValues();

        Meal meal = getMeal(record);

        contentValues.put(MEAL_ID, meal.getId());
        contentValues.put(MEAL_RATION_ID, meal.getParent().getId());

        return contentValues;
    }

    @Override
    public DatabaseRecord getRecord(Cursor cursor) {
        Meal meal = new Meal();

        meal.setId(getInteger(cursor, MEAL_ID));

        FoodItemHelper foodItemHelper = new FoodItemHelper(getContext());
        meal.setRecords(foodItemHelper.getCurrentDietTypeRecords(meal.getId()));

        return meal;
    }

    @Override
    public String getSQLStatement(Integer value) {
        return "Select * from " + TABLE_MEAL + " where " + MEAL_RATION_ID + "= " + value;
    }

    @Override
    public void insertRecord(ContentValues contentValues, DietType dietType) {
        Long id = database.insert(getTable(), null, contentValues);

        dietType.setId(id.intValue());
        dietType.saveRecords(new FoodItemHelper(getContext()));
    }

    @Override
    public void deleteChildRecords(DietType dietType) {
        FoodItemHelper foodItemHelper = new FoodItemHelper(getContext());

        for (DatabaseRecord record : dietType.getRecords()) {
            foodItemHelper.deleteRecord(record);
        }
    }

    public Meal getMeal(DatabaseRecord record) {
        return (Meal) record;
    }
}
