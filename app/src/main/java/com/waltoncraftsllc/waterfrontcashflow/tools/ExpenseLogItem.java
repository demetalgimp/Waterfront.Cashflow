package com.waltoncraftsllc.waterfrontcashflow.tools;

import java.sql.Date;
import java.util.ArrayList;

public class ExpenseLogItem {
    Date mDate;
    String mTender;
    String mVendor;
    ArrayList<ExpenseLogItem_Group> mGroup;
    String mRecurring;
    boolean mReceipt;
    boolean mServer;
    boolean mIsRecurring;

    public ExpenseLogItem(String date, String tender, String vendor, ArrayList<ExpenseLogItem_Group> group, String recurring, String receipt, String server) {
        this.mDate = Date.valueOf(date);
        this.mTender = tender;
        this.mVendor = vendor;
        this.mGroup = group;
        this.mRecurring = recurring;
        this.mReceipt = (receipt.equals("✔"));
        this.mServer = (server.equals("✔"));
    }
    public ExpenseLogItem(String date, String tender, String vendor, ArrayList<ExpenseLogItem_Group> group) {
        this.mDate = Date.valueOf(date);
        this.mTender = tender;
        this.mVendor = vendor;
        this.mGroup = group;
        this.mRecurring = "";
        this.mReceipt = false;
        this.mServer = false;
    }

    public String dateToString() {
        return BudgetItem.sqlDateToString(mDate);
    }
    public void setDate(String date) {
        this.mDate = Date.valueOf(date);
    }
    public String getTender() {
        return mTender;
    }
    public void setTender(String mTender) {
        this.mTender = mTender;
    }
    public String getVendor() {
        return mVendor;
    }
    public void setVendor(String mVendor) {
        this.mVendor = mVendor;
    }
    public ArrayList<ExpenseLogItem_Group> getGroup() {
        return mGroup;
    }
    public void setGroup(ArrayList<ExpenseLogItem_Group> mGroup) {
        this.mGroup = mGroup;
    }
    public String getRecurring() {
        return mRecurring;
    }
    public void setRecurring(String mRecurring) {
        this.mRecurring = mRecurring;
    }
    public boolean hasReceipt() {
        return mReceipt;
    }
    public void setReceipt(boolean mReceipt) {
        this.mReceipt = mReceipt;
    }
    public boolean hasServer() {
        return mServer;
    }
    public void setServer(boolean mServer) {
        this.mServer = mServer;
    }
    public boolean isRecurring() {
        return mIsRecurring;
    }
    public void setRecurring(boolean mIsRecurring) {
        this.mIsRecurring = mIsRecurring;
    }
}
