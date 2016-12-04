package kasper.pagh.keebin;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import entity.CoffeeBrand;
import entity.CoffeeShop;

/**
 * Created by mrlef on 12/4/2016.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "KeebinDB";

    // CoffeeBrands table name
    private static final String TABLE_COFFEEBRAND = "coffeeBrand";

    // CoffeeBranch Table Columns names
    private static final String KEY_ID = "brandId";
    private static final String KEY_NAME = "brandName";
    private static final String KEY_NUMBEROFCOFFEENEEDED = "numberOfCoffeeNeeded";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_COFFEEBRAND + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_NUMBEROFCOFFEENEEDED + "INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COFFEEBRAND);

        // Create tables again
        onCreate(db);
    }


    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new coffeeBrand
    public void addCoffeeBrand(CoffeeBrand coffeeBrand) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, coffeeBrand.getBrandName()); // CoffeeBrand Name
        values.put(KEY_NUMBEROFCOFFEENEEDED , coffeeBrand.getCoffeesNeeded()); // CoffeeBrand coffees needed

        // Inserting Row
        db.insert(TABLE_COFFEEBRAND, null, values);
        db.close(); // Closing database connection
    }

    // Getting single CoffeeBrand
    public CoffeeBrand getBrandbyId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_COFFEEBRAND, new String[] { KEY_ID,
                        KEY_NAME, KEY_NUMBEROFCOFFEENEEDED }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        CoffeeBrand cb = new CoffeeBrand(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), Integer.parseInt(cursor.getString(2)));
        // return contact
        return cb;
    }

    // Getting All CoffeeBrands
    public List<CoffeeBrand> getAllCoffeeBrands() {
        List<CoffeeBrand> coffeeBrandList = new ArrayList<CoffeeBrand>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_COFFEEBRAND;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CoffeeBrand cb = new CoffeeBrand();
                cb.setDatabaseId(Integer.parseInt(cursor.getString(0)));
                cb.setBrandName(cursor.getString(1));
                cb.setCoffeesNeeded(Integer.parseInt(cursor.getString(2)));
                // Adding contact to list
                coffeeBrandList.add(cb);
            } while (cursor.moveToNext());
        }

        // return contact list
        return coffeeBrandList;
    }


    // Updating single CoffeeBrand
    public int updateCoffeeBrand(String brandName, int coffeesNeeded, int brandId)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, brandName);
        values.put(KEY_NUMBEROFCOFFEENEEDED, coffeesNeeded);

        // updating row
        return db.update(TABLE_COFFEEBRAND, values, KEY_ID + "=?",
                new String[] { String.valueOf(brandId) });}


    // Deleting single CoffeeBrand
    public void deleteCoffeeBrandById(int brandId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COFFEEBRAND, KEY_ID + "=?",
                new String[] { String.valueOf(brandId) });
        db.close();
    }
}
