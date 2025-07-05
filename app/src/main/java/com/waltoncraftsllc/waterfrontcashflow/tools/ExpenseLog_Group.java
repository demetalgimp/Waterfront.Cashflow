package com.waltoncraftsllc.waterfrontcashflow.tools;

import static com.waltoncraftsllc.waterfrontcashflow.adapters.Sqlite_ConnectionHelper.getSpinnerKey;
import static com.waltoncraftsllc.waterfrontcashflow.adapters.Sqlite_ConnectionHelper.getSpinnerText;
import static com.waltoncraftsllc.waterfrontcashflow.adapters.Sqlite_ConnectionHelper.mCategories;
import static com.waltoncraftsllc.waterfrontcashflow.adapters.Sqlite_ConnectionHelper.mPeriodicities;
import static com.waltoncraftsllc.waterfrontcashflow.tools.DatabaseContract.BUDGET_TIME_BRACKET__PERIODICITY_FK;
import static com.waltoncraftsllc.waterfrontcashflow.tools.DatabaseContract.EXPENSE_LOG_GROUP__CATEGORY_FK;
import static com.waltoncraftsllc.waterfrontcashflow.tools.DatabaseContract.EXPENSE_LOG_GROUP__DEBIT;
import static com.waltoncraftsllc.waterfrontcashflow.tools.DatabaseContract.EXPENSE_LOG_GROUP__EXPENSE_LOG__FK;
import static com.waltoncraftsllc.waterfrontcashflow.tools.DatabaseContract.EXPENSE_LOG_GROUP__FOR_WHOM;
import static com.waltoncraftsllc.waterfrontcashflow.tools.DatabaseContract.EXPENSE_LOG_GROUP__TAX;

import android.content.ContentValues;
import android.database.Cursor;

public class ExpenseLog_Group {
    long mExpenseLog_ID;
    String mCategory;
    Money mDebit;
    Money mTax;
    String mForWhom;

    public ExpenseLog_Group(String category, Money debit, Money tax, String for_whom) {
        this.mCategory = category;
        this.mDebit = debit;
        this.mTax = tax;
        this.mForWhom = for_whom;
    }

//--- The class is best suited to manage database records
    public ExpenseLog_Group(Cursor group_cursor) {
        int expense_log_id = group_cursor.getColumnIndex(EXPENSE_LOG_GROUP__EXPENSE_LOG__FK);
        int category_col_index = group_cursor.getColumnIndex(EXPENSE_LOG_GROUP__CATEGORY_FK);
        int debit_col_index = group_cursor.getColumnIndex(EXPENSE_LOG_GROUP__DEBIT);
        int tax_col_index = group_cursor.getColumnIndex(EXPENSE_LOG_GROUP__TAX);
        int for_whom_col_index = group_cursor.getColumnIndex(EXPENSE_LOG_GROUP__FOR_WHOM);
        mExpenseLog_ID =group_cursor.getLong(expense_log_id);
        mCategory = getSpinnerText(mCategories, group_cursor.getLong(category_col_index));
        mDebit = new Money(group_cursor.getString(debit_col_index));
        mTax = new Money(group_cursor.getString(tax_col_index));
        mForWhom = group_cursor.getString(for_whom_col_index);
    }

//--- The class is best suited to manage database records
    public static ContentValues fillDatabaseRecord(ExpenseLog_Group group, long expense_log_id) {
        ContentValues values = new ContentValues();
        values.put(EXPENSE_LOG_GROUP__EXPENSE_LOG__FK, expense_log_id);
        values.put(EXPENSE_LOG_GROUP__CATEGORY_FK, getSpinnerKey(mCategories, group.getCategory()));
        values.put(EXPENSE_LOG_GROUP__DEBIT, group.getDebit());
        values.put(EXPENSE_LOG_GROUP__TAX, group.getTax());
        values.put(EXPENSE_LOG_GROUP__FOR_WHOM, group.getForWhom());
        values.put(BUDGET_TIME_BRACKET__PERIODICITY_FK, getSpinnerKey(mPeriodicities, group.getCategory()));
        return values;
    }

    public String getCategory() {
        return mCategory;
    }
    public String getDebit() {
        return mDebit.toString();
    }
    public String getTax() {
        return mTax.toString();
    }
    public String getForWhom() {
        return mForWhom;
    }
}
