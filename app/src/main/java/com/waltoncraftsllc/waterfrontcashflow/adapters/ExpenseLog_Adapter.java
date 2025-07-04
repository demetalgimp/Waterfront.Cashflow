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
import com.waltoncraftsllc.waterfrontcashflow.tools.ExpenseLogItem;
import com.waltoncraftsllc.waterfrontcashflow.tools.ExpenseLogItem_Group;
import com.waltoncraftsllc.waterfrontcashflow.tools.Money;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ExpenseLog_Adapter extends RecyclerView.Adapter<ExpenseLog_Adapter.ViewHolder> {
    static ArrayList<ExpenseLogItem> items = new ArrayList<>();

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
        public void populate(ExpenseLogItem_Group item) {
            mSpinner_Category.setAdapter(mCategory_ArrayAdapter);
            int index = mCategory_ArrayAdapter.getPosition(item.getCategory());
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

    public ExpenseLog_Adapter() {
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

        ExpenseLogItem expenseLogItem = items.get(position);
        holder.mTextView_Date.setText(expenseLogItem.dateToString());
        holder.mSpinner_Tender.setAdapter(mTender_ArrayAdapter);
        index = mTender_ArrayAdapter.getPosition(expenseLogItem.getTender());
        holder.mSpinner_Tender.setSelection(index);
        holder.mTextView_Vendor.setText(expenseLogItem.getVendor());

        //--- Fill the table of Groups
        for (int i = 0; i < holder.mExpenseLogGroups.size(); i++ ) {
            //--- Ensure that the number of rows match
            if ( i < expenseLogItem.getGroup().size() ) {
                holder.mExpenseLogGroups.get(i).setVisible(View.VISIBLE);
                holder.mExpenseLogGroups.get(i).populate(expenseLogItem.getGroup().get(i));

            } else {
                holder.mExpenseLogGroups.get(i).setVisible(View.GONE);
            }
        }

        holder.mCheckbox_Receipt.setChecked(expenseLogItem.hasReceipt());
        holder.mCheckbox_Server.setChecked(expenseLogItem.hasServer());
        holder.mCheckbox_IsRecurring.setChecked(expenseLogItem.isRecurring());
        holder.mSpinner_Periodicity.setVisibility( (expenseLogItem.isRecurring()? View.VISIBLE: View.GONE) );
        holder.mCheckbox_IsRecurring.setOnClickListener(v -> {
            holder.mSpinner_Periodicity.setVisibility((((CheckBox) v).isChecked() ? View.VISIBLE : View.GONE));
        });

        if ( expenseLogItem.isRecurring() ) {
            holder.mSpinner_Periodicity.setAdapter(mCategoryPeriodicity_ArrayAdapter);
            index = mCategoryPeriodicity_ArrayAdapter.getPosition(expenseLogItem.getRecurring());
            holder.mSpinner_Periodicity.setSelection(index);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static void setupTestData() {
        items.add(new ExpenseLogItem("2025-05-28", "CC-Credit card", "Lowe's",
                new ArrayList<>(Collections.singletonList(new ExpenseLogItem_Group("Repairs (home)", new Money("-141.54"), new Money("9.81"), ""))),
                "", "✔", "✔")
        );
        items.add(new ExpenseLogItem("2025-05-28", "CC-Credit card", "Walmart",
                new ArrayList<>(Collections.singletonList(new ExpenseLogItem_Group("Food (consumables)", new Money("-15.33"), new Money("0.45"), ""))),
                "", "✔", "✔")
        );
        items.add(new ExpenseLogItem("2025-05-29", "CC-Credit card", "Harbor Freight",
                new ArrayList<>(Collections.singletonList(new ExpenseLogItem_Group("Repairs (home)", new Money("-10.72"), new Money("0.74"), ""))),
                "", "✔", "✔")
        );
        items.add(new ExpenseLogItem("2025-05-29", "CC-Credit card", "RevereHealth",
                new ArrayList<>(Collections.singletonList(new ExpenseLogItem_Group("Copay (personal health)", new Money("-162.99"), new Money("0.00"), ""))),
                "", "", "✔")
        );
        items.add(new ExpenseLogItem("2025-05-29", "CK-checking", "The Church of Jesus Christ of Latter-day Saints",
                new ArrayList<>(Collections.singletonList(new ExpenseLogItem_Group("Fast Offering (Charity)", new Money("-50.00"), new Money("0.00"), ""))),
                "", "", "✔")
        );
        items.add(new ExpenseLogItem("2025-05-29", "CK-checking", "The Church of Jesus Christ of Latter-day Saints",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Non-Food (consumables)", new Money("-338.00"), new Money("0.00"), ""))),
                "", "", "✔")
        );
        items.add(new ExpenseLogItem("2025-05-29", "CK-checking", "The Church of Jesus Christ of Latter-day Saints",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Non-Food (consumables)", new Money("-172.00"), new Money("0.00"), ""))),
                "", "", "✔")
        );
        items.add(new ExpenseLogItem("2025-05-29", "CK-checking", "The Church of Jesus Christ of Latter-day Saints",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Non-Food (consumables)", new Money("-172.00"), new Money("0.00"), ""))),
                "", "", "✔")
        );
        items.add(new ExpenseLogItem("2025-05-29", "CK-checking", "The Church of Jesus Christ of Latter-day Saints",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Non-Food (consumables)", new Money("-172.00"), new Money("0.00"), ""))),
                "", "", "✔")
        );
        items.add(new ExpenseLogItem("2025-05-29", "CK-checking", "The Church of Jesus Christ of Latter-day Saints",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Tithing (charity)", new Money("-135.10"), new Money("0.00"), ""))),
                "", "", "✔")
        );
        items.add(new ExpenseLogItem("2025-05-29", "DC-debit card", "Winco Foods",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Food (consumables)", new Money("-12.80"), new Money("0.37"), ""))),
                "", "✔", "✔")
        );
        items.add(new ExpenseLogItem("2025-05-30", "CA-cash", "Memo's Bakery",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Food (consumables)", new Money("-10.21"), new Money("0.71"), ""))),
                "", "✔", "✖")
        );
        items.add(new ExpenseLogItem("2025-05-31", "CC-Credit card", "CostCo Wholesale",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Food (consumables)", new Money("-115.39"), new Money("3.31"), ""))),
                "", "✔", "✔")
        );
        items.add(new ExpenseLogItem("2025-05-31", "CC-Credit card", "CostCo Wholesale",
                new ArrayList<>(Arrays.asList(
                        new ExpenseLogItem_Group("Non-food (consumables)", new Money("-11.81"), new Money("0.82"), ""),
                        new ExpenseLogItem_Group("Food (consumables)", new Money("9.99"), new Money("0.00"), ""))),
                "", "", "")
        );
        items.add(new ExpenseLogItem("2025-05-31", "CC-Credit card", "CostCo Wholesale",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Food (consumables)", new Money("-125.60"), new Money("0.00"), ""))),
                "", "", "✔")
        );
        items.add(new ExpenseLogItem("2025-05-31", "GC-gift card", "CVS Pharmacy",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Food (consumables)", new Money("-18.45"), new Money("0.54"), ""))),
                "", "✔", "n/a")
        );
        items.add(new ExpenseLogItem("2025-05-31", "Venmo", "John",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Food (consumables)", new Money("1.00"), new Money("0.00"), ""))),
                "", "n/a", "✔")
        );
        items.add(new ExpenseLogItem("2025-05-31", "CK-checking", "Newrez",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Mortgage (home)", new Money("-1200.00"), new Money("0.00"), ""))),
                "Monthly", "n/a", "✔")
        );
        items.add(new ExpenseLogItem("2025-05-31", "RE-reimbursement", "T.Mobile",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Utilities (home)", new Money("18.95"), new Money("0.00"), "who? May"))),
                "", "", "✔")
        );
        items.add(new ExpenseLogItem("2025-05-31", "RE-reimbursement", "T.Mobile",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Utilities (home)", new Money("18.95"), new Money("0.00"), "who? June"))),
                "", "", "✔")
        );
        items.add(new ExpenseLogItem("2025-05-31", "CC-Credit card", "Target/CVS",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Prescriptions (personal health)", new Money("-70.91"), new Money("0.00"), ""))),
                "", "✔", "✔")
        );
        items.add(new ExpenseLogItem("2025-05-31", "CC-Credit card", "Target",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Food (consumables)", new Money("-2.87"), new Money("0.08"), ""))),
                "", "✔", "✔")
        );
        items.add(new ExpenseLogItem("2025-05-31", "DC-debit card", "Winco Foods",
                new ArrayList<>(Arrays.asList(
                        new ExpenseLogItem_Group("Food (consumables)", new Money("-133.31"), new Money("1.44"), ""),
                        new ExpenseLogItem_Group("Non-Food (consumables)", new Money("-20.75"), new Money("1.44"), ""))),
                "", "✔", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-01", "VM-Venmo", "T.Mobile",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Utilities (home)", new Money("14.66"), new Money("0.00"), "Elizabeth"))),
                "", "n/a", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-01", "CA-cash", "T.Mobile",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Utilities (home)", new Money("30.00"), new Money("0.00"), "John"))),
                "", "n/a", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-01", "CC-Credit card", "Utopia Fiber",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Utilities (home)", new Money("-30.00"), new Money("0.00"), ""))),
                "Monthly", "n/a", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-02", "CC-Credit card", "eBay",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Mad Money", new Money("-29.67"), new Money("1.41"), ""))),
                "", "", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-02", "CK-checking", "Rocky Mountain Power",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Utilities (home)", new Money("-117.00"), new Money("14.68"), ""))),
                "Monthly", "✔", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-02", "CC-Credit card", "USCCA",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Services (subscriptions)", new Money("-33.23"), new Money("0.00"), "Sean"))),
                "Monthly", "n/a", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-03", "CC-Credit card", "Audible",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Entertainment (subscriptions)", new Money("-4.74"), new Money("0.00"), ""))),
                "", "", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-03", "CC-Credit card", "Audible",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Entertainment (subscriptions)", new Money("-4.19"), new Money("0.00"), ""))),
                "", "", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-03", "CC-Credit card", "Audible",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Entertainment (subscriptions)", new Money("-4.74"), new Money("0.00"), ""))),
                "", "", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-03", "CC-Credit card", "Audible",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Entertainment (subscriptions)", new Money("-4.74"), new Money("0.00"), ""))),
                "", "", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-03", "CC-Credit card", "Holiday Oil",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Fuel (Car)", new Money("-46.64"), new Money("0.00"), ""))),
                "", "", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-03", "CA-cash", "Memo's Bakery",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Food (consumables)", new Money("-6.18"), new Money("0.43"), ""))),
                "", "✔", "n/a")
        );
        items.add(new ExpenseLogItem("2025-06-04", "CC-Credit card", "Audible",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Entertainment (subscriptions)", new Money("-4.74"), new Money("0.00"), ""))),
                "", "", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-04", "CC-Credit card", "Great Clips",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Haircuts (personal health)", new Money("-26.00"), new Money("0.00"), ""))),
                "", "✔", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-05", "CA-cash", "Memo's Bakery",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Food (consumables)", new Money("-6.72"), new Money("0.47"), ""))),
                "", "✔", "n/a")
        );
        items.add(new ExpenseLogItem("2025-06-05", "CC-Credit card", "Savory Thai Orem",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Food (consumables)", new Money("-27.05"), new Money("0.00"), ""))),
                "", "", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-05", "DC-debit card", "T.Mobile",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Utilities (home)", new Money("-205.00"), new Money("0.61"), ""))),
                "Monthly", "✔", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-05", "CC-Credit card", "Target",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Food (consumables)", new Money("-1.43"), new Money("0.04"), ""))),
                "", "✔", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-06", "CC-Credit card", "Gunnies",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Mad Money", new Money("-64.46"), new Money("4.47"), ""))),
                "", "✔", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-06", "CC-Credit card", "Walmart",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Food (consumables)", new Money("-9.98"), new Money("0.29"), ""))),
                "", "✔", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-07", "CC-Credit card", "CostCo Wholesale",
                new ArrayList<>(Arrays.asList(
                        new ExpenseLogItem_Group("Food (consumables)", new Money("-45.16"), new Money("1.32"), ""),
                        new ExpenseLogItem_Group("Non-Food (consumables)", new Money("-10.73"), new Money("0.74"), ""))),
                "", "", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-07", "CC-Credit card", "Get Some Guns",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Mad Money", new Money("-13.70"), new Money("0.95"), ""))),
                "", "✔", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-07", "CC-Credit card", "Michael's",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Mad Money", new Money("-5.25"), new Money("0.36"), ""))),
                "", "✔", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-07", "DC-debit card", "Winco Foods",
                new ArrayList<>(Arrays.asList(
                        new ExpenseLogItem_Group("Food (consumables)", new Money("-111.45"), new Money("3.25"), ""),
                        new ExpenseLogItem_Group("Non-Food (consumables)", new Money("-3.20"), new Money("0.22"), ""))),
                "", "✔", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-08", "CC-Credit card", "Pluralsight",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Services (subscriptions)", new Money("240.00"), new Money("0.00"), ""))),
                "", "", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-09", "PP-Paypal", "(Chinese robbers - \"thermal camera\")",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Mad Money", new Money("45.98"), new Money("0.00"), ""))),
                "", "", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-09", "CK-checking", "Enbridge Gas",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Utilities (home)", new Money("-56.00"), new Money("0.00"), ""))),
                "Monthly", "✔", "")
        );
        items.add(new ExpenseLogItem("2025-06-09", "CC-Credit card", "Holiday Oil",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Fuel (car)", new Money("-69.71"), new Money("7.98"), "21.86 gal"))),
                "", "✔", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-09", "CC-Credit card", "Web*BlueHost.com",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Services (subscriptions)", new Money("-2.68"), new Money("0.19"), ""))),
                "Monthly", "n/a", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-10", "CC-Credit card", "Provo InstaCare",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Copay (personal health)", new Money("-10.00"), new Money("0.00"), ""))),
                "", "", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-10", "RE-reimbursement", "T.Mobile",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Utilities (home)", new Money("14.58"), new Money("0.00"), "David"))),
                "", "", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-11", "CC-Credit card", "Amazon",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Mad Money", new Money("-23.19"), new Money("1.61"), ""))),
                "", "", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-11", "CC-Credit card", "Target",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Prescriptions (personal health)", new Money("-24.66"), new Money("0.00"), ""))),
                "", "", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-11", "CK-checking", "XMission",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Utilities (home)", new Money("-54.00"), new Money("0.00"), ""))),
                "Monthly", "n/a", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-12", "CC-Credit card", "Amazon",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Yard (home)", new Money("-21.48"), new Money("1.49"), ""))),
                "", "", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-12", "CK-checking", "City of Orem",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Utilities (home)", new Money("-109.78"), new Money("0.00"), ""))),
                "Monthly", "", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-12", "DC-debit card", "Winco Foods",
                new ArrayList<>(Arrays.asList(
                        new ExpenseLogItem_Group("Food (consumables)", new Money("-9.91"), new Money("0.29"), ""),
                        new ExpenseLogItem_Group("Non-Food (consumables)", new Money("-38.80"), new Money("2.69"), ""))),
                "", "✔", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-13", "CC-Credit card", "Audible",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Entertainment (subscriptions)", new Money("-49.42"), new Money("0.00"), ""))),
                "", "", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-14", "CC-Credit card", "Amazon",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Yard (home)", new Money("-23.63"), new Money("1.64"), ""))),
                "", "", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-14", "CC-Credit card", "Amazon",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Yard (home)", new Money("-40.82"), new Money("2.83"), ""))),
                "", "", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-14", "CC-Credit card", "CostCo Wholesale",
                new ArrayList<>(Arrays.asList(
                        new ExpenseLogItem_Group("Food (consumables)", new Money("-101.42"), new Money("3.04"), ""),
                        new ExpenseLogItem_Group("Non-Food (consumables)", new Money("-73.34"), new Money("4.88"), ""),
                        new ExpenseLogItem_Group("Non-Food (consumables)", new Money("-73.34"), new Money("4.88"), ""))),
                "", "✔", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-14", "CC-Credit card", "Great Clips",
                new ArrayList<>(Arrays.asList(
                        new ExpenseLogItem_Group("Haircuts (personal health)", new Money("-54.00"), new Money("0.00"), ""),
                        new ExpenseLogItem_Group("Non-Food (consumables)", new Money("-73.34"), new Money("4.88"), ""),
                        new ExpenseLogItem_Group("Non-Food (consumables)", new Money("-73.34"), new Money("4.88"), ""),
                        new ExpenseLogItem_Group("Non-Food (consumables)", new Money("-73.34"), new Money("4.88"), ""))),
                "", "✔", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-14", "CC-Credit card", "LabCorp",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Copay (personal health)", new Money("-15.00"), new Money("0.00"), ""))),
                "", "", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-17", "CC-Credit card", "HUR Jewelers",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Mad Money", new Money("-244.99"), new Money("0.00"), ""))),
                "", "✔", "")
        );
        items.add(new ExpenseLogItem("2025-06-17", "CC-Credit card", "Mt Timpanogos Temple",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Mad Money", new Money("-6.00"), new Money("0.00"), ""))),
                "", "✔", "")
        );
        items.add(new ExpenseLogItem("2025-06-17", "CC-Credit card", "Utah Dept of Commerce",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Mad Money", new Money("-18.00"), new Money("0.00"), ""))),
                "", "✔", "")
        );
        items.add(new ExpenseLogItem("2025-06-18", "CK-checking", "The Church of Jesus Christ of Latter-day Saints",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Tithing (Charity)", new Money("-172.00"), new Money("0.00"), ""))),
                "", "✔", "")
        );
        items.add(new ExpenseLogItem("2025-06-18", "CK-checking", "The Church of Jesus Christ of Latter-day Saints",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Tithing (Charity)", new Money("-172.00"), new Money("0.00"), ""))),
                "", "✔", "")
        );
        items.add(new ExpenseLogItem("2025-06-18", "CK-checking", "The Church of Jesus Christ of Latter-day Saints",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Tithing (Charity)", new Money("-172.00"), new Money("0.00"), ""))),
                "", "✔", "")
        );
        items.add(new ExpenseLogItem("2025-06-19", "DC-debit card", "Winco Foods",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Food (consumables)", new Money("-14.74"), new Money("0.43"), ""))),
                "", "✔", "")
        );
        items.add(new ExpenseLogItem("2025-06-20", "CC-Credit card", "Gunnies",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Mad Money", new Money("64.46"), new Money("-4.47"), ""))),
                "", "✔", "")
        );
        items.add(new ExpenseLogItem("2025-06-20", "CC-Credit card", "Target/CVS",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Prescriptions (personal health)", new Money("-161.15"), new Money("0.00"), ""))),
                "", "✔", "")
        );
        items.add(new ExpenseLogItem("2025-06-21", "CC-Credit card", "Amazon Digital",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Utilities (home)", new Money("-3.75"), new Money("0.26"), "Rachel"))),
                "Monthly", "", "✔")
        );
        items.add(new ExpenseLogItem("2025-06-21", "CC-Credit card", "CostCo Wholesale",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Food (consumables)", new Money("-69.07"), new Money("1.76"), ""))),
                "", "", "")
        );
        items.add(new ExpenseLogItem("2025-06-21", "CC-Credit card", "CostCo Wholesale",
                new ArrayList<>(Collections.singletonList(new ExpenseLogItem_Group("Non-Food (consumables)", new Money("-12.85"), new Money("0.86"), ""))),
                "", "✔", "")
        );
        items.add(new ExpenseLogItem("2025-06-21", "VM-Venmo", "T.Mobile",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Utilities (home)", new Money("42.52"), new Money("0.00"), "Risa"))),
                "", "", "")
        );
        items.add(new ExpenseLogItem("2025-06-22", "VM-Venmo", "T.Mobile",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Utilities (home)", new Money("27.63"), new Money("0.00"), "Elizabeth"))),
                "", "", "")
        );
        items.add(new ExpenseLogItem("2025-06-23", "CK-checking", "Geico",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Insurance (Car)", new Money("-303.00"), new Money("0.00"), ""))),
                "", "", "")
        );
        items.add(new ExpenseLogItem("2025-06-30", "CK-checking", "Newrez",
                new ArrayList<>(Collections.singletonList(
                        new ExpenseLogItem_Group("Mortgage (home)", new Money("-1200.00"), new Money("0.00"), ""))),
                "Monthly", "n/a", "")
        );
    }
}
