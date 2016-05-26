package antarit.dietgen.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import antarit.dietgen.datarecords.DatabaseRecord;
import antarit.dietgen.datarecords.User;


public class UserHelper extends DatabaseRecordHelper {

    public static final String TABLE_USER = "User";
    public static final String USER_ID = "_id";
    public static final String USER_NAME = "name";
    public static final String USER_AGE = "age";
    public static final String USER_HEIGHT = "height";
    public static final String USER_WEIGHT = "weight";
    public static final String USER_MEALS_COUNT = "meals_count";
    public static final String USER_GENDER = "gender";
    public static final String USER_PHYSICAL_ACTIVITY_LEVEL = "physical_activity_level";

    public UserHelper(Context context) {
        super(context);
        setTable(TABLE_USER);
    }

    @Override
    public void updateRecord(ContentValues contentValues, DatabaseRecord record) {
        User user = getUser(record);
        database.update(getTable(), contentValues, "_id = ?", new String[]{user.getId().toString()});
    }

    @Override
    public ContentValues getContentValues(DatabaseRecord record) {
        
        ContentValues contentValues = new ContentValues();
        User user = getUser(record);

        contentValues.put(USER_ID, user.getId());
        contentValues.put(USER_NAME, user.getName());
        contentValues.put(USER_AGE, user.getAge());
        contentValues.put(USER_HEIGHT, user.getHeight());
        contentValues.put(USER_WEIGHT, user.getWeight());
        contentValues.put(USER_MEALS_COUNT, user.getMealsCount());

        Integer gender = getAsInt(user.getGender());
        contentValues.put(USER_GENDER, gender);

        Integer physicalActivityLevel = getAsInt(user.getPhysicalActivityLevel());
        contentValues.put(USER_PHYSICAL_ACTIVITY_LEVEL, physicalActivityLevel);

        return contentValues;
    }

    @Override
    public DatabaseRecord getRecord(Cursor cursor) {
        User user = new User();

        user.setId(getInteger(cursor, USER_ID));
        user.setName(getString(cursor, USER_NAME));
        user.setAge(getInteger(cursor, USER_AGE));
        user.setHeight(getInteger(cursor, USER_HEIGHT));
        user.setWeight(getInteger(cursor, USER_WEIGHT));
        user.setMealsCount(getInteger(cursor, USER_MEALS_COUNT));

        User.Gender gender = getGenderFromInt(getInteger(cursor, USER_GENDER));
        user.setGender(gender);

        User.PhysicalActivityLevel physicalActivityLevel = getPhysicalActivityLevelFromInt(getInteger(cursor, USER_PHYSICAL_ACTIVITY_LEVEL));
        user.setPhysicalActivityLevel(physicalActivityLevel);

        return user;
    }

    @Override
    public String getSQLStatement(Integer value) {
        return null;
    }

    private User getUser(DatabaseRecord record) {
        try{
            return (User) record;
        } catch (Exception e) {
            return new User();
        }
    }

    public User.Gender getGenderFromInt(Integer value) {
        switch (value) {
            case 1:
                return User.Gender.Male;
            case 2:
                return User.Gender.Female;
            default:
                return null;
        }
    }

    public User.PhysicalActivityLevel getPhysicalActivityLevelFromInt(Integer value) {
        switch (value) {
            case 1:
                return User.PhysicalActivityLevel.Sedentary;
            case 2:
                return User.PhysicalActivityLevel.Light;
            case 3:
                return User.PhysicalActivityLevel.Moderate;
            case 4:
                return User.PhysicalActivityLevel.Active;
            case 5:
                return User.PhysicalActivityLevel.Extreme;
            default:
                return null;
        }
    }

    public Integer getAsInt(User.Gender gender) {
        switch (gender) {
            case Male:
                return 1;
            case Female:
                return 2;
            default:
                return -1;
        }
    }

    public Integer getAsInt(User.PhysicalActivityLevel PhysicalActivityLevel) {
        switch (PhysicalActivityLevel) {
            case Sedentary:
                return 1;
            case Light:
                return 2;
            case Moderate:
                return 3;
            case Active:
                return 4;
            case Extreme:
                return 5;
            default:
                return -1;
        }
    }
}