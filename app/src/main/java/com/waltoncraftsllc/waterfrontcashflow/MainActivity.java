package com.waltoncraftsllc.waterfrontcashflow;

import static com.waltoncraftsllc.waterfrontcashflow.database.DatabaseContract.DATABASE_NAME;
import static com.waltoncraftsllc.waterfrontcashflow.database.DatabaseContract.DATABASE_VERSION;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.waltoncraftsllc.waterfrontcashflow.adapters.Budget_Adapter;
import com.waltoncraftsllc.waterfrontcashflow.adapters.Expense_Adapter;
import com.waltoncraftsllc.waterfrontcashflow.database.Sqlite_ConnectionHelper;
import com.waltoncraftsllc.waterfrontcashflow.databinding.ActivityMainBinding;

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
        db_helper = new Sqlite_ConnectionHelper(this.getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);
        ArrayList<CharSequence> categories = db_helper.queryCategories();
        ArrayList<CharSequence> periodicities = db_helper.queryPeriodicities();
        ArrayList<CharSequence> tenders = db_helper.queryTenders();

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
        binding.buttonExpense.setOnClickListener((view) -> binding.recyclerViewMainActivity.setAdapter(new Expense_Adapter()));

        // Example of a call to a native method
//        TextView tv = binding.sampleText;
//        tv.setText(stringFromJNI());
    }

    /**
     * A native method that is implemented by the 'waterfrontcashflow' native library,
     * which is packaged with this application.
     */
//    public native String stringFromJNI();
}