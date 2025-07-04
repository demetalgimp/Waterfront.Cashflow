package com.waltoncraftsllc.waterfrontcashflow.tools;

public class ExpenseLog_Group {
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

    public String getCategory() {
        return mCategory;
    }
//    public void setCategory(String mCategory) {
//        this.mCategory = mCategory;
//    }
    public String getDebit() {
        return mDebit.toString();
    }
//    public void setDebit(Money mDebit) {
//        this.mDebit = mDebit;
//    }
    public String getTax() {
        return mTax.toString();
    }
//    public void setTax(Money mTax) {
//        this.mTax = mTax;
//    }
    public String getForWhom() {
        return mForWhom;
    }
//    public void setForWhom(String mForWhom) {
//        this.mForWhom = mForWhom;
//    }
}

