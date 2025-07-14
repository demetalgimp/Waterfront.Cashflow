package com.waltoncraftsllc.waterfrontcashflow.containers;

import static com.waltoncraftsllc.waterfrontcashflow.database.Sqlite_ConnectionHelper.getCategorySpinnerKey;
import static com.waltoncraftsllc.waterfrontcashflow.database.Sqlite_ConnectionHelper.findCategorySpinnerText;
import static com.waltoncraftsllc.waterfrontcashflow.database.Sqlite_ConnectionHelper.getPeriodicitySpinnerKey;
import static com.waltoncraftsllc.waterfrontcashflow.database.DatabaseContract.BUDGET_TIME_BRACKET__PERIODICITY_FK;
import static com.waltoncraftsllc.waterfrontcashflow.database.DatabaseContract.EXPENSE_GROUP__CATEGORY_FK;
import static com.waltoncraftsllc.waterfrontcashflow.database.DatabaseContract.EXPENSE_GROUP__DEBIT;
import static com.waltoncraftsllc.waterfrontcashflow.database.DatabaseContract.EXPENSE_GROUP__EXPENSE__FK;
import static com.waltoncraftsllc.waterfrontcashflow.database.DatabaseContract.EXPENSE_GROUP__FOR_WHOM;
import static com.waltoncraftsllc.waterfrontcashflow.database.DatabaseContract.EXPENSE_GROUP__TAX;

import android.content.ContentValues;
import android.database.Cursor;

import com.waltoncraftsllc.waterfrontcashflow.tools.Money;

public class Expense_Group {
    long mExpense_ID;
    String mCategory;
    Money mDebit;
    Money mTax;
    String mForWhom;

    public Expense_Group(String category, Money debit, Money tax, String for_whom) {
        this.mCategory = category;
        this.mDebit = debit;
        this.mTax = tax;
        this.mForWhom = for_whom;
    }

//--- The class is best suited to manage database records
    public Expense_Group(Cursor group_cursor) {
        int expense_id = group_cursor.getColumnIndex(EXPENSE_GROUP__EXPENSE__FK);
        int category_col_index = group_cursor.getColumnIndex(EXPENSE_GROUP__CATEGORY_FK);
        int debit_col_index = group_cursor.getColumnIndex(EXPENSE_GROUP__DEBIT);
        int tax_col_index = group_cursor.getColumnIndex(EXPENSE_GROUP__TAX);
        int for_whom_col_index = group_cursor.getColumnIndex(EXPENSE_GROUP__FOR_WHOM);
        mExpense_ID =group_cursor.getLong(expense_id);
        mCategory = findCategorySpinnerText(group_cursor.getLong(category_col_index));
        mDebit = new Money(group_cursor.getString(debit_col_index));
        mTax = new Money(group_cursor.getString(tax_col_index));
        mForWhom = group_cursor.getString(for_whom_col_index);
    }

//--- The class is best suited to manage database records
    public static ContentValues fillDatabaseRecord(Expense_Group group, long expense_id) {
        ContentValues values = new ContentValues();
        values.put(EXPENSE_GROUP__EXPENSE__FK, expense_id);
        values.put(EXPENSE_GROUP__CATEGORY_FK, getCategorySpinnerKey(group.getCategory()));
        values.put(EXPENSE_GROUP__DEBIT, group.getDebit());
        values.put(EXPENSE_GROUP__TAX, group.getTax());
        values.put(EXPENSE_GROUP__FOR_WHOM, group.getForWhom());
        values.put(BUDGET_TIME_BRACKET__PERIODICITY_FK, getPeriodicitySpinnerKey(group.getCategory()));
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
