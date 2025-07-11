package com.waltoncraftsllc.waterfrontcashflow.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.waltoncraftsllc.waterfrontcashflow.R;

public class ExpenseFragment extends Fragment {

    private ExpenseViewModel mViewModel;
    private TextView[] mButtons = new TextView[5];
    private LinearLayout[] mLayout = new LinearLayout[5];

    public static ExpenseFragment newInstance() {
        return new ExpenseFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense, container, false);
        mLayout[0] = view.findViewById(R.id.linearLayout_group_1);
        mLayout[1] = view.findViewById(R.id.linearLayout_group_2);
        mLayout[2] = view.findViewById(R.id.linearLayout_group_3);
        mLayout[3] = view.findViewById(R.id.linearLayout_group_4);
        mLayout[4] = view.findViewById(R.id.linearLayout_group_5);
        mButtons[0] = view.findViewById(R.id.textView_expense_group_add_button_1);
        mButtons[1] = view.findViewById(R.id.textView_expense_group_add_button_2);
        mButtons[2] = view.findViewById(R.id.textView_expense_group_add_button_3);
        mButtons[3] = view.findViewById(R.id.textView_expense_group_add_button_4);
        mButtons[0].setOnClickListener(v -> {
            mLayout[1].setVisibility(View.VISIBLE);
            mButtons[0].setVisibility(View.INVISIBLE);
        });
        mButtons[1].setOnClickListener(v -> {
            mLayout[2].setVisibility(View.VISIBLE);
            mButtons[1].setVisibility(View.INVISIBLE);
        });
        mButtons[2].setOnClickListener(v -> {
            mLayout[3].setVisibility(View.VISIBLE);
            mButtons[2].setVisibility(View.INVISIBLE);
        });
        mButtons[3].setOnClickListener(v -> {
            mLayout[4].setVisibility(View.VISIBLE);
            mButtons[3].setVisibility(View.INVISIBLE);
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        // TODO: Use the ViewModel
    }

}