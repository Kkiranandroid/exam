package data.base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import Commmon.Methods.CommonMethods;
import get.set.LoginGetSet;

public class LoginTable {

	private SQLiteDatabase database;
	private MainDatBase dbHelper;
	private Context context;

	public LoginTable(Context context) {
		dbHelper = new MainDatBase(context);
		this.context=context;
	}

	public void open() throws SQLException {

		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public static final String TABLE_CONTACTS = "LoginTable";
	public static final String KEY_ID = "id";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_PASSWORD = "password";
	private static final String KEY_AUTHKEY = "AuthKey";
	private static final String KEY_DEALER_NAME = "dealerName";
	private static final String KEY_PROFILE_PIC = "profilepic";


	private static String CREATE_CONTACTS_TABLE = "CREATE TABLE "+ TABLE_CONTACTS + "("
			+ KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_EMAIL + " TEXT ,"
			+ KEY_PASSWORD+ " TEXT ,"
			+ KEY_AUTHKEY + " TEXT , "
			+ KEY_DEALER_NAME + " TEXT , "
			+ KEY_PROFILE_PIC + " TEXT "
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
	public void addlogindetails(LoginGetSet loginData) {
		SQLiteDatabase db = this.database;
		ContentValues values = new ContentValues();

		String AuthKey=loginData.getUserId();
		CommonMethods.userid=AuthKey;

		values.put(KEY_EMAIL, loginData.getUserEmail());
		values.put(KEY_PASSWORD, loginData.getUserpassword());
		values.put(KEY_AUTHKEY, AuthKey);
		values.put(KEY_DEALER_NAME, loginData.getUserName());
		values.put(KEY_PROFILE_PIC, loginData.getProfilePic());

		try {
			db.insert(TABLE_CONTACTS, null, values);
			close();
			dbHelper.close();
		} catch (Exception e) {
			close();
			dbHelper.close();
		}
	}

	// getting all
	public ArrayList<LoginGetSet> getallpermission(String selectQuery) {
		ArrayList<LoginGetSet> contactList = new ArrayList<LoginGetSet>();
		Cursor cursor = null;
		try {

			open();
			SQLiteDatabase db = this.database;
			cursor = db.rawQuery(selectQuery, null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (cursor.moveToFirst()) {
			do {
				LoginGetSet loginData = new LoginGetSet();

				loginData.setId(cursor.getString(0));
				loginData.setUserEmail(cursor.getString(cursor
						.getColumnIndex("email")));
				loginData.setUserpassword(cursor.getString(cursor
						.getColumnIndex("password")));
				loginData.setUserId(cursor.getString(cursor
						.getColumnIndex("AuthKey")));
				loginData.setUserName(cursor.getString(cursor
						.getColumnIndex("dealerName")));
				loginData.setProfilePic(cursor.getString(cursor
						.getColumnIndex("profilepic")));
				contactList.add(loginData);
			} while (cursor.moveToNext());
		}
		if (cursor != null) {
			cursor.close();
		}

		close();
		dbHelper.close();
		return contactList;
	}

	public String getAuthKey(String selectAuthKeyQuery) {
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


	public String getCartId(String selectAuthKeyQuery) {
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
						.getColumnIndex("cartId"));
			} while (cursor.moveToNext());
		}
		if (cursor != null) {
			cursor.close();
		}
		close();
		dbHelper.close();
		return AuthKeyValue;
	}



	public String getDealerName() {
		String AuthKeyValue="";
		String selectAuthKeyQuery="select * from "+TABLE_CONTACTS;
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
						.getColumnIndex(KEY_DEALER_NAME));
			} while (cursor.moveToNext());
		}
		if (cursor != null) {
			cursor.close();
		}
		close();
		dbHelper.close();
		return AuthKeyValue;
	}

	public String getProfilePic() {
		String profilePicPath="";
		String selectAuthKeyQuery="select * from "+TABLE_CONTACTS;
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
				profilePicPath=cursor.getString(cursor
						.getColumnIndex(KEY_PROFILE_PIC));
			} while (cursor.moveToNext());
		}
		if (cursor != null) {
			cursor.close();
		}
		close();
		dbHelper.close();
		return profilePicPath;
	}

	public void updateDealerName(String newName)
	{
		Cursor cursor = null;
		try {
			open();
			SQLiteDatabase db = this.database;
			String qry="UPDATE "+TABLE_CONTACTS+" SET "+KEY_DEALER_NAME+"= '"+newName+"' where "+KEY_ID+" = '"+getAuthKey("Select * from LoginTable")+"'";
			Log.i("Query :-->",qry);
			cursor =db.rawQuery(qry, null);

			if(cursor.getColumnCount()!=0) {
				Toast.makeText(context, TABLE_CONTACTS + " is updated", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(context,TABLE_CONTACTS + " is not updated", Toast.LENGTH_SHORT).show();
		}
	}

	public String getPhoneNumber(String selectAuthKeyQuery) {
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
						.getColumnIndex("email"));
			} while (cursor.moveToNext());
		}
		if (cursor != null) {
			cursor.close();
		}
		close();
		dbHelper.close();
		return AuthKeyValue;
	}

	public void updateProfilePic(String newPath)
	{
		Cursor cursor = null;
		try {
			open();
			SQLiteDatabase db = this.database;
			String qry="UPDATE "+TABLE_CONTACTS+" SET "+KEY_PROFILE_PIC+"= '"+newPath+"' where "+KEY_ID+" = '"+CommonMethods.userid+"'";
			Log.i("Query :-->",qry);
			cursor =db.rawQuery(qry, null);

			if(cursor.getColumnCount()!=0) {
				Toast.makeText(context, TABLE_CONTACTS + " is updated", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(context,TABLE_CONTACTS + " is not updated", Toast.LENGTH_SHORT).show();
		}
	}

	public void delettableuserTB()
	{
		SQLiteDatabase db = this.database;
		db.delete(TABLE_CONTACTS , null , null);
		close();
		dbHelper.close();
	}
}
