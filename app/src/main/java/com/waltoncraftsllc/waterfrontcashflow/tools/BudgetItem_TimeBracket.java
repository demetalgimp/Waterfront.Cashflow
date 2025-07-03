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
        double week_scaler = 6.5/52;
        double month_scaler = 1;
        this.mPerAnnum = Integer.valueOf(periodicity.replaceAll("[^0-9]*", ""));
        switch ( periodicity ) {
            case "Weekly (52/yr)":          week_scaler = 52.0/52; month_scaler = 52.0/12; break;
            case "Biweekly (26/yr)":        week_scaler = 26.0/52; month_scaler = 26.0/12; break;
            case "Semimonthly (24/yr)":     week_scaler = 13.0/52; month_scaler = 24.0/12; break;
            case "Monthly (12/yr)":         week_scaler =  6.5/52; month_scaler = 12.0/12; break;
            case "Quarterly (4/yr)":        week_scaler =  4.0/52; month_scaler =  3.0/12; break;
            case "Triannually (3/yr)":      week_scaler =  3.0/52; month_scaler =  3.0/12; break;
            case "Semiannually (2/yr)":     week_scaler =  2.0/52; month_scaler =  2.0/12; break;
            case "Annually/yearly (1/yr)":
            default:                        week_scaler =  1.0/52; month_scaler = 1.0/12; break;
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
