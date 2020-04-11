package com.example.assignment5_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLDatabase extends SQLiteOpenHelper {

    public static final String COL_2 = "ITEM";
    public static final String COL_3 = "DATE";
    public static final String COL_4 = "PRICE";

    public static final String DATABASE_NAME = "database3.db";

    public static final String TABLE_NAME = "Spending";

    public SQLDatabase(Context context){
        super(context, DATABASE_NAME, null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        database.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, ITEM varchar(250), DATE varchar(250), PRICE DOUBLE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int NewVersion){
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }

    public Cursor retrieveAll(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return result;
    }

    public boolean addRow(rowData model){
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, model.mItem);
        contentValues.put(COL_3, model.mDate);
        contentValues.put(COL_4, model.mPrice);
        long result = database.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public static class rowData {
        public String mDate, mItem;
        public double mPrice;
    }
}