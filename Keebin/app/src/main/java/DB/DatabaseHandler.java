package DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import entity.CoffeeBrand;
import entity.Token;

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
    private static final String TABLE_TOKENS = "tokens";

    // CoffeeBranch Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "brandName";
    private static final String KEY_DATABASEID = "dataBaseId";
    private static final String KEY_NUMBEROFCOFFEENEEDED = "numberOfCoffeeNeeded";
    private static final String KEY_TOKENNAME = "tokenName";
    private static final String KEY_TOKEN = "token";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_COFFEEBRAND_TABLE = "CREATE TABLE " + TABLE_COFFEEBRAND + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_DATABASEID + " INTEGER,"
                + KEY_NUMBEROFCOFFEENEEDED + " INTEGER,"
                + "UNIQUE(" + KEY_NAME + ") ON CONFLICT IGNORE);";
        //sat op så hvis der forsøges at tilføje et CoffeeBrand med samme navn så ignorerer den det.

        String CREATE_TOKENS_TABLE = "CREATE TABLE " + TABLE_TOKENS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TOKENNAME + " TEXT,"
                + KEY_TOKEN + " TEXT,"
                + "UNIQUE(" + KEY_TOKENNAME + ") ON CONFLICT IGNORE);";

        db.execSQL(CREATE_TOKENS_TABLE);
        db.execSQL(CREATE_COFFEEBRAND_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COFFEEBRAND);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOKENS);

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
        values.put(KEY_DATABASEID, coffeeBrand.getId());
        values.put(KEY_NAME, coffeeBrand.getBrandName()); // CoffeeBrand Name
        values.put(KEY_NUMBEROFCOFFEENEEDED , coffeeBrand.getNumberOfCoffeeNeeded()); // CoffeeBrand coffees needed


        // Inserting Row
        db.insert(TABLE_COFFEEBRAND, null, values);
        db.close(); // Closing database connection
    }

    // Getting single CoffeeBrand on client DB ID
    public CoffeeBrand getBrandbyId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_COFFEEBRAND, new String[] { KEY_ID,
                        KEY_NAME, KEY_NUMBEROFCOFFEENEEDED, KEY_DATABASEID }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        CoffeeBrand cb = new CoffeeBrand(cursor.getInt(0),
                cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
        db.close();
        // return contact
        return cb;
    }

    // Getting single CoffeeBrand on Server DB ID
    public CoffeeBrand getBrandbyServerId(int brandName) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_COFFEEBRAND, new String[] { KEY_ID,
                        KEY_NAME, KEY_NUMBEROFCOFFEENEEDED, KEY_DATABASEID }, KEY_DATABASEID + "=?",
                new String[] { String.valueOf(brandName) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        CoffeeBrand cb = new CoffeeBrand(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
        db.close();
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
                cb.setId(Integer.parseInt(cursor.getString(0)));
                cb.setBrandName(cursor.getString(1));
                cb.setDataBaseId(Integer.parseInt(cursor.getString(2)));
                cb.setNumberOfCoffeeNeeded(Integer.parseInt(cursor.getString(3)));

                // Adding contact to list
                coffeeBrandList.add(cb);
            } while (cursor.moveToNext());
        }

        // return contact list
        db.close();
        return coffeeBrandList;
    }


    // Updating single CoffeeBrand
    public int updateCoffeeBrand(String brandName, int coffeesNeeded, int brandId, int dataBaseId)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, brandName);
        values.put(KEY_NUMBEROFCOFFEENEEDED, coffeesNeeded);
        values.put(KEY_DATABASEID, dataBaseId);

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




    //Tokens CRUD
    // Adding new Token
    public void addToken(Token token) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TOKENNAME, token.getName());
        values.put(KEY_TOKEN, token.getTokenData());


        // Inserting Row
        db.insert(TABLE_TOKENS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single Token on client DB ID
    public Token getTokenByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TOKENS, new String[] { KEY_ID,
                        KEY_TOKENNAME, KEY_TOKEN }, KEY_TOKENNAME + "=?",
                new String[] { name }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Token t = new Token(cursor.getInt(0),
                cursor.getString(1), cursor.getString(2));
        db.close();
        // return contact
        return t;
    }


    // Getting All CoffeeBrands
    public List<Token> getAllTokens() {
        List<Token> tokenList = new ArrayList<Token>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TOKENS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Token t = new Token();
                t.setId(Integer.parseInt(cursor.getString(0)));
                t.setName(cursor.getString(1));
                t.setTokenData(cursor.getString(2));

                // Adding contact to list
                tokenList.add(t);
            } while (cursor.moveToNext());
        }

        // return contact list
        db.close();
        return tokenList;
    }


    // Updating single CoffeeBrand
    public int updateToken(String tokenName, String tokenData)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TOKENNAME, tokenName);
        values.put(KEY_TOKEN, tokenData);

        // updating row
        return db.update(TABLE_TOKENS, values, KEY_TOKENNAME + "=?",
                new String[] { tokenName });}


    // Deleting single CoffeeBrand
    public void deleteToken(String tokenName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TOKENS, KEY_TOKENNAME + "=?",
                new String[] { tokenName });
        db.close();
    }



    public boolean hasObject(String id) {
        SQLiteDatabase db = getWritableDatabase();
        String selectString = "SELECT * FROM " + TABLE_TOKENS + " WHERE " + KEY_TOKENNAME + " =?";



        // Add the String you are searching by here.
        // Put it in an array to avoid an unrecognized token error
        Cursor cursor = db.rawQuery(selectString, new String[] {id});

                boolean hasObject = false;
        if(cursor.moveToFirst()){
            hasObject = true;

            //region if you had multiple records to check for, use this region.

            int count = 0;
            while(cursor.moveToNext()){
                count++;
            }
            //here, count is records found
            Log.d("", String.format("%d records found", count));

            //endregion

        }

        cursor.close();          // Dont forget to close your cursor
        db.close();              //AND your Database!
        return hasObject;



    }

//    public boolean isTableExists(String tableName, boolean openDb) {
//        SQLiteDatabase db = getWritableDatabase();
//        if(openDb) {
//            if(db == null || !db.isOpen()) {
//                db = getReadableDatabase();
//            }
//
//            if(!db.isReadOnly()) {
//                db.close();
//                db = getReadableDatabase();
//            }
//        }
//
//        Cursor cursor = db.rawQuery("select DISTINCT " + TABLE_TOKENS +  " from KeebinDB  where " + TABLE_TOKENS + " = '"+TABLE_TOKENS+"'", null);
//        if(cursor!=null) {
//            if(cursor.getCount()>0) {
//                cursor.close();
//                return true;
//            }
//            cursor.close();
//        }
//        return false;
//    }

}
