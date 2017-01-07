package com.arpaul.avantari_qs2.dataaccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.arpaul.avantari_qs2.dataobjects.WordsDO;


/**
 * Created by ARPaul on 09-05-2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    /**
     * Database specific constant declarations
     */
    private SQLiteDatabase db;

    static final String CREATE_WORDS_DB_TABLE =
            " CREATE TABLE IF NOT EXISTS " + CPConstants.WORDS_TABLE_NAME +
                    " (" + CPConstants.TABLE_ID   + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    WordsDO.ALPHABET                + " VARCHAR  NOT NULL, " +
                    WordsDO.MEANING                 + " VARCHAR  " +
                    ");";


    DataBaseHelper(Context context){
        super(context, CPConstants.DATABASE_NAME, null, CPConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_WORDS_DB_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CPConstants.WORDS_TABLE_NAME);
        onCreate(db);
    }
}
