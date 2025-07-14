package com.waltoncraftsllc.waterfrontcashflow.fragments;

import static com.waltoncraftsllc.waterfrontcashflow.MainActivity.getCategory_ArrayAdapter;

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

import com.waltoncraftsllc.waterfrontcashflow.R;

import java.util.ArrayList;

public class ExpenseGroupListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public ExpenseGroupListFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment ExpenseGroupListFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static ExpenseGroupListFragment newInstance(String param1, String param2) {
//        ExpenseGroupListFragment fragment = new ExpenseGroupListFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expense_group_list, container, false);
    }
    ArrayList<LinearLayout> mLayouts = new ArrayList<>();
    ArrayList<TextView> mButtons = new ArrayList<>();
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for ( int id : new int[] {
                R.id.linearLayout_group_1,
                R.id.linearLayout_group_2,
                R.id.linearLayout_group_3,
                R.id.linearLayout_group_4,
                R.id.linearLayout_group_5} ) {
            mLayouts.add(view.findViewById(id));
        }
        for ( int id : new int[] {
                R.id.textView_expense_group_add_button_1,
                R.id.textView_expense_group_add_button_2,
                R.id.textView_expense_group_add_button_3,
                R.id.textView_expense_group_add_button_4} ) {
            mButtons.add(view.findViewById(id));
        }
//        for ( int i = 1; i < mLayouts.size() - 1; i++ ) {
//            mLayouts.get(i).setVisibility(View.GONE);
//        }
        for ( int id : new int[] {
                R.id.spinner_category_1,
                R.id.spinner_category_2,
                R.id.spinner_category_3,
                R.id.spinner_category_4,
                R.id.spinner_category_5} ) {
            ((Spinner)view.findViewById(id)).setAdapter(getCategory_ArrayAdapter());
        }
        mButtons.get(0).setOnClickListener(v -> {
            mLayouts.get(1).setVisibility(View.VISIBLE);
//            mButtons.get(0).setVisibility(View.INVISIBLE);
            mButtons.get(0).setText("–");
        });
        mButtons.get(1).setOnClickListener(v -> {
            mLayouts.get(2).setVisibility(View.VISIBLE);
//            mButtons.get(1).setVisibility(View.INVISIBLE);
            mButtons.get(1).setText("–");
        });
        mButtons.get(2).setOnClickListener(v -> {
            mLayouts.get(3).setVisibility(View.VISIBLE);
//            mButtons.get(2).setVisibility(View.INVISIBLE);
            mButtons.get(2).setText("–");
        });
        mButtons.get(3).setOnClickListener(v -> {
            mLayouts.get(4).setVisibility(View.VISIBLE);
//            mButtons.get(3).setVisibility(View.INVISIBLE);
            mButtons.get(3).setText("–");
        });
    }

}