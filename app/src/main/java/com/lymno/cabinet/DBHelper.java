package com.lymno.cabinet;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper
{
    String name;

    public DBHelper(Context context, String name)
    {
        super(context, name, null, 1);
        this.name = name;
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        switch (name)
        {
            case KidsDataBase.NAME:
                database.execSQL("create table " + KidsDataBase.NAME + "("
                        + KidsDataBase.FIRST_NAME + " text,"
                        + KidsDataBase.SECOND_NAME + " text,"
                        + KidsDataBase.MIDDLE_NAME + " text,"
                        + KidsDataBase.SCHOOL + " text,"
                        + KidsDataBase.CLASS + " text,"
                        + KidsDataBase.DATE_BIRTH + " text,"
                        + KidsDataBase.PARENT_ID + " int,"
                        + KidsDataBase.BORN_CERTIFIKATE + " text" +");");
                break;
            default:
                break;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
    {

    }
}