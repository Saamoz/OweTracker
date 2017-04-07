package com.saamoz.owetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Arrays;

public class OweDBManager extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "owes.db";
    private static final String TABLE_OWES = "owes";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_DESC = "desc";
    private static final String COLUMN_VALUE = "value";
    String TAG = "com.saamoz.owetracker";

    public OweDBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_OWES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DESC + " TEXT, " +
                COLUMN_VALUE + " INTEGER" + ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OWES);
        onCreate(db);
    }

    public void addOwe(Owe owe){
        ContentValues values = new ContentValues();

        String description = owe.get_desc();
        String value = String.valueOf(owe.get_value());

        values.put(COLUMN_DESC, description);
        values.put(COLUMN_VALUE, value);

        Log.i(TAG, "Attempting to add: " + description + " - " + value);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_OWES, null, values);
        db.close();
    }

    public void deleteOwe(String oweDesc){
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + TABLE_OWES + " WHERE " + COLUMN_DESC + "=\"" + oweDesc + "\";";
        db.execSQL(query);
    }

    public void removeOwes(String oweDesc, int value){
        int finalVal = getOweValue(oweDesc) - value;
        SQLiteDatabase db = getWritableDatabase();
        if (finalVal < 1){
           deleteOwe(oweDesc);
        }else{
            String query = "UPDATE " + TABLE_OWES + " SET " + COLUMN_VALUE + " = " + finalVal +
                    " WHERE " + COLUMN_DESC + " = " + oweDesc + ";";
            db.execSQL(query);
        }
    }

    public String[] DBtoArray(String column){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_OWES + " WHERE 1", null);
        String[] array = new String[c.getCount()];
        int i = 0;
        while(c.moveToNext()){
            array[i] = c.getString(c.getColumnIndex(column));
            i++;
        }
        Log.i(TAG, "String of db is " + databaseToString());
        Log.i(TAG, "Array Log is " + Arrays.toString(array));
        c.close();
        db.close();
        return array;
    }

    public int getOweValue(String oweDesc){
        SQLiteDatabase db = getWritableDatabase();
        String oweValueString = null;
        Cursor c = db.query(TABLE_OWES, new String[] {COLUMN_VALUE, COLUMN_DESC},
                COLUMN_DESC + " = " + oweDesc , null, null, null, null);
        int count = c.getCount();
        if(count == 1){
            c.moveToFirst();
            oweValueString = c.getString(c.getColumnIndex(COLUMN_VALUE));
        }
        c.close();
        db.close();
        return Integer.parseInt(oweValueString);
    }

    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_OWES + " WHERE 1";

        //Cursor points to a location in your results
        Cursor c = db.rawQuery(query, null);
        //Move to the first row in your results
        c.moveToFirst();

        //Position after the last row means the end of the results
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex(COLUMN_DESC)) != null) {
                dbString += c.getString(c.getColumnIndex(COLUMN_DESC));
            }
            c.moveToNext();
        }
        c.close();
        db.close();
        return dbString;
    }
}
