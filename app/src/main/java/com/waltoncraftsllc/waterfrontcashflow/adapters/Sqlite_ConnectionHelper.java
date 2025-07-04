package com.waltoncraftsllc.waterfrontcashflow.adapters;
import static com.waltoncraftsllc.waterfrontcashflow.tools.DatabaseContract.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.waltoncraftsllc.waterfrontcashflow.tools.BudgetItem;
import com.waltoncraftsllc.waterfrontcashflow.tools.BudgetItem_TimeBracket;
import com.waltoncraftsllc.waterfrontcashflow.tools.DatabaseContract;
import com.waltoncraftsllc.waterfrontcashflow.tools.ExpenseLogItem;
import com.waltoncraftsllc.waterfrontcashflow.tools.ExpenseLogItem_Group;
import com.waltoncraftsllc.waterfrontcashflow.tools.Money;
import com.waltoncraftsllc.waterfrontcashflow.tools.Pair;

import java.util.ArrayList;

public class Sqlite_ConnectionHelper extends SQLiteOpenHelper {
//    private static Sqlite_ConnectionHelper mSQLiteHelper = null;

//    public Sqlite_ConnectionHelper getInstance() {
//        return mSQLiteHelper;
//    }
    public Sqlite_ConnectionHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
//        mSQLiteHelper = this;
    }

    private final Pair[] mDefaultPeriodicity = new Pair[] {
            new Pair<Long, String>(52L, "Weekly (52/yr)"), new Pair<Long, String>(26L, "Biweekly (26/yr)"), 
            new Pair<Long, String>(24L, "Semimonthly (24/yr)"), new Pair<Long, String>(12L, "Monthly (12/yr)"), 
            new Pair<Long, String>(4L, "Quarterly (4/yr)"), new Pair<Long, String>(3L, "Triannual (3/yr)")
    };

    private final Pair[] mDefaultTender = new Pair[] {
            new Pair<Long, String>(0L, "CA-cash"), new Pair<Long, String>(0L, "CC-Credit card"),
            new Pair<Long, String>(0L, "CK-checking"), new Pair<Long, String>(0L, "DB-disbursement"),
            new Pair<Long, String>(0L, "DC-debit card"), new Pair<Long, String>(0L, "FA-Flexible Spending Account"),
            new Pair<Long, String>(0L, "GC-gift card"), new Pair<Long, String>(0L, "PP-Paypal"),
            new Pair<Long, String>(0L, "RE-reimbursement"), new Pair<Long, String>(0L, "TR-transfer"),
            new Pair<Long, String>(0L, "VM-Venmo")
    };

    private final Pair[] mDefaultCategories = new Pair[] {
            new Pair<Long, String>(0L, "Cars/Insurance"), new Pair<Long, String>(0L, "Cars/Shop/Maintenance"),
            new Pair<Long, String>(0L, "Cars/Shop/Repairs"), new Pair<Long, String>(0L, "Cars/Fuel"),
            new Pair<Long, String>(0L, "Home/Mortgage/Regular"), new Pair<Long, String>(0L, "Home/Mortgage/Additional principal"),
            new Pair<Long, String>(0L, "Home/Mortgage/Taxes"), new Pair<Long, String>(0L, "Home/Mortgage/Home insurance"),
            new Pair<Long, String>(0L, "Home/Utilities/Cellphone"), new Pair<Long, String>(0L, "Home/Utilities/City"),
            new Pair<Long, String>(0L, "Home/Utilities/Electricity"), new Pair<Long, String>(0L, "Home/Utilities/Gas"),
            new Pair<Long, String>(0L, "Home/Utilities/Internet"), new Pair<Long, String>(0L, "Home/Repairs/Professional"),
            new Pair<Long, String>(0L, "Home/Repairs/Self repairs"), new Pair<Long, String>(0L, "Home/General/Household"),
            new Pair<Long, String>(0L, "Home/General/Office"), new Pair<Long, String>(0L, "Home/General/Yard"),
            new Pair<Long, String>(0L, "Consumables/Food"), new Pair<Long, String>(0L, "Consumables/Non-Food"),
            new Pair<Long, String>(0L, "Personal/Insurance/Dental"), new Pair<Long, String>(0L, "Personal/Insurance/Medical"),
            new Pair<Long, String>(0L, "Personal/Insurance/Prescriptions"), new Pair<Long, String>(0L, "Personal/Insurance/Copay"),
            new Pair<Long, String>(0L, "Personal/Insurance/Vision"), new Pair<Long, String>(0L, "Personal/Health/General"),
            new Pair<Long, String>(0L, "Provisions/Education"), new Pair<Long, String>(0L, "Provisions/Gifts/Family"),
            new Pair<Long, String>(0L, "Provisions/Gifts/Other"), new Pair<Long, String>(0L, "Subscription Services/Entertainment/Amazon channels"),
            new Pair<Long, String>(0L, "Subscription Services/Entertainment/Rentals"), new Pair<Long, String>(0L, "Services/USCCA"),
            new Pair<Long, String>(0L, "Services/Amazon Prime"), new Pair<Long, String>(0L, "Services/Audible"),
            new Pair<Long, String>(0L, "Services/Accountants"), new Pair<Long, String>(0L, "Services/Costco")
    };
    public long getSpinnerKey(Pair<Long, String>[] list, String periodicity) {
        for ( Pair<Long, String> pair : list ) {
            if ( pair.getValue().equals(periodicity) ) {
                return pair.getKey();
            }
        }
        return -1;
    }
    public String getSpinnerText(Pair<Long, String>[] list, long period) {
        for ( Pair<Long, String> pair : list ) {
            if ( pair.getKey() == period ) {
                return pair.getValue();
            }
        }
        return "";
    }

//====================================================================================================================================================
    private void createCategoriesTable(SQLiteDatabase db) {
        db.execSQL("create table " + DEFAULT_CATEGORIES__TABLE_DEFINITION);
        for ( Pair<Long, String> category : mDefaultCategories ) {
            ContentValues columns = new ContentValues();
            columns.put(DEFAULT_CATEGORIES__NAME, category.getValue());
            category.setKey(db.insert(DEFAULT_CATEGORIES__TABLE_NAME, null, columns)); // <-- this is temporatory, i.e., not stored in DB
        }
    }
    private void createTendersTable(SQLiteDatabase db) {
        db.execSQL("create table " + DEFAULT_TENDER_LABELS__TABLE_DEFINITION);
        for ( Pair<Long, String> tender : mDefaultTender ) {
            ContentValues columns = new ContentValues();
            columns.put(DEFAULT_TENDER_LABELS__NAME, tender.getValue());
            tender.setKey(db.insert(DEFAULT_TENDER_LABELS__TABLE_NAME, null, columns)); // <-- this is temporatory, i.e., not stored in DB

        }
    }
    private void createPeriodicitiesTable(SQLiteDatabase db) {
        db.execSQL("create table " + DEFAULT_PERIODICITY_LABELS__TABLE_DEFINITION);
        for ( Pair<Long, String> periodicity : mDefaultPeriodicity ) {
            ContentValues columns = new ContentValues();
            columns.put(DEFAULT_PERIODICITY_LABELS__ID, periodicity.getKey());
            columns.put(DEFAULT_PERIODICITY_LABELS__NAME, periodicity.getValue());
            db.insert(DEFAULT_PERIODICITY_LABELS__TABLE_NAME, null, columns); // <-- no need to store ID as it's the key
        }
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
    //--- User data
        db.execSQL("create table " + BUDGET_TIME_BRACKET__TABLE_DEFINITION);
        db.execSQL("create table " + BUDGET__TABLE_DEFINITION);
        db.execSQL("create table " + EXPENSE_LOG__TABLE_DEFINITION);
        db.execSQL("create table " + EXPENSE_LOG_GROUP__TABLE_DEFINITION);

    //--- Default labels
        createCategoriesTable(db);
        createTendersTable(db);
        createPeriodicitiesTable(db);
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
        ArrayList<CharSequence> categories = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

    //--- Open Periodicity table
        String query = "select * from " + DEFAULT_CATEGORIES__TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {

        //--- Translate name to column number (a really good idea!)
            int columnIndex = cursor.getColumnIndex(DEFAULT_CATEGORIES__NAME);

        //--- iterate through all of the labels.
            do {
                categories.add(cursor.getString(columnIndex));
            } while (cursor.moveToNext());
        }
        db.close();

        return categories;
    }

//FIXME: this needs to replace the local data structure
    public void setCategories(ArrayList<CharSequence> categories) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("drop table if exists " + DEFAULT_CATEGORIES__TABLE_NAME);
        //TODO: add conversion here
        createCategoriesTable(db);
        db.close();
    }

    public ArrayList<CharSequence> getPeriodicities() {
        ArrayList<CharSequence> periods = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

    //--- Open Periodicity table
        String query = "select * from " + DEFAULT_PERIODICITY_LABELS__TABLE_NAME; // <-- grab all labels
        Cursor cursor = db.rawQuery(query, null);
        if ( cursor.moveToFirst() ) {

        //--- Translate name to column number (a really good idea!)
            int columnIndex = cursor.getColumnIndex(DEFAULT_PERIODICITY_LABELS__NAME);

        //--- iterate through all of the labels.
            do {
                periods.add(cursor.getString(columnIndex));
            } while ( cursor.moveToNext() );
        }
        db.close();

        return periods;
    }
//FIXME: this needs to replace the local data structure
    public void setPeriodicities(ArrayList<CharSequence> periodicities) {
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("drop table if exists " + DEFAULT_PERIODICITY_LABELS__TABLE_NAME);
        //TODO: add conversion here
        createPeriodicitiesTable(db);
        db.close();
    }

    public ArrayList<CharSequence> getDefaultTenders() {
        ArrayList<CharSequence> tenders = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

    //--- Open Tender table
        String query = "select * from " + DEFAULT_TENDER_LABELS__TABLE_NAME; // <-- grabbing all labels
        Cursor cursor = db.rawQuery(query, null);
        if ( cursor.moveToFirst() ) {

        //--- Translate name to column number (a really good idea!)
            int columnIndex = cursor.getColumnIndex(DEFAULT_TENDER_LABELS__NAME);

        //--- iterate through all of the labels.
            do {
                String tender = cursor.getString(columnIndex);
                tenders.add(tender);
            } while ( cursor.moveToNext() );
        }

    //--- Close database link and return labels.
        db.close();
        return tenders;
   }

//FIXME: this needs to replace the local data structure
   public void setTenders(ArrayList<CharSequence> tenders) {
       SQLiteDatabase db = getReadableDatabase();
       db.execSQL("drop table if exists " + DEFAULT_TENDER_LABELS__TABLE_NAME);
       //TODO: add conversion here
       createTendersTable(db);
       db.close();
   }

//--- Budget Item Record -----------------------------------------------------------------------------------------------------------------------------
    public ArrayList<BudgetItem> getBudgetRecords() {
        ArrayList<BudgetItem> budget_records = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

    //--- Open Budget table
        String query = "select * from " + BUDGET__TABLE_NAME;
        Cursor budget_cursor = db.rawQuery(query, null);
        if ( budget_cursor.moveToFirst() ) {

        //--- Translate name to column number (a really good idea!)
            int id_col_index = budget_cursor.getColumnIndex(BUDGET__ID);
            int name_col_index = budget_cursor.getColumnIndex(BUDGET__NAME);
            int due_date_col_index = budget_cursor.getColumnIndex(BUDGET__DUE_DATE);
            int amount_cap_col_index = budget_cursor.getColumnIndex(BUDGET__AMOUNT_CAP);

        //--- iterate through all budget records
            do {

            //--- Get the budget record ID to get time brackets
                ArrayList<BudgetItem_TimeBracket> brackets = new ArrayList<>();
                long id = budget_cursor.getLong(id_col_index);
                query = "select * from " + BUDGET_TIME_BRACKET__TABLE_NAME + " where " + BUDGET_TIME_BRACKET__BUDGET_FK + "=?";
                String[] where_args = new String[] { String.valueOf(id) };
                Cursor bracket_cursor = db.rawQuery(query, where_args);
            //--- If there are time brackets (there SHOULD be)...
                if ( bracket_cursor.moveToFirst() ) {

                //--- Translate name to column number (a really good idea!)
                    int from_date_col_index = bracket_cursor.getColumnIndex(BUDGET_TIME_BRACKET__FROM_DATE);
                    int to_date_col_index = bracket_cursor.getColumnIndex(BUDGET_TIME_BRACKET__TO_DATE);
                    int amount_col_index = bracket_cursor.getColumnIndex(BUDGET_TIME_BRACKET__AMOUNT);
                    int periodicity_col_index = bracket_cursor.getColumnIndex(BUDGET_TIME_BRACKET__PERIODICITY_FK);

                //--- ... iterate.
                    do {
                        String from_date = bracket_cursor.getString(from_date_col_index);
                        String to_date = bracket_cursor.getString(to_date_col_index);
                        String amount = bracket_cursor.getString(amount_col_index);
                        String period = bracket_cursor.getString(periodicity_col_index);
                        brackets.add(new BudgetItem_TimeBracket(from_date, to_date, new Money(amount), period));
                    } while ( bracket_cursor.moveToNext() );
                }

            //--- Get the rest of the Budget record fields and populate the struct.

                String name = budget_cursor.getString(name_col_index);
                String due_date = budget_cursor.getString(due_date_col_index);
                String amount_cap = budget_cursor.getString(amount_cap_col_index);
                BudgetItem budgetItem = new BudgetItem(name, brackets, due_date, new Money(amount_cap));
                budgetItem.setID(id);

            //--- Add to list.
                budget_records.add(budgetItem);
            } while ( budget_cursor.moveToNext() );
        }

    //--- Close and return all of the Budget items
        db.close();
        return budget_records;
    }
    public long insertBudgetItem(BudgetItem item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

    //--- Add budget record
        values.put(DatabaseContract.BUDGET__NAME, item.getName());
        values.put(DatabaseContract.BUDGET__DUE_DATE, item.getDueDate());
        values.put(DatabaseContract.BUDGET__AMOUNT_CAP, item.getAmountCap());
        long row_id = db.insert(BUDGET__TABLE_NAME, null, values); // <-- Grab record ID for time brackets

    //--- For all time brackets...
        for ( BudgetItem_TimeBracket bracket: item.getTimeBrackets() ) {
            values.clear(); // <-- clear out data from budget record
            values.put(BUDGET_TIME_BRACKET__BUDGET_FK, row_id);
            values.put(BUDGET_TIME_BRACKET__FROM_DATE, bracket.getFromDate());
            values.put(BUDGET_TIME_BRACKET__TO_DATE, bracket.getToDate());
            values.put(BUDGET_TIME_BRACKET__AMOUNT, bracket.getAmount());
//FIXME:            values.put(BUDGET_TIME_BRACKET__PERIODICITY_FK, bracket.getPeriodicity_str());
        //--- insert
            db.insert(BUDGET_TIME_BRACKET__TABLE_NAME, null, values);
        }
        db.close();
        return row_id;
    }
    public void deleteBudgetRecord(long budget_id) {

    //--- Delete the time brackets first...
        String where_clause = BUDGET_TIME_BRACKET__BUDGET_FK + "=?";
        String[] where_args = new String[] { String.valueOf(budget_id) };
        SQLiteDatabase db = getWritableDatabase();
        db.delete(BUDGET_TIME_BRACKET__TABLE_NAME, where_clause, where_args);

    //--- ... then the budget.
        where_clause = BUDGET__ID + "=?";
        db.delete(BUDGET__TABLE_NAME, where_clause, where_args);
        db.close();
    }
    public void updateBudgetRecord(BudgetItem budgetItem) {
//TODO
    }

//--- Expense Item Record ----------------------------------------------------------------------------------------------------------------------------
    public long insertExpenseRecord(ExpenseLogItem item) {
        long rec_id = -1;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

    //--- Add Expense record
        values.put(DatabaseContract.EXPENSE_LOG__DATE, item.dateToString());
        values.put(DatabaseContract.EXPENSE_LOG__TENDER_FK, item.getTenderString());
        long row_id = db.insert(BUDGET__TABLE_NAME, null, values); // <-- Grab record ID for time brackets

        //--- For all group items...
        for ( ExpenseLogItem_Group group : item.getGroup() ) {
            values.clear(); // <-- clear out data from budget record
//FIXME            values.put(EXPENSE_LOG_GROUP__CATEGORY_FK, group.getCategory());
            values.put(EXPENSE_LOG_GROUP__DEBIT, group.getDebit());
            values.put(EXPENSE_LOG_GROUP__TAX, group.getTax());
            values.put(EXPENSE_LOG_GROUP__FOR_WHOM, group.getForWhom());
//FIXME:            values.put(BUDGET_TIME_BRACKET__PERIODICITY_FK, bracket.getPeriodicity_str());
            //--- insert
            db.insert(BUDGET_TIME_BRACKET__TABLE_NAME, null, values);
        }
        db.close();

        return rec_id;
    }
}
