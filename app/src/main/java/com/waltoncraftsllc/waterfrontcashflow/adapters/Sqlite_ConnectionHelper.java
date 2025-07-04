package com.waltoncraftsllc.waterfrontcashflow.adapters;
import static com.waltoncraftsllc.waterfrontcashflow.tools.DatabaseContract.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.waltoncraftsllc.waterfrontcashflow.tools.Budget;
import com.waltoncraftsllc.waterfrontcashflow.tools.Budget_TimeBracket;
import com.waltoncraftsllc.waterfrontcashflow.tools.DatabaseContract;
import com.waltoncraftsllc.waterfrontcashflow.tools.ExpenseLog;
import com.waltoncraftsllc.waterfrontcashflow.tools.ExpenseLog_Group;
import com.waltoncraftsllc.waterfrontcashflow.tools.Money;
import com.waltoncraftsllc.waterfrontcashflow.tools.Pair;

import java.util.ArrayList;

public class Sqlite_ConnectionHelper extends SQLiteOpenHelper {
    public Sqlite_ConnectionHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private Pair<Long, String>[] mDefaultPeriodicities = new Pair[] {
            new Pair<Long, String>(52L, "Weekly (52/yr)"),
            new Pair<Long, String>(26L, "Biweekly (26/yr)"),
            new Pair<Long, String>(24L, "Semimonthly (24/yr)"),
            new Pair<Long, String>(12L, "Monthly (12/yr)"),
            new Pair<Long, String>(4L, "Quarterly (4/yr)"),
            new Pair<Long, String>(3L, "Triannually (3/yr)"),
            new Pair<Long, String>(3L, "Semiannually (2/yr)"),
            new Pair<Long, String>(3L, "Annually (1/yr)")
    };

    private Pair<Long, String>[] mDefaultTenders = new Pair[] {
            new Pair<Long, String>(0L, "CA-cash"),
            new Pair<Long, String>(0L, "CC-Credit card"),
            new Pair<Long, String>(0L, "CK-checking"),
            new Pair<Long, String>(0L, "DB-disbursement"),
            new Pair<Long, String>(0L, "DC-debit card"),
            new Pair<Long, String>(0L, "FA-Flexible Spending Account"),
            new Pair<Long, String>(0L, "GC-gift card"),
            new Pair<Long, String>(0L, "PP-Paypal"),
            new Pair<Long, String>(0L, "RE-reimbursement"),
            new Pair<Long, String>(0L, "TR-transfer"),
            new Pair<Long, String>(0L, "VM-Venmo")
    };

    private Pair<Long, String>[] mDefaultCategories = new Pair[] {
            new Pair<Long, String>(0L, "Cars/Insurance"),
            new Pair<Long, String>(0L, "Cars/Shop/Maintenance"),
            new Pair<Long, String>(0L, "Cars/Shop/Repairs"),
            new Pair<Long, String>(0L, "Cars/Fuel"),
            new Pair<Long, String>(0L, "Home/Mortgage/Regular"),
            new Pair<Long, String>(0L, "Home/Mortgage/Additional principal"),
            new Pair<Long, String>(0L, "Home/Mortgage/Taxes"),
            new Pair<Long, String>(0L, "Home/Mortgage/Home insurance"),
            new Pair<Long, String>(0L, "Home/Utilities/Cellphone"),
            new Pair<Long, String>(0L, "Home/Utilities/City"),
            new Pair<Long, String>(0L, "Home/Utilities/Electricity"),
            new Pair<Long, String>(0L, "Home/Utilities/Gas"),
            new Pair<Long, String>(0L, "Home/Utilities/Internet"),
            new Pair<Long, String>(0L, "Home/Repairs/Professional"),
            new Pair<Long, String>(0L, "Home/Repairs/Self repairs"),
            new Pair<Long, String>(0L, "Home/General/Household"),
            new Pair<Long, String>(0L, "Home/General/Office"),
            new Pair<Long, String>(0L, "Home/General/Yard"),
            new Pair<Long, String>(0L, "Consumables/Food"),
            new Pair<Long, String>(0L, "Consumables/Non-Food"),
            new Pair<Long, String>(0L, "Personal/Insurance/Dental"),
            new Pair<Long, String>(0L, "Personal/Insurance/Medical"),
            new Pair<Long, String>(0L, "Personal/Insurance/Prescriptions"),
            new Pair<Long, String>(0L, "Personal/Insurance/Copay"),
            new Pair<Long, String>(0L, "Personal/Insurance/Vision"),
            new Pair<Long, String>(0L, "Personal/Health/General"),
            new Pair<Long, String>(0L, "Provisions/Education"),
            new Pair<Long, String>(0L, "Provisions/Gifts/Family"),
            new Pair<Long, String>(0L, "Provisions/Gifts/Other"),
            new Pair<Long, String>(0L, "Subscription Services/Entertainment/Amazon channels"),
            new Pair<Long, String>(0L, "Subscription Services/Entertainment/Rentals"),
            new Pair<Long, String>(0L, "Services/USCCA"),
            new Pair<Long, String>(0L, "Services/Amazon Prime"),
            new Pair<Long, String>(0L, "Services/Audible"),
            new Pair<Long, String>(0L, "Services/Accountants"),
            new Pair<Long, String>(0L, "Services/Costco")
    };

    /**
     * long getSpinnerKey(Pair<Long, String>[] list, String periodicity) - Convert the text into the database key by searching 'list'.
     * @param list:        #Pair<Long, String>[]# The list of Spinner text and database IDs.
     * @param periodicity: #String# The text to search.
     * @return result:     #long# The database ID.
     */
    public long getSpinnerKey(Pair<Long, String>[] list, String periodicity) {
        long result = -1;
        for ( Pair<Long, String> pair : list ) {
            if ( pair.getValue().equals(periodicity) ) {
                result = pair.getKey();
                break;
            }
        }
        return result;
    }

    /**
     * String getSpinnerText(Pair<Long, String>[] list, long period) - Convert database ID into spinner-ready text.
     * @param list:    #Pair <Long, String>[]# The list of Spinner text and database IDs.
     * @param period:  #String# The database ID to find.
     * @return result: #String# The Spinner-ready text.
     */
    public String getSpinnerText(Pair<Long, String>[] list, long period) {
        String result = "";
        for ( Pair<Long, String> pair : list ) {
            if ( pair.getKey() == period ) {
                result = pair.getValue();
                break;
            }
        }
        return result;
    }

//====================================================================================================================================================

    /**
     * void createCategoriesTable(SQLiteDatabase db) - Create the "Categories" string table.
     * @param db: #SQLiteDatabase# The SQLite database handle.
     */
    private void createCategoriesTable(SQLiteDatabase db) {
        db.execSQL("create table " + DEFAULT_CATEGORIES__TABLE_DEFINITION);
        for ( Pair<Long, String> category : mDefaultCategories ) {
            ContentValues columns = new ContentValues();
            columns.put(DEFAULT_CATEGORIES__NAME, category.getValue());
            category.setKey(db.insert(DEFAULT_CATEGORIES__TABLE_NAME, null, columns)); // <-- this is temporatory, i.e., not stored in DB
        }
    }

    /**
     * void createTendersTable(SQLiteDatabase db) - Create the "Tenders" (or Legal Tenders) string table.
     * @param db: #SQLiteDatabase# The SQLite database handle.
     */
    private void createTendersTable(SQLiteDatabase db) {
        db.execSQL("create table " + DEFAULT_TENDER_LABELS__TABLE_DEFINITION);
        for ( Pair<Long, String> tender : mDefaultTenders) {
            ContentValues columns = new ContentValues();
            columns.put(DEFAULT_TENDER_LABELS__NAME, tender.getValue());
            tender.setKey(db.insert(DEFAULT_TENDER_LABELS__TABLE_NAME, null, columns)); // <-- this is temporatory, i.e., not stored in DB

        }
    }

    /**
     * void createPeriodicitiesTable(SQLiteDatabase db) - Create the "Periodicities" string table.
     * @param db: #SQLiteDatabase# The SQLite database handle.
     */
    private void createPeriodicitiesTable(SQLiteDatabase db) {
        db.execSQL("create table " + DEFAULT_PERIODICITY_LABELS__TABLE_DEFINITION);
        for ( Pair<Long, String> periodicity : mDefaultPeriodicities) {
            ContentValues columns = new ContentValues();
            columns.put(DEFAULT_PERIODICITY_LABELS__ID, periodicity.getKey());
            columns.put(DEFAULT_PERIODICITY_LABELS__NAME, periodicity.getValue());
            db.insert(DEFAULT_PERIODICITY_LABELS__TABLE_NAME, null, columns); // <-- no need to store ID as it's the key
        }
    }

    /**
     * void onCreate(SQLiteDatabase db) - do any initialization upon first creating new database.
     * @param db: #SQLiteDatabase# The SQLite database handle.
     */
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

    /**
     * void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) - do any revisions on the database as needed.
     * @param db: #SQLiteDatabase# The SQLite database handle.
     * @param oldVersion: #int# The old database version.
     * @param newVersion: #int# The new database version.
     */
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

    /**
     * ArrayList<CharSequence> queryDefaultCategories() - Retrieve all categories from the database.
     * @return categories #ArrayList<CharSequence>#
     */
    public ArrayList<CharSequence> queryCategories() {
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
        cursor.close();
        db.close();

        return categories;
    }

    /**
     * void setCategories(ArrayList<CharSequence> categories) - The user can modify the categories, so that's why they are stored in the database.
     * @param categories: #ArrayList<CharSequence># - The new set of customized catergories.
     */
    public void setCategories(ArrayList<CharSequence> categories) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("drop table if exists " + DEFAULT_CATEGORIES__TABLE_NAME);
        Pair<Long, String>[] pairs = new Pair[categories.size()];
        int index = 0;
        for ( CharSequence category : categories ) {
            pairs[index++] = new Pair<Long, String>(0L, category.toString());
        }
        mDefaultCategories = pairs;
        createCategoriesTable(db);
        db.close();
    }

    /**
     * ArrayList<CharSequence> getPeriodicities() - Get the periodicities from the database.
     * @return periods - #ArrayList<CharSequence># - Periodicities.
     */
    public ArrayList<CharSequence> queryPeriodicities() {
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
        cursor.close();
        db.close();

        return periods;
    }

    /**
     * void setPeriodicities(ArrayList<CharSequence> periodicities) - The user can modify the periodicities, so that's why they are stored in the database.
     * @param periodicities - #ArrayList<CharSequence>#
     */
    public void setPeriodicities(ArrayList<CharSequence> periodicities) {
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("drop table if exists " + DEFAULT_PERIODICITY_LABELS__TABLE_NAME);
        Pair<Long, String>[] pairs = new Pair[periodicities.size()];
        int index = 0;
        for ( CharSequence periodicity : periodicities ) {
            String periodicity_text = periodicity.toString();
            long frequency = Long.parseLong(periodicity_text.replaceAll("[^0-9]*", ""));
            pairs[index] = new Pair<Long, String>(frequency, periodicity_text);
        }
        mDefaultPeriodicities = pairs;
        createPeriodicitiesTable(db);
        db.close();
    }

    /**
     * ArrayList<CharSequence> getDefaultTenders() - Get the "tenders" (the methods of payment) strings.
     * @return tenders: ArrayList<CharSequence>
     */
    public ArrayList<CharSequence> queryTenders() {
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
        cursor.close();

    //--- Close database link and return labels.
        db.close();
        return tenders;
    }

    /**
     * void setTenders(ArrayList<CharSequence> tenders) - Revise the "tenders" (the methods of payment), so that's why they are stored in the database.
     * @param tenders: #ArrayList<CharSequence>#
     */
    public void setTenders(ArrayList<CharSequence> tenders) {
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("drop table if exists " + DEFAULT_TENDER_LABELS__TABLE_NAME);
        Pair<Long, String>[] pairs = new Pair[tenders.size()];
        int index = 0;
        for ( CharSequence tender : tenders ) {
            pairs[index++] = new Pair<Long, String>(0L, tender.toString());
        }
        mDefaultTenders = pairs;
        createTendersTable(db);
        db.close();
    }

