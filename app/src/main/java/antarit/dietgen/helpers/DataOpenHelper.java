package antarit.dietgen.helpers;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DataOpenHelper extends SQLiteAssetHelper {

    public static final String DB_NAME = "data.db";
    public static final Integer VERSION = 1;

    private static DataOpenHelper instance;

    public static synchronized DataOpenHelper getInstance(Context context) {
        if (instance==null) {
            instance = new DataOpenHelper(context.getApplicationContext());
        }
        return instance;
    }

    private DataOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

}