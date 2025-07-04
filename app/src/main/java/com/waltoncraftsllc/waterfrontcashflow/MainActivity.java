package com.waltoncraftsllc.waterfrontcashflow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.waltoncraftsllc.waterfrontcashflow.adapters.Budget_Adapter;
import com.waltoncraftsllc.waterfrontcashflow.adapters.ExpenseLog_Adapter;
import com.waltoncraftsllc.waterfrontcashflow.adapters.Sqlite_ConnectionHelper;
import com.waltoncraftsllc.waterfrontcashflow.databinding.ActivityMainBinding;
import com.waltoncraftsllc.waterfrontcashflow.tools.DatabaseContract;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayAdapter<CharSequence> mCategoryPeriodicity_ArrayAdapter;
    public static ArrayAdapter<CharSequence> mCategory_ArrayAdapter;
    public static ArrayAdapter<CharSequence> mTender_ArrayAdapter;

    static {
        System.loadLibrary("waterfrontcashflow");
    }

    private ActivityMainBinding binding;
    public Sqlite_ConnectionHelper db_helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db_helper = new Sqlite_ConnectionHelper(this.getApplicationContext(), DatabaseContract.DATABASE_NAME, null, DatabaseContract.DATABASE_VERSION);
        ArrayList<CharSequence> categories = db_helper.queryCategories();
        ArrayList<CharSequence> periodicities = db_helper.queryPeriodicities();
        ArrayList<CharSequence> tenders = db_helper.queryTenders();
        //TODO: fill periodicity spinner

        mCategoryPeriodicity_ArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, periodicities);
        mCategoryPeriodicity_ArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mCategory_ArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        mCategory_ArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mTender_ArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tenders);
        mTender_ArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recyclerViewMainActivity.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewMainActivity.setAdapter(new Budget_Adapter());
        binding.buttonBudgetPlanner.setOnClickListener((view) -> binding.recyclerViewMainActivity.setAdapter(new Budget_Adapter()));
        binding.buttonExpenseLog.setOnClickListener((view) -> binding.recyclerViewMainActivity.setAdapter(new ExpenseLog_Adapter()));

        // Example of a call to a native method
//        TextView tv = binding.sampleText;
//        tv.setText(stringFromJNI());
    }

    /**
     * A native method that is implemented by the 'waterfrontcashflow' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}