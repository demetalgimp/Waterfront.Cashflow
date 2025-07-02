package com.waltoncraftsllc.waterfrontcashflow.tools;

public class Money {
    boolean mPrefix = true;
    String mSymbol = "$";
    boolean mDecimalPeriod = true;
    int mAmount = 0;

    public class IncompatibleMoneyDenomination extends Exception {
        public IncompatibleMoneyDenomination(String message) { super(message); }
    }

    public Money(boolean is_prefix, String symbol, double amount) {
        mPrefix = is_prefix;
        mSymbol = symbol;
        mAmount = (int)(amount * 100);
    }

    private void testDenomination(Money money) throws Money.IncompatibleMoneyDenomination {
        if ( money.mPrefix != this.mPrefix  ||  !money.mSymbol.equals(this.mSymbol) ) {
            throw new Money.IncompatibleMoneyDenomination("Trying to do math on different denominations");
        }
    }

    public Money() {}
    public Money(String amount) {
        mPrefix = true;
        // us_num   -> [$]<dig>{1,3}[,<dig>{3}]*[.[<dig>{2}]]
        //          -> [US]<dig>{1,3}[,<dig>{3}]*[.[<dig>{2}]]
        // en_num   -> <dig>{1,3}[,<dig>{3}]*[.[<dig>{2}]]₤
        //          -> BPD<dig>{1,3}[,<dig>{3}]*[.[<dig>{2}]]
        // eu_num   -> <dig>{1,3}[.<dig>{3}]*[,[<dig>{2}]]€
        //          -> EUR<dig>{1,3}[.<dig>{3}]*[,[<dig>{2}]]
        if ( amount.startsWith("BPD") || amount.startsWith("₤") || amount.endsWith("₤") ) {
            mPrefix = true;
            mSymbol = "₤";
            amount = amount.replaceAll("[,]", "").replace("BPD", "").replaceAll(mSymbol, "");

        } else if ( amount.startsWith("EUR") || amount.endsWith("€") ) {
            mPrefix = false;
            mSymbol = "€";
            amount = amount.replaceAll("[.]", "").replace("EUR", "").replace(mSymbol, "");
            amount = amount.replace(",", ".");
            mDecimalPeriod = false;

        } else { //--- Assume USD
            mSymbol = "$";
            amount = amount.replaceAll("[,]", "").replace("USD", "").replace("US", "").replace(mSymbol, "");
        }

        try {
            mAmount = (int)(Double.parseDouble(amount) * 100);
        } catch ( NumberFormatException e ) {
            e.printStackTrace();
        }
    }

    public Money divide(Money denominator) throws Money.IncompatibleMoneyDenomination {
        testDenomination(denominator);
        return divide(denominator);
    }

    public Money divide(double denominator) {
        double amount = mAmount / denominator;
        return new Money(mPrefix, mSymbol, amount/100);
    }

    public Money multiply(double multiplicand) {
        double amount = mAmount * multiplicand;
        return new Money(mPrefix, mSymbol, amount/100);
    }

    public String toString() {
        if (mPrefix) {
            return String.format("%s%,.2f", mSymbol, ((double)mAmount)/100);
        } else {
            return String.format("%,.2f%s", mAmount, ((double)mAmount)/100); //TECH DEBT: fix European comma/period
        }
    }
}
