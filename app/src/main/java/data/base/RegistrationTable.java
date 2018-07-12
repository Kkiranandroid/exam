package data.base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import Commmon.Methods.CommonMethods;
import get.set.LoginGetSet;

/**
 * Created by soumyay on 7/20/2016.
 */
public class RegistrationTable {

    private SQLiteDatabase database;
    private MainDatBase dbHelper;
    private Context context;

    public RegistrationTable(Context context) {
        dbHelper = new MainDatBase(context);
        this.context=context;
    }

    public void open() throws SQLException {

        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }
    public static final String TABLE_CONTACTS1 = "RegisterTable";
    public static final String KEY_ID = "id";
    private static final String USER_ID = "AuthKey";



    private static String CREATE_CONTACTS_TABLE = "CREATE TABLE "+ TABLE_CONTACTS1 + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + USER_ID + " TEXT "
            + ")";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_CONTACTS_TABLE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + CREATE_CONTACTS_TABLE);
        onCreate(database);
    }

    // insert into
    public void addlogindetails(String userid) {
        SQLiteDatabase db = this.database;
        ContentValues values = new ContentValues();

        //String AuthKey=userid;
        CommonMethods.userid=userid;
        values.put(USER_ID, userid);


        try {
            db.insert(TABLE_CONTACTS1, null, values);
            close();
            dbHelper.close();
        } catch (Exception e) {
            close();
            dbHelper.close();
        }
    }
    public String getAuthKey1(String selectAuthKeyQuery) {
        String AuthKeyValue="";
        Cursor cursor = null;
        try {

            open();
            SQLiteDatabase db = this.database;
            cursor = db.rawQuery(selectAuthKeyQuery, null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (cursor.moveToFirst()) {
            do {
                AuthKeyValue=cursor.getString(cursor
                        .getColumnIndex("AuthKey"));
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        close();
        dbHelper.close();
        return AuthKeyValue;
    }
    public void delettableuserTB()
    {
        SQLiteDatabase db = this.database;
        db.delete(TABLE_CONTACTS1 , null , null);
        close();
        dbHelper.close();
    }
}
