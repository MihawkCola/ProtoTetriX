package de.prog3.tatrixproto.game.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "Highscore.db";
    private static String TABLE_NAME = "Highscore_t";
    private static String COL_1 = "Highscore";


    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + COL_1 + "TEXT PRIMARY KEY" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String highscore) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, highscore);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public String getHighScore(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select MAX(Highscore) " +TABLE_NAME,null);
        String highscore = result.getString(0);
        return highscore;
    }



}