//--- Budget Record -----------------------------------------------------------------------------------------------------------------------------
    /**
     * ArrayList<Budget> getBudgetRecords() - Retrieve all budget records. These include "time bracketed" items which encapsulate changes in expected expenses.
     * @return budget_records: #ArrayList<Budget>#
     */
    public ArrayList<Budget> queryBudgetRecords() {
        ArrayList<Budget> budget_records = new ArrayList<>();
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
                ArrayList<Budget_TimeBracket> brackets = new ArrayList<>();
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
                        brackets.add(new Budget_TimeBracket(from_date, to_date, new Money(amount), period));
                    } while ( bracket_cursor.moveToNext() );
                }
                bracket_cursor.close();

            //--- Get the rest of the Budget record fields and populate the struct.

                String name = budget_cursor.getString(name_col_index);
                String due_date = budget_cursor.getString(due_date_col_index);
                String amount_cap = budget_cursor.getString(amount_cap_col_index);
                Budget budget = new Budget(name, brackets, due_date, new Money(amount_cap));
                budget.setID(id);

            //--- Add to list.
                budget_records.add(budget);
            } while ( budget_cursor.moveToNext() );
        }
        budget_cursor.close();

    //--- Close and return all of the Budget items
        db.close();
        return budget_records;
    }

    /**
     * long insertBudgetRecord(Budget item) - Add a new budget item to the database.
     * @param item: #Budget#
     * @return row_id: #long# The row ID of the budget item with the associated "time brackets."
     */
    public long insertBudgetRecord(Budget item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

    //--- Add budget record
        values.put(DatabaseContract.BUDGET__NAME, item.getName());
        values.put(DatabaseContract.BUDGET__DUE_DATE, item.getDueDate());
        values.put(DatabaseContract.BUDGET__AMOUNT_CAP, item.getAmountCap());
        long row_id = db.insert(BUDGET__TABLE_NAME, null, values); // <-- Grab record ID for time brackets

    //--- For all time brackets...
        for ( Budget_TimeBracket bracket: item.getTimeBrackets() ) {
            values.clear(); // <-- clear out data from budget record
            values.put(BUDGET_TIME_BRACKET__BUDGET_FK, row_id);
            values.put(BUDGET_TIME_BRACKET__FROM_DATE, bracket.getFromDate());
            values.put(BUDGET_TIME_BRACKET__TO_DATE, bracket.getToDate());
            values.put(BUDGET_TIME_BRACKET__AMOUNT, bracket.getAmount());
            long periodicity = getSpinnerKey(mDefaultPeriodicities, bracket.getPeriodicity_str());
            values.put(BUDGET_TIME_BRACKET__PERIODICITY_FK, periodicity);
        //--- insert
            db.insert(BUDGET_TIME_BRACKET__TABLE_NAME, null, values);
        }
        db.close();
        return row_id;
    }

    /**
     * void deleteBudgetRecord(long budget_id) - Delete a budget record along with all "time brackets."
     * @param budget_id: #long# The database ID to delete.
     */
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
    public void updateBudgetRecord(Budget budget) {
//TODO
    }

//--- ExpenseLog Record ----------------------------------------------------------------------------------------------------------------------------
    /**
     * ArrayList<ExpenseLog> getExpenseRecords() - Get expense records. I imagine that this can ultimately be very huge. That's a problem.
     * @return results: #ArrayList<ExpenseLog>#
     */
    public ArrayList<ExpenseLog> queryExpenseLogRecords() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<ExpenseLog> results = new ArrayList<>();
//TODO
        return results;
    }

    /**
     * long insertExpenseLogRecord(ExpenseLog item) - Add a new expense record with all grouped expense categories.
     * @param item: #ExpenseLog# - The record to add.
     * @return rec_id: #long# - The database ID for the new record.
     */
    public long insertExpenseLogRecord(ExpenseLog item) {
        long rec_id;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

    //--- Add Expense record
        values.put(DatabaseContract.EXPENSE_LOG__DATE, item.dateToString());
        values.put(DatabaseContract.EXPENSE_LOG__TENDER_FK, item.getTenderString());
        rec_id = db.insert(BUDGET__TABLE_NAME, null, values); // <-- Grab record ID for time brackets

        //--- For all group items...
        for ( ExpenseLog_Group group : item.getGroup() ) {
            values.clear();

            values.put(EXPENSE_LOG_GROUP__CATEGORY_FK, getSpinnerKey(mDefaultCategories, group.getCategory()));
            values.put(EXPENSE_LOG_GROUP__DEBIT, group.getDebit());
            values.put(EXPENSE_LOG_GROUP__TAX, group.getTax());
            values.put(EXPENSE_LOG_GROUP__FOR_WHOM, group.getForWhom());
            values.put(BUDGET_TIME_BRACKET__PERIODICITY_FK, getSpinnerKey(mDefaultPeriodicities, group.getCategory()));

        //--- insert (ignore the returned key)
            db.insert(BUDGET_TIME_BRACKET__TABLE_NAME, null, values);
        }
        db.close();

        return rec_id;
    }

    /**
     * void deleteExpenseLogRecord(long expense_id) - Delete an expense record along with the "group" of categories.
     * @param expense_id: #long# The database ID to delete.
     */
    public void deleteExpenseLogRecord(long expense_id) {
        //--- Delete the time groups first...
        String where_clause = EXPENSE_LOG_GROUP__ID + "=?";
        String[] where_args = new String[] { String.valueOf(expense_id) };
        SQLiteDatabase db = getWritableDatabase();
        db.delete(EXPENSE_LOG_GROUP__TABLE_NAME, where_clause, where_args);

        //--- ... then the expense log.
        where_clause = EXPENSE_LOG__ID + "=?";
        db.delete(EXPENSE_LOG__TABLE_NAME, where_clause, where_args);
        db.close();
    }
    public void updateExpenseLogRecord(ExpenseLog item) {
//TODO
    }

}
