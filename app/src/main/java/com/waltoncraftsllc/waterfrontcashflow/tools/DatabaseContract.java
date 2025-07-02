package com.waltoncraftsllc.waterfrontcashflow.tools;

public class DatabaseContract {
    public static final String DATABASE_NAME = "Waterfront.db";

    //--- Planned-Budget Table
    public static final String BUDGET__TABLE_NAME = "BudgetTable";
    public static final String BUDGET__ID = "ID", BUDGET__ID_TYPE = " INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String BUDGET__NAME = "Name", BUDGET__NAME_TYPE = " STRING";
    public static final String BUDGET__DUE_DATE = "DueDate", BUDGET__TO_DUE_DATE_TYPE = " STRING";
    public static final String BUDGET__AMOUNT_CAP = "AmountCap", BUDGET__AMOUNT_CAP_TYPE = " STRING";
    public static final String BUDGET__TABLE_DEFINITION = BUDGET__TABLE_NAME + " (" +
                BUDGET__ID + BUDGET__ID_TYPE + ", " +
                BUDGET__NAME + BUDGET__NAME_TYPE + ", " +
                BUDGET__DUE_DATE + BUDGET__TO_DUE_DATE_TYPE + ", " +
                BUDGET__AMOUNT_CAP + BUDGET__AMOUNT_CAP_TYPE +
            ")";

    //--- Planned-Budget-Time-Bracket Table
    public static final String BUDGET_TIME_BRACKET__TABLE_NAME = "TimeBracketTable";
    public static final String BUDGET_TIME_BRACKET__ID = "ID", BUDGET_TIME_BRACKET__ID_TYPE = " INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String BUDGET_TIME_BRACKET__FK = "FK_ID", BUDGET_TIME_BRACKET__FK_TYPE = " INTEGER";
    public static final String BUDGET_TIME_BRACKET__FROM_DATE = "FromDate", BUDGET_TIME_BRACKET__FROM_DATE_TYPE = " STRING";
    public static final String BUDGET_TIME_BRACKET__TO_DATE = "ToDate", BUDGET_TIME_BRACKET__TO_DATE_TYPE = " STRING";
    public static final String BUDGET_TIME_BRACKET__AMOUNT = "Amount", BUDGET_TIME_BRACKET__AMOUNT_TYPE = " STRING";  // <-- currency
    public static final String BUDGET_TIME_BRACKET__PERIODICITY_FK = "Periodicity", BUDGET_TIME_BRACKET__PERIODICITY_FK_TYPE = " INTEGER"; // integer (enum)
    public static final String BUDGET_TIME_BRACKET__TABLE_DEFINITION = BUDGET_TIME_BRACKET__TABLE_NAME + " (" +
                BUDGET_TIME_BRACKET__ID + BUDGET_TIME_BRACKET__ID_TYPE + ", " +
                BUDGET_TIME_BRACKET__FK + BUDGET_TIME_BRACKET__FK_TYPE + ", " +
                BUDGET_TIME_BRACKET__FROM_DATE + BUDGET_TIME_BRACKET__FROM_DATE_TYPE + ", " +
                BUDGET_TIME_BRACKET__TO_DATE + BUDGET_TIME_BRACKET__TO_DATE_TYPE + ", " +
                BUDGET_TIME_BRACKET__AMOUNT + BUDGET_TIME_BRACKET__AMOUNT_TYPE + ", " +
                BUDGET_TIME_BRACKET__PERIODICITY_FK + BUDGET_TIME_BRACKET__PERIODICITY_FK_TYPE +
            ")";

    //--- Expense-Log Table
    public static final String EXPENSE_LOG__TABLE_NAME = "ExpenseLogTable";
    public static final String EXPENSE_LOG__ID = "ID", EXPENSE_LOG__ID_TYPE = " INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String EXPENSE_LOG__DATE = "Date", EXPENSE_LOG__DATE_TYPE = " STRING";
    public static final String EXPENSE_LOG__TENDER = "Tender", EXPENSE_LOG__TENDER_TYPE = " STRING"; // <-- enum?
    public static final String EXPENSE_LOG__FOR_WHOM = "ForWhom", EXPENSE_LOG__FOR_WHOM_TYPE = " STRING";
    public static final String EXPENSE_LOG__RECURRING = "Recurring", EXPENSE_LOG__RECURRING_TYPE = " STRING";
    public static final String EXPENSE_LOG__RECEIPT = "Receipt", EXPENSE_LOG__RECEIPT_TYPE = " INTEGER";
    public static final String EXPENSE_LOG__ON_SERVER = "Server", EXPENSE_LOG__SERVER_TYPE = " INTEGER";
    public static final String EXPENSE_LOG__TABLE_DEFINITION = EXPENSE_LOG__TABLE_NAME + " (" +
                EXPENSE_LOG__ID + EXPENSE_LOG__ID_TYPE + ", " +
                EXPENSE_LOG__DATE + EXPENSE_LOG__DATE_TYPE + ", " +
                EXPENSE_LOG__TENDER + EXPENSE_LOG__TENDER_TYPE + ", " +
                EXPENSE_LOG__FOR_WHOM + EXPENSE_LOG__FOR_WHOM_TYPE + ", " +
                EXPENSE_LOG__RECURRING + EXPENSE_LOG__RECURRING_TYPE + ", " +
                EXPENSE_LOG__RECEIPT + EXPENSE_LOG__RECEIPT_TYPE + ", " +
                EXPENSE_LOG__ON_SERVER + EXPENSE_LOG__SERVER_TYPE +
            ")";

