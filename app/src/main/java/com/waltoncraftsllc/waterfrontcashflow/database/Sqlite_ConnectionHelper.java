package com.waltoncraftsllc.waterfrontcashflow.database;
import static com.waltoncraftsllc.waterfrontcashflow.database.DatabaseContract.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.waltoncraftsllc.waterfrontcashflow.MainActivity;
import com.waltoncraftsllc.waterfrontcashflow.containers.Budget;
import com.waltoncraftsllc.waterfrontcashflow.containers.Budget_TimeBracket;
import com.waltoncraftsllc.waterfrontcashflow.containers.Expense;
import com.waltoncraftsllc.waterfrontcashflow.containers.Expense_Group;
import com.waltoncraftsllc.waterfrontcashflow.tools.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class Sqlite_ConnectionHelper extends SQLiteOpenHelper {
    private static Sqlite_ConnectionHelper instance;
    public static Pair<Long, String>[] mCategories = new Pair[] {
            new Pair<Long, String>(0L, "Cars/Insurance"),                   new Pair<Long, String>(1L, "Cars/Shop/Maintenance"),
            new Pair<Long, String>(2L, "Cars/Shop/Repairs"),                new Pair<Long, String>(3L, "Cars/Fuel"),
            new Pair<Long, String>(4L, "Home/Mortgage/Regular"),            new Pair<Long, String>(5L, "Home/Mortgage/Additional principal"),
            new Pair<Long, String>(6L, "Home/Mortgage/Taxes"),              new Pair<Long, String>(7L, "Home/Mortgage/Home insurance"),
            new Pair<Long, String>(8L, "Home/Utilities/Cellphone"),         new Pair<Long, String>(9L, "Home/Utilities/City"),
            new Pair<Long, String>(10L, "Home/Utilities/Electricity"),      new Pair<Long, String>(11L, "Home/Utilities/Gas"),
            new Pair<Long, String>(12L, "Home/Utilities/Internet"),         new Pair<Long, String>(13L, "Home/Repairs/Professional"),
            new Pair<Long, String>(14L, "Home/Repairs/Self repairs"),       new Pair<Long, String>(15L, "Home/General/Household"),
            new Pair<Long, String>(16L, "Home/General/Office"),             new Pair<Long, String>(17L, "Home/General/Yard"),
            new Pair<Long, String>(18L, "Charities/Church/Fast Offerings"), new Pair<Long, String>(19L, "Charities/Church/Tithing"),
            new Pair<Long, String>(20L, "Consumables/Food"),                new Pair<Long, String>(21L, "Consumables/Non-Food"),
            new Pair<Long, String>(22L, "Personal/Insurance/Dental"),       new Pair<Long, String>(23L, "Personal/Insurance/Medical"),
            new Pair<Long, String>(24L, "Personal/Insurance/Prescriptions"),new Pair<Long, String>(25L, "Personal/Insurance/Copay"),
            new Pair<Long, String>(26L, "Personal/Insurance/Vision"),       new Pair<Long, String>(27L, "Personal/Health/General"),
            new Pair<Long, String>(28L, "Provisions/Education"),            new Pair<Long, String>(29L, "Provisions/Mad Money"),
            new Pair<Long, String>(30L, "Provisions/Gifts/Family"),         new Pair<Long, String>(31L, "Provisions/Gifts/Other"),
            new Pair<Long, String>(32L, "Subscription Services/Entertainment/Amazon channels"),
            new Pair<Long, String>(33L, "Subscription Services/Entertainment/Rentals"),
            new Pair<Long, String>(34L, "Services/USCCA"),                  new Pair<Long, String>(35L, "Services/Other"),
            new Pair<Long, String>(36L, "Services/Amazon Prime"),           new Pair<Long, String>(37L, "Services/Audible"),
            new Pair<Long, String>(38L, "Services/Accountants"),            new Pair<Long, String>(39L, "Services/Costco")
    };
    public static Pair<Long, String>[] mPeriodicities = new Pair[] {
            new Pair<Long, String>(52L, "Weekly (52/yr)"),      new Pair<Long, String>(26L, "Biweekly (26/yr)"),
            new Pair<Long, String>(24L, "Semimonthly (24/yr)"), new Pair<Long, String>(12L, "Monthly (12/yr)"),
            new Pair<Long, String>(4L, "Quarterly (4/yr)"),     new Pair<Long, String>(3L, "Triannually (3/yr)"),
            new Pair<Long, String>(2L, "Semiannually (2/yr)"),  new Pair<Long, String>(1L, "Annually (1/yr)")
    };
    public static Pair<Long, String>[] mLegalTenders = new Pair[] {
            new Pair<Long, String>(0L, "CA-cash"),              new Pair<Long, String>(1L, "CC-Credit card"),
            new Pair<Long, String>(2L, "CK-checking"),          new Pair<Long, String>(3L, "DB-disbursement"),
            new Pair<Long, String>(4L, "DC-debit card"),        new Pair<Long, String>(5L, "FA-Flexible Spending Account"),
            new Pair<Long, String>(6L, "GC-gift card"),         new Pair<Long, String>(7L, "PP-Paypal"),
            new Pair<Long, String>(8L, "RE-reimbursement"),     new Pair<Long, String>(9L, "TR-transfer"),
            new Pair<Long, String>(10L, "VM-Venmo")
    };

/** public Sqlite_ConnectionHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version)
 *
 * @param context
 * @param name
 * @param factory
 * @param version
 */
    public Sqlite_ConnectionHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        instance = this;
    }

/** public static Sqlite_ConnectionHelper getInstance()
 * Get singleton instance
 * @return Sqlite_ConnectionHelper
 */
    public static Sqlite_ConnectionHelper getInstance() {
        if ( instance == null ) {
            instance = new Sqlite_ConnectionHelper(MainActivity.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
        }
        return instance;
    }

/** void onCreate(SQLiteDatabase db)
 * Do any initialization upon first creating new database.
 * @param db: #SQLiteDatabase# The SQLite database handle.
 */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //--- User data
        db.execSQL(BUDGET__DEFINE_TABLE);
        db.execSQL(BUDGET_TIME_BRACKET__DEFINE_TABLE);
        db.execSQL(EXPENSE__DEFINE_TABLE);
        db.execSQL(EXPENSE_GROUP__DEFINE_TABLE);

        //--- Default labels
//        createCategoriesTable(db);
//        createLegalTendersTable(db);
//        createPeriodicitiesTable(db);
    }

/** void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
 * Do any revisions on the database as needed.
 * @param db: #SQLiteDatabase# The SQLite database handle.
 * @param oldVersion: #int# The old database version.
 * @param newVersion: #int# The new database version.
 */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //--- Purge database
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        if ( cursor.moveToFirst() ) {
            String table_name;
            do {
                table_name = cursor.getString(0);
                if ( !(table_name.equals("android_metadata")  ||  table_name.equals("sqlite_sequence")) ) {
                    db.rawQuery("DROP TABLE " + table_name, null);
                }
            } while ( cursor.moveToNext() );
        }
        cursor.close();

        //--- Create tables again
        db.close();
        db = getWritableDatabase();
        onCreate(db);
    }

/** long getSpinnerKey(Pair<Long, String>[] list, String periodicity)
 * Convert the text into the database key by searching 'list'.
 * @param list:        #Pair<Long, String>[]# The list of Spinner text and database IDs.
 * @param periodicity: #String# The text to search.
 * @return result:     #long# The database ID.
 */
    private static long findSpinnerKey(Pair<Long, String>[] list, String periodicity) {
        return Arrays.stream(list).filter((pair) -> pair.getValue().equals(periodicity)).findFirst().map(Pair::getKey).orElse(-1L);
    }
    public static long getCategorySpinnerKey(String category)       { return findSpinnerKey(mCategories, category); }
    public static long getPeriodicitySpinnerKey(String periodicity) { return findSpinnerKey(mPeriodicities, periodicity); }
    public static long getLegalTenderSpinnerKey(String periodicity) { return findSpinnerKey(mLegalTenders, periodicity); }

