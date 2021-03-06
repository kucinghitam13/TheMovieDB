package com.example.android.themoviedb.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.themoviedb.Model.Movies;

import java.util.ArrayList;

/**
 * Created by kucinghitam13 on 8/26/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHandler";
    //database version
    private static final int DATABASE_VERSION = 1;

    //database name
    private static final String DATABASE_NAME = "moviesManager";

    //Movies table name
    private static final String TABLE_MOVIES = "movies";

    //movies table column names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_POSTER = "poster_path";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MOVIES_TABLE = "CREATE TABLE " + TABLE_MOVIES + " (" +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_TITLE + " TEXT," +
                KEY_POSTER + " TEXT)";

        Log.d(TAG, "onCreate: table movie create:" + CREATE_MOVIES_TABLE);
        db.execSQL(CREATE_MOVIES_TABLE);
    }


    //upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //hapus table lama jika ada
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        //buat kembali table
        onCreate(db);
    }

    //ALL CRUD METHOD

    public boolean isMovieExist(Movies m) {
        String query = "SELECT  * FROM " + TABLE_MOVIES + " WHERE " + KEY_ID + "=" + m.getId();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        boolean movieCount = cursor.getCount() != 0;
        cursor.close();
        return movieCount;
    }

    //tambahkan movie
    public void addMovie(Movies movies) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, movies.getId()); // put id movie
        values.put(KEY_TITLE, movies.getTitle()); // put title movie
        values.put(KEY_POSTER, movies.getPoster_path()); // put favorite

        //inserting row to table
        db.insert(TABLE_MOVIES, null, values);
        db.close();
    }

    //ambil data semua movie
    public ArrayList<Movies> getAllListMovies() {
        ArrayList<Movies> movieList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MOVIES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Movies movies = new Movies(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2));

                // Adding movie to list
                movieList.add(movies);
            } while (cursor.moveToNext());
        }
        // return movie list
        cursor.close();
        return movieList;
    }

    //delete movie
    public void deleteMovie(Movies movies) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MOVIES, KEY_ID + " = ?",
                new String[]{String.valueOf(movies.getId())});
        db.close();
    }


    /*public int getCountMovies() {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MOVIES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        return cursor.getCount();
    }*/

    //truncate movie
    /*public void clearMovie() {
        SQLiteDatabase db = this.getWritableDatabase();

        //clear table
        db.execSQL("DELETE FROM " + TABLE_MOVIES);
        db.close();
    }*/

    //ambil data 1 movie
    /*public Movies getMovie(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MOVIES, new String[]{KEY_ID,
                        KEY_TITLE, KEY_POSTER}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Movies movies = new Movies(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));

        return movies;
    }*/

    //update movie
    /*public int updateMovie(Movies movies) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, movies.getOriginal_title()); // put title movie
        values.put(KEY_POSTER, movies.getPoster_path()); // put favorite

        // updating row
        return db.update(TABLE_MOVIES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(movies.getId())});
    }*/
}
