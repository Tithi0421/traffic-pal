package com.afinal.android.trafficpal.Activity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Tithi on 08-06-2017.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper mInstance = null;

    public static final String DATABASE_NAME = "Database";
    public static final String DATABASE_TABLE = "PoliceAccount";
    public static final String DATABASE_TABLE1 = "Records";
    private static final int DATABASE_VERSION = 1;
    private Context mCxt;

    public static final String Key_ID = "_id";
    public static final String Username = "Username";
    public static final String Password = "Password";

    public static final String Key_ID1 = "id";
    public static final String Lno = "Lno";
    public static final String Name = "Name";
    public static final String Vio = "Vio";
    public static final String Date = "Date";
    public static final String Total = "Total";






    public static DatabaseHelper getInstance(Context ctx) {
        /**
         * use the application context as suggested by CommonsWare.
         * this will ensure that you dont accidentally leak an Activitys
         * context (see this article for more information:
         * http://android-developers.blogspot.nl/2009/01/avoiding-memory-leaks.html)
         */
        if (mInstance == null) {
            mInstance = new DatabaseHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    /**
     * constructor should be private to prevent direct instantiation.
     * make call to static factory method "getInstance()" instead.
     */
    private DatabaseHelper(Context ctx) {

        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.mCxt = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTENT_TABLE = "CREATE TABLE " + DATABASE_TABLE + "("
                + Key_ID + " INTEGER PRIMARY KEY," + Username + " TEXT," + Password + " TEXT"  +  ")";
        Log.d("Sql create",""+CREATE_CONTENT_TABLE);
        db.execSQL(CREATE_CONTENT_TABLE);

        db.execSQL("INSERT INTO " + DATABASE_TABLE + "(Username, Password ) VALUES ('rakesh02','qwerty')");
        db.execSQL("INSERT INTO " + DATABASE_TABLE + "(Username, Password ) VALUES ('manish03','asdfgh')");

        String CREATE_CONTENT_TABLE1 = "CREATE TABLE " + DATABASE_TABLE1 + "("
                + Key_ID1 + " INTEGER PRIMARY KEY," + Lno + " TEXT," + Name + " TEXT," + Vio + " TEXT," + Date  + " TEXT," + Total +  " TEXT" + ")";
        Log.d("Sql create",""+CREATE_CONTENT_TABLE1);
        db.execSQL(CREATE_CONTENT_TABLE1);


      /*  String CREATE_CONTENT_TABLE1 = "CREATE TABLE " + DATABASE_TABLE1 + "("
                + Key_ID1 + " INTEGER PRIMARY KEY," + Equip + " TEXT," + Equip_Sr + " TEXT," + Make + " TEXT," + Date_Mfg + " TEXT," + Capacity + " TEXT," + Location + " TEXT," + Shell_Check + " TEXT," + Smooth_Check + " TEXT," + Drain_Check + " TEXT," + Tight_Check + " TEXT," + Thick_Check + " TEXT," + Lamination_Check + " TEXT," + Crack_Check + " TEXT," + Hydro_Check + " TEXT," + Range_Check + " TEXT," + Valve_Check + " TEXT" + ")";
        Log.d("Sql create",""+CREATE_CONTENT_TABLE1);
        db.execSQL(CREATE_CONTENT_TABLE1); */

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);

        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE1);

        // Create tables again
        onCreate(db);
    }
}

