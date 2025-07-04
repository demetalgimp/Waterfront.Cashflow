package com.waltoncraftsllc.waterfrontcashflow.tools;

public class DatabaseContract {
    public static final String DATABASE_NAME = "Waterfront.db";

//======================================================================= STRING CONSTANTS =======================================================================
//--- Category-labels table
    public static final String DEFAULT_CATEGORIES__TABLE_NAME = "DefaultCategoryNames";
    public static final String DEFAULT_CATEGORIES__ID = "ID", DEFAULT_CATEGORIES__ID_TYPE = " INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String DEFAULT_CATEGORIES__NAME = "Name", DEFAULT_CATEGORIES__NAME_TYPE = " TEXT NOT NULL UNIQUE ON CONFLICT REPLACE";

    public static final String DEFAULT_CATEGORIES__TABLE_DEFINITION = DEFAULT_CATEGORIES__TABLE_NAME + " (" +
            DEFAULT_CATEGORIES__ID + DEFAULT_CATEGORIES__ID_TYPE + "," +
            DEFAULT_CATEGORIES__NAME + DEFAULT_CATEGORIES__NAME_TYPE +
        ")";

    //--- Tender-labels table
    public static final String DEFAULT_TENDER_LABELS__TABLE_NAME = "DefaultTenderLabels";
    public static final String DEFAULT_TENDER_LABELS__ID = "ID", DEFAULT_TENDER_LABELS__ID_TYPE = " INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String DEFAULT_TENDER_LABELS__NAME = "Label", DEFAULT_TENDER_LABELS__NAME_TYPE = " TEXT NOT NULL UNIQUE ON CONFLICT REPLACE";

    public static final String DEFAULT_TENDER_LABELS__TABLE_DEFINITION = DEFAULT_TENDER_LABELS__TABLE_NAME + "(" +
            DEFAULT_TENDER_LABELS__ID + DEFAULT_TENDER_LABELS__ID_TYPE + "," +
            DEFAULT_TENDER_LABELS__NAME + DEFAULT_TENDER_LABELS__NAME_TYPE +
        ")";

    //--- Periodicity-labels table
    public static final String DEFAULT_PERIODICITY_LABELS__TABLE_NAME = "DefaultPeriodicityLabels";
    public static final String DEFAULT_PERIODICITY_LABELS__ID = "ID", DEFAULT_PERIODICITY_LABELS__ID_TYPE = " INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String DEFAULT_PERIODICITY_LABELS__NAME = "Label", DEFAULT_PERIODICITY_LABELS__NAME_TYPE = " TEXT NOT NULL UNIQUE ON CONFLICT REPLACE";

    public static final String DEFAULT_PERIODICITY_LABELS__TABLE_DEFINITION = DEFAULT_PERIODICITY_LABELS__TABLE_NAME + "(" +
            DEFAULT_PERIODICITY_LABELS__ID + DEFAULT_PERIODICITY_LABELS__ID_TYPE + "," +
            DEFAULT_PERIODICITY_LABELS__NAME + DEFAULT_PERIODICITY_LABELS__NAME_TYPE +
        ")";

//======================================================================= USER DATA =======================================================================
    public static final String YYYY_MM_DD = " TEXT";
    public static final String MONEY = " REAL";

    //--- Planned-Budget Table
    public static final String BUDGET__TABLE_NAME = "BudgetTable";
    public static final String BUDGET__ID = "ID", BUDGET__ID_TYPE = " INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String BUDGET__NAME = "Name", BUDGET__NAME_TYPE = " TEXT"; // <-- this really is the category!!
    public static final String BUDGET__DUE_DATE = "DueDate", BUDGET__TO_DUE_DATE_TYPE = YYYY_MM_DD;
    public static final String BUDGET__AMOUNT_CAP = "AmountCap", BUDGET__AMOUNT_CAP_TYPE = MONEY;

