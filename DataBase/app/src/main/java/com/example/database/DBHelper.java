package com.example.database;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import  android.content.ContentValues;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public  class DBHelper  extends SQLiteOpenHelper {



    // Constructor
    public DBHelper(Context context) {
        super(context, DBName, null, version);
    }


    public static final  String DBName = "College";

    public static final int version = 1;


    public static final  String tableName = "Students";
    public static final  String name_col = "StudentName";
    public static final  String roll_col = "RollNumber";
    public static final  String marks_col = "TotalMarks";
    public static final  String result_col = "Result";

    @Override
    public void  onCreate(SQLiteDatabase db){

        String query = "CREATE TABLE " + tableName + "( " + roll_col
                + " TEXT PRIMARY KEY , " + name_col +
                " TEXT , " + marks_col + " INTEGER , " + result_col +
                " TEXT ) "  ;

        db.execSQL(query);

    }

    public void addNewStudent( String name , String roll , int marks , String result ){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(name_col , name);
        values.put(roll_col , roll);
        values.put(marks_col , marks);
        values.put(result_col , result);

        db.insert(tableName ,null ,  values);

        db.close();
    }

    public int updateStudentByRole(String role , String name , int mark , String result){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(roll_col , role);
        values.put(name_col , role);
        values.put(marks_col , mark);
        values.put(result_col , role);

        return db.update( tableName , values , roll_col + " = ?" ,  new String[]{role});

    }

    @SuppressLint("Range")
    public Map<String,String>  getStudentByRole(String role){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery( "SELECT * FROM " + tableName + " WHERE " + roll_col + " = ?" ,new String[]{role});

        Map<String,String> result = new HashMap<>();

        if(cursor.moveToFirst()) {

            result.put("name" , cursor.getString( cursor.getColumnIndex(name_col) ) );
            result.put("role" , cursor.getString( cursor.getColumnIndex(roll_col) ) );
            result.put("mark" , String.valueOf(cursor.getInt( cursor.getColumnIndex(marks_col) )));
            result.put("result" , cursor.getString( cursor.getColumnIndex(result_col) ) );
        }

        Log.d("DBHelper" , "Student detail " +result.toString());
        return  result;


    }



    public int deleteStudentByRole(String role){

        SQLiteDatabase db = this.getWritableDatabase();

        return  db.delete(tableName , roll_col + " = ?" , new String[]{role});

    }
    @Override
    public  void  onUpgrade(SQLiteDatabase db ,int older_ver , int new_ver){

        db.execSQL( "DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
    }





}
