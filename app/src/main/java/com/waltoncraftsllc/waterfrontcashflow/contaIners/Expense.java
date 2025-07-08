package com.waltoncraftsllc.waterfrontcashflow.contaIners;

import static com.waltoncraftsllc.waterfrontcashflow.database.Sqlite_ConnectionHelper.getLegalTenderSpinnerText;
import static com.waltoncraftsllc.waterfrontcashflow.database.Sqlite_ConnectionHelper.getPeriodicitySpinnerKey;
import static com.waltoncraftsllc.waterfrontcashflow.database.Sqlite_ConnectionHelper.getPeriodicitySpinnerText;
import static com.waltoncraftsllc.waterfrontcashflow.database.DatabaseContract.ANDROID_UI_DATE_PATTERN;
import static com.waltoncraftsllc.waterfrontcashflow.database.DatabaseContract.CHECK_MARK;
import static com.waltoncraftsllc.waterfrontcashflow.database.DatabaseContract.EXPENSE__DATE;
import static com.waltoncraftsllc.waterfrontcashflow.database.DatabaseContract.EXPENSE__ID;
import static com.waltoncraftsllc.waterfrontcashflow.database.DatabaseContract.EXPENSE__ON_SERVER;
import static com.waltoncraftsllc.waterfrontcashflow.database.DatabaseContract.EXPENSE__RECEIPT;
import static com.waltoncraftsllc.waterfrontcashflow.database.DatabaseContract.EXPENSE__RECURRING_FK;
import static com.waltoncraftsllc.waterfrontcashflow.database.DatabaseContract.EXPENSE__TENDER_FK;
import static com.waltoncraftsllc.waterfrontcashflow.database.DatabaseContract.EXPENSE__VENDOR;

import android.content.ContentValues;
import android.database.Cursor;

import com.waltoncraftsllc.waterfrontcashflow.database.DatabaseContract;

import java.sql.Date;
import java.util.ArrayList;

public class Expense {
    long mID;
    Date mDate;
    String mTenderString;
    String mVendor;
    ArrayList<Expense_Group> mGroup;
    String mRecurring;
    boolean mReceipt;
    boolean mServer;
    boolean mIsRecurring; // <-- generated

    public Expense(String date, String tender, String vendor, ArrayList<Expense_Group> group, String recurring, String receipt, String server) {
        this.mDate = Date.valueOf(date);
        this.mTenderString = tender;
        this.mVendor = vendor;
        this.mGroup = group;
        this.mRecurring = recurring;
        this.mReceipt = (receipt.equals(CHECK_MARK));
        this.mServer = (server.equals(CHECK_MARK));
    }

    public Expense(Cursor expense_cursor, ArrayList<Expense_Group> group) {
    //--- Translate name to column number
        int id_col_index = expense_cursor.getColumnIndex(EXPENSE__ID);
        int date_col_index = expense_cursor.getColumnIndex(EXPENSE__DATE);
        int tender_col_index = expense_cursor.getColumnIndex(EXPENSE__TENDER_FK);
        int vendor_col_index = expense_cursor.getColumnIndex(EXPENSE__VENDOR);
        int recurring_col_index = expense_cursor.getColumnIndex(EXPENSE__RECURRING_FK);
        int receipt_col_index = expense_cursor.getColumnIndex(EXPENSE__RECEIPT);
        int server_col_index = expense_cursor.getColumnIndex(EXPENSE__ON_SERVER);
        mID = expense_cursor.getLong(id_col_index);
        mDate = Date.valueOf(expense_cursor.getString(date_col_index));
        mTenderString = getLegalTenderSpinnerText(expense_cursor.getLong(tender_col_index));
        mVendor = expense_cursor.getString(vendor_col_index);
        mGroup = group;
        mRecurring = getPeriodicitySpinnerText(expense_cursor.getLong(recurring_col_index));
        mReceipt = (expense_cursor.getString(receipt_col_index).equals(CHECK_MARK));
        mServer = (expense_cursor.getString(server_col_index).equals(CHECK_MARK));
    }

    public static ContentValues fillDatabaseRecord(Expense item) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.EXPENSE__DATE, DatabaseContract.toString(item.getDate(), ANDROID_UI_DATE_PATTERN));
        values.put(DatabaseContract.EXPENSE__TENDER_FK, item.getTenderString());
        values.put(EXPENSE__VENDOR, item.getVendor());
    //NOTE: no group here required b/c Expense_Group points to this.
        values.put(EXPENSE__RECURRING_FK, getPeriodicitySpinnerKey(item.mRecurring));
        values.put(EXPENSE__RECEIPT, item.mReceipt);
        values.put(EXPENSE__ON_SERVER, item.mServer);
        return values;
    }

    public Date getDate() {
        return mDate;
    }

    public String getTenderString() {
        return mTenderString;
    }
    public String getVendor() {
        return mVendor;
    }
    public ArrayList<Expense_Group> getGroup() {
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