/** String getSpinnerText(Pair<Long, String>[] list, long period)
 * Convert database ID into spinner-ready text.
 * @param list:    #Pair <Long, String>[]# The list of Spinner text and database IDs.
 * @param period:  #String# The database ID to find.
 * @return result: #String# The Spinner-ready text.
 */
    private static String findSpinnerText(Pair<Long, String>[] list, Long period) {
        return Arrays.stream(list).filter((pair) -> pair.getKey().equals(period)).findFirst().map(Pair::getValue).orElse("");
//        String result = "";
//        for ( Pair<Long, String> pair : list ) {
//            if ( pair.getKey() == period ) {
//                result = pair.getValue();
//                break;
//            }
//        }
//        return result;
    }

    /**
     **   Shorthand methods using generics
     **/
    public static String findCategorySpinnerText(long id)           { return findSpinnerText(mCategories, id);     }
    public static String findPeriodicitySpinnerText(long id)        { return findSpinnerText(mPeriodicities, id);  }
    public static String findLegalTenderSpinnerText(long id)        { return findSpinnerText(mPeriodicities, id);  }
    public static long findCategorySpinnerID(String name)           { return findSpinnerKey(mCategories, name);    }
    public static long findPeriodicitySpinnerID(String name)        { return findSpinnerKey(mPeriodicities, name); }
    public static long findLegalTenderSpinnerText(String name)      { return findSpinnerKey(mPeriodicities, name); }

//***************************************************************** CONSTANTS TABLES *****************************************************************
//----------------------------------------------------------------- Categories Table -----------------------------------------------------------------

//    /** private void createLabelsTable(SQLiteDatabase db, String define_table_sql, Pair<Long, String>[] pairs, String table_name, String key_name, String value_name)
//     *
//     * @param db
//     * @param define_table_sql
//     * @param pairs
//     * @param table_name
//     * @param key_name
//     * @param value_name
//     */
//    private void createLabelsTable(SQLiteDatabase db, String define_table_sql, Pair<Long, String>[] pairs, String table_name, String key_name, String value_name) {
//        db.execSQL(define_table_sql);
//        for ( Pair<Long, String> category : pairs) {
//            ContentValues columns = new ContentValues();
//            columns.put(key_name, category.getKey());
//            columns.put(value_name, category.getValue());
//            category.setKey(db.insert(table_name, null, columns)); // <-- this is temporatory, i.e., not stored in DB
//        }
//    }

//    /** public ArrayList<CharSequence> queryLabelsTable(String query_string, String key_name, String value_name)
//     *
//     * @param query_string
//     * @param key_name
//     * @param value_name
//     * @return #ArrayList<CharSequence>#
//     */
//    public ArrayList<CharSequence> queryLabelsTable(String query_string, String key_name, String value_name) {
//        ArrayList<CharSequence> labels = new ArrayList<>();
//        SQLiteDatabase db = getReadableDatabase();
//
//        //--- Open Periodicity table
//        Cursor cursor = db.rawQuery(query_string, null);
//        if (cursor.moveToFirst()) {
//
//            //--- Translate name to column number (a really good idea!)
//            int key_index = cursor.getColumnIndex(key_name);
//            int value_index = cursor.getColumnIndex(value_name);
//
//            //--- iterate through all of the labels.
//            do {
//                labels.add(cursor.getString(value_index));
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//
//        return labels;
//    }

//    /** void createCategoriesTable(SQLiteDatabase db)
//     * Create the "Categories" string table.
//     * @param db: #SQLiteDatabase# The SQLite database handle.
//     */
//    private void createCategoriesTable(SQLiteDatabase db) {
//        createLabelsTable(db, CATEGORIES__DEFINE_TABLE, mCategories, CATEGORIES__TABLE_NAME, CATEGORIES__ID, CATEGORIES__NAME);
//    }

    public ArrayList<CharSequence> toStrings(Pair[] pairs) {
        ArrayList<CharSequence> result = new ArrayList<>();
        for ( Pair<Long, String> pair : pairs ) {
            result.add(pair.getValue());
        }
        return result;
    }
    /** ArrayList<CharSequence> queryCategories()
     * Retrieve all categories from the database.
     * @return categories #ArrayList<CharSequence>#
     */
    public ArrayList<CharSequence> queryCategories() {
//        return queryLabelsTable(CATEGORIES__QUERY_TABLE, CATEGORIES__ID, CATEGORIES__NAME);
        return toStrings(mCategories);
    }

//    /** void replaceCategories(ArrayList<CharSequence> categories)
//     * The user can modify the categories, so that's why they are stored in the database.
//     * @param categories: #ArrayList<CharSequence># - The new set of customized categories.
//     *
//     * FIXME: We cannot replace the table without fixing all of the references to it.
//     */
//    public void replaceCategories(ArrayList<CharSequence> categories) {
//        SQLiteDatabase db = getWritableDatabase();
//        db.execSQL(CATEGORIES__DROP_TABLE);//"drop table if exists " + CATEGORIES__TABLE_NAME);
//        Pair<Long, String>[] pairs = new Pair[categories.size()];
//        long index = 0;
//        for ( CharSequence category : categories ) {
//            pairs[(int)index] = new Pair<Long, String>(index++, category.toString());
//        }
//        mCategories = pairs;
//        createCategoriesTable(db);
//        db.close();
//    }

//---------------------------------------------------------------- Periodicities Table ---------------------------------------------------------------
    /** void createPeriodicitiesTable(SQLiteDatabase db)
     * Create the "Periodicities" string table.
     * @param db: #SQLiteDatabase# The SQLite database handle.
     */
//    private void createPeriodicitiesTable(SQLiteDatabase db) {
//        createLabelsTable(db, PERIODICITY_LABELS__DEFINE_TABLE, mPeriodicities, PERIODICITY_LABELS__TABLE_NAME, PERIODICITY_LABELS__ID, PERIODICITY_LABELS__NAME);
//    }

    /** ArrayList<CharSequence> queryPeriodicities()
     * Get the periodicities from the database.
     * @return periods - #ArrayList<CharSequence># - Periodicities.
     */
    public ArrayList<CharSequence> queryPeriodicities() {
//        return queryLabelsTable(PERIODICITY_LABELS__QUERY_TABLE, PERIODICITY_LABELS__ID, PERIODICITY_LABELS__NAME);
        return toStrings(mPeriodicities);
    }

    /** void replacePeriodicities(ArrayList<CharSequence> periodicities)
     * Replace periodicities
     * @param periodicities - #ArrayList<CharSequence>#
     *
     * FIXME: We cannot replace the table without fixing all of the references to it.
     */
//    public void replacePeriodicities(ArrayList<CharSequence> periodicities) {
//        SQLiteDatabase db = getWritableDatabase();
//        db.execSQL(PERIODICITY_LABELS__DROP_TABLE);
//        Pair<Long, String>[] pairs = new Pair[periodicities.size()];
//        int index = 0;
//        for ( CharSequence periodicity : periodicities ) {
//            String periodicity_text = periodicity.toString();
//            long frequency = Long.parseLong(periodicity_text.replaceAll("[^0-9]*", ""));
//            pairs[index] = new Pair<Long, String>(frequency, periodicity_text);
//        }
//        mPeriodicities = pairs;
//        createPeriodicitiesTable(db);
//        db.close();
//    }

//--------------------------------------------------------------------- Tenders Table ----------------------------------------------------------------
    /** void createTendersTable(SQLiteDatabase db)
     * Create the "Tenders" (or Legal Tenders) string table.
     * @param db: #SQLiteDatabase# The SQLite database handle.
     */
//    private void createLegalTendersTable(SQLiteDatabase db) {
//        createLabelsTable(db, LEGAL_TENDER_LABELS__DEFINE_TABLE, mLegalTenders, LEGAL_TENDER_LABELS__TABLE_NAME, LEGAL_TENDER_LABELS__ID, LEGAL_TENDER_LABELS__NAME);        db.execSQL(LEGAL_TENDER_LABELS__DEFINE_TABLE);
//    }

    /** ArrayList<CharSequence> queryTenders()
     * Get the "tenders" (the methods of payment) strings.
     * @return tenders: #ArrayList<CharSequence>#
     */
    public ArrayList<CharSequence> queryLegalTenders() {
//        return queryLabelsTable(LEGAL_TENDER_LABELS__QUERY_TABLE, LEGAL_TENDER_LABELS__ID, LEGAL_TENDER_LABELS__NAME);
        return toStrings(mLegalTenders);
    }

    /** void replaceTenders(ArrayList<CharSequence> tenders)
     * Replace (legal) tenders
     * @param tenders: #ArrayList<CharSequence>#
     */
