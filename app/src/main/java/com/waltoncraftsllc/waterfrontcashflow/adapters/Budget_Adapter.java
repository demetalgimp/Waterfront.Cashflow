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
import com.waltoncraftsllc.waterfrontcashflow.tools.BudgetItem;
import com.waltoncraftsllc.waterfrontcashflow.tools.BudgetItem_TimeBracket;
import com.waltoncraftsllc.waterfrontcashflow.tools.BudgetTree;
import com.waltoncraftsllc.waterfrontcashflow.tools.Money;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class Budget_Adapter extends RecyclerView.Adapter<Budget_Adapter.ViewHolder> {
    static ArrayList<BudgetItem> mBudgetItems = new ArrayList<>();

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
        public void populate(BudgetItem_TimeBracket item) {
            mTextView_FromDate.setText(item.getFromDate());
            mTextView_ToDate.setText(item.getToDate());
            mTextView_Amount.setText(String.valueOf(item.getAmount()));
            mSpinner_Periodicity.setAdapter(mCategoryPeriodicity_ArrayAdapter);
            int index = mCategoryPeriodicity_ArrayAdapter.getPosition(item.getPeriodicity_str());
            mSpinner_Periodicity.setSelection(index);
            Locale locale = new Locale("English", "United States");
            android.icu.util.Currency currency = android.icu.util.Currency.getInstance(locale);
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

        BudgetItem budgetItem = mBudgetItems.get(position);
        holder.mTextView_Name.setText(budgetItem.getName());

        //--- Fill the table of Groups
        if (budgetItem.getTimeBrackets() != null) {
            for (int i = 0; i < holder.mTimeBrackets.size(); i++) {
                holder.mLayout_TimeBracket.setVisibility(View.VISIBLE);
                //--- Ensure that the number of rows match
                if (i < budgetItem.getTimeBrackets().size()) {
                    holder.mTimeBrackets.get(i).setVisible(View.VISIBLE);
                    holder.mTimeBrackets.get(i).populate(budgetItem.getTimeBrackets().get(i));

                } else {
                    holder.mTimeBrackets.get(i).setVisible(View.GONE);
                }
            }

        } else {
            holder.mLayout_TimeBracket.setVisibility(View.GONE);
        }

        holder.mLabel_AmountCap.setVisibility( (budgetItem.hasAmountCap()? View.VISIBLE: View.GONE) );
        holder.mTextView_AmountCap.setVisibility( (budgetItem.hasAmountCap()? View.VISIBLE: View.GONE) );
        holder.mTextView_DueDate.setVisibility( (budgetItem.hasDueDate()? View.VISIBLE: View.GONE) );
        holder.mLabel_DueDate.setVisibility( (budgetItem.hasDueDate()? View.VISIBLE: View.GONE) );

        mCategoryPeriodicity_ArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        holder.mTextView_DueDate.setText( (budgetItem.hasDueDate()? budgetItem.dueDateToString(): "") );
        holder.mTextView_AmountCap.setText( (budgetItem.hasAmountCap()? budgetItem.getAmountCap(): "") );
    }

    @Override
    public int getItemCount() {
        return mBudgetItems.size();
    }

    private BudgetTree tree;

    private void setupTestData() {
        mBudgetItems.add(new BudgetItem("./Cars/Insurance", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("600"), "Monthly")
                }))));
        mBudgetItems.add(new BudgetItem("./Cars/Shop/Maintenance/Ford F150", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("125"), "Annually")
                }))));
        mBudgetItems.add(new BudgetItem("./Cars/Shop/Maintenance/Subaru Forester", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("125"), "Annually")
                }))));
        mBudgetItems.add(new BudgetItem("./Cars/Shop/Repairs/Ford F150", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("500"), "Annually")
                }))));
        mBudgetItems.add(new BudgetItem("./Cars/Shop/Repairs/Subaru Forester", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("150"), "Annually")
                }))));

        mBudgetItems.add(new BudgetItem("./Home/Mortgage/Regular", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("764"), "Monthly")
                }))));
        mBudgetItems.add(new BudgetItem("./Home/Mortgage/Additional principal", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("184"), "Monthly")
                }))));
        mBudgetItems.add(new BudgetItem("./Home/Mortgage/Taxes", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("1802"), "Annually")
                })), "2025-08-01", new Money("1802")));
        mBudgetItems.add(new BudgetItem("./Home/Mortgage/Home Insurance", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("1132"), "Annually")
                })), "2025-08-01", new Money("1132")));
        mBudgetItems.add(new BudgetItem("./Home/Utilities/Cellphone", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("45"), "Monthly")
                }))));
        mBudgetItems.add(new BudgetItem("./Home/Utilities/Water", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("170"), "Monthly")
                }))));
        mBudgetItems.add(new BudgetItem("./Home/Utilities/Electricity", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("117"), "Monthly")
                }))));
        mBudgetItems.add(new BudgetItem("./Home/Utilities/Natural Gas", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("60"), "Monthly")
                }))));
        mBudgetItems.add(new BudgetItem("./Home/Utilities/Fiber -- Utopia", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("30"), "Monthly")
                }))));
        mBudgetItems.add(new BudgetItem("./Home/Utilities/Internet", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("54"), "Monthly")
                }))));

        mBudgetItems.add(new BudgetItem("./Home/Maintenance/Repairs/Professional", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("35"), "Monthly")
                }))));
        mBudgetItems.add(new BudgetItem("./Home/Maintenance/Repairs/Self repairs", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("15"), "Monthly")
                }))));

        mBudgetItems.add(new BudgetItem("./Home/Maintenance/General/Household", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("35"), "Monthly")
                }))));
        mBudgetItems.add(new BudgetItem("./Home/Maintenance/General/Office", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("10"), "Monthly")
                }))));
        mBudgetItems.add(new BudgetItem("./Home/Maintenance/General/Yard", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("35"), "Monthly")
                }))));

        mBudgetItems.add(new BudgetItem("./Consumables/Food", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-03-31", new Money("45"), "Weekly"),
                        new BudgetItem_TimeBracket("2025-04-01", "2025-07-31", new Money("275"), "Weekly"),
                        new BudgetItem_TimeBracket("2025-08-01", "2025-10-31", new Money("275"), "Weekly"),
                        new BudgetItem_TimeBracket("2025-11-01", "2025-12-31", new Money("275"), "Weekly")
                }))));
        mBudgetItems.add(new BudgetItem("./Consumables/Non-Food", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-03-31", new Money("45"), "Weekly"),
                        new BudgetItem_TimeBracket("2025-04-01", "2025-12-31", new Money("50"), "Weekly")
                }))));

        mBudgetItems.add(new BudgetItem("./Personal/Health/Dental", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("11"), "Monthly")
                }))));
        mBudgetItems.add(new BudgetItem("./Personal/Health/Medical", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("9"), "Monthly")
                }))));
        mBudgetItems.add(new BudgetItem("./Personal/Health/Prescriptions", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("16"), "Monthly")
                }))));
        mBudgetItems.add(new BudgetItem("./Personal/Health/Copay", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("20"), "Monthly")
                }))));
        mBudgetItems.add(new BudgetItem("./Personal/Health/Vision", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("0"), "Monthly")
                }))));
        mBudgetItems.add(new BudgetItem("./Personal/Health/General", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("0"), "Monthly")
                }))));
        mBudgetItems.add(new BudgetItem("./Personal/Health/Hygiene", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("20"), "Monthly")
                }))));

        mBudgetItems.add(new BudgetItem("./Provisions/Education", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("20"), "Monthly")
                }))));

        mBudgetItems.add(new BudgetItem("./Provisions/Mad money/Father", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("20"), "Monthly")
                }))));
        mBudgetItems.add(new BudgetItem("./Provisions/Mad money/Mother", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-05-17", new Money("50"), "Weekly"),
                        new BudgetItem_TimeBracket("2025-05-18", "2025-12-31", new Money("0"), "Weekly")
                })
        )));

        mBudgetItems.add(new BudgetItem("./Provisions/Personal Gifts/Family", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("200"), "Annually")
                }))));
        mBudgetItems.add(new BudgetItem("./Provisions/Personal Gifts/Other", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("200"), "Annually")
                }))));
        mBudgetItems.add(new BudgetItem("./Subscription Services/Entertainment/Amazon channels", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-03-30", new Money("0"), "Monthly"),
                        new BudgetItem_TimeBracket("2025-04-01", "2025-12-31", new Money("10"), "Monthly")
                }))));
        mBudgetItems.add(new BudgetItem("./Subscription Services/Entertainment/Rentals", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-07-17", new Money("3"), "Weekly"),
                        new BudgetItem_TimeBracket("2025-07-18", "2025-12-31", new Money("3"), "Weekly")
                }))));

        mBudgetItems.add(new BudgetItem("./Services/Google Drive", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("26"), "Annually")
                }))));
        mBudgetItems.add(new BudgetItem("./Services/Orem Rec Center", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("0"), "Annually")
                })), "2025-01-01", new Money("310")));
        mBudgetItems.add(new BudgetItem("./Services/Amazon Prime", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("149"), "Annually")
                })), "2025-10-01", new Money("149")));
        mBudgetItems.add(new BudgetItem("./Services/Audible", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("160"), "Annually")
                })), "2025-07-01", new Money("160")));
        mBudgetItems.add(new BudgetItem("./Services/Accountants", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("335"), "Annually")
                })), "2025-05-01", new Money("330")));
        mBudgetItems.add(new BudgetItem("./Services/Costco", new ArrayList<>(Arrays.asList(
                new BudgetItem_TimeBracket[]{
                        new BudgetItem_TimeBracket("2025-01-01", "2025-12-31", new Money("130"), "Annually")
                })), "2025-03-01", new Money("130")));
    }
}
