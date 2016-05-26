package antarit.dietgen.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import antarit.dietgen.datarecords.DatabaseRecord;
import antarit.dietgen.datarecords.DietType;

public abstract class DietTypeHelper extends DatabaseRecordHelper {

    private Context mContext;

    DietTypeHelper(Context context) {
        super(context);
        setContext(context);
    }

    @Override
    public void saveRecord(DatabaseRecord record) {
        DietType dietType = (DietType) record;
        ContentValues contentValues = getContentValues(record);
        insertRecord(contentValues, dietType);
    }

    @Override
    public void deleteRecord(DatabaseRecord record) {
        DietType dietType = (DietType) record;
        deleteChildRecords(dietType);
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    public abstract void insertRecord(ContentValues contentValues, DietType dietType);

    public abstract void deleteChildRecords(DietType dietType);

    public ArrayList<DatabaseRecord> getCurrentDietTypeRecords(Integer dietTypeId) {
        ArrayList<DatabaseRecord> records = new ArrayList<>();

        Cursor recordsCursor = database.rawQuery(getSQLStatement(dietTypeId), null);
        while (recordsCursor.moveToNext()) {
            DietType record = (DietType) getRecord(recordsCursor);
            records.add(record);
        }

        return records;
    }
}