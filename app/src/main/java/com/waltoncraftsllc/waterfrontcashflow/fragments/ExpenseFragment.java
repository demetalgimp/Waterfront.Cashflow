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
import android.widget.Spinner;
import android.widget.TextView;

import com.waltoncraftsllc.waterfrontcashflow.MainActivity;
import com.waltoncraftsllc.waterfrontcashflow.R;

public class ExpenseFragment extends Fragment {

    private ExpenseViewModel mViewModel;

    public static ExpenseFragment newInstance() {
        return new ExpenseFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense, container, false);
        ((Spinner)view.findViewById(R.id.spinner_tender)).setAdapter(MainActivity.getLegalTender_ArrayAdapter());
        return view;
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
////         TODO: Use the ViewModel
//    }
}