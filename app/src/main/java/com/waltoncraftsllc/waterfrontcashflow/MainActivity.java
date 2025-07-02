package com.waltoncraftsllc.waterfrontcashflow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.waltoncraftsllc.waterfrontcashflow.adapters.Budget_Adapter;
import com.waltoncraftsllc.waterfrontcashflow.adapters.ExpenseLog_Adapter;
import com.waltoncraftsllc.waterfrontcashflow.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    public static ArrayAdapter<CharSequence> mCategoryPeriodicity_ArrayAdapter;
    public static ArrayAdapter<CharSequence> mCategory_ArrayAdapter;
    public static ArrayAdapter<CharSequence> mTender_ArrayAdapter;

    static {
        System.loadLibrary("waterfrontcashflow");
    }

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategoryPeriodicity_ArrayAdapter = ArrayAdapter.createFromResource(this, R.array.category_periodicity, android.R.layout.simple_spinner_item);
        mCategoryPeriodicity_ArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCategory_ArrayAdapter = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_item);
        mCategory_ArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTender_ArrayAdapter = ArrayAdapter.createFromResource(this, R.array.tender, android.R.layout.simple_spinner_item);
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