package com.waltoncraftsllc.waterfrontcashflow.database;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DatabaseContract {
    public static final int DATABASE_VERSION = 15;
    public static final String DATABASE_NAME = "Waterfront.db";
    public static final String CHECK_MARK = "✔";
    public static final String ANDROID_UI_DATE_PATTERN = "MM/dd";
    public static final String DATABASE_DATE_PATTERN = "yyyy-MM-dd";
    public static final String YYYY_MM_DD__TYPE = " text";
    public static final String MONEY__TYPE = " text";

//================================================================= STRING CONSTANTS =================================================================

//--- Category-labels table [works & integrated]------------------------------------------------------------------------------------------------------
    public static final String CATEGORIES__TABLE_NAME = "CategoryLabels";
    public static final String CATEGORIES__ID = "Category_ID",  CATEGORIES__ID_TYPE = " integer primary key";
    public static final String CATEGORIES__NAME = "Name",       CATEGORIES__NAME_TYPE = " text not null unique on conflict replace";
    public static final String CATEGORIES__DEFINE_TABLE = "create table if not exists " + CATEGORIES__TABLE_NAME +  " (" +
            CATEGORIES__ID + CATEGORIES__ID_TYPE + "," +
            CATEGORIES__NAME + CATEGORIES__NAME_TYPE +
        ")";
    public static final String CATEGORIES__QUERY_TABLE = "select * from " + CATEGORIES__TABLE_NAME;
    public static final String CATEGORIES__DROP_TABLE = "drop table if exists " + CATEGORIES__TABLE_NAME;

//--- Tender-labels table [works & integrated]--------------------------------------------------------------------------------------------------------
    public static final String LEGAL_TENDER_LABELS__TABLE_NAME = "TenderLabels";
    public static final String LEGAL_TENDER_LABELS__ID = "Tender_ID",   LEGAL_TENDER_LABELS__ID_TYPE = " integer primary key";
    public static final String LEGAL_TENDER_LABELS__NAME = "Label",     LEGAL_TENDER_LABELS__NAME_TYPE = " text not null unique on conflict replace";
    public static final String LEGAL_TENDER_LABELS__DEFINE_TABLE = "create table if not exists " + LEGAL_TENDER_LABELS__TABLE_NAME + "(" +
            LEGAL_TENDER_LABELS__ID + LEGAL_TENDER_LABELS__ID_TYPE + "," +
            LEGAL_TENDER_LABELS__NAME + LEGAL_TENDER_LABELS__NAME_TYPE +
        ")";
    public static final String LEGAL_TENDER_LABELS__QUERY_TABLE = "select * from " + LEGAL_TENDER_LABELS__TABLE_NAME;
    public static final String LEGAL_TENDER_LABELS__DROP_TABLE = "drop table if exists " + LEGAL_TENDER_LABELS__TABLE_NAME;

//--- Periodicity-labels table [works & integrated]---------------------------------------------------------------------------------------------------
    public static final String PERIODICITY_LABELS__TABLE_NAME = "PeriodicityLabels";
    public static final String PERIODICITY_LABELS__ID = "Periodicity_ID",   DEFAULT_PERIODICITY_LABELS__ID_TYPE = " integer primary key";
    public static final String PERIODICITY_LABELS__NAME = "Label",          DEFAULT_PERIODICITY_LABELS__NAME_TYPE = " text not null unique on conflict replace";
    public static final String PERIODICITY_LABELS__DEFINE_TABLE = "create table if not exists " + PERIODICITY_LABELS__TABLE_NAME + "(" +
            PERIODICITY_LABELS__ID + DEFAULT_PERIODICITY_LABELS__ID_TYPE + "," +
            PERIODICITY_LABELS__NAME + DEFAULT_PERIODICITY_LABELS__NAME_TYPE +
        ")";
    public static final String PERIODICITY_LABELS__QUERY_TABLE = "select * from " + PERIODICITY_LABELS__TABLE_NAME;
    public static final String PERIODICITY_LABELS__DROP_TABLE = "drop table if exists " + PERIODICITY_LABELS__TABLE_NAME;

    public static final String REFERENCES_CATEGORY      = " references " + CATEGORIES__TABLE_NAME + "(" + CATEGORIES__ID + ")";
    public static final String REFERENCES_LEGAL_TENDER  = " references " + LEGAL_TENDER_LABELS__TABLE_NAME + "(" + LEGAL_TENDER_LABELS__ID + ")"; // <-- enum?
    public static final String REFERENCES_PERIODICITIES = " references " + PERIODICITY_LABELS__TABLE_NAME + "(" + PERIODICITY_LABELS__ID + ")";
    //======================================================================= USER DATA =======================================================================

    //--- Planned-Budget Table [works]----------------------------------------------------------------------------------------------------------------
    public static final String BUDGET__TABLE_NAME = "BudgetTable";
    public static final String BUDGET__ID = "Budget_ID",                                BUDGET__ID_TYPE = " integer primary key autoincrement";
    public static final String BUDGET__NAME = "Name",                                   BUDGET__NAME_TYPE = " text"; // <-- this really is the category!!
    public static final String BUDGET__DUE_DATE = "DueDate",                            BUDGET__TO_DUE_DATE_TYPE = YYYY_MM_DD__TYPE;
    public static final String BUDGET__AMOUNT_CAP = "AmountCap",                        BUDGET__AMOUNT_CAP_TYPE = MONEY__TYPE;
    public static final String BUDGET__DEFINE_TABLE = "create table if not exists " + BUDGET__TABLE_NAME +  " (" +
            BUDGET__ID + BUDGET__ID_TYPE + ", " +
            BUDGET__NAME + BUDGET__NAME_TYPE + ", " +
            BUDGET__DUE_DATE + BUDGET__TO_DUE_DATE_TYPE + ", " +
            BUDGET__AMOUNT_CAP + BUDGET__AMOUNT_CAP_TYPE +
        ")";
    public static final String BUDGET__QUERY_TABLE = "select * from " + BUDGET__TABLE_NAME;
    public static final String BUDGET__DROP_TABLE = "drop table if exists " + BUDGET__TABLE_NAME;

    public static final String REFERENCES_BUDGET = "references " + BUDGET__TABLE_NAME + "(" + BUDGET__ID + ")";


//--- Planned-Budget-Time-Bracket Table [works]-------------------------------------------------------------------------------------------------------
    public static final String BUDGET_TIME_BRACKET__TABLE_NAME = "TimeBracketTable";
    public static final String BUDGET_TIME_BRACKET__ID = "Bracket_ID",                  BUDGET_TIME_BRACKET__ID__TYPE = " integer primary key autoincrement";
    public static final String BUDGET_TIME_BRACKET__BUDGET_FK = "FK_BudgetID",          BUDGET_TIME_BRACKET__BUDGET_FK__TYPE = " integer " + REFERENCES_BUDGET;
    public static final String BUDGET_TIME_BRACKET__FROM_DATE = "FromDate",             BUDGET_TIME_BRACKET__FROM_DATE__TYPE = YYYY_MM_DD__TYPE;
    public static final String BUDGET_TIME_BRACKET__TO_DATE = "ToDate",                 BUDGET_TIME_BRACKET__TO_DATE__TYPE = YYYY_MM_DD__TYPE;
    public static final String BUDGET_TIME_BRACKET__AMOUNT = "Amount";
    public static final String BUDGET_TIME_BRACKET__AMOUNT__TYPE = MONEY__TYPE;  // <-- currency
    public static final String BUDGET_TIME_BRACKET__PERIODICITY_FK = "FK_Periodicity",  BUDGET_TIME_BRACKET__PERIODICITY_FK__TYPE = " integer " + REFERENCES_PERIODICITIES;

    public static final String BUDGET_TIME_BRACKET__DEFINE_TABLE = "create table if not exists " + BUDGET_TIME_BRACKET__TABLE_NAME +  " (" +
            BUDGET_TIME_BRACKET__ID + BUDGET_TIME_BRACKET__ID__TYPE + ", " +
            BUDGET_TIME_BRACKET__BUDGET_FK + BUDGET_TIME_BRACKET__BUDGET_FK__TYPE + ", " +
            BUDGET_TIME_BRACKET__FROM_DATE + BUDGET_TIME_BRACKET__FROM_DATE__TYPE + ", " +
            BUDGET_TIME_BRACKET__TO_DATE + BUDGET_TIME_BRACKET__TO_DATE__TYPE + ", " +
            BUDGET_TIME_BRACKET__AMOUNT + BUDGET_TIME_BRACKET__AMOUNT__TYPE + ", " +
            BUDGET_TIME_BRACKET__PERIODICITY_FK + BUDGET_TIME_BRACKET__PERIODICITY_FK__TYPE +
        ")";

    public static final String BUDGET_TIME_BRACKET__QUERY_TABLE = "select * from " + BUDGET_TIME_BRACKET__TABLE_NAME + " where " + BUDGET_TIME_BRACKET__BUDGET_FK + "=?";
    public static final String BUDGET_TIME_BRACKET__DROP_TABLE = "drop table if exists " + BUDGET_TIME_BRACKET__TABLE_NAME;

//--- Expense Table ----------------------------------------------------------------------------------------------------------------------------------
    public static final String EXPENSE__TABLE_NAME = "ExpenseTable";
    public static final String EXPENSE__ID = "Expense_ID",                              EXPENSE__ID_TYPE = " integer primary key autoincrement";
    public static final String EXPENSE__DATE = "Date",                                  EXPENSE__DATE_TYPE = YYYY_MM_DD__TYPE;
/**/    public static final String EXPENSE__TENDER_FK = "FK_Tender",                        EXPENSE__TENDER_FK__TYPE = " text";//" integer " + REFERENCES_LEGAL_TENDER;
    public static final String EXPENSE__VENDOR = "Vendor",                              EXPENSE__VENDOR_TYPE = "text";
/**/    public static final String EXPENSE__RECURRING_FK = "Recurring",                     EXPENSE__RECURRING_FK__TYPE = " text";//" integer " + REFERENCES_PERIODICITIES;
    public static final String EXPENSE__RECEIPT = "Receipt",                            EXPENSE__RECEIPT_TYPE = " integer";
    public static final String EXPENSE__ON_SERVER = "Server",                           EXPENSE__SERVER_TYPE = " integer";

    public static final String EXPENSE__DEFINE_TABLE = "create table if not exists " + EXPENSE__TABLE_NAME +  " (" +
            EXPENSE__ID + EXPENSE__ID_TYPE + ", " +
            EXPENSE__DATE + EXPENSE__DATE_TYPE + ", " +
            EXPENSE__TENDER_FK + EXPENSE__TENDER_FK__TYPE + ", " +
            EXPENSE__VENDOR + EXPENSE__VENDOR_TYPE + ", " +
            EXPENSE__RECURRING_FK + EXPENSE__RECURRING_FK__TYPE + ", " +
            EXPENSE__RECEIPT + EXPENSE__RECEIPT_TYPE + ", " +
            EXPENSE__ON_SERVER + EXPENSE__SERVER_TYPE +
        ")";
    public static final String EXPENSE__QUERY_TABLE = "select * from " + EXPENSE__TABLE_NAME;
    public static final String EXPENSE__DROP_TABLE = "drop table if exists " + EXPENSE__TABLE_NAME;

    public static final String REFERENCES_EXPENSE = "references " + EXPENSE__TABLE_NAME + "(" + EXPENSE__ID + ")";
    //--- Expense-Group Table ----------------------------------------------------------------------------------------------------------------------------
    public static final String EXPENSE_GROUP__TABLE_NAME = "ExpenseGroupTable";
    public static final String EXPENSE_GROUP__ID = "Group_ID",                          EXPENSE_GROUP__ID_TYPE = " integer primary key autoincrement";
    public static final String EXPENSE_GROUP__EXPENSE__FK = "FK_EXPENSE",               EXPENSE_GROUP__EXPENSE_FK__TYPE = " integer " + REFERENCES_EXPENSE;
/**/    public static final String EXPENSE_GROUP__CATEGORY_FK = "Category",                 EXPENSE_GROUP__CATEGORY_FK__TYPE = " text";//" integer " + REFERENCES_CATEGORY;
    public static final String EXPENSE_GROUP__DEBIT = "Debit",                          EXPENSE_GROUP__DEBIT__TYPE = MONEY__TYPE;
    public static final String EXPENSE_GROUP__TAX = "Tax",                              EXPENSE_GROUP__TAX__TYPE = MONEY__TYPE;
    public static final String EXPENSE_GROUP__FOR_WHOM = "ForWhom",                     EXPENSE_GROUP__FOR_WHOM__TYPE = "text";

    public static final String EXPENSE_GROUP__DEFINE_TABLE = "create table if not exists " + EXPENSE_GROUP__TABLE_NAME +  " (" +
            EXPENSE_GROUP__ID + EXPENSE_GROUP__ID_TYPE + ", " +
            EXPENSE_GROUP__EXPENSE__FK + EXPENSE_GROUP__EXPENSE_FK__TYPE + ", " +
            EXPENSE_GROUP__CATEGORY_FK + EXPENSE_GROUP__CATEGORY_FK__TYPE + ", " +
            EXPENSE_GROUP__DEBIT + EXPENSE_GROUP__DEBIT__TYPE + ", " +
            EXPENSE_GROUP__TAX + EXPENSE_GROUP__TAX__TYPE +
            EXPENSE_GROUP__FOR_WHOM + EXPENSE_GROUP__FOR_WHOM__TYPE +
        ")";
    public static final String EXPENSE_GROUP__QUERY_TABLE = "select * from " + EXPENSE_GROUP__TABLE_NAME + " where " + EXPENSE_GROUP__EXPENSE__FK + "=?";
    public static final String EXPENSE_GROUP__DROP_TABLE = "drop table if exists " + EXPENSE_GROUP__TABLE_NAME;

//=============================================================== Database helper methods ============================================================
    public static String toString(Date date, String pattern) {
        return new SimpleDateFormat(pattern, Locale.US).format(date);
    }
}
