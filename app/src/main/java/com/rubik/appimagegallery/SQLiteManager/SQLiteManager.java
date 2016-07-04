package com.rubik.appimagegallery.SQLiteManager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
* Created by Rubik on 20/6/16.
*/
public class SQLiteManager {
private static final String TAG = SQLiteManager.class.getSimpleName();
private static SQLiteManager dbInstance;
private static SQLiteOpenHelper dbHelper;
private SQLiteDatabase db;

private Integer dbOpenCount = 0;


public static synchronized void initialize(SQLiteOpenHelper helper) {
    if (dbInstance == null) {
        dbInstance = new SQLiteManager();
        dbHelper = helper;
        //  dbHelper.getWritableDatabase(); // for calling onCreate methoh we call getWritableDatabase

    }
}

public static synchronized SQLiteManager getConexion() {
    if (dbInstance == null) {
        throw new IllegalStateException(TAG + " is not initialized, call initializeInstance(..) method first.");
    }
    return dbInstance;
}

public synchronized SQLiteDatabase connect(String mode) {
    dbOpenCount+=1;
    if(dbOpenCount == 1) {
        // Opening new database
        if (mode.equals("write")) {
            db = dbHelper.getWritableDatabase();
        } else if (mode.equals("read")) {
            db = dbHelper.getReadableDatabase();
        }
        Log.d(TAG," CONECTED DB in  " + mode +" mode" );
    }
    return db;
}

public synchronized void closeDB() {
    dbOpenCount-=1;
    if((dbOpenCount == 0) && (db != null && db.isOpen())) {
        // Closing database
        db.close();
        Log.d(TAG," DESCONECTED DB");
    }
}

public static boolean isDbEmmty (String id, String table) {

    boolean isEmpty = false;
    SQLiteDatabase db = getConexion().connect("read");
    String query = "SELECT " + id  + " FROM " + table + "";

    try {
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.getCount() == 0) {
            isEmpty = true; // Favourite is already exist
        }
        cursor.close();
    } catch (SQLiteException ex) {ex.printStackTrace();
    } finally {SQLiteManager.getConexion().closeDB();
    }
    return isEmpty;
}

}