    //--- Expense-Log-Group Table
    public static final String EXPENSE_LOG_GROUP__TABLE_NAME = "ExpenseLogGroupTable";
    public static final String EXPENSE_LOG_GROUP__ID = "ID", EXPENSE_LOG_GROUP__ID_TYPE = " INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String EXPENSE_LOG_GROUP__FK = "FK_ID", EXPENSE_LOG_GROUP__FK_TYPE = " INTEGER";
    public static final String EXPENSE_LOG_GROUP__CATEGORY = "Category", EXPENSE_LOG_GROUP__CATEGORY_TYPE = " STRING";
    public static final String EXPENSE_LOG_GROUP__DEBIT = "Debit", EXPENSE_LOG_GROUP__DEBIT_TYPE = " STRING";
    public static final String EXPENSE_LOG_GROUP__TAX = "Tax", EXPENSE_LOG_GROUP__TAX_TYPE = " STRING";
    public static final String EXPENSE_LOG_GROUP__TABLE_DEFINITION = EXPENSE_LOG_GROUP__TABLE_NAME + " (" +
                EXPENSE_LOG_GROUP__ID + EXPENSE_LOG_GROUP__ID_TYPE + ", " +
                EXPENSE_LOG_GROUP__FK + EXPENSE_LOG_GROUP__FK_TYPE + ", " +
                EXPENSE_LOG_GROUP__CATEGORY + EXPENSE_LOG_GROUP__CATEGORY_TYPE + ", " +
                EXPENSE_LOG_GROUP__DEBIT + EXPENSE_LOG_GROUP__DEBIT_TYPE + ", " +
                EXPENSE_LOG_GROUP__TAX + EXPENSE_LOG_GROUP__TAX_TYPE +
            ")";// <-- currency

    public static final String DEFAULT_CATEGORIES__TABLE_NAME = "DefaultCategoryNames";
    static final String DEFAULT_CATEGORIES__ID = "ID", DEFAULT_CATEGORIES__ID_TYPE = " INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String DEFAULT_CATEGORIES__NAME = "Name";
    static final String DEFAULT_CATEGORIES__NAME_TYPE = " STRING";
    public static final String DEFAULT_CATEGORIES__TABLE_DEFINITION = DEFAULT_CATEGORIES__TABLE_NAME + " (" +
                DEFAULT_CATEGORIES__ID + DEFAULT_CATEGORIES__ID_TYPE + "," +
                DEFAULT_CATEGORIES__NAME + DEFAULT_CATEGORIES__NAME_TYPE +
            ")";


    public static final String DEFAULT_TENDER_LABELS__TABLE_NAME = "DefaultTenderLabels";
    static final String DEFAULT_TENDER_LABELS__ID = "ID", DEFAULT_TENDER_LABELS__ID_TYPE = " INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String DEFAULT_TENDER_LABELS__NAME = "Label";
    static final String DEFAULT_TENDER_LABELS__NAME_TYPE = " STRING";
    public static final String DEFAULT_TENDER_LABELS__TABLE_DEFINITION = DEFAULT_TENDER_LABELS__TABLE_NAME + "(" +
                DEFAULT_TENDER_LABELS__ID + DEFAULT_TENDER_LABELS__ID_TYPE + "," +
                DEFAULT_TENDER_LABELS__NAME + DEFAULT_TENDER_LABELS__NAME_TYPE +
            ")";


    public static final String DEFAULT_PERIODICITY_LABELS__TABLE_NAME = "DefaultPeriodicityLabels";
    public static final String DEFAULT_PERIODICITY_LABELS__NAME = "Label";
    static final String DEFAULT_PERIODICITY_LABELS__NAME_TYPE = " STRING";
    public static final String DEFAULT_PERIODICITY_LABELS__VALUE = "Value";
    static final String DEFAULT_PERIODICITY_LABELS__VALUE_TYPE = " INTEGER DEFAULT 0";
    public static final String DEFAULT_PERIODICITY_LABELS__TABLE_DEFINITION = DEFAULT_PERIODICITY_LABELS__TABLE_NAME + "(" +
                DEFAULT_PERIODICITY_LABELS__NAME + DEFAULT_PERIODICITY_LABELS__NAME_TYPE + "," +
                DEFAULT_PERIODICITY_LABELS__VALUE + DEFAULT_PERIODICITY_LABELS__VALUE_TYPE +
            ")";
}