//    public void replaceLegalTenders(ArrayList<CharSequence> tenders) {
//        SQLiteDatabase db = getWritableDatabase();
//        db.execSQL(LEGAL_TENDER_LABELS__DROP_TABLE);
//        Pair<Long, String>[] pairs = new Pair[tenders.size()];
//        int index = 0;
//        for ( CharSequence tender : tenders ) {
//            pairs[index++] = new Pair<Long, String>(0L, tender.toString());
//        }
//        mLegalTenders = pairs;
//        createLegalTendersTable(db);
//        db.close();
//    }

//********************************************************************* USER DATA ********************************************************************
//--- Budget Record -----------------------------------------------------------------------------------------------------------------------------
    /** ArrayList<Budget> getBudgetRecords()
     * Retrieve all budget records. These include "time bracketed" items which encapsulate changes in expected expenses.
     *
     * Basic Algorithm:
     *      Get the Budget table's field offsets
     *      Loop on Budget table
     *          Get the Budget record's primary key
     *          Loop on BudgetTimeBracket table
     *              Get BudgetTimeBracket table's field offsets
     *              Collect TimeBucket using Budget record's primary key
     *              Store
     *          End
     *          Get rest of Budget record
     *          Store
     *      End
     *      Return collection
     *
     * @return budget_records: #ArrayList<Budget>#
     */
    public ArrayList<Budget> queryBudgetRecords() {
        ArrayList<Budget> budget_records = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

    //--- Open Budget table
        Cursor budget_cursor = db.rawQuery(BUDGET__QUERY_TABLE, null);
        if ( budget_cursor.moveToFirst() ) {

        //--- Translate name to column number (a really good idea!)
            int id_col_index = budget_cursor.getColumnIndex(BUDGET__ID);

        //--- iterate through all budget records
            do {
            //--- Get the budget record ID to get time bracket_array
                ArrayList<Budget_TimeBracket> bracket_array = new ArrayList<>();
                long id = budget_cursor.getLong(id_col_index);

            //--- If there are time bracket_array (there SHOULD be at least one)...
                Cursor bracket_cursor = db.rawQuery(BUDGET_TIME_BRACKET__QUERY_TABLE, new String[] { String.valueOf(id) });
                if ( bracket_cursor.moveToFirst() ) {
                //--- ... iterate.
                    do {
                        bracket_array.add(new Budget_TimeBracket(bracket_cursor));
                    } while ( bracket_cursor.moveToNext() );
                }
                bracket_cursor.close();

            //--- Add to list.
                budget_records.add(new Budget(budget_cursor, bracket_array));
            } while ( budget_cursor.moveToNext() );
        }
        budget_cursor.close();

    //--- Clean up
        db.close();
        return budget_records;
    }

    /** long insertBudgetRecord(Budget budget)
     * Add a new budget budget to the database.
     * @param budget: #Budget#
     * @return row_id: #long# The row ID of the budget budget with the associated "time brackets."
     */
    public long insertBudgetRecord(Budget budget) {
        SQLiteDatabase db = getWritableDatabase();

    //--- First, add budget record, getting ID; then, for all time brackets, add records
        long budget_rec_id = db.insert(BUDGET__TABLE_NAME, null, Budget.fillDatabaseRecord(budget)); // <-- Grab record ID for time brackets
        for ( Budget_TimeBracket bracket: budget.getTimeBrackets() ) {
            long id = db.insert(BUDGET_TIME_BRACKET__TABLE_NAME, null, Budget_TimeBracket.fillDatabaseRecord(bracket, budget_rec_id));
        }

    //--- Clean up
        db.close();
        return budget_rec_id;
    }

    /** void deleteBudgetRecord(long budget_id)
     * Delete a budget record along with all "time brackets."
     * @param budget_id: #long# The database ID to delete.
     */
    public void deleteBudgetRecord(long budget_id) {
        SQLiteDatabase db = getWritableDatabase();

    //--- Delete the time brackets first...
        String where_clause = BUDGET_TIME_BRACKET__BUDGET_FK + "=?";
        String[] where_args = new String[] { String.valueOf(budget_id) };
        db.delete(BUDGET_TIME_BRACKET__TABLE_NAME, where_clause, where_args);

    //--- ... then the budget.
        where_clause = BUDGET__ID + "=?";
        db.delete(BUDGET__TABLE_NAME, where_clause, where_args);
        db.close();
    }

    /** public void updateBudgetRecord(Budget budget)
     *
     * @param budget
     */
    public void updateBudgetRecord(Budget budget) {
//TODO
    }

