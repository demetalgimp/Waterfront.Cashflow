package com.waltoncraftsllc.waterfrontcashflow.adapters;
import static com.waltoncraftsllc.waterfrontcashflow.tools.DatabaseContract.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.waltoncraftsllc.waterfrontcashflow.tools.ExpenseLogItem;
import com.waltoncraftsllc.waterfrontcashflow.tools.Pair;

import java.util.ArrayList;
import java.util.List;

public class Sqlite_ConnectionHelper extends SQLiteOpenHelper {
    public final int DATABASE_VERSION = 2;
    private static Sqlite_ConnectionHelper mSQLiteHelper = null;

    public Sqlite_ConnectionHelper getInstance() {
        return mSQLiteHelper;
    }
    public Sqlite_ConnectionHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, 1);
        mSQLiteHelper = this;
    }

    private final String[] mDefaultPeriodicity = {
            "Weekly (52/yr)", "Biweekly (26/yr)", "Semimonthly (24/yr)", "Monthly (12/yr)", "Quarterly (4/yr)", "Triannual (3/yr)",
            "Semiannually (2/yr)", "Annually (1/yr)", "Yearly (1/yr)"
    };

    private final String[] mDefaultTender = {
            "CA-cash", "CC-Credit card", "CK-checking", "DB-disbursement", "DC-debit card", "FA-Flexible Spending Account", "GC-gift card",
            "IN-income", "PP-Paypal", "RE-reimbursement", "TR-transfer", "VM-Venmo"
    };

    private final String[] mDefaultCategories = {
            "Cars/Insurance", "Cars/Shop/Maintenance", "Cars/Shop/Repairs", "Cars/Fuel", "Home/Mortgage/Regular", "Home/Mortgage/Additional principal",
            "Home/Mortgage/Taxes", "Home/Mortgage/Home insurance", "Home/Utilities/Cellphone", "Home/Utilities/City", "Home/Utilities/Electricity",
            "Home/Utilities/Gas", "Home/Utilities/Internet", "Home/Repairs/Professional", "Home/Repairs/Self repairs", "Home/General/Household",
            "Home/General/Office", "Home/General/Yard", "Consumables/Food", "Consumables/Non-Food", "Personal/Insurance/Dental",
            "Personal/Insurance/Medical", "Personal/Insurance/Prescriptions", "Personal/Insurance/Copay", "Personal/Insurance/Vision",
            "Personal/Health/General", "Provisions/Education", "Provisions/Gifts/Family", "Provisions/Gifts/Other",
            "Subscription Services/Entertainment/Amazon channels", "Subscription Services/Entertainment/Rentals", "Services/USCCA",
            "Services/Amazon Prime", "Services/Audible", "Services/Accountants", "Services/Costco"
    };

//====================================================================================================================================================
    @Override
    public void onCreate(SQLiteDatabase db) {
    //--- User data
        db.execSQL("create table " + BUDGET_TIME_BRACKET__TABLE_DEFINITION);
        db.execSQL("create table " + BUDGET__TABLE_DEFINITION);
        db.execSQL("create table " + EXPENSE_LOG__TABLE_DEFINITION);
        db.execSQL("create table " + EXPENSE_LOG_GROUP__TABLE_DEFINITION);

    //--- Default labels
        db.execSQL("create table " + DEFAULT_CATEGORIES__TABLE_DEFINITION);
        for ( String category : mDefaultCategories ) {
            ContentValues columns = new ContentValues();
            columns.put(DEFAULT_CATEGORIES__NAME, category);
            long result = db.insert(DEFAULT_CATEGORIES__TABLE_NAME, null, columns);
            result = result;
        }
        db.execSQL("create table " + DEFAULT_TENDER_LABELS__TABLE_DEFINITION);
        for ( String tender : mDefaultTender ) {
            ContentValues columns = new ContentValues();
            columns.put(DEFAULT_TENDER_LABELS__NAME, tender);
            db.insert(DEFAULT_TENDER_LABELS__TABLE_NAME, null, columns);
        }
        db.execSQL("create table " + DEFAULT_PERIODICITY_LABELS__TABLE_DEFINITION);
        for ( String periodicity : mDefaultPeriodicity ) {
            ContentValues columns = new ContentValues();
            columns.put(DEFAULT_PERIODICITY_LABELS__NAME, periodicity);
            columns.put(DEFAULT_PERIODICITY_LABELS__VALUE, periodicity.replaceAll("[^0-9]*/yr[)]", ""));
            db.insert(DEFAULT_PERIODICITY_LABELS__TABLE_NAME, null, columns);
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //--- Purge database
        db.execSQL("drop table if exists " + BUDGET_TIME_BRACKET__TABLE_DEFINITION);
        db.execSQL("drop table if exists " + BUDGET__TABLE_DEFINITION);
        db.execSQL("drop table if exists " + EXPENSE_LOG__TABLE_DEFINITION);
        db.execSQL("drop table if exists " + EXPENSE_LOG_GROUP__TABLE_DEFINITION);
        db.execSQL("drop table if exists " + DEFAULT_CATEGORIES__TABLE_NAME);
        db.execSQL("drop table if exists " + DEFAULT_PERIODICITY_LABELS__TABLE_NAME);
        db.execSQL("drop table if exists " + DEFAULT_TENDER_LABELS__TABLE_NAME);

        //--- Create tables again
        onCreate(db);
    }

//====================================================================================================================================================
    public ArrayList<CharSequence> getDefaultCategories() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<CharSequence> categories = new ArrayList<>();

        String query = "select * from " + DEFAULT_CATEGORIES__TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(DEFAULT_CATEGORIES__NAME);
            do {
                categories.add(cursor.getString(columnIndex));
            } while (cursor.moveToNext());
        }
        db.close();

        return categories;
    }
    public ArrayList<CharSequence> getPeriodicities() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<CharSequence> periods = new ArrayList<>();

        String query = "select * from " + DEFAULT_PERIODICITY_LABELS__TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        if ( cursor.moveToFirst() ) {
            int columnIndex = cursor.getColumnIndex(DEFAULT_PERIODICITY_LABELS__NAME);
            do {
                periods.add(cursor.getString(columnIndex));
            } while ( cursor.moveToNext() );
        }
        db.close();

        return periods;
    }
    public ArrayList<CharSequence> getDefaultTenders() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<CharSequence> tenders = new ArrayList<>();

        String query = "select * from " + DEFAULT_TENDER_LABELS__TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        if ( cursor.moveToFirst() ) {
            int columnIndex = cursor.getColumnIndex(DEFAULT_TENDER_LABELS__NAME);
            do {
                String tender = cursor.getString(columnIndex);
                tenders.add(tender);
            } while ( cursor.moveToNext() );
        }
        db.close();

        return tenders;
    }

//----------------------------------------------------------------------------------------------------------------------------------------------------
    public void add(ExpenseLogItem item) {

    }
}
