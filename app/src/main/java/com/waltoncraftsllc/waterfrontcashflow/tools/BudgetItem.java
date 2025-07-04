package com.waltoncraftsllc.waterfrontcashflow.tools;

import androidx.annotation.NonNull;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class BudgetItem {
    private static final String DATE_FORMAT = "MM/dd";
    long mID;
    String mName;
    ArrayList<BudgetItem_TimeBracket> mTimeBrackets;
    Date mDueDate = null;//String mDueDate = null;
    Money mAmountCap = null;

    public BudgetItem(@NonNull String name, @NonNull ArrayList<BudgetItem_TimeBracket> brackets, @NonNull String due_date, Money amount_cap) {
        this.mName = name;
        this.mTimeBrackets = brackets;
        this.mDueDate = Date.valueOf(due_date);
        this.mAmountCap = amount_cap;
    }
    public BudgetItem(String mName, @NonNull ArrayList<BudgetItem_TimeBracket> brackets) {
        this.mName = mName;
        this.mTimeBrackets = brackets;
    }
//    public BudgetItem(String mName) {
//        this.mName = mName;
//    }

    public static String sqlDateToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        return simpleDateFormat.format(date);
    }

    public void setID(long id) {
        mID = id;
    }
    public boolean hasDueDate() { return (mDueDate != null); }
    public boolean hasAmountCap() { return (mAmountCap != null); }
    public String dueDateToString() { return sqlDateToString(mDueDate); }
//    public void setDueDate(String due_date) { this.mDueDate = Date.valueOf(due_date); }
    public String getDueDate() { return sqlDateToString(mDueDate); }
    public String getAmountCap() {
        return mAmountCap.toString();
    }
//    public void setAmountCap(Money mAmountCap) {
//        this.mAmountCap = mAmountCap;
//    }
    public String getName() { return mName; }
    public void setName(String mName) { this.mName = mName; }
    public ArrayList<BudgetItem_TimeBracket> getTimeBrackets() {
        return mTimeBrackets;
    }
//    public void setTimeBrackets(ArrayList<BudgetItem_TimeBracket> mTimeBrackets) {
//        this.mTimeBrackets = mTimeBrackets;
//    }
}
