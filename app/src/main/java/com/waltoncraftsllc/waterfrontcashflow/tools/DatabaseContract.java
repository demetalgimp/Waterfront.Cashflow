package com.waltoncraftsllc.waterfrontcashflow.tools;

public class DatabaseContract {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Waterfront.db";

//======================================================================= STRING CONSTANTS =======================================================================
//--- Category-labels table
    public static final String DEFAULT_CATEGORIES__TABLE_NAME = "CategoryLabels";
    public static final String DEFAULT_CATEGORIES__ID = "ID", DEFAULT_CATEGORIES__ID_TYPE = " INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String DEFAULT_CATEGORIES__NAME = "Name", DEFAULT_CATEGORIES__NAME_TYPE = " TEXT NOT NULL UNIQUE ON CONFLICT REPLACE";

    public static final String DEFAULT_CATEGORIES__DEFINE_TABLE = "create table " + DEFAULT_CATEGORIES__TABLE_NAME + " (" +
            DEFAULT_CATEGORIES__ID + DEFAULT_CATEGORIES__ID_TYPE + "," +
            DEFAULT_CATEGORIES__NAME + DEFAULT_CATEGORIES__NAME_TYPE +
        ")";
    public static final String DEFAULT_CATEGORIES__QUERY_TABLE = "select * from " + DEFAULT_CATEGORIES__TABLE_NAME;
    public static final String DEFAULT_CATEGORIES__DROP_TABLE = "drop table if exists " + DEFAULT_CATEGORIES__TABLE_NAME;

    //--- Tender-labels table
    public static final String DEFAULT_TENDER_LABELS__TABLE_NAME = "TenderLabels";
    public static final String DEFAULT_TENDER_LABELS__ID = "ID", DEFAULT_TENDER_LABELS__ID_TYPE = " INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String DEFAULT_TENDER_LABELS__NAME = "Label", DEFAULT_TENDER_LABELS__NAME_TYPE = " TEXT NOT NULL UNIQUE ON CONFLICT REPLACE";

    public static final String DEFAULT_TENDER_LABELS__DEFINE_TABLE = "create table " + DEFAULT_TENDER_LABELS__TABLE_NAME + "(" +
            DEFAULT_TENDER_LABELS__ID + DEFAULT_TENDER_LABELS__ID_TYPE + "," +
            DEFAULT_TENDER_LABELS__NAME + DEFAULT_TENDER_LABELS__NAME_TYPE +
        ")";
    public static final String DEFAULT_TENDER_LABELS__QUERY_TABLE = "select * from " + DEFAULT_TENDER_LABELS__TABLE_NAME;
    public static final String DEFAULT_TENDER_LABELS__DROP_TABLE = "drop table if exists " + DEFAULT_TENDER_LABELS__TABLE_NAME;

    //--- Periodicity-labels table
    public static final String DEFAULT_PERIODICITY_LABELS__TABLE_NAME = "PeriodicityLabels";
    public static final String DEFAULT_PERIODICITY_LABELS__ID = "ID", DEFAULT_PERIODICITY_LABELS__ID_TYPE = " INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String DEFAULT_PERIODICITY_LABELS__NAME = "Label", DEFAULT_PERIODICITY_LABELS__NAME_TYPE = " TEXT NOT NULL UNIQUE ON CONFLICT REPLACE";

    public static final String DEFAULT_PERIODICITY_LABELS__DEFINE_TABLE = "create table " + DEFAULT_PERIODICITY_LABELS__TABLE_NAME + "(" +
            DEFAULT_PERIODICITY_LABELS__ID + DEFAULT_PERIODICITY_LABELS__ID_TYPE + "," +
            DEFAULT_PERIODICITY_LABELS__NAME + DEFAULT_PERIODICITY_LABELS__NAME_TYPE +
        ")";
    public static final String DEFAULT_PERIODICITY_LABELS__QUERY_TABLE = "select * from " + DEFAULT_PERIODICITY_LABELS__TABLE_NAME;
    public static final String DEFAULT_PERIODICITY_LABELS__DROP_TABLE = "drop table if exists " + DEFAULT_PERIODICITY_LABELS__TABLE_NAME;

//======================================================================= USER DATA =======================================================================
    public static final String YYYY_MM_DD = " TEXT";
    public static final String MONEY = " TEXT";

    //--- Planned-Budget Table
    public static final String BUDGET__TABLE_NAME = "BudgetTable";
    public static final String BUDGET__ID = "ID", BUDGET__ID_TYPE = " INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String BUDGET__NAME = "Name", BUDGET__NAME_TYPE = " TEXT"; // <-- this really is the category!!
    public static final String BUDGET__DUE_DATE = "DueDate", BUDGET__TO_DUE_DATE_TYPE = YYYY_MM_DD;
    public static final String BUDGET__AMOUNT_CAP = "AmountCap", BUDGET__AMOUNT_CAP_TYPE = MONEY;

