package com.example.simpleapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DatabaseHelper extends SQLiteOpenHelper {

    String DB_PATH = null;
    private static String DB_NAME = "mydatabase";
    public static SQLiteDatabase myDataBase;
    private final Context myContext;
    public static final String TABLE_TODO="TODOS";
    public static final String ID = "_ID";
    public static final String DATE = "DATE";
    public static final String TODO = "TODO";

    public static final String TABLE_INCOME = "INCOMES";
    public static final String ID_2 = "_ID";
    public static final String INCOME = "INCOME";

    public static final String TABLE_EXPENCE = "EXPENCES";
    public static final String ID_3 = "_ID";
    public static final String EXPENCE = "EXPENCE";


    public DatabaseHelper(Context context, int version) {
        super(context, DB_NAME, null,3);
        this.myContext = context;
        this.DB_PATH = "/data/data/com.example.simpleapp/databases/";
        Log.e("Path 1", DB_PATH);
    }


    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
            Log.e("Path 1","exist");
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {
        Log.e("Path 1","coping database");
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        Log.e("Path 1","opened");

    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();

            }
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        Log.e("Path 1","getting data");
        return myDataBase.query(table, null, null, null, null, null, null);
    }
    public boolean insertData(String todo,String d,String m,String y) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TODO, todo);
        contentValues.put("DAY",d);
        contentValues.put("MONTH",m);
        contentValues.put("YEAR",y);
        long result = db.insert(TABLE_TODO, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;

    }

    public boolean insertIncome(String income) {
        SQLiteDatabase myDataBase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(INCOME, income);
        long result = myDataBase.insert(TABLE_INCOME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertintIncome(int income) {
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String[] st=currentDate.split("-");
        String d=st[0];
        String m=st[1];
        String y=st[2];
        SQLiteDatabase myDataBase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(INCOME, income);
        contentValues.put("DAY",d);
        contentValues.put("MONTH",m);
        contentValues.put("YEAR",y);
        long result = myDataBase.insert(TABLE_INCOME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertExpence(int expence) {
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
       String[] st=currentDate.split("-");
       String d=st[0];
       String m=st[1];
       String y=st[2];
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EXPENCE, expence);
        contentValues.put("DAY",d);
        contentValues.put("MONTH",m);
        contentValues.put("YEAR",y);
        long result = db.insert(TABLE_EXPENCE, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertintExpence(int expence) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EXPENCE, expence);
        long result = db.insert(TABLE_EXPENCE, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_TODO,null);
        return c;
    }

    public Cursor getIncome() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from INCOMES", null);
        return res;
    }
    public Cursor getidIncome(int id1) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_INCOME+"where id = "+id1+";", null);
        return res;
    }

    public Cursor getExpence() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_EXPENCE, null);

        return res;
    }
    public Cursor getYears(String table)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(" select distinct year from "+table, null);
        return res;
    }
    public Cursor getYearIncome(int year)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_INCOME+" WHERE YEAR="+year, null);
        return res;
    }
    public Cursor getYearExpence(int year)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_EXPENCE+" WHERE YEAR="+year, null);
        return res;
    }
    public Cursor getmonthlyIncome(String dber,String clm )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select year,month,sum("+clm+") from "+dber+" group by year,month;", null);
        return res;
    }
    public Boolean DeleteReminder(long ids)
    {
        SQLiteDatabase db = this.getWritableDatabase();

       int result=db.delete(TABLE_TODO, "_id" + "=?", new String[]{""+ids});
        if (result == -1)
            return false;
        else
            return true;
        //delete  from incomes where _id=1;
    }
    // select year,month,sum(income) from incomes group by year,month;
}
