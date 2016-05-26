package antarit.dietgen.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import antarit.dietgen.datarecords.DatabaseRecord;


public abstract class DatabaseRecordHelper {

//Select * from Food_Product where category_id = (select category_id_2 from Food_Product_Compatibility where category_id_1 = 10 order by random() limit 1) order by random() limit 1;
    private String mTable;
    private String mWhere;
    public SQLiteDatabase database;

    DatabaseRecordHelper (Context context) {
        DataOpenHelper dbHelper = DataOpenHelper.getInstance(context);
        database = dbHelper.getWritableDatabase();
        setWhere(null);
    }

    public void setTable(String table) {
        this.mTable = table;
    }

    public void setWhere(String where) {
        this.mWhere = where;
    }

    public String getTable() {
        return mTable;
    }

    public String getWhere() {
        return mWhere;
    }

    public abstract void updateRecord(ContentValues contentValues, DatabaseRecord record);

    public abstract ContentValues getContentValues(DatabaseRecord record);

    public abstract DatabaseRecord getRecord(Cursor cursor);

    public abstract String getSQLStatement(Integer value);

    //Read records from db
    public Cursor getAllTableRecordsCursor() {
        return database.query(getTable(), null, getWhere(), null, null, null, null);
    }

    private Cursor getRecordCursorById(Integer id) {
        String sql = "Select * from " + getTable() + " where _id = " + id + ";";
        return getCursorWithCurrentRowId(database.rawQuery(sql, null));
    }

    public DatabaseRecord getRecordById(Integer id) {
        return getRecord(getRecordCursorById(id));
    }

    //Save records in db
    public void saveRecord(DatabaseRecord record) {
        ContentValues contentValues = getContentValues(record);
        boolean update = isExist(record);

        if(update)
            updateRecord(contentValues, record);
        else
            insertRecord(contentValues);
    }

    public void deleteRecord(DatabaseRecord record) {
        database.delete(getTable(), "_id = " + record.getId().toString(), null);
    }

    public void insertRecord(ContentValues contentValues) {
        database.insert(getTable(), null, contentValues);
    }

    public boolean isExist(DatabaseRecord record) {
        return record.getId()!= null;
    }

    //Convert Cursor to DatabaseRecord

    public Integer getColumnIndex(Cursor cursor, String key) {
        return cursor.getColumnIndex(key);
    }

    public Integer getInteger(Cursor cursor, String key) {
        return cursor.getInt(getColumnIndex(cursor, key));
    }

    public Float getFloat(Cursor cursor, String key) {
        return cursor.getFloat(getColumnIndex(cursor, key));
    }

    public String getString(Cursor cursor, String key) {
        return cursor.getString(getColumnIndex(cursor, key));
    }

    public Boolean getBoolean(Cursor cursor, String key) {
        Integer boolValue = cursor.getInt(getColumnIndex(cursor, key));

        if( boolValue == 1)
            return Boolean.TRUE;
        else
            return Boolean.FALSE;
    }

    //Read random record
    public DatabaseRecord getRandomRecord(Integer value) {
        return getRecord(getRandomRecordCursor(getSQLStatement(value)));
    }

    private Cursor getRandomRecordCursor(String sql) {
        return getCursorWithCurrentRowId(database.rawQuery(sql, null));
    }

    private Cursor getCursorWithCurrentRowId(Cursor cursor) {
        cursor.moveToFirst();
        return cursor;
    }
}