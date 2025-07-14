package com.waltoncraftsllc.waterfrontcashflow;

import static com.waltoncraftsllc.waterfrontcashflow.database.DatabaseContract.DATABASE_NAME;
import static com.waltoncraftsllc.waterfrontcashflow.database.DatabaseContract.DATABASE_VERSION;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.waltoncraftsllc.waterfrontcashflow.adapters.Budget_Adapter;
import com.waltoncraftsllc.waterfrontcashflow.adapters.Expense_Adapter;
import com.waltoncraftsllc.waterfrontcashflow.database.Sqlite_ConnectionHelper;
import com.waltoncraftsllc.waterfrontcashflow.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    private static ArrayAdapter<CharSequence> mPeriodicity_ArrayAdapter = null;
    private static ArrayAdapter<CharSequence> mCategory_ArrayAdapter = null;
    private static ArrayAdapter<CharSequence> mTender_ArrayAdapter = null;
    public static ArrayAdapter<CharSequence> getLegalTender_ArrayAdapter() {
        if ( mTender_ArrayAdapter == null ) {
            mTender_ArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, Sqlite_ConnectionHelper.getInstance().queryLegalTenders());
            mTender_ArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        }
        return mTender_ArrayAdapter;
    }
    public static ArrayAdapter<CharSequence> getPeriodicity_ArrayAdapter() {
        if ( mPeriodicity_ArrayAdapter == null ) {
            mPeriodicity_ArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, Sqlite_ConnectionHelper.getInstance().queryLegalTenders());
            mPeriodicity_ArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        }
        return mPeriodicity_ArrayAdapter;
    }
    public static ArrayAdapter<CharSequence> getCategory_ArrayAdapter() {
        if ( mCategory_ArrayAdapter == null ) {
            mCategory_ArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, Sqlite_ConnectionHelper.getInstance().queryLegalTenders());
            mCategory_ArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        }
        return mCategory_ArrayAdapter;
    }

    static {
        System.loadLibrary("waterfrontcashflow");
    }

    private ActivityMainBinding binding;
//    public Sqlite_ConnectionHelper db_helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        new Sqlite_ConnectionHelper(this.getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recyclerViewMainActivity.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewMainActivity.setAdapter(new Budget_Adapter());
        binding.buttonBudgetPlanner.setOnClickListener((view) -> binding.recyclerViewMainActivity.setAdapter(new Budget_Adapter()));
        binding.buttonExpense.setOnClickListener((view) -> binding.recyclerViewMainActivity.setAdapter(new Expense_Adapter()));

        // Example of a call to a native method
//        TextView tv = binding.sampleText;
//        tv.setText(stringFromJNI());
    }

//    /**
//     * A native method that is implemented by the 'waterfrontcashflow' native library,
//     * which is packaged with this application.
//     */
//    public native String stringFromJNI();
}