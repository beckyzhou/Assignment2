package com.becky.assignment2;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Becky on 2015-04-01.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE;
    public static final String COLUMN_ID;
    public static final String COLUMN_FIRSTNAME;
    public static final String COLUMN_LASTNAME;
    public static final String COLUMN_STUDENTNUM;
    public static final String COLUMN_EMAIL;
    private static final String DATABASE_NAME;
    private static final int DATABASE_VERSION;
    private static final String DATABASE_CREATE;

    static
    {
        TABLE               = "student";
        COLUMN_ID           = "_id";
        COLUMN_FIRSTNAME    = "first_name";
        COLUMN_LASTNAME     = "last_name";
        COLUMN_EMAIL        = "email_address";
        COLUMN_STUDENTNUM   = "student_number";
        DATABASE_NAME       = ""; //////////////////////////////////////////////////
        DATABASE_VERSION    = 1;
        DATABASE_CREATE     = "create table " +
                TABLE + "(" + COLUMN_ID +
                " integer primary key autoincrement, " +
                COLUMN_FIRSTNAME + "text not null, " +
                COLUMN_LASTNAME + "text not null, " +
                COLUMN_EMAIL + "text not null, " +
                COLUMN_STUDENTNUM + " text not null);";

    }

    public SQLiteHelper(final Context context)
    {
        super(context,
                DATABASE_NAME,
                null,
                DATABASE_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase database)
    {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db,
                          final int            oldVersion,
                          final int            newVersion)
    {
        Log.w(SQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public void putInformation(SQLiteHelper dop, String first, String last, String email, String studentNum) {
        SQLiteDatabase SQ = dop.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FIRSTNAME, first);
        cv.put(COLUMN_LASTNAME, last);
        cv.put(COLUMN_EMAIL, email);
        cv.put(COLUMN_STUDENTNUM, studentNum);
        long k = SQ.insert(TABLE, null, cv);
        Log.d("DATABASE", "INSERTED");
    }

    public Cursor getInformation(SQLiteHelper dop) {
        SQLiteDatabase SQ = dop.getReadableDatabase();
        String[] columns = { COLUMN_FIRSTNAME, COLUMN_LASTNAME, COLUMN_EMAIL, COLUMN_STUDENTNUM };
        Cursor CR = SQ.query(TABLE, columns, null, null, null, null, null);
        return CR;

    }

    public void deleteAll(SQLiteHelper dop) {
        SQLiteDatabase SQ = dop.getWritableDatabase();
        SQ.execSQL("delete  from "+ TABLE);
    }

}
