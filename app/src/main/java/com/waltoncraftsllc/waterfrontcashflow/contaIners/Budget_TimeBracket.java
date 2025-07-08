package com.waltoncraftsllc.waterfrontcashflow.contaIners;

import static com.waltoncraftsllc.waterfrontcashflow.database.DatabaseContract.BUDGET_TIME_BRACKET__AMOUNT;
import static com.waltoncraftsllc.waterfrontcashflow.database.DatabaseContract.BUDGET_TIME_BRACKET__BUDGET_FK;
import static com.waltoncraftsllc.waterfrontcashflow.database.DatabaseContract.BUDGET_TIME_BRACKET__FROM_DATE;
import static com.waltoncraftsllc.waterfrontcashflow.database.DatabaseContract.BUDGET_TIME_BRACKET__PERIODICITY_FK;
import static com.waltoncraftsllc.waterfrontcashflow.database.DatabaseContract.BUDGET_TIME_BRACKET__TO_DATE;
import static com.waltoncraftsllc.waterfrontcashflow.database.Sqlite_ConnectionHelper.getPeriodicitySpinnerText;

import android.content.ContentValues;
import android.database.Cursor;

import com.waltoncraftsllc.waterfrontcashflow.database.DatabaseContract;
import com.waltoncraftsllc.waterfrontcashflow.database.Sqlite_ConnectionHelper;
import com.waltoncraftsllc.waterfrontcashflow.tools.Money;

import java.sql.Date;

public class Budget_TimeBracket {
        long mBudget_FK;
        Date mFromDate;
        Date mToDate;
        Money mAmount;
        String mPeriodicity_str;
        long mPeriodicity;

        Money proratedMonthly; // <-- generated
        Money proratedWeekly; // <-- generated

    public Budget_TimeBracket(long budget_fk, String from, String to, Money amount, String periodicity) {
        this.mBudget_FK = budget_fk;
        this.mFromDate = Date.valueOf(from);
        this.mToDate = Date.valueOf(to);
        this.mAmount = amount;
        this.mPeriodicity_str = periodicity;
        this.mPeriodicity = Integer.parseInt(periodicity.replaceAll("[^0-9]*", ""));
        calculateScalers();
    }

    public Budget_TimeBracket(Cursor bracket_cursor) {
        //--- Translate name to column number (I can't do this ahead of time for optimization. Sorry.)
        int budget_fk_col_index = bracket_cursor.getColumnIndex(BUDGET_TIME_BRACKET__BUDGET_FK);
        int from_date_col_index = bracket_cursor.getColumnIndex(BUDGET_TIME_BRACKET__FROM_DATE);
        int to_date_col_index = bracket_cursor.getColumnIndex(BUDGET_TIME_BRACKET__TO_DATE);
        int amount_col_index = bracket_cursor.getColumnIndex(BUDGET_TIME_BRACKET__AMOUNT);
        int periodicity_col_index = bracket_cursor.getColumnIndex(BUDGET_TIME_BRACKET__PERIODICITY_FK);
        mBudget_FK = bracket_cursor.getLong(budget_fk_col_index);
        mFromDate = Date.valueOf(bracket_cursor.getString(from_date_col_index));
        mToDate = Date.valueOf(bracket_cursor.getString(to_date_col_index));
        mAmount = new Money(bracket_cursor.getString(amount_col_index));
        mPeriodicity = bracket_cursor.getLong(periodicity_col_index);  // <-- represents both the primary key and the actual annual frequency.
        mPeriodicity_str = getPeriodicitySpinnerText(mPeriodicity);
        calculateScalers();
    }

    private void calculateScalers() {
        double week_scaler, month_scaler;
        switch ( (int)mPeriodicity ) {
            case 52: week_scaler = 52.0/52; month_scaler = 52.0/12; break;
            case 26: week_scaler = 26.0/52; month_scaler = 26.0/12; break;
            case 24: week_scaler = 13.0/52; month_scaler = 24.0/12; break;
            case 12: week_scaler =  6.5/52; month_scaler = 12.0/12; break;
            case 4:  week_scaler =  4.0/52; month_scaler =  4.0/12; break;
            case 3:  week_scaler =  3.0/52; month_scaler =  3.0/12; break;
            case 2:  week_scaler =  2.0/52; month_scaler =  2.0/12; break;
            case 1:
            default: week_scaler =  1.0/52; month_scaler =  1.0/12; break;
        }
        this.proratedMonthly = mAmount.multiply(month_scaler);
        this.proratedWeekly = mAmount.multiply(week_scaler);
    }

    public static ContentValues fillDatabaseRecord(Budget_TimeBracket bracket, long budget_id) {
        ContentValues values = new ContentValues();
        values.put(BUDGET_TIME_BRACKET__BUDGET_FK, budget_id);
        values.put(BUDGET_TIME_BRACKET__FROM_DATE, DatabaseContract.toString(bracket.mFromDate, "yyyy-MM-dd"));
        values.put(BUDGET_TIME_BRACKET__TO_DATE, DatabaseContract.toString(bracket.mToDate, "yyyy-MM-dd"));
        values.put(BUDGET_TIME_BRACKET__AMOUNT, bracket.getAmount().toString());
        long periodicity = Sqlite_ConnectionHelper.getPeriodicitySpinnerKey(bracket.getPeriodicity_str());
        values.put(BUDGET_TIME_BRACKET__PERIODICITY_FK, periodicity);
        return values;
    }

    public Date getToDate() {
        return mToDate;
    }
    public Date getFromDate() {
        return mToDate;
    }
    public Money getAmount() {
        return mAmount;
    }
    public String getPeriodicity_str() {
        return mPeriodicity_str;
    }
    public String getProratedMonthly() {
        return proratedMonthly.toString() + "/mo";
    }
    public String getProratedWeekly() {
        return proratedWeekly.toString() + "/wk";
    }
}
