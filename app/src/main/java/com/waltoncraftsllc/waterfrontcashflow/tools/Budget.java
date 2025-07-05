package com.waltoncraftsllc.waterfrontcashflow.tools;

import static com.waltoncraftsllc.waterfrontcashflow.tools.DatabaseContract.BUDGET__AMOUNT_CAP;
import static com.waltoncraftsllc.waterfrontcashflow.tools.DatabaseContract.BUDGET__DUE_DATE;
import static com.waltoncraftsllc.waterfrontcashflow.tools.DatabaseContract.BUDGET__ID;
import static com.waltoncraftsllc.waterfrontcashflow.tools.DatabaseContract.BUDGET__NAME;

import android.content.ContentValues;
import android.database.Cursor;

import androidx.annotation.NonNull;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Budget {
    private static final String DATE_FORMAT = "MM/dd";
    long mID;
    String mName;
    ArrayList<Budget_TimeBracket> mTimeBrackets;
    Date mDueDate = null;//String mDueDate = null;
    Money mAmountCap = null;

    public Budget(@NonNull String name, @NonNull ArrayList<Budget_TimeBracket> brackets, @NonNull String due_date, Money amount_cap) {
        this.mName = name;
        this.mTimeBrackets = brackets;
        this.mDueDate = Date.valueOf(due_date);
        this.mAmountCap = amount_cap;
    }
    public Budget(String mName, @NonNull ArrayList<Budget_TimeBracket> brackets) {
        this.mName = mName;
        this.mTimeBrackets = brackets;
    }
    public Budget(Cursor budget_cursor, ArrayList<Budget_TimeBracket> brackets) {
        int id_col_index = budget_cursor.getColumnIndex(BUDGET__ID);
        int name_col_index = budget_cursor.getColumnIndex(BUDGET__NAME);
        int due_date_col_index = budget_cursor.getColumnIndex(BUDGET__DUE_DATE);
        int amount_cap_col_index = budget_cursor.getColumnIndex(BUDGET__AMOUNT_CAP);
        mID = budget_cursor.getLong(id_col_index);
        mName = budget_cursor.getString(name_col_index);
        mTimeBrackets = brackets;
        mDueDate = Date.valueOf(budget_cursor.getString(due_date_col_index));
        mAmountCap = new Money(budget_cursor.getString(amount_cap_col_index));
    }

    public static ContentValues fillDatabaseRecord(Budget item) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.BUDGET__NAME, item.getName());
        values.put(DatabaseContract.BUDGET__DUE_DATE, item.getDueDate());
        values.put(DatabaseContract.BUDGET__AMOUNT_CAP, item.getAmountCap());
        return values;
    }

    public static String sqlDateToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        return simpleDateFormat.format(date);
    }

    public boolean hasDueDate() { return (mDueDate != null); }
    public boolean hasAmountCap() { return (mAmountCap != null); }
    public String dueDateToString() { return sqlDateToString(mDueDate); }
    public String getDueDate() { return sqlDateToString(mDueDate); }
    public String getAmountCap() {
        return mAmountCap.toString();
    }
    public String getName() { return mName; }
    public void setName(String mName) { this.mName = mName; }
    public ArrayList<Budget_TimeBracket> getTimeBrackets() {
        return mTimeBrackets;
    }
}
