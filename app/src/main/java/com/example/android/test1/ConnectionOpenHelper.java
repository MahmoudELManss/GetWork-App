package com.example.android.test1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConnectionOpenHelper extends SQLiteOpenHelper{


    public ConnectionOpenHelper(Context context) {
        super(context, Helper.DbNAME, null, Helper.DbVSERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("CREATE TABLE " + Helper.DbTABLEUSER + " ( " +
                Helper.DbUSEREMAIL + " TEXT PRIMARY KEY ," +
                Helper.DbUSERPASS + " TEXT ," +
                Helper.DbUSERNAME + " TEXT ," +
                Helper.DbUSERCITY + " TEXT ," +
                Helper.DbUSERPHONENUMBER + "  TEXT, " +
                Helper.DbUSERPHOTO + " TEXT " + ");"
        );

        db.execSQL("CREATE TABLE " + Helper.DbTABLEWORKER + " ( " +
                "id INTEGER  PRIMARY KEY AUTOINCREMENT," +
                Helper.DbWORKEREMAIL + " TEXT ," +
                Helper.DbWORKERNAME + " TEXT ," +
                Helper.DbWORKERJOB + " TEXT ," +
                Helper.DbWORKERCITY + "  TEXT, " +
                Helper.DbWORKERDESCRIPTION + " TEXT , " +
                Helper.DbWORKERPHONENUMBER + " TEXT , " +
                Helper.DbWORKERPHOTO1 + " TEXT , " +
                Helper.DbWORKERPHOTO2 + " TEXT  " +
                ");"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE " + Helper.DbTABLEUSER + ";");
        db.execSQL("DROP TABLE " + Helper.DbTABLEWORKER + ";");
        onCreate(db);
    }


    public long insertInRegister(String email, String pass, String name, String city,String phone) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Helper.DbUSEREMAIL, email);
        cv.put(Helper.DbUSERPASS, pass);
        cv.put(Helper.DbUSERNAME, name);
        cv.put(Helper.DbUSERCITY, city);
        cv.put(Helper.DbUSERPHONENUMBER,phone);
        cv.put(Helper.DbUSERPHOTO,"0");

        long id = db.insert(Helper.DbTABLEUSER, null, cv);

        return id;
    }


    public Cursor selectRowForGoogle() {

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(Helper.DbTABLEUSER, new String[]{Helper.DbUSEREMAIL}, null, null, null, null, null);
        return c;
    }

    public long insertInAddWorker( String name, String phone,String job, String city,String Description) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Helper.DbWORKEREMAIL, "default");
        cv.put(Helper.DbWORKERNAME, name);
        cv.put(Helper.DbWORKERCITY, city);
        cv.put(Helper.DbWORKERJOB,job);
        cv.put(Helper.DbWORKERDESCRIPTION, Description);
        cv.put(Helper.DbWORKERPHONENUMBER,phone);
        cv.put(Helper.DbWORKERPHOTO1,"0");
        cv.put(Helper.DbWORKERPHOTO2,"0");

        long id = db.insert(Helper.DbTABLEWORKER, null, cv);

        return id;
    }

    public Cursor selectUser(String email) {

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(Helper.DbTABLEUSER, null,Helper.DbUSEREMAIL+" =?" , new String[]{email}, null, null, null);
        return c;
    }

    public int updateProfile(String name , String phone, String city,String email) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Helper.DbUSERNAME, name);
        cv.put(Helper.DbUSERPHONENUMBER, phone);
        cv.put(Helper.DbUSERCITY,city);

        int c = db.update(Helper.DbTABLEUSER, cv, Helper.DbUSEREMAIL + " = ? ", new String[]{email});
        return c;
    }

    public Cursor search(String city ){

        SQLiteDatabase db=getReadableDatabase();
        Cursor c=db.query(Helper.DbTABLEWORKER,null, Helper.DbWORKERCITY + " =?",new String[]{city},null,null,null);
        return c;
    }



    public Cursor homeSearch(String city ){

        SQLiteDatabase db=getReadableDatabase();
        Cursor c=db.query(Helper.DbTABLEWORKER,null, Helper.DbWORKERCITY + " =?",new String[]{city},null,null,null);
        return c;
    }




}
