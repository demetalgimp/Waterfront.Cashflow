package com.waltoncraftsllc.waterfrontcashflow.tools;

import java.sql.Date;
import java.util.ArrayList;

public class ExpenseLog {
    Date mDate;
    String mTenderString;
    String mVendor;
    ArrayList<ExpenseLog_Group> mGroup;
    String mRecurring;
    boolean mReceipt;
    boolean mServer;
    boolean mIsRecurring;

    public ExpenseLog(String date, String tender, String vendor, ArrayList<ExpenseLog_Group> group, String recurring, String receipt, String server) {
        this.mDate = Date.valueOf(date);
        this.mTenderString = tender;
        this.mVendor = vendor;
        this.mGroup = group;
        this.mRecurring = recurring;
        this.mReceipt = (receipt.equals("✔"));
        this.mServer = (server.equals("✔"));
    }

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