    public static final String BUDGET__DEFINE_TABLE = "create table " + BUDGET__TABLE_NAME + " (" +
            BUDGET__ID + BUDGET__ID_TYPE + ", " +
            BUDGET__NAME + BUDGET__NAME_TYPE + ", " +
            BUDGET__DUE_DATE + BUDGET__TO_DUE_DATE_TYPE + ", " +
            BUDGET__AMOUNT_CAP + BUDGET__AMOUNT_CAP_TYPE +
        ")";
    public static final String BUDGET__QUERY_TABLE = "select * from " + BUDGET__TABLE_NAME;
    public static final String BUDGET__DROP_TABLE = "drop table if exists " + BUDGET__TABLE_NAME;

//--- Planned-Budget-Time-Bracket Table
    public static final String BUDGET_TIME_BRACKET__TABLE_NAME = "TimeBracketTable";
    public static final String BUDGET_TIME_BRACKET__ID = "ID", BUDGET_TIME_BRACKET__ID__TYPE = " INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String BUDGET_TIME_BRACKET__BUDGET_FK = "FK_BudgetID", BUDGET_TIME_BRACKET__BUDGET_FK__TYPE = " INTEGER NON NULL REFERENCES " +
            BUDGET__TABLE_NAME + "(" + BUDGET__ID + ")";
    public static final String BUDGET_TIME_BRACKET__FROM_DATE = "FromDate", BUDGET_TIME_BRACKET__FROM_DATE__TYPE = YYYY_MM_DD;
    public static final String BUDGET_TIME_BRACKET__TO_DATE = "ToDate", BUDGET_TIME_BRACKET__TO_DATE__TYPE = YYYY_MM_DD;
    public static final String BUDGET_TIME_BRACKET__AMOUNT = "Amount", BUDGET_TIME_BRACKET__AMOUNT__TYPE = MONEY;  // <-- currency
    public static final String BUDGET_TIME_BRACKET__PERIODICITY_FK = "FK_Periodicity", BUDGET_TIME_BRACKET__PERIODICITY_FK__TYPE =
            " INTEGER NON NULL REFERENCES " + DEFAULT_PERIODICITY_LABELS__TABLE_NAME + "(" + DEFAULT_PERIODICITY_LABELS__ID + ")";

    public static final String BUDGET_TIME_BRACKET__DEFINE_TABLE = "create table " + BUDGET_TIME_BRACKET__TABLE_NAME + " (" +
            BUDGET_TIME_BRACKET__ID + BUDGET_TIME_BRACKET__ID__TYPE + ", " +
            BUDGET_TIME_BRACKET__BUDGET_FK + BUDGET_TIME_BRACKET__BUDGET_FK__TYPE + ", " +
            BUDGET_TIME_BRACKET__FROM_DATE + BUDGET_TIME_BRACKET__FROM_DATE__TYPE + ", " +
            BUDGET_TIME_BRACKET__TO_DATE + BUDGET_TIME_BRACKET__TO_DATE__TYPE + ", " +
            BUDGET_TIME_BRACKET__AMOUNT + BUDGET_TIME_BRACKET__AMOUNT__TYPE + ", " +
            BUDGET_TIME_BRACKET__PERIODICITY_FK + BUDGET_TIME_BRACKET__PERIODICITY_FK__TYPE +
        ")";
    public static final String BUDGET_TIME_BRACKET__QUERY_TABLE = "select * from " + BUDGET_TIME_BRACKET__TABLE_NAME + " where " +
            BUDGET_TIME_BRACKET__BUDGET_FK + "=?";
    public static final String BUDGET_TIME_BRACKET__DROP_TABLE = "drop table if exists " + BUDGET_TIME_BRACKET__TABLE_NAME;

//--- Expense-Log Table
    public static final String EXPENSE_LOG__TABLE_NAME = "ExpenseLogTable";
    public static final String EXPENSE_LOG__ID = "ID", EXPENSE_LOG__ID_TYPE = " INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String EXPENSE_LOG__DATE = "Date", EXPENSE_LOG__DATE_TYPE = YYYY_MM_DD;
    public static final String EXPENSE_LOG__TENDER_FK = "FK_Tender",
            EXPENSE_LOG__TENDER_FK__TYPE = " INTEGER NON NULL REFERENCES " + DEFAULT_TENDER_LABELS__TABLE_NAME + "(" + DEFAULT_TENDER_LABELS__ID + ")"; // <-- enum?
    public static final String EXPENSE_LOG__VENDOR = "Vendor", EXPENSE_LOG__VENDOR_TYPE = " TEXT";
    public static final String EXPENSE_LOG__RECURRING_FK = "Recurring", EXPENSE_LOG__RECURRING_FK__TYPE = " INTEGER NON NULL REFERENCES " +
            DEFAULT_PERIODICITY_LABELS__TABLE_NAME + "(" + DEFAULT_PERIODICITY_LABELS__ID + ")";
    public static final String EXPENSE_LOG__RECEIPT = "Receipt", EXPENSE_LOG__RECEIPT_TYPE = " INTEGER";
    public static final String EXPENSE_LOG__ON_SERVER = "Server", EXPENSE_LOG__SERVER_TYPE = " INTEGER";

