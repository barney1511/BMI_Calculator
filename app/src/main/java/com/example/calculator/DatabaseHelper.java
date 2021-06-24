package com.example.calculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String BMI_TABLE = "BMI_TABLE";
    public static final String COLUMN_BMI_RESULT = "BMI_RESULT";
    public static final String COLUMN_WEIGHT = "WEIGHT";
    public static final String COLUMN_HEIGHT = "HEIGHT";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "calculator.db", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSt = "CREATE TABLE " + BMI_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_BMI_RESULT + " INT, " + COLUMN_WEIGHT + " INT, " + COLUMN_HEIGHT + " INT)";

        db.execSQL(createTableSt);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + BMI_TABLE);
        onCreate(db);
    }

    public boolean addOne(ModelBMI modelBMI) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_BMI_RESULT, modelBMI.getResult());
        cv.put(COLUMN_WEIGHT, modelBMI.getWeight());
        cv.put(COLUMN_HEIGHT, modelBMI.getHeight());

        long insert = db.insert(BMI_TABLE, null, cv);
        if (insert == 1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteOne(ModelBMI modelBMI) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + BMI_TABLE + " WHERE " + COLUMN_HEIGHT + "=" + modelBMI.getHeight() + " AND " + COLUMN_WEIGHT + "=" + modelBMI.getWeight();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    public List<ModelBMI> getAll(){
        List<ModelBMI> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + BMI_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            do {

                int bmiResult = cursor.getInt(1);
                int weight = cursor.getInt(2);
                int height = cursor.getInt(3);

                ModelBMI newBMI = new ModelBMI(bmiResult, weight, height);
                returnList.add(newBMI);

            } while (cursor.moveToNext());

        }

        Collections.reverse(returnList);

        cursor.close();
        db.close();

        return returnList;
    }
}
