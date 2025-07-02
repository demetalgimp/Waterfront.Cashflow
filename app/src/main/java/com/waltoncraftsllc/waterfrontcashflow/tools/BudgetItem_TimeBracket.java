package com.waltoncraftsllc.waterfrontcashflow.tools;

import static com.waltoncraftsllc.waterfrontcashflow.tools.BudgetItem.sqlDateToString;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BudgetItem_TimeBracket {
        int mID;
        Date mFromDate;
        Date mToDate;
        Money mAmount;
        String mPeriodicity_str = "";
        int mPerAnnum = 1;
        Money proratedMonthly = new Money(); // <-- generated
        Money proratedWeekly = new Money();; // <-- generated

    public BudgetItem_TimeBracket(String from, String to, Money amount, String periodicity) {
        this.mFromDate = Date.valueOf(from);
        this.mToDate = Date.valueOf(to);
        this.mAmount = amount;
        this.mPeriodicity_str = periodicity;
        double week_scaler = 52/12.0;
        double month_scaler = 1;
        switch ( periodicity ) {
            case "Weekly":       this.mPerAnnum = 52; week_scaler = 1.0;    month_scaler = 52/12.0; break;
            case "Biweekly":     this.mPerAnnum = 26; week_scaler = 1/2.0;  month_scaler = 52/24.0; break;
            case "Semimonthly":  this.mPerAnnum = 24; week_scaler = 1/24.0; month_scaler = 2.0;     break;
            case "Monthly":      this.mPerAnnum = 12; week_scaler = 1/12.0; month_scaler = 1.0;     break;
            case "Quarterly":    this.mPerAnnum = 4;  week_scaler = 4/52.0; month_scaler = 1/4.0;   break;
            case "Triannually":  this.mPerAnnum = 3;  week_scaler = 3/52.0; month_scaler = 1/3.0;   break;
            case "Semiannually": this.mPerAnnum = 2;  week_scaler = 2/52.0; month_scaler = 1/6.0;   break;
            case "Once":         this.mPerAnnum = 1;  week_scaler = 1/52.0; month_scaler = 1/12.0;  break;
            case "Annually":     this.mPerAnnum = 1;  week_scaler = 1/52.0; month_scaler = 1/12.0;  break;
            case "Yearly":       this.mPerAnnum = 1;  week_scaler = 1/52.0; month_scaler = 1/12.0;  break;
        }
        this.proratedMonthly = mAmount.multiply(month_scaler);
        this.proratedWeekly = mAmount.multiply(week_scaler);
    }
    public String getFromDate() {
        return sqlDateToString(mFromDate);
    }
    public void setFromDate(String from) {
        this.mFromDate = Date.valueOf(from);
    }
    public String getToDate() {
        return sqlDateToString(mToDate);
    }
    public void setToDate(String to) {
        this.mToDate = Date.valueOf(to);
    }
    public String getAmount() {
        return mAmount.toString();
    }
    public void setAmount(Money mAmount) {
        this.mAmount = mAmount;
    }
    public String getPeriodicity_str() {
        return mPeriodicity_str;
    }
    public void setPeriodicity(String periodicity) {
        this.mPeriodicity_str = periodicity;
    }
    public String getProratedMonthly() {
        return proratedMonthly.toString() + "/mo";
    }
    public String getProratedWeekly() {
        return proratedWeekly.toString() + "/wk";
    }
}
