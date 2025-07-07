package com.waltoncraftsllc.waterfrontcashflow.adapters;

import static com.waltoncraftsllc.waterfrontcashflow.MainActivity.mCategoryPeriodicity_ArrayAdapter;
import static com.waltoncraftsllc.waterfrontcashflow.MainActivity.mCategory_ArrayAdapter;
import static com.waltoncraftsllc.waterfrontcashflow.MainActivity.mTender_ArrayAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.waltoncraftsllc.waterfrontcashflow.R;
import com.waltoncraftsllc.waterfrontcashflow.database.Sqlite_ConnectionHelper;
import com.waltoncraftsllc.waterfrontcashflow.database.DatabaseContract;
import com.waltoncraftsllc.waterfrontcashflow.contaIners.Expense;
import com.waltoncraftsllc.waterfrontcashflow.contaIners.Expense_Group;
import com.waltoncraftsllc.waterfrontcashflow.tools.Money;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Expense_Adapter extends RecyclerView.Adapter<Expense_Adapter.ViewHolder> {
    static ArrayList<Expense> expenses = new ArrayList<>();

    static class ExpenseLogGroup {
        public LinearLayout mLinearLayout_group;
        public Spinner mSpinner_Category;
        public TextView mTextView_Debit, mTextView_Tax, mTextView_ForWhom;
        public ExpenseLogGroup(LinearLayout group, Spinner category, TextView debit, TextView tax, TextView forWhom) {
            mLinearLayout_group = group;
            mSpinner_Category = category;
            mTextView_Debit = debit;
            mTextView_Tax = tax;
            mTextView_ForWhom = forWhom;
        }
        public void populate(Expense_Group item) {
            mSpinner_Category.setAdapter(mCategory_ArrayAdapter);
            String category = item.getCategory();
            int index = mCategory_ArrayAdapter.getPosition(category);
            mSpinner_Category.setSelection(index);

            mTextView_Debit.setText(String.valueOf(item.getDebit()));
            mTextView_Tax.setText(String.valueOf(item.getTax()));
            mTextView_ForWhom.setText(String.valueOf(item.getForWhom()));
        }
        public void setVisible(int visible) {
            mLinearLayout_group.setVisibility(visible);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView_Date, mTextView_Vendor;
        Spinner mSpinner_Tender, mSpinner_Periodicity;
        CheckBox mCheckbox_Receipt, mCheckbox_Server, mCheckbox_IsRecurring;
        ArrayList<ExpenseLogGroup> mExpenseLogGroups = new ArrayList<>();

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView_Date = itemView.findViewById(R.id.textView_date);
            mSpinner_Tender = itemView.findViewById(R.id.spinner_tender);
            mTextView_Vendor = itemView.findViewById(R.id.textView_vendor);

            mExpenseLogGroups.add(new ExpenseLogGroup(itemView.findViewById(R.id.linearLayout_group_1),
                    itemView.findViewById(R.id.spinner_category_1), itemView.findViewById(R.id.textView_debit_1),
                    itemView.findViewById(R.id.textView_tax_1), itemView.findViewById(R.id.textView_for_whom_1)));
            mExpenseLogGroups.add(new ExpenseLogGroup(itemView.findViewById(R.id.linearLayout_group_2),
                    itemView.findViewById(R.id.spinner_category_2), itemView.findViewById(R.id.textView_debit_2),
                    itemView.findViewById(R.id.textView_tax_2), itemView.findViewById(R.id.textView_for_whom_2)));
            mExpenseLogGroups.add(new ExpenseLogGroup(itemView.findViewById(R.id.linearLayout_group_3),
                    itemView.findViewById(R.id.spinner_category_3), itemView.findViewById(R.id.textView_debit_3),
                    itemView.findViewById(R.id.textView_tax_3), itemView.findViewById(R.id.textView_for_whom_3)));
            mExpenseLogGroups.add(new ExpenseLogGroup(itemView.findViewById(R.id.linearLayout_group_4),
                    itemView.findViewById(R.id.spinner_category_4), itemView.findViewById(R.id.textView_debit_4),
                    itemView.findViewById(R.id.textView_tax_4), itemView.findViewById(R.id.textView_for_whom_4)));
            mExpenseLogGroups.add(new ExpenseLogGroup(itemView.findViewById(R.id.linearLayout_group_5),
                    itemView.findViewById(R.id.spinner_category_5), itemView.findViewById(R.id.textView_debit_5),
                    itemView.findViewById(R.id.textView_tax_5), itemView.findViewById(R.id.textView_for_whom_5)));

            mSpinner_Periodicity = itemView.findViewById(R.id.spinner_recurring);
            mSpinner_Periodicity.setAdapter(mCategoryPeriodicity_ArrayAdapter);
            mCheckbox_Receipt = itemView.findViewById(R.id.checkBox_receipt);
            mCheckbox_Server = itemView.findViewById(R.id.checkBox_server);
            mCheckbox_IsRecurring = itemView.findViewById(R.id.checkBox_is_recurring);
        }
    }

    public Expense_Adapter() {
        setupTestData();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_expense_log, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int index;
        holder.itemView.findViewById(R.id.constraintLayout_expense_log).setBackgroundColor( ((position & 1) == 0? 0x8EE2BF: 0xD7F5E9) );

        Expense expense = expenses.get(position);
        holder.mTextView_Date.setText(DatabaseContract.toString(expense.getDate(), DatabaseContract.ANDROID_UI_DATE_PATTERN));
        holder.mSpinner_Tender.setAdapter(mTender_ArrayAdapter);
        String tender = expense.getTenderString();
        index = mTender_ArrayAdapter.getPosition(tender);
        holder.mSpinner_Tender.setSelection(index);
        holder.mTextView_Vendor.setText(expense.getVendor());

        //--- Fill the table of Groups
        for (int i = 0; i < holder.mExpenseLogGroups.size(); i++ ) {
            //--- Ensure that the number of rows match
            if ( i < expense.getGroup().size() ) {
                holder.mExpenseLogGroups.get(i).setVisible(View.VISIBLE);
                holder.mExpenseLogGroups.get(i).populate(expense.getGroup().get(i));

            } else {
                holder.mExpenseLogGroups.get(i).setVisible(View.GONE);
            }
        }

        holder.mCheckbox_Receipt.setChecked(expense.hasReceipt());
        holder.mCheckbox_Server.setChecked(expense.hasServer());
        holder.mCheckbox_IsRecurring.setChecked(expense.isRecurring());
        holder.mSpinner_Periodicity.setVisibility( (expense.isRecurring()? View.VISIBLE: View.GONE) );
        holder.mCheckbox_IsRecurring.setOnClickListener(v -> holder.mSpinner_Periodicity.setVisibility((((CheckBox) v).isChecked() ? View.VISIBLE : View.GONE)));

        if ( expense.isRecurring() ) {
            holder.mSpinner_Periodicity.setAdapter(mCategoryPeriodicity_ArrayAdapter);
            index = mCategoryPeriodicity_ArrayAdapter.getPosition(expense.getRecurring());
            holder.mSpinner_Periodicity.setSelection(index);
        }
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    static void setupTestData() {
        expenses.add(new Expense("2025-06-06", "CC-Credit card", "Walmart",
                new ArrayList<>(Collections.singletonList(
                        new Expense_Group("Consumables/Food", new Money("-9.98"), new Money("0.29"), ""))),
                "", "✔", "✔")
        );
        expenses.add(new Expense("2025-06-07", "CC-Credit card", "CostCo Wholesale",
                new ArrayList<>(Arrays.asList(
                        new Expense_Group("Consumables/Food", new Money("-45.16"), new Money("1.32"), ""),
                        new Expense_Group("Consumables/Non-Food", new Money("-10.73"), new Money("0.74"), ""))),
                "", "", "✔")
        );
        expenses.add(new Expense("2025-06-14", "CC-Credit card", "CostCo Wholesale",
                new ArrayList<>(Arrays.asList(
                        new Expense_Group("Consumables/Food", new Money("-101.42"), new Money("3.04"), ""),
                        new Expense_Group("Consumables/Non-Food", new Money("-73.34"), new Money("4.88"), ""),
                        new Expense_Group("Consumables/Non-Food", new Money("-73.34"), new Money("4.88"), ""))),
                "", "✔", "✔")
        );
        expenses.add(new Expense("2025-06-14", "CC-Credit card", "Great Clips",
                new ArrayList<>(Arrays.asList(
                        new Expense_Group("Personal/Health/General", new Money("-54.00"), new Money("0.00"), ""),
                        new Expense_Group("Consumables/Non-Food", new Money("-73.34"), new Money("4.88"), ""),
                        new Expense_Group("Consumables/Non-Food", new Money("-73.34"), new Money("4.88"), ""),
                        new Expense_Group("Consumables/Non-Food", new Money("-73.34"), new Money("4.88"), ""))),
                "", "✔", "✔")
        );
        for ( Expense expense : expenses) {
            Sqlite_ConnectionHelper.getInstance().insertExpenseLogRecord(expense);
        }
        ArrayList<Expense> log = Sqlite_ConnectionHelper.getInstance().queryExpenseRecords();
    }
}
