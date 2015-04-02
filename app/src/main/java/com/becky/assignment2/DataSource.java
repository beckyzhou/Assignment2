package com.becky.assignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Becky on 2015-04-01.
 */
public class DataSource {

    // Database fields
    private final SQLiteHelper dbHelper;
    private final String[] allColumns;
    private SQLiteDatabase database;

    {
        allColumns = new String[]
                {
                        SQLiteHelper.COLUMN_ID,
                        SQLiteHelper.COLUMN_FIRSTNAME,
                        SQLiteHelper.COLUMN_LASTNAME,
                };
    }

    public DataSource(Context context)
    {
        dbHelper = new SQLiteHelper(context);
    }

    public void open()
            throws SQLException
    {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Student createStudent(String first, String last)
    {
        final ContentValues values;
        final long          insertId;
        final Cursor        cursor;
        final Student       newStudent;

        values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_FIRSTNAME, first);
        values.put(SQLiteHelper.COLUMN_LASTNAME, last);
        insertId = database.insert(SQLiteHelper.TABLE,
                null,
                values);
        cursor = database.query(SQLiteHelper.TABLE,
                allColumns,
                SQLiteHelper.COLUMN_ID + " = " + insertId,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        newStudent = cursorToStudent(cursor);
        cursor.close();

        return (newStudent);
    }

    public void deleteStudent(final Student student)
    {
        final long id;

        id = student.getId();
        System.out.println("Student deleted with id: " + id);
        database.delete(SQLiteHelper.TABLE,
                SQLiteHelper.COLUMN_ID + " = " + id,
                null);
    }

    public List<Student> getAllStudents()
    {
        final List<Student> comments;
        final Cursor        cursor;

        comments = new ArrayList<Student>();
        cursor   = database.query(SQLiteHelper.TABLE,
                allColumns,
                null,
                null,
                null,
                null,
                null);

        try
        {
            cursor.moveToFirst();

            while(!(cursor.isAfterLast()))
            {
                final Student student;

                student = cursorToStudent(cursor);
                comments.add(student);
                cursor.moveToNext();
            }

        }
        finally
        {
            // make sure to close the cursor
            cursor.close();
        }

        return (comments);
    }

    private Student cursorToComment(final Cursor cursor)
    {
        final Student student;

        student = new Student();
        student.setId(cursor.getLong(0));
        student.setComment(cursor.getString(1));

        return (student);
    }

}
