package com.waltoncraftsllc.waterfrontcashflow.tools;

import static com.waltoncraftsllc.waterfrontcashflow.adapters.Sqlite_ConnectionHelper.getSpinnerKey;
import static com.waltoncraftsllc.waterfrontcashflow.adapters.Sqlite_ConnectionHelper.getSpinnerText;
import static com.waltoncraftsllc.waterfrontcashflow.adapters.Sqlite_ConnectionHelper.mPeriodicities;
import static com.waltoncraftsllc.waterfrontcashflow.adapters.Sqlite_ConnectionHelper.mTenders;
import static com.waltoncraftsllc.waterfrontcashflow.tools.DatabaseContract.EXPENSE_LOG__DATE;
import static com.waltoncraftsllc.waterfrontcashflow.tools.DatabaseContract.EXPENSE_LOG__ID;
import static com.waltoncraftsllc.waterfrontcashflow.tools.DatabaseContract.EXPENSE_LOG__ON_SERVER;
import static com.waltoncraftsllc.waterfrontcashflow.tools.DatabaseContract.EXPENSE_LOG__RECEIPT;
import static com.waltoncraftsllc.waterfrontcashflow.tools.DatabaseContract.EXPENSE_LOG__RECURRING_FK;
import static com.waltoncraftsllc.waterfrontcashflow.tools.DatabaseContract.EXPENSE_LOG__TENDER_FK;
import static com.waltoncraftsllc.waterfrontcashflow.tools.DatabaseContract.EXPENSE_LOG__VENDOR;

import android.content.ContentValues;
import android.database.Cursor;

import java.sql.Date;
import java.util.ArrayList;

public class ExpenseLog {
    long mID;
    Date mDate;
    String mTenderString;
    String mVendor;
    ArrayList<ExpenseLog_Group> mGroup;
    String mRecurring;
    boolean mReceipt;
    boolean mServer;
    boolean mIsRecurring; // <-- generated

    public ExpenseLog(String date, String tender, String vendor, ArrayList<ExpenseLog_Group> group, String recurring, String receipt, String server) {
        this.mDate = Date.valueOf(date);
        this.mTenderString = tender;
        this.mVendor = vendor;
        this.mGroup = group;
        this.mRecurring = recurring;
        this.mReceipt = (receipt.equals("✔"));
        this.mServer = (server.equals("✔"));
    }

    public ExpenseLog(Cursor expense_log_cursor, ArrayList<ExpenseLog_Group> group) {
    //--- Translate name to column number
        int id_col_index = expense_log_cursor.getColumnIndex(EXPENSE_LOG__ID);
        int date_col_index = expense_log_cursor.getColumnIndex(EXPENSE_LOG__DATE);
        int tender_col_index = expense_log_cursor.getColumnIndex(EXPENSE_LOG__TENDER_FK);
        int vendor_col_index = expense_log_cursor.getColumnIndex(EXPENSE_LOG__VENDOR);
        int recurring_col_index = expense_log_cursor.getColumnIndex(EXPENSE_LOG__RECURRING_FK);
        int receipt_col_index = expense_log_cursor.getColumnIndex(EXPENSE_LOG__RECEIPT);
        int server_col_index = expense_log_cursor.getColumnIndex(EXPENSE_LOG__ON_SERVER);
        mID = expense_log_cursor.getLong(id_col_index);
        mDate = Date.valueOf(expense_log_cursor.getString(date_col_index));
        mTenderString = getSpinnerText(mTenders, expense_log_cursor.getLong(tender_col_index));
        mVendor = expense_log_cursor.getString(vendor_col_index);
        mGroup = group;
        mRecurring = getSpinnerText(mPeriodicities, expense_log_cursor.getLong(recurring_col_index));
        mReceipt = (expense_log_cursor.getString(receipt_col_index).equals("✔"));
        mServer = (expense_log_cursor.getString(server_col_index).equals("✔"));
    }

    public static ContentValues fillDatabaseRecord(ExpenseLog item) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.EXPENSE_LOG__DATE, item.dateToString());
        values.put(DatabaseContract.EXPENSE_LOG__TENDER_FK, item.getTenderString());
        values.put(EXPENSE_LOG__VENDOR, item.getVendor());
    //NOTE: no group here required b/c ExpenseLog_Group points to this.
        values.put(EXPENSE_LOG__RECURRING_FK, getSpinnerKey(mPeriodicities, item.mRecurring));
        values.put(EXPENSE_LOG__RECEIPT, (item.mReceipt? "✔": ""));
        values.put(EXPENSE_LOG__ON_SERVER, (item.mServer? "✔": ""));
        return values;
    }
//    public void setID(long id) { mID = id; }
    public String dateToString() {
        return Budget.sqlDateToString(mDate);
    }
    public String getTenderString() {
        return mTenderString;
    }
    public String getVendor() {
        return mVendor;
    }
    public ArrayList<ExpenseLog_Group> getGroup() {
        return mGroup;
    }
    public String getRecurring() {
        return mRecurring;
    }
    public boolean hasReceipt() {
        return mReceipt;
    }
    public boolean hasServer() {
        return mServer;
    }
    public boolean isRecurring() {
        return mIsRecurring;
    }
}
