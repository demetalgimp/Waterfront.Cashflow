package com.waltoncraftsllc.waterfrontcashflow.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.waltoncraftsllc.waterfrontcashflow.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    TextView[] mCalendarTextViews;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCalendarTextViews = new TextView[mCalendarIDs.length];
        for ( int i = 0; i < mCalendarIDs.length; i++ ) {
            mCalendarTextViews[i] = view.findViewById(mCalendarIDs[i]);
            mCalendarTextViews[i].setOnClickListener(v -> {
                v.setBackgroundColor(0);
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }
    //            R.id.view_Calendar_name,
    final int[] mCalendarIDs = {
            R.id.textView_Sunday_week1, R.id.textView_Monday_week1, R.id.textView_Tuesday_week1, R.id.textView_Wednesday_week1,
            R.id.textView_Thursday_week1, R.id.textView_Friday_week1, R.id.textView_Saturday_week1,
            R.id.textView_Sunday_week2, R.id.textView_Monday_week2, R.id.textView_Tuesday_week2, R.id.textView_Wednesday_week2,
            R.id.textView_Thursday_week2, R.id.textView_Friday_week2, R.id.textView_Saturday_week2,
            R.id.textView_Sunday_week3, R.id.textView_Monday_week3, R.id.textView_Tuesday_week3, R.id.textView_Wednesday_week3,
            R.id.textView_Thursday_week3, R.id.textView_Friday_week3, R.id.textView_Saturday_week3,
            R.id.textView_Sunday_week4, R.id.textView_Monday_week4, R.id.textView_Tuesday_week4, R.id.textView_Wednesday_week4,
            R.id.textView_Thursday_week4, R.id.textView_Friday_week4, R.id.textView_Saturday_week4,
            R.id.textView_Sunday_week5, R.id.textView_Monday_week5, R.id.textView_Tuesday_week5, R.id.textView_Wednesday_week5,
            R.id.textView_Thursday_week5, R.id.textView_Friday_week5, R.id.textView_Saturday_week5,
            R.id.textView_Sunday_week6, R.id.textView_Monday_week6, R.id.textView_Tuesday_week6, R.id.textView_Wednesday_week6,
            R.id.textView_Thursday_week6, R.id.textView_Friday_week6, R.id.textView_Saturday_week6
    };
}