//--- ExpenseLog Record ----------------------------------------------------------------------------------------------------------------------------
    /** ArrayList<ExpenseLog> queryExpenseRecords()
     * Get expense records. I imagine that this can ultimately be very huge. That's a problem.
     *
     * Basic Algorithm:
     *      Get the ExpenseLog table's field offsets
     *      Loop on ExpenseLog table
     *          Get the ExpenseLog record's primary key
     *          Loop on ExpenseLogGroup table
     *              Get ExpenseLogGroup table's field offsets
     *              Collect ExpenseLogGroup using Budget record's primary key
     *              Store
     *          End
     *          Get rest of ExpenseLog record
     *          Store
     *      End
     *      Return collection
     *
     * @return results: #ArrayList<ExpenseLog>#
     */
    public ArrayList<Expense> queryExpenseRecords() {
        ArrayList<Expense> expense_records = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor expense_log_cursor = db.rawQuery(EXPENSE__QUERY_TABLE, null);
        if ( expense_log_cursor.moveToFirst() ) {

        //--- iterate through all expense log records
            int id_col_index = expense_log_cursor.getColumnIndex(EXPENSE__ID);  //<-- Translate name to column number (a really good idea!)
            do {
            //--- If there are time brackets (there SHOULD be), iterate
                ArrayList<Expense_Group> group_array = new ArrayList<>();
                long expense_id = expense_log_cursor.getLong(id_col_index);  // <-- Get the expense log record ID to get time brackets
                Cursor group_cursor = db.rawQuery(EXPENSE_GROUP__QUERY_TABLE, new String[] { String.valueOf(expense_id) });
                if ( group_cursor.moveToFirst() ) {
                    do {
                        group_array.add(new Expense_Group(group_cursor));
                    } while ( group_cursor.moveToNext() );
                }

            //--- Close cursor and add record to list.
                group_cursor.close();
                expense_records.add(new Expense(expense_log_cursor, group_array));

            } while ( expense_log_cursor.moveToNext() );
        }
        expense_log_cursor.close();

    //--- Clean up
        db.close();
        return expense_records;
    }

    /** long insertExpenseLogRecord(ExpenseLog expense)
     * Add a new expense record with all grouped expense categories.
     * @param expense: #ExpenseLog# - The record to add.
     * @return rec_id: #long# - The database ID for the new record.
     */
    public long insertExpenseLogRecord(Expense expense) {
        long rec_id = -1;
        SQLiteDatabase db = getWritableDatabase();

    //--- Add Expense record
        ContentValues values = Expense.fillDatabaseRecord(expense);
        rec_id = db.insert(EXPENSE_GROUP__TABLE_NAME, null, values); // <-- Grab record ID for time brackets

    //--- For all group items, add records
        for (Expense_Group group : expense.getGroup()) {
            db.insert(EXPENSE_GROUP__TABLE_NAME, null, Expense_Group.fillDatabaseRecord(group, rec_id));
        }

    //--- Clean up
        db.close();
        return rec_id;
    }

    /** void deleteExpenseLogRecord(long expense_id)
     * Delete an expense record along with the "group" of categories.
     * @param expense_id: #long# The database ID to delete.
     */
    public void deleteExpenseLogRecord(long expense_id) {
        //--- Delete the time groups first...
        String where_clause = EXPENSE_GROUP__ID + "=?";
        String[] where_args = new String[] { String.valueOf(expense_id) };
        SQLiteDatabase db = getWritableDatabase();
        db.delete(EXPENSE_GROUP__TABLE_NAME, where_clause, where_args);

        //--- ... then the expense log.
        where_clause = EXPENSE__ID + "=?";
        db.delete(EXPENSE__TABLE_NAME, where_clause, where_args);
        db.close();
    }

    /** public void updateExpenseLogRecord(Expense item)
     *
     * @param item
     */
    public void updateExpenseLogRecord(Expense item) {
//TODO
    }
}
