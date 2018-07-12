package data.base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import Commmon.Methods.CommonMethods;

/**
 * Created by soumyay on 8/3/2016.
 */
public class CategoryTable {
    private SQLiteDatabase database;
    private MainDatBase dbHelper;
    private Context context;

    public CategoryTable(Context context) {
        dbHelper = new MainDatBase(context);
        this.context=context;
    }

    public void open() throws SQLException {

        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }
    public static final String TABLE_CONTACTS2 = "CategoryTable";
    public static final String KEY_ID = "id";
    private static final String CATEGORY_ID = "CatId";



    private static String CREATE_CONTACTS_TABLE = "CREATE TABLE "+ TABLE_CONTACTS2 + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + CATEGORY_ID + " TEXT "
            + ")";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_CONTACTS_TABLE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + CREATE_CONTACTS_TABLE);
        onCreate(database);
    }
    public void addlogindetails(String catId) {
        SQLiteDatabase db = this.database;
        ContentValues values = new ContentValues();

        //String AuthKey=userid;
        CommonMethods.category_Id=catId;
        values.put(CATEGORY_ID, catId);


        try {
            db.insert(TABLE_CONTACTS2, null, values);
            close();
            dbHelper.close();
        } catch (Exception e) {
            close();
            dbHelper.close();
        }
    }
    public String getCategoryId(String selectedcategorId) {
        String CategoryValue="";
        Cursor cursor = null;
        try {

            open();
            SQLiteDatabase db = this.database;
            cursor = db.rawQuery(selectedcategorId, null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (cursor.moveToFirst()) {
            do {
                CategoryValue=cursor.getString(cursor
                        .getColumnIndex("CatId"));
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        close();
        dbHelper.close();
        return CategoryValue;
    }
    public void delettableuserTB()
    {
        SQLiteDatabase db = this.database;
        db.delete(TABLE_CONTACTS2 , null , null);
        close();
        dbHelper.close();
    }
}
