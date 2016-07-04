package com.rubik.appimagegallery;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.rubik.appimagegallery.SQLiteManager.SQLiteManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rubik on 22/6/16.
 */
public class ImagesSQL {
    private static final String TAG = ImagesSQL.class.getSimpleName();
    private Images image;

    //Columns Table
    private static final String ID = "idImg";
    private static final String NAME = "nameIMG";
    private static final String URL = "urlIMG";

    //Table
    public static final String IMG_TABLE = "IMAGES";
    private static final String CREATE_TABLE =
            "CREATE TABLE " + IMG_TABLE + " (\n" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,\n" +
                    NAME + " TEXT UNIQUE NOT NULL ,\n" +
                    URL + " TEXT  \n" +
                    ")";



    public ImagesSQL () {image = new Images();}

    public static String createTableImages () {
        return CREATE_TABLE;
    }


                     /*
                        -------------------------------------------
                                         CRUD
                        -------------------------------------------
                     */

    public void addImage ( Images images) {
        SQLiteDatabase db = SQLiteManager.getConexion().connect("write");

        ContentValues values = new ContentValues();
        values.put(NAME, images.getName());
        values.put(URL, images.getUrl());

        //Insert
        db.insert(IMG_TABLE,null,values);
        SQLiteManager.getConexion().closeDB();

        Log.d(TAG, "Insert a Image whit name = " + images.getName() + " into sqlite");
    }

    public int updateImage ( Images images) {
        SQLiteDatabase db = SQLiteManager.getConexion().connect("write");

        ContentValues values = new ContentValues();
        values.put(NAME, images.getName());
        values.put(URL, images.getUrl());

                    /* update */
        return db.update(
                IMG_TABLE ,
                values ,
                ID + " = ?",
                new String[] { String.valueOf(images.getIdImage()) }
        );

    }

    public void deleteImage ( int id) {
        SQLiteDatabase db = SQLiteManager.getConexion().connect("write");

        db.delete(
                IMG_TABLE ,
                ID + " = ?",
                new String[] {String.valueOf(id)}
        );
        SQLiteManager.getConexion().closeDB();
        Log.d(TAG, "Deleted Image from sqlite");
    }

    public void deleteAllImage () {
        SQLiteDatabase db = SQLiteManager.getConexion().connect("write");
        db.delete(IMG_TABLE,null,null);
        SQLiteManager.getConexion().closeDB();
        Log.d(TAG, "Deleted all Image info from sqlite");
    }

    public Images getImage (int id) {
        SQLiteDatabase db = SQLiteManager.getConexion().connect("read");

        try {
            Cursor cursor = db.query(
                    IMG_TABLE,
                    new String[]{ID, NAME, URL},
                    ID + "=?",
                    new String[] { String.valueOf(id)}, null, null, null, null
            );

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();

                image = new Images();
                image.setIdImage(cursor.getShort(0));
                image.setName(cursor.getString(1));
                image.setUrl(cursor.getString(3));
                cursor.close();
            }
        } catch (SQLiteException ex) {
            ex.printStackTrace();
        } finally {
            SQLiteManager.getConexion().closeDB();
        }

        return image;
    }

    public List<Images> getAllImage () {
        String query = "SELECT " + ID + "," + NAME + "," + URL + " FROM IMAGES ";
        List<Images> listImages = new ArrayList<Images>();

        SQLiteDatabase db = SQLiteManager.getConexion().connect("read");
        try {
            Cursor cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        image = new Images();
                        image.setIdImage(Integer.parseInt(cursor.getString(0)));
                        image.setName(cursor.getString(1));
                        image.setUrl(cursor.getString(2));
                            //add to list
                        listImages.add(image);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        } catch (SQLiteException ex) {
            ex.printStackTrace();
        } finally {
            SQLiteManager.getConexion().closeDB();
        }

        return listImages;
    }

}

