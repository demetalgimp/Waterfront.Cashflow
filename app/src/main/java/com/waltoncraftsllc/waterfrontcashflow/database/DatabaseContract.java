package com.waltoncraftsllc.waterfrontcashflow.database;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DatabaseContract {
    public static final int DATABASE_VERSION = 12;
    public static final String DATABASE_NAME = "Waterfront.db";
    public static final String CHECK_MARK = "✔";
    public static final String ANDROID_UI_DATE_PATTERN = "MM/dd";
    public static final String DATABASE_DATE_PATTERN = "yyyy-MM-dd";

//================================================================= STRING CONSTANTS =================================================================

//--- Category-labels table [works & integrated]------------------------------------------------------------------------------------------------------
    public static final String CATEGORIES__TABLE_NAME = "CategoryLabels";
    public static final String DEFAULT_CATEGORIES__ID = "Category_ID";
        public static final String DEFAULT_CATEGORIES__ID_TYPE = " INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String CATEGORIES__NAME = "Name";
        public static final String DEFAULT_CATEGORIES__NAME_TYPE = " TEXT NOT NULL UNIQUE ON CONFLICT REPLACE";
    public static final String CATEGORIES__DEFINE_TABLE = "create table " + CATEGORIES__TABLE_NAME + " (" +
            DEFAULT_CATEGORIES__ID + DEFAULT_CATEGORIES__ID_TYPE + "," +
            CATEGORIES__NAME + DEFAULT_CATEGORIES__NAME_TYPE +
        ")";
    public static final String DEFAULT_CATEGORIES__QUERY_TABLE = "select * from " + CATEGORIES__TABLE_NAME;
    public static final String DEFAULT_CATEGORIES__DROP_TABLE = "drop table if exists " + CATEGORIES__TABLE_NAME;

//--- Tender-labels table [works & integrated]--------------------------------------------------------------------------------------------------------
    public static final String TENDER_LABELS__TABLE_NAME = "TenderLabels";
    public static final String DEFAULT_TENDER_LABELS__ID = "Tender_ID";
        public static final String DEFAULT_TENDER_LABELS__ID_TYPE = " INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String TENDER_LABELS__NAME = "Label";
        public static final String DEFAULT_TENDER_LABELS__NAME_TYPE = " TEXT NOT NULL UNIQUE ON CONFLICT REPLACE";
    public static final String TENDER_LABELS__DEFINE_TABLE = "create table " + TENDER_LABELS__TABLE_NAME + "(" +
            DEFAULT_TENDER_LABELS__ID + DEFAULT_TENDER_LABELS__ID_TYPE + "," +
            TENDER_LABELS__NAME + DEFAULT_TENDER_LABELS__NAME_TYPE +
        ")";
    public static final String DEFAULT_TENDER_LABELS__QUERY_TABLE = "select * from " + TENDER_LABELS__TABLE_NAME;
    public static final String DEFAULT_TENDER_LABELS__DROP_TABLE = "drop table if exists " + TENDER_LABELS__TABLE_NAME;

//--- Periodicity-labels table [works & integrated]---------------------------------------------------------------------------------------------------
    public static final String PERIODICITY_LABELS__TABLE_NAME = "PeriodicityLabels";
    public static final String PERIODICITY_LABELS__ID = "Periodicity_ID";
        public static final String DEFAULT_PERIODICITY_LABELS__ID_TYPE = " INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String PERIODICITY_LABELS__NAME = "Label";
        public static final String DEFAULT_PERIODICITY_LABELS__NAME_TYPE = " TEXT NOT NULL UNIQUE ON CONFLICT REPLACE";
    public static final String PERIODICITY_LABELS__DEFINE_TABLE = "create table " + PERIODICITY_LABELS__TABLE_NAME + "(" +
            PERIODICITY_LABELS__ID + DEFAULT_PERIODICITY_LABELS__ID_TYPE + "," +
            PERIODICITY_LABELS__NAME + DEFAULT_PERIODICITY_LABELS__NAME_TYPE +
        ")";
    public static final String DEFAULT_PERIODICITY_LABELS__QUERY_TABLE = "select * from " + PERIODICITY_LABELS__TABLE_NAME;
    public static final String DEFAULT_PERIODICITY_LABELS__DROP_TABLE = "drop table if exists " + PERIODICITY_LABELS__TABLE_NAME;

//======================================================================= USER DATA =======================================================================
    public static final String YYYY_MM_DD = " TEXT";
    public static final String MONEY = " TEXT";

    //--- Planned-Budget Table [works]----------------------------------------------------------------------------------------------------------------
    public static final String BUDGET__TABLE_NAME = "BudgetTable";
    public static final String BUDGET__ID = "Budget_ID";
        public static final String BUDGET__ID_TYPE = " INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String BUDGET__NAME = "Name";
        public static final String BUDGET__NAME_TYPE = " TEXT"; // <-- this really is the category!!
    public static final String BUDGET__DUE_DATE = "DueDate";
        public static final String BUDGET__TO_DUE_DATE_TYPE = YYYY_MM_DD;
    public static final String BUDGET__AMOUNT_CAP = "AmountCap";
        public static final String BUDGET__AMOUNT_CAP_TYPE = MONEY;
    public static final String BUDGET__DEFINE_TABLE = "create table " + BUDGET__TABLE_NAME + " (" +
            BUDGET__ID + BUDGET__ID_TYPE + ", " +
            BUDGET__NAME + BUDGET__NAME_TYPE + ", " +
            BUDGET__DUE_DATE + BUDGET__TO_DUE_DATE_TYPE + ", " +
            BUDGET__AMOUNT_CAP + BUDGET__AMOUNT_CAP_TYPE +
        ")";
    public static final String BUDGET__QUERY_TABLE = "select * from " + BUDGET__TABLE_NAME;
    public static final String BUDGET__DROP_TABLE = "drop table if exists " + BUDGET__TABLE_NAME;

//--- Planned-Budget-Time-Bracket Table [works]-------------------------------------------------------------------------------------------------------
    public static final String BUDGET_TIME_BRACKET__TABLE_NAME = "TimeBracketTable";
    public static final String BUDGET_TIME_BRACKET__ID = "Bracket_ID";
        public static final String BUDGET_TIME_BRACKET__ID__TYPE = " INTEGER PRIMARY KEY AUTOINCREMENT";
//    public static final String BUDGET_TIME_BRACKET__BUDGET_FK = "FK_BudgetID";
//    public static final String BUDGET_TIME_BRACKET__BUDGET_FK__TYPE = " INTEGER REFERENCES " + BUDGET__TABLE_NAME + "(" + BUDGET__ID + ")";
    public static final String BUDGET_TIME_BRACKET__BUDGET_FK = "FK_BudgetID";
        public static final String BUDGET_TIME_BRACKET__BUDGET_FK__TYPE = " INTEGER";
    public static final String BUDGET_TIME_BRACKET__FROM_DATE = "FromDate";
        public static final String BUDGET_TIME_BRACKET__FROM_DATE__TYPE = YYYY_MM_DD;
    public static final String BUDGET_TIME_BRACKET__TO_DATE = "ToDate";
        public static final String BUDGET_TIME_BRACKET__TO_DATE__TYPE = YYYY_MM_DD;
    public static final String BUDGET_TIME_BRACKET__AMOUNT = "Amount";
    public static final String BUDGET_TIME_BRACKET__AMOUNT__TYPE = MONEY;  // <-- currency
    public static final String BUDGET_TIME_BRACKET__PERIODICITY_FK = "FK_Periodicity";
        public static final String BUDGET_TIME_BRACKET__PERIODICITY_FK__TYPE = " INTEGER NON NULL REFERENCES " + PERIODICITY_LABELS__TABLE_NAME + "(" + PERIODICITY_LABELS__ID + ")";
    public static final String BUDGET_TIME_BRACKET__DEFINE_TABLE = "create table " + BUDGET_TIME_BRACKET__TABLE_NAME + " (" +
            BUDGET_TIME_BRACKET__ID + BUDGET_TIME_BRACKET__ID__TYPE + ", " +
            BUDGET_TIME_BRACKET__BUDGET_FK + BUDGET_TIME_BRACKET__BUDGET_FK__TYPE + ", " +
            BUDGET_TIME_BRACKET__FROM_DATE + BUDGET_TIME_BRACKET__FROM_DATE__TYPE + ", " +
            BUDGET_TIME_BRACKET__TO_DATE + BUDGET_TIME_BRACKET__TO_DATE__TYPE + ", " +
            BUDGET_TIME_BRACKET__AMOUNT + BUDGET_TIME_BRACKET__AMOUNT__TYPE + ", " +
            BUDGET_TIME_BRACKET__PERIODICITY_FK + BUDGET_TIME_BRACKET__PERIODICITY_FK__TYPE +
        ")";
    public static final String BUDGET_TIME_BRACKET__QUERY_TABLE = "select * from " + BUDGET_TIME_BRACKET__TABLE_NAME + " where " + BUDGET_TIME_BRACKET__BUDGET_FK + "=?";
    public static final String BUDGET_TIME_BRACKET__DROP_TABLE = "drop table if exists " + BUDGET_TIME_BRACKET__TABLE_NAME;

//--- Expense Table ------------------------------------------------------------------------------------------------------------------------------
    public static final String EXPENSE__TABLE_NAME = "ExpenseTable";
    public static final String EXPENSE__ID = "Expense_ID";
        public static final String EXPENSE__ID_TYPE = " INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String EXPENSE__DATE = "Date";
        public static final String EXPENSE__DATE_TYPE = YYYY_MM_DD;
//    public static final String EXPENSE__TENDER_FK = "FK_Tender";
//    public static final String EXPENSE__TENDER_FK__TYPE = " INTEGER NON NULL REFERENCES " + DEFAULT_TENDER_LABELS__TABLE_NAME + "(" + DEFAULT_TENDER_LABELS__ID + ")"; // <-- enum?
//FIXME: broken Tender
    public static final String EXPENSE__TENDER_FK = "FK_Tender";
        public static final String EXPENSE__TENDER_FK__TYPE = " TEXT";
    public static final String EXPENSE__VENDOR = "Vendor";
        public static final String EXPENSE__VENDOR_TYPE = " TEXT";
    public static final String EXPENSE__RECURRING_FK = "Recurring";
    public static final String EXPENSE__RECURRING_FK__TYPE = " INTEGER NON NULL REFERENCES " + PERIODICITY_LABELS__TABLE_NAME + "(" + PERIODICITY_LABELS__ID + ")";
//FIXME: broken Recurring=-1
    public static final String EXPENSE__RECEIPT = "Receipt";
        public static final String EXPENSE__RECEIPT_TYPE = " INTEGER";
    public static final String EXPENSE__ON_SERVER = "Server";
        public static final String EXPENSE__SERVER_TYPE = " INTEGER";
    public static final String EXPENSE__DEFINE_TABLE = "create table " + EXPENSE__TABLE_NAME + " (" +
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

//--- Expense-Group Table ------------------------------------------------------------------------------------------------------------------------
    public static final String EXPENSE_GROUP__TABLE_NAME = "ExpenseGroupTable";
    public static final String EXPENSE_GROUP__ID = "Group_ID";
        public static final String EXPENSE_GROUP__ID_TYPE = " INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String EXPENSE_GROUP__EXPENSE__FK = "FK_EXPENSE";
        public static final String EXPENSE_GROUP__EXPENSE_FK__TYPE = " INTEGER REFERENCES " + EXPENSE__TABLE_NAME + "(" + EXPENSE__ID + ")";
    public static final String EXPENSE_GROUP__CATEGORY_FK = "Category";
        public static final String EXPENSE_GROUP__CATEGORY_FK__TYPE = " INTEGER";
    public static final String EXPENSE_GROUP__DEBIT = "Debit";
        public static final String EXPENSE_GROUP__DEBIT__TYPE = MONEY;
    public static final String EXPENSE_GROUP__TAX = "Tax";
        public static final String EXPENSE_GROUP__TAX__TYPE = MONEY;
    public static final String EXPENSE_GROUP__FOR_WHOM = "ForWhom";
        public static final String EXPENSE_GROUP__FOR_WHOM__TYPE = "TEXT";
    public static final String EXPENSE_GROUP__DEFINE_TABLE = "create table " + EXPENSE_GROUP__TABLE_NAME + " (" +
            EXPENSE_GROUP__ID + EXPENSE_GROUP__ID_TYPE + ", " +
            EXPENSE_GROUP__EXPENSE__FK + EXPENSE_GROUP__EXPENSE_FK__TYPE + ", " +
            EXPENSE_GROUP__CATEGORY_FK + EXPENSE_GROUP__CATEGORY_FK__TYPE + ", " +
            EXPENSE_GROUP__DEBIT + EXPENSE_GROUP__DEBIT__TYPE + ", " +
            EXPENSE_GROUP__TAX + EXPENSE_GROUP__TAX__TYPE +
            EXPENSE_GROUP__FOR_WHOM + EXPENSE_GROUP__FOR_WHOM__TYPE +
        ")";
    public static final String EXPENSE_GROUP__QUERY_TABLE = "select * from " + EXPENSE_GROUP__TABLE_NAME + " where " +
            EXPENSE_GROUP__EXPENSE__FK + "=?";
    public static final String EXPENSE_GROUP__DROP_TABLE = "drop table if exists " + EXPENSE_GROUP__TABLE_NAME;

//=============================================================== Database helper methods ============================================================
    public static String toString(Date date, String pattern) {
        return new SimpleDateFormat(pattern, Locale.US).format(date);
    }
    public static Date toDate(String date_txt) {
        return Date.valueOf(date_txt);
    }
}
