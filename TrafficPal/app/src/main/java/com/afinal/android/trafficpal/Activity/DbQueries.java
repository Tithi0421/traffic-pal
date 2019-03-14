package com.afinal.android.trafficpal.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Tithi on 08-06-2017.
 */
public class DbQueries {
    private SQLiteDatabase db;
    private Context context;
    private DatabaseHelper mDbHelper;

    public DbQueries(Context context) {
        this.context = context;
    }


    /*   public long addUserAccout(UserAccount userAccount) {
           ContentValues values = new ContentValues();
           values.put(DatabaseHelper.First_Name, userAccount.getFname());
           values.put(DatabaseHelper.Last_Name, userAccount.getLname());
           values.put(DatabaseHelper.Email_ID, userAccount.getEmail());
           values.put(DatabaseHelper.Password, userAccount.getPass());
           mDbHelper = DatabaseHelper.getInstance(context);
           db = mDbHelper.getWritableDatabase();
           long id = db.insert(DatabaseHelper.DATABASE_TABLE, null, values);
           db.close();
           return id;

       } */
    public long addRecords(Records records) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.Lno, records.getLno());
        values.put(DatabaseHelper.Name, records.getName1());
        values.put(DatabaseHelper.Vio, records.getVio());
        values.put(DatabaseHelper.Date, records.getDate());
        values.put(DatabaseHelper.Total,records.getTotal());

        mDbHelper = DatabaseHelper.getInstance(context);
        db = mDbHelper.getWritableDatabase();
        long id = db.insert(DatabaseHelper.DATABASE_TABLE1, null, values);
        db.close();
        return id;

    }
    public void deleteRecords(Long id) {
        mDbHelper = DatabaseHelper.getInstance(context);
        db = mDbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.DATABASE_TABLE1, DatabaseHelper.Key_ID1 + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }


  /*  public void addUserAccout1(UserAccount userAccount) {
        ContentValues values = new ContentValues();
        SharedPreferenceUtils sharedPreferenceUtils = SharedPreferenceUtils.getInstance();
        Long id = sharedPreferenceUtils.getLong(context, "key");
        values.put(DatabaseHelper.Civility, userAccount.getCiv());
        values.put(DatabaseHelper.Birthday, userAccount.getBirth());
        values.put(DatabaseHelper.Phone, userAccount.getPh());
        values.put(DatabaseHelper.Country, userAccount.getCoun());
        values.put(DatabaseHelper.Hobbies, userAccount.getHob());
        mDbHelper = DatabaseHelper.getInstance(context);
        db = mDbHelper.getWritableDatabase();
        String where = DatabaseHelper.Key_ID + "="+id;
        long row =  db.update(DatabaseHelper.DATABASE_TABLE, values, where, null);
        db.close();


    } */

    public long validateUser(String name, String password) {
        long id = 0;
        mDbHelper = DatabaseHelper.getInstance(context);
        db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.DATABASE_TABLE, null, DatabaseHelper.Username + " = '" + name + "'" + " AND " + DatabaseHelper.Password + " = '" + password + "'", null, null, null, null);
        if (cursor.getCount() > 0 && cursor != null) {
            while (cursor.moveToNext()) {
                id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.Key_ID));
            }
            return id;
        } else {
            return -1;
        }
    }


   /* UserAccount getUserAccount(int id) {
        db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.DATABASE_TABLE, new String[]{DatabaseHelper.Key_ID, DatabaseHelper.First_Name, DatabaseHelper.Last_Name, DatabaseHelper.Email_ID, DatabaseHelper.Password, DatabaseHelper.Civility, DatabaseHelper.Birthday, DatabaseHelper.Phone, DatabaseHelper.Country, DatabaseHelper.Hobbies}, DatabaseHelper.Key_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        UserAccount userAccount = new UserAccount(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9));
        return userAccount;
    }

    public List<UserAccount> getAllUserAccount() {
        List<UserAccount> userAccountList = new ArrayList<UserAccount>();
        String selectQuery = "SELECT  * FROM " + DatabaseHelper.DATABASE_TABLE;

        db = mDbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                UserAccount userAccount = new UserAccount();
                userAccount.setId(Integer.parseInt(cursor.getString(0)));
                userAccount.setFname(cursor.getString(1));
                userAccount.setLname(cursor.getString(2));
                userAccount.setEmail(cursor.getString(3));
                userAccount.setPass(cursor.getString(4));

                userAccountList.add(userAccount);
            } while (cursor.moveToNext());
        }

        return userAccountList;
    }


    public int updateUserAccount(UserAccount userAccount) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.First_Name, userAccount.getFname());
        values.put(DatabaseHelper.Last_Name, userAccount.getLname());
        values.put(DatabaseHelper.Email_ID, userAccount.getEmail());
        values.put(DatabaseHelper.Password, userAccount.getPass());
        db = mDbHelper.getWritableDatabase();

        return db.update(DatabaseHelper.DATABASE_TABLE, values, DatabaseHelper.Key_ID + " = ?", new String[]{String.valueOf(userAccount.getId())});

    }

    public void deleteUserAccount(UserAccount userAccount) {
        db = mDbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.DATABASE_TABLE, DatabaseHelper.Key_ID + " = ?", new String[]{String.valueOf(userAccount.getId())});
        db.close();
    }

    public int getUserAccountCount() {
        String countQuery = "SELECT  * FROM " + DatabaseHelper.DATABASE_TABLE;
        db = mDbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

*/
}
