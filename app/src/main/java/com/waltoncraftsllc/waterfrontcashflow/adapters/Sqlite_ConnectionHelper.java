package com.waltoncraftsllc.waterfrontcashflow.adapters;
import static com.waltoncraftsllc.waterfrontcashflow.tools.DatabaseContract.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.waltoncraftsllc.waterfrontcashflow.tools.Pair;

public class Sqlite_ConnectionHelper extends SQLiteOpenHelper {

    public Sqlite_ConnectionHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, 1);
    }

    private final Pair[] mDefaultPeriodicity = {
            new Pair<String, Integer>("Once", 1),         new Pair<String, Integer>("Weekly", 52),      new Pair<String, Integer>("Biweekly", 26),
            new Pair<String, Integer>("Semimonthly", 24), new Pair<String, Integer>("Monthly", 12),     new Pair<String, Integer>("Quarterly", 4),
            new Pair<String, Integer>("Triannual", 3),    new Pair<String, Integer>("Semiannually", 2), new Pair<String, Integer>("Annually", 1)
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

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + BUDGET_TIME_BRACKET__TABLE_DEFINITION);
        db.execSQL("create table " + BUDGET__TABLE_DEFINITION);
        db.execSQL("create table " + EXPENSE_LOG__TABLE_DEFINITION);
        db.execSQL("create table " + EXPENSE_LOG_GROUP__TABLE_DEFINITION);

    //---
        db.execSQL("create table " + DEFAULT_CATEGORIES__TABLE_DEFINITION);
        for ( String category : mDefaultCategories ) {
            ContentValues columns = new ContentValues();
            columns.put(DEFAULT_CATEGORIES__NAME, category);
            db.insert(DEFAULT_CATEGORIES__TABLE_NAME, null, columns);
        }

    //---
        db.execSQL("create table " + DEFAULT_TENDER_LABELS__TABLE_DEFINITION);
        for ( String tender : mDefaultTender ) {
            ContentValues columns = new ContentValues();
            columns.put(DEFAULT_TENDER_LABELS__NAME, tender);
            db.insert(DEFAULT_TENDER_LABELS__TABLE_NAME, null, columns);
        }

    //---
        db.execSQL("create table " + DEFAULT_PERIODICITY_LABELS__TABLE_DEFINITION);
        for ( Pair<String, Integer> periodicity : mDefaultPeriodicity ) {
            ContentValues columns = new ContentValues();
            columns.put(DEFAULT_PERIODICITY_LABELS__NAME, periodicity.getKey());
            columns.put(DEFAULT_PERIODICITY_LABELS__VALUE, periodicity.getValue());
            db.insert(DEFAULT_PERIODICITY_LABELS__TABLE_NAME, null, columns);
        }
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