    public static final String EXPENSE_LOG__DEFINE_TABLE = "create table " + EXPENSE_LOG__TABLE_NAME + " (" +
            EXPENSE_LOG__ID + EXPENSE_LOG__ID_TYPE + ", " +
            EXPENSE_LOG__DATE + EXPENSE_LOG__DATE_TYPE + ", " +
            EXPENSE_LOG__TENDER_FK + EXPENSE_LOG__TENDER_FK__TYPE + ", " +
            EXPENSE_LOG__VENDOR + EXPENSE_LOG__VENDOR_TYPE + ", " +
            EXPENSE_LOG__RECURRING_FK + EXPENSE_LOG__RECURRING_FK__TYPE + ", " +
            EXPENSE_LOG__RECEIPT + EXPENSE_LOG__RECEIPT_TYPE + ", " +
            EXPENSE_LOG__ON_SERVER + EXPENSE_LOG__SERVER_TYPE +
        ")";
    public static final String EXPENSE_LOG__QUERY_TABLE = "select * from " + EXPENSE_LOG__TABLE_NAME;
    public static final String EXPENSE_LOG__DROP_TABLE = "drop table if exists " + EXPENSE_LOG__TABLE_NAME;

//--- Expense-Log-Group Table
    public static final String EXPENSE_LOG_GROUP__TABLE_NAME = "ExpenseLogGroupTable";
    public static final String EXPENSE_LOG_GROUP__ID = "ID", EXPENSE_LOG_GROUP__ID_TYPE = " INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String EXPENSE_LOG_GROUP__EXPENSE_LOG__FK = "FK_EXPENSE_LOG",
            EXPENSE_LOG_GROUP__EXPENSE_LOG_FK__TYPE = " INTEGER REFERENCES " + EXPENSE_LOG__TABLE_NAME + "(" + EXPENSE_LOG__ID + ")";
    public static final String EXPENSE_LOG_GROUP__CATEGORY_FK = "Category",
            EXPENSE_LOG_GROUP__CATEGORY_FK__TYPE = " INTEGER REFERENCES " + DEFAULT_CATEGORIES__TABLE_NAME + "(" + DEFAULT_CATEGORIES__ID + ")";
    public static final String EXPENSE_LOG_GROUP__DEBIT = "Debit", EXPENSE_LOG_GROUP__DEBIT__TYPE = MONEY;
    public static final String EXPENSE_LOG_GROUP__TAX = "Tax", EXPENSE_LOG_GROUP__TAX__TYPE = MONEY;
    public static final String EXPENSE_LOG_GROUP__FOR_WHOM = "ForWhom", EXPENSE_LOG_GROUP__FOR_WHOM__TYPE = "TEXT";

    public static final String EXPENSE_LOG_GROUP__DEFINE_TABLE = "create table " + EXPENSE_LOG_GROUP__TABLE_NAME + " (" +
            EXPENSE_LOG_GROUP__ID + EXPENSE_LOG_GROUP__ID_TYPE + ", " +
            EXPENSE_LOG_GROUP__EXPENSE_LOG__FK + EXPENSE_LOG_GROUP__EXPENSE_LOG_FK__TYPE + ", " +
            EXPENSE_LOG_GROUP__CATEGORY_FK + EXPENSE_LOG_GROUP__CATEGORY_FK__TYPE + ", " +
            EXPENSE_LOG_GROUP__DEBIT + EXPENSE_LOG_GROUP__DEBIT__TYPE + ", " +
            EXPENSE_LOG_GROUP__TAX + EXPENSE_LOG_GROUP__TAX__TYPE +
            EXPENSE_LOG_GROUP__FOR_WHOM + EXPENSE_LOG_GROUP__FOR_WHOM__TYPE +
        ")";
    public static final String EXPENSE_LOG_GROUP__QUERY_TABLE = "select * from " + EXPENSE_LOG_GROUP__TABLE_NAME + " where " +
            EXPENSE_LOG_GROUP__EXPENSE_LOG__FK + "=?";
    public static final String EXPENSE_LOG_GROUP__DROP_TABLE = "drop table if exists " + EXPENSE_LOG_GROUP__TABLE_NAME;
}
