package com.waltoncraftsllc.waterfrontcashflow.tools;

import static com.waltoncraftsllc.waterfrontcashflow.tools.Budget.sqlDateToString;

import java.sql.Date;

public class Budget_TimeBracket {
        int mID;
        Date mFromDate;
        Date mToDate;
        Money mAmount;
        String mPeriodicity_str;
        int mPerAnnum;
        Money proratedMonthly; // <-- generated
        Money proratedWeekly; // <-- generated

    public Budget_TimeBracket(String from, String to, Money amount, String periodicity) {
        this.mFromDate = Date.valueOf(from);
        this.mToDate = Date.valueOf(to);
        this.mAmount = amount;
        this.mPeriodicity_str = periodicity;
        double week_scaler;
        double month_scaler;
        this.mPerAnnum = Integer.parseInt(periodicity.replaceAll("[^0-9]*", ""));
//FIXME: out of date
        switch ( periodicity ) {
            case "Weekly (52/yr)":          week_scaler = 52.0/52; month_scaler = 52.0/12; break;
            case "Biweekly (26/yr)":        week_scaler = 26.0/52; month_scaler = 26.0/12; break;
            case "Semimonthly (24/yr)":     week_scaler = 13.0/52; month_scaler = 24.0/12; break;
            case "Monthly (12/yr)":         week_scaler =  6.5/52; month_scaler = 12.0/12; break;
            case "Quarterly (4/yr)":        week_scaler =  4.0/52; month_scaler =  3.0/12; break;
            case "Triannually (3/yr)":      week_scaler =  3.0/52; month_scaler =  3.0/12; break;
            case "Semiannually (2/yr)":     week_scaler =  2.0/52; month_scaler =  2.0/12; break;
            case "Annually/yearly (1/yr)":
            default:                        week_scaler =  1.0/52; month_scaler =  1.0/12; break;
        }
//---
        this.proratedMonthly = mAmount.multiply(month_scaler);
        this.proratedWeekly = mAmount.multiply(week_scaler);
    }
    public String getFromDate() {
        return sqlDateToString(mFromDate);
    }
    public String getToDate() {
        return sqlDateToString(mToDate);
    }
    public String getAmount() {
        return mAmount.toString();
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
