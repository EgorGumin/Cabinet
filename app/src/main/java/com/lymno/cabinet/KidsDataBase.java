package com.lymno.cabinet;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class KidsDataBase {
    static final String NAME = "LymnoKidsDataBase";
    static final String FIRST_NAME = "first_name";
    static final String SECOND_NAME = "second_name";
    static final String MIDDLE_NAME = "middle_name";
    static final String SCHOOL = "school";
    static final String CLASS = "class";
    static final String DATE_BIRTH = "date_birth";
    static final String PARENT_ID = "parent_id";
    static final String BORN_CERTIFIKATE = "born_certificate";

    private Context context;
    private DBHelper dbHelper;
    private ContentValues cv;
    private SQLiteDatabase db;
    private Cursor cursor;
    private String[] columns;

    public KidsDataBase(Context context)
    {
        this.context = context;
    }

    public ArrayList<Kid> getKids()
    {
        dbHelper = new DBHelper(context, NAME);
        ArrayList<Kid> kids = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        columns = new String[]{FIRST_NAME, SECOND_NAME, MIDDLE_NAME, SCHOOL, CLASS, DATE_BIRTH, PARENT_ID, BORN_CERTIFIKATE};
        cursor = db.query(NAME, columns, null, null, null, null, null);

        if (cursor.moveToFirst())
        {
            while (!cursor.isAfterLast()) {
                String firstName = cursor.getString(cursor.getColumnIndex(FIRST_NAME));
                String secondName = cursor.getString(cursor.getColumnIndex(SECOND_NAME));
                String middleName = cursor.getString(cursor.getColumnIndex(MIDDLE_NAME));
                String school = cursor.getString(cursor.getColumnIndex(SCHOOL));
                String classOfSchool = cursor.getString(cursor.getColumnIndex(CLASS));
                String dateOfBirth = cursor.getString(cursor.getColumnIndex(DATE_BIRTH));
                int parentID = cursor.getInt(cursor.getColumnIndex(PARENT_ID));
                String bornCertificate = cursor.getString(cursor.getColumnIndex(BORN_CERTIFIKATE));

                kids.add(new Kid(firstName, secondName ,middleName, school, classOfSchool, dateOfBirth, parentID, bornCertificate));

                cursor.moveToNext();
            }
        }

        cursor.close();
        dbHelper.close();
        return kids;
    }

    public void addKid (Kid kid)
    {
        dbHelper = new DBHelper(context, NAME);
        cv = new ContentValues();
        db = dbHelper.getWritableDatabase();

        cv.put(FIRST_NAME, kid.getFirstName());
        cv.put(SECOND_NAME, kid.getSecondName());
        cv.put(MIDDLE_NAME, kid.getMiddleName());
        cv.put(SCHOOL, kid.getSchool());
        cv.put(CLASS, kid.getClassOfSchool());
        cv.put(DATE_BIRTH, kid.getDateOfBirth());
        cv.put(PARENT_ID, kid.getParentId());
        cv.put(BORN_CERTIFIKATE, kid.getBornCertificate());

        db.insert(NAME, null, cv);
        dbHelper.close();
    }

    public void recreateDataBase(ArrayList<Kid> kids)
    {
        deleteDataBase();
        dbHelper = new DBHelper(context, NAME);
        for (int i = 0; i < kids.size(); ++i)
        {
            addKid(kids.get(i));
        }
        dbHelper.close();
    }

    private void deleteDataBase()
    {
        dbHelper = new DBHelper(context, NAME);
        db = dbHelper.getWritableDatabase();
        db.delete(NAME, null, null);
        dbHelper.close();
    }
}
