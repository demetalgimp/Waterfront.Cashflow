package com.waltoncraftsllc.waterfrontcashflow.containers;

import static com.waltoncraftsllc.waterfrontcashflow.database.DatabaseContract.BUDGET__AMOUNT_CAP;
import static com.waltoncraftsllc.waterfrontcashflow.database.DatabaseContract.BUDGET__DUE_DATE;
import static com.waltoncraftsllc.waterfrontcashflow.database.DatabaseContract.BUDGET__ID;
import static com.waltoncraftsllc.waterfrontcashflow.database.DatabaseContract.BUDGET__NAME;
import static com.waltoncraftsllc.waterfrontcashflow.database.DatabaseContract.DATABASE_DATE_PATTERN;

import android.content.ContentValues;
import android.database.Cursor;

import androidx.annotation.NonNull;

import com.waltoncraftsllc.waterfrontcashflow.database.DatabaseContract;
import com.waltoncraftsllc.waterfrontcashflow.tools.Money;

import java.sql.Date;
import java.util.ArrayList;

public class Budget {
    long mID;
    String mName;
    ArrayList<Budget_TimeBracket> mTimeBrackets;
    Date mDueDate = null;
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
        if ( item.getDueDate() != null ) {
            values.put(DatabaseContract.BUDGET__DUE_DATE, DatabaseContract.toString(item.getDueDate(), DATABASE_DATE_PATTERN));
        }
        if ( item.getAmountCap() != null ) {
            values.put(DatabaseContract.BUDGET__AMOUNT_CAP, item.getAmountCap().toString());
        }
        return values;
    }

    public boolean hasDueDate()          { return (mDueDate != null); }
    public boolean hasAmountCap()        { return (mAmountCap != null); }
    public Date    getDueDate()          { return mDueDate; }
    public Money   getAmountCap()        { return mAmountCap; }
    public String  getName()             { return mName; }
    public void    setName(String mName) { this.mName = mName; }
    public ArrayList<Budget_TimeBracket> getTimeBrackets() { return mTimeBrackets; }
    public void    setID(long budgetId)  { mID = budgetId; }
}
