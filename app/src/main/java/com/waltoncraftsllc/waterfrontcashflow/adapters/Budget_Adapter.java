package com.waltoncraftsllc.waterfrontcashflow.adapters;

import static com.waltoncraftsllc.waterfrontcashflow.MainActivity.mCategoryPeriodicity_ArrayAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.waltoncraftsllc.waterfrontcashflow.R;
import com.waltoncraftsllc.waterfrontcashflow.tools.Budget;
import com.waltoncraftsllc.waterfrontcashflow.tools.Budget_TimeBracket;
import com.waltoncraftsllc.waterfrontcashflow.tools.Money;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Budget_Adapter extends RecyclerView.Adapter<Budget_Adapter.ViewHolder> {
    static ArrayList<Budget> mBudgets = new ArrayList<>();

    static class TimeBracketItem {
        LinearLayout mLinearLayout_TimeBracket;
        TextView mTextView_FromDate, mTextView_ToDate, mTextView_Amount;
        Spinner mSpinner_Periodicity;
        TextView mTextView_Monthly, mTextView_Weekly;

        public TimeBracketItem(LinearLayout timeBracket, TextView fromDate, TextView toDate, TextView amount, Spinner periodicity, TextView monthly, TextView weekly) {
            mLinearLayout_TimeBracket = timeBracket;
            mTextView_FromDate = fromDate;
            mTextView_ToDate = toDate;
            mTextView_Amount = amount;
            mSpinner_Periodicity = periodicity;
            mTextView_Monthly = monthly;
            mTextView_Weekly = weekly;
        }
        public void populate(Budget_TimeBracket item) {
            mTextView_FromDate.setText(item.getFromDate());
            mTextView_ToDate.setText(item.getToDate());
            mTextView_Amount.setText(String.valueOf(item.getAmount()));
            mSpinner_Periodicity.setAdapter(mCategoryPeriodicity_ArrayAdapter);
            int index = mCategoryPeriodicity_ArrayAdapter.getPosition(item.getPeriodicity_str());
            mSpinner_Periodicity.setSelection(index);
//            Locale locale = new Locale("English", "United States");
            mTextView_Monthly.setText(item.getProratedMonthly());
            mTextView_Weekly.setText(item.getProratedWeekly());
        }
        public void setVisible(int visible) {
            mLinearLayout_TimeBracket.setVisibility(visible);
        }
    }

    public Budget_Adapter() {
        setupTestData();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView_Name;
        TextView mLabel_DueDate, mTextView_DueDate, mLabel_AmountCap, mTextView_AmountCap;
        LinearLayout mLayout_TimeBracket;
        ArrayList<TimeBracketItem> mTimeBrackets = new ArrayList<>();

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView_Name = itemView.findViewById(R.id.textView_Name);
            mTimeBrackets.add(new TimeBracketItem(itemView.findViewById(R.id.linearLayout_TimeBracket_1),
                    itemView.findViewById(R.id.textView_From_1), itemView.findViewById(R.id.textView_To_1),
                    itemView.findViewById(R.id.textView_Amount_1), itemView.findViewById(R.id.spinner_Periodicity_1),
                    itemView.findViewById(R.id.textView_MonthlyProrated_1), itemView.findViewById(R.id.textView_WeeklyProrated_1)));
            mTimeBrackets.add(new TimeBracketItem(itemView.findViewById(R.id.linearLayout_TimeBracket_2),
                    itemView.findViewById(R.id.textView_From_2), itemView.findViewById(R.id.textView_To_2),
                    itemView.findViewById(R.id.textView_Amount_2), itemView.findViewById(R.id.spinner_Periodicity_2),
                    itemView.findViewById(R.id.textView_MonthlyProrated_2), itemView.findViewById(R.id.textView_WeeklyProrated_2)));
            mTimeBrackets.add(new TimeBracketItem(itemView.findViewById(R.id.linearLayout_TimeBracket_3),
                    itemView.findViewById(R.id.textView_From_3), itemView.findViewById(R.id.textView_To_3),
                    itemView.findViewById(R.id.textView_Amount_3), itemView.findViewById(R.id.spinner_Periodicity_3),
                    itemView.findViewById(R.id.textView_MonthlyProrated_3), itemView.findViewById(R.id.textView_WeeklyProrated_3)));
            mTimeBrackets.add(new TimeBracketItem(itemView.findViewById(R.id.linearLayout_TimeBracket_4),
                    itemView.findViewById(R.id.textView_From_4), itemView.findViewById(R.id.textView_To_4),
                    itemView.findViewById(R.id.textView_Amount_4), itemView.findViewById(R.id.spinner_Periodicity_4),
                    itemView.findViewById(R.id.textView_MonthlyProrated_4), itemView.findViewById(R.id.textView_WeeklyProrated_4)));
            mTimeBrackets.add(new TimeBracketItem(itemView.findViewById(R.id.linearLayout_TimeBracket_5),
                    itemView.findViewById(R.id.textView_From_5), itemView.findViewById(R.id.textView_To_5),
                    itemView.findViewById(R.id.textView_Amount_5), itemView.findViewById(R.id.spinner_Periodicity_5),
                    itemView.findViewById(R.id.textView_MonthlyProrated_5), itemView.findViewById(R.id.textView_WeeklyProrated_5)));
            mTextView_DueDate = itemView.findViewById(R.id.textView_DueDate);
            mLabel_DueDate = itemView.findViewById(R.id.label_DueDate);
            mTextView_AmountCap = itemView.findViewById(R.id.textView_Cap);
            mLabel_AmountCap = itemView.findViewById(R.id.label_AmountCap);
            mLayout_TimeBracket = itemView.findViewById(R.id.linearLayout_TimeBracket_Main);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_budget, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.findViewById(R.id.constraintLayout_budget_item).setBackgroundColor(((position & 1) == 0 ? 0x8EE2BF : 0xD7F5E9));

        Budget budget = mBudgets.get(position);
        holder.mTextView_Name.setText(budget.getName());

        //--- Fill the table of Groups
        if (budget.getTimeBrackets() != null) {
            for (int i = 0; i < holder.mTimeBrackets.size(); i++) {
                holder.mLayout_TimeBracket.setVisibility(View.VISIBLE);
                //--- Ensure that the number of rows match
                if (i < budget.getTimeBrackets().size()) {
                    holder.mTimeBrackets.get(i).setVisible(View.VISIBLE);
                    holder.mTimeBrackets.get(i).populate(budget.getTimeBrackets().get(i));

                } else {
                    holder.mTimeBrackets.get(i).setVisible(View.GONE);
                }
            }

        } else {
            holder.mLayout_TimeBracket.setVisibility(View.GONE);
        }

        holder.mLabel_AmountCap.setVisibility( (budget.hasAmountCap()? View.VISIBLE: View.GONE) );
        holder.mTextView_AmountCap.setVisibility( (budget.hasAmountCap()? View.VISIBLE: View.GONE) );
        holder.mTextView_DueDate.setVisibility( (budget.hasDueDate()? View.VISIBLE: View.GONE) );
        holder.mLabel_DueDate.setVisibility( (budget.hasDueDate()? View.VISIBLE: View.GONE) );

        mCategoryPeriodicity_ArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        holder.mTextView_DueDate.setText( (budget.hasDueDate()? budget.dueDateToString(): "") );
        holder.mTextView_AmountCap.setText( (budget.hasAmountCap()? budget.getAmountCap(): "") );
    }

    @Override
    public int getItemCount() {
        return mBudgets.size();
    }

//    private BudgetTree tree;

    private void setupTestData() {
        mBudgets.add(new Budget("./Cars/Shop/Maintenance/Ford F150", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("125"), "Annually (1/yr)")))));
        mBudgets.add(new Budget("./Cars/Shop/Maintenance/Subaru Forester", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("125"), "Annually (1/yr)")))));
        mBudgets.add(new Budget("./Cars/Shop/Repairs/Ford F150", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("500"), "Annually (1/yr)")))));
        mBudgets.add(new Budget("./Cars/Shop/Repairs/Subaru Forester", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("150"), "Annually (1/yr)")))));
        mBudgets.add(new Budget("./Cars/Insurance", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("600"), "Monthly (12/yr)")))));

        mBudgets.add(new Budget("./Home/Mortgage/Regular", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("764"), "Monthly (12/yr)")))));
        mBudgets.add(new Budget("./Home/Mortgage/Additional principal", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("184"), "Monthly (12/yr)")))));
        mBudgets.add(new Budget("./Home/Mortgage/Taxes", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("1802"), "Annually (1/yr)"))), "2025-08-01", new Money("1802")));
        mBudgets.add(new Budget("./Home/Mortgage/Home Insurance", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("1132"), "Annually (1/yr)"))), "2025-08-01", new Money("1132")));
        mBudgets.add(new Budget("./Home/Utilities/Cellphone", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("45"), "Monthly (12/yr)")))));
        mBudgets.add(new Budget("./Home/Utilities/Water", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("170"), "Monthly (12/yr)")))));
        mBudgets.add(new Budget("./Home/Utilities/Electricity", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("117"), "Monthly (12/yr)")))));
        mBudgets.add(new Budget("./Home/Utilities/Natural Gas", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("60"), "Monthly (12/yr)")))));
        mBudgets.add(new Budget("./Home/Utilities/Fiber -- Utopia", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("30"), "Monthly (12/yr)")))));
        mBudgets.add(new Budget("./Home/Utilities/Internet", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("54"), "Monthly (12/yr)")))));

        mBudgets.add(new Budget("./Home/Maintenance/Repairs/Professional", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("35"), "Monthly (12/yr)")))));
        mBudgets.add(new Budget("./Home/Maintenance/Repairs/Self repairs", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("15"), "Monthly (12/yr)")))));

        mBudgets.add(new Budget("./Home/Maintenance/General/Household", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("35"), "Monthly (12/yr)")))));
        mBudgets.add(new Budget("./Home/Maintenance/General/Office", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("10"), "Monthly (12/yr)")))));
        mBudgets.add(new Budget("./Home/Maintenance/General/Yard", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("35"), "Monthly (12/yr)")))));

        mBudgets.add(new Budget("./Consumables/Food", new ArrayList<>(Arrays.asList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-03-31", new Money("45"), "Weekly (52/yr)"),
                new Budget_TimeBracket(0L, "2025-04-01", "2025-07-31", new Money("275"), "Weekly (52/yr)"),
                new Budget_TimeBracket(0L, "2025-08-01", "2025-10-31", new Money("275"), "Weekly (52/yr)"),
                new Budget_TimeBracket(0L, "2025-11-01", "2025-12-31", new Money("275"), "Weekly (52/yr)")))));
        mBudgets.add(new Budget("./Consumables/Non-Food", new ArrayList<>(Arrays.asList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-03-31", new Money("45"), "Weekly (52/yr)"),
                new Budget_TimeBracket(0L, "2025-04-01", "2025-12-31", new Money("50"), "Weekly (52/yr)")))));

        mBudgets.add(new Budget("./Personal/Health/Dental", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("11"), "Monthly (12/yr)")))));
        mBudgets.add(new Budget("./Personal/Health/Medical", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("9"), "Monthly (12/yr)")))));
        mBudgets.add(new Budget("./Personal/Health/Prescriptions", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("16"), "Monthly (12/yr)")))));
        mBudgets.add(new Budget("./Personal/Health/Copay", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("20"), "Monthly (12/yr)")))));
        mBudgets.add(new Budget("./Personal/Health/Vision", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("0"), "Monthly (12/yr)")))));
        mBudgets.add(new Budget("./Personal/Health/General", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("0"), "Monthly (12/yr)")))));
        mBudgets.add(new Budget("./Personal/Health/Hygiene", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("20"), "Monthly (12/yr)")))));

        mBudgets.add(new Budget("./Provisions/Education", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("20"), "Monthly (12/yr)")))));

        mBudgets.add(new Budget("./Provisions/Mad money/Father", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("20"), "Monthly (12/yr)")))));
        mBudgets.add(new Budget("./Provisions/Mad money/Mother", new ArrayList<>(Arrays.asList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-05-17", new Money("50"), "Weekly (52/yr)"),
                new Budget_TimeBracket(0L, "2025-05-18", "2025-12-31", new Money("0"), "Weekly (52/yr)"))
        )));

        mBudgets.add(new Budget("./Provisions/Personal Gifts/Family", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("200"), "Annually (1/yr)")))));
        mBudgets.add(new Budget("./Provisions/Personal Gifts/Other", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("200"), "Annually (1/yr)")))));
        mBudgets.add(new Budget("./Subscription Services/Entertainment/Amazon channels", new ArrayList<>(Arrays.asList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-03-30", new Money("0"), "Monthly (12/yr)"),
                new Budget_TimeBracket(0L, "2025-04-01", "2025-12-31", new Money("10"), "Monthly (12/yr)")))));
        mBudgets.add(new Budget("./Subscription Services/Entertainment/Rentals", new ArrayList<>(Arrays.asList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-07-17", new Money("3"), "Weekly (52/yr)"),
                new Budget_TimeBracket(0L, "2025-07-18", "2025-12-31", new Money("3"), "Weekly (52/yr)")))));

        mBudgets.add(new Budget("./Services/Google Drive", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("26"), "Annually (1/yr)")))));
        mBudgets.add(new Budget("./Services/Orem Rec Center", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("0"), "Annually (1/yr)"))), "2025-01-01", new Money("310")));
        mBudgets.add(new Budget("./Services/Amazon Prime", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("149"), "Annually (1/yr)"))), "2025-10-01", new Money("149")));
        mBudgets.add(new Budget("./Services/Audible", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("160"), "Annually (1/yr)"))), "2025-07-01", new Money("160")));
        mBudgets.add(new Budget("./Services/Accountants", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("335"), "Annually (1/yr)"))), "2025-05-01", new Money("330")));
        mBudgets.add(new Budget("./Services/Costco", new ArrayList<>(Collections.singletonList(
                new Budget_TimeBracket(0L, "2025-01-01", "2025-12-31", new Money("130"), "Annually (1/yr)"))), "2025-03-01", new Money("130")));
    }
}