    public static final String BUDGET__TABLE_DEFINITION = BUDGET__TABLE_NAME + " (" +
            BUDGET__ID + BUDGET__ID_TYPE + ", " +
            BUDGET__NAME + BUDGET__NAME_TYPE + ", " +
            BUDGET__DUE_DATE + BUDGET__TO_DUE_DATE_TYPE + ", " +
            BUDGET__AMOUNT_CAP + BUDGET__AMOUNT_CAP_TYPE +
        ")";

//--- Planned-Budget-Time-Bracket Table
    public static final String BUDGET_TIME_BRACKET__TABLE_NAME = "TimeBracketTable";
    public static final String BUDGET_TIME_BRACKET__ID = "ID", BUDGET_TIME_BRACKET__ID__TYPE = " INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String BUDGET_TIME_BRACKET__BUDGET_FK = "FK_BudgetID",
            BUDGET_TIME_BRACKET__BUDGET_FK__TYPE = " INTEGER NON NULL REFERENCES " + BUDGET__TABLE_NAME + "(" + BUDGET__ID + ")";
    public static final String BUDGET_TIME_BRACKET__FROM_DATE = "FromDate", BUDGET_TIME_BRACKET__FROM_DATE__TYPE = YYYY_MM_DD;
    public static final String BUDGET_TIME_BRACKET__TO_DATE = "ToDate", BUDGET_TIME_BRACKET__TO_DATE__TYPE = YYYY_MM_DD;
    public static final String BUDGET_TIME_BRACKET__AMOUNT = "Amount", BUDGET_TIME_BRACKET__AMOUNT__TYPE = MONEY;  // <-- currency
    public static final String BUDGET_TIME_BRACKET__PERIODICITY_FK = "FK_Periodicity",
            BUDGET_TIME_BRACKET__PERIODICITY_FK__TYPE = " INTEGER NON NULL REFERENCES " + DEFAULT_PERIODICITY_LABELS__TABLE_NAME + "(" + DEFAULT_PERIODICITY_LABELS__ID + ")";
    public static final String BUDGET_TIME_BRACKET__TABLE_DEFINITION = BUDGET_TIME_BRACKET__TABLE_NAME + " (" +
            BUDGET_TIME_BRACKET__ID + BUDGET_TIME_BRACKET__ID__TYPE + ", " +
            BUDGET_TIME_BRACKET__BUDGET_FK + BUDGET_TIME_BRACKET__BUDGET_FK__TYPE + ", " +
            BUDGET_TIME_BRACKET__FROM_DATE + BUDGET_TIME_BRACKET__FROM_DATE__TYPE + ", " +
            BUDGET_TIME_BRACKET__TO_DATE + BUDGET_TIME_BRACKET__TO_DATE__TYPE + ", " +
            BUDGET_TIME_BRACKET__AMOUNT + BUDGET_TIME_BRACKET__AMOUNT__TYPE + ", " +
            BUDGET_TIME_BRACKET__PERIODICITY_FK + BUDGET_TIME_BRACKET__PERIODICITY_FK__TYPE +
        ")";

//--- Expense-Log Table
    public static final String EXPENSE_LOG__TABLE_NAME = "ExpenseLogTable";
    public static final String EXPENSE_LOG__ID = "ID", EXPENSE_LOG__ID_TYPE = " INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String EXPENSE_LOG__DATE = "Date", EXPENSE_LOG__DATE_TYPE = YYYY_MM_DD;
    public static final String EXPENSE_LOG__TENDER_FK = "FK_Tender",
            EXPENSE_LOG__TENDER_FK__TYPE = " INTEGER NON NULL REFERENCES " + DEFAULT_TENDER_LABELS__TABLE_NAME + "(" + DEFAULT_TENDER_LABELS__ID + ")"; // <-- enum?
    public static final String EXPENSE_LOG__FOR_WHOM = "ForWhom", EXPENSE_LOG__FOR_WHOM_TYPE = " TEXT";
    public static final String EXPENSE_LOG__RECURRING_FK = "Recurring",
            EXPENSE_LOG__RECURRING_FK__TYPE = " INTEGER NON NULL REFERENCES " + DEFAULT_PERIODICITY_LABELS__TABLE_NAME + "(" + DEFAULT_PERIODICITY_LABELS__ID + ")";
    public static final String EXPENSE_LOG__RECEIPT = "Receipt", EXPENSE_LOG__RECEIPT_TYPE = " INTEGER";
    public static final String EXPENSE_LOG__ON_SERVER = "Server", EXPENSE_LOG__SERVER_TYPE = " INTEGER";

    public static final String EXPENSE_LOG__TABLE_DEFINITION = EXPENSE_LOG__TABLE_NAME + " (" +
            EXPENSE_LOG__ID + EXPENSE_LOG__ID_TYPE + ", " +
            EXPENSE_LOG__DATE + EXPENSE_LOG__DATE_TYPE + ", " +
            EXPENSE_LOG__TENDER_FK + EXPENSE_LOG__TENDER_FK__TYPE + ", " +
            EXPENSE_LOG__FOR_WHOM + EXPENSE_LOG__FOR_WHOM_TYPE + ", " +
            EXPENSE_LOG__RECURRING_FK + EXPENSE_LOG__RECURRING_FK__TYPE + ", " +
            EXPENSE_LOG__RECEIPT + EXPENSE_LOG__RECEIPT_TYPE + ", " +
            EXPENSE_LOG__ON_SERVER + EXPENSE_LOG__SERVER_TYPE +
        ")";

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

    public static final String EXPENSE_LOG_GROUP__TABLE_DEFINITION = EXPENSE_LOG_GROUP__TABLE_NAME + " (" +
            EXPENSE_LOG_GROUP__ID + EXPENSE_LOG_GROUP__ID_TYPE + ", " +
            EXPENSE_LOG_GROUP__EXPENSE_LOG__FK + EXPENSE_LOG_GROUP__EXPENSE_LOG_FK__TYPE + ", " +
            EXPENSE_LOG_GROUP__CATEGORY_FK + EXPENSE_LOG_GROUP__CATEGORY_FK__TYPE + ", " +
            EXPENSE_LOG_GROUP__DEBIT + EXPENSE_LOG_GROUP__DEBIT__TYPE + ", " +
            EXPENSE_LOG_GROUP__TAX + EXPENSE_LOG_GROUP__TAX__TYPE +
            EXPENSE_LOG_GROUP__FOR_WHOM + EXPENSE_LOG_GROUP__FOR_WHOM__TYPE +
        ")";
}
