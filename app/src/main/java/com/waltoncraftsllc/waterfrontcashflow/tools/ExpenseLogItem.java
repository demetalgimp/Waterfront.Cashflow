package com.waltoncraftsllc.waterfrontcashflow.tools;

import java.sql.Date;
import java.util.ArrayList;

public class ExpenseLogItem {
    Date mDate;
    String mTenderString;
    String mVendor;
    ArrayList<ExpenseLogItem_Group> mGroup;
    String mRecurring;
    boolean mReceipt;
    boolean mServer;
    boolean mIsRecurring;

    public ExpenseLogItem(String date, String tender, String vendor, ArrayList<ExpenseLogItem_Group> group, String recurring, String receipt, String server) {
        this.mDate = Date.valueOf(date);
        this.mTenderString = tender;
        this.mVendor = vendor;
        this.mGroup = group;
        this.mRecurring = recurring;
        this.mReceipt = (receipt.equals("✔"));
        this.mServer = (server.equals("✔"));
    }
//    public ExpenseLogItem(String date, String tender, String vendor, ArrayList<ExpenseLogItem_Group> group) {
//        this.mDate = Date.valueOf(date);
//        this.mTenderString = tender;
//        this.mVendor = vendor;
//        this.mGroup = group;
//        this.mRecurring = "";
//        this.mReceipt = false;
//        this.mServer = false;
//    }

    public String dateToString() {
        return BudgetItem.sqlDateToString(mDate);
    }
    public String getTenderString() {
        return mTenderString;
    }
    public String getVendor() {
        return mVendor;
    }
    public ArrayList<ExpenseLogItem_Group> getGroup() {
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
