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
import com.waltoncraftsllc.waterfrontcashflow.widget.VerticalTextView;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

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
//    private String mParam1;
//    private String mParam2;
    TextView mView_CurrentMonth;
    VerticalTextView mView_PreviousMonth;
    VerticalTextView mView_NextMonth;

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
    private static TextView[] mCalendarTextViews;
    private LocalDate mThisMonth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void writeMonth(TextView view, LocalDate date) {
        Month month = date.getMonth();
        String month_name = month.getDisplayName(TextStyle.FULL, Locale.US);
        view.setText(String.format(Locale.US, "  %s (%04d)  ", month_name, date.getYear()));
//        view.setBackgroundColor(0xFF0000);
    }

    /** private void fillCalendarView(LocalDate date)
     * Fill
     * @param date
     */
    private void fillCalendarView(LocalDate date) {
        mThisMonth = date;
        int start_day_of_month = LocalDate.of(date.getYear(), date.getMonth(), 1).getDayOfWeek().getValue();
        int days_in_current_month = mThisMonth.lengthOfMonth();
        int days_in_previous_month = mThisMonth.minusMonths(1).lengthOfMonth();
/******************************************************************************************************
                                                  [0...(start_day_of_month - 1)] = previous month
         [start_day_of_month...(start_day_of_month + days_in_current_month - 1)] = current month
[(start_day_of_month + days_in_current_month)...(mCalendarTextViews.length - 1)] = next month
*******************************************************************************************************/
        for ( int i = 0; i < mCalendarTextViews.length; i++ ) {
//            int background = 0xF03030;
            if ( i < start_day_of_month ) {
                mCalendarTextViews[i].setText(String.valueOf(i + days_in_previous_month - start_day_of_month + 1));
//                mCalendarTextViews[i].setTextColor(0x404040);
            }
            if ( start_day_of_month <= i  &&  i < start_day_of_month + days_in_current_month ) {
                mCalendarTextViews[i].setText(String.valueOf(i - start_day_of_month + 1));
//                mCalendarTextViews[i].setTextColor(0xFFFFFF);
//                background = 0x0000FF;
            }
            if ( (start_day_of_month + days_in_current_month) <= i  &&  i < mCalendarTextViews.length ) {
                mCalendarTextViews[i].setText(String.valueOf(i - (days_in_current_month + start_day_of_month) + 1));
//                mCalendarTextViews[i].setTextColor(0x404040);
            }
//            mCalendarTextViews[i].setBackgroundColor(background);
        }

        writeMonth(mView_CurrentMonth, mThisMonth);
        writeMonth(mView_PreviousMonth, mThisMonth.minusMonths(1));
        writeMonth(mView_NextMonth, mThisMonth.plusMonths(1));

        LocalDate now = LocalDate.now();
        if ( mThisMonth.getMonth() == now.getMonth()  &&  mThisMonth.getYear() == now.getYear() ) {
            int current_day = mThisMonth.getDayOfMonth();
            int index_to_TextView = (7 - mThisMonth.getDayOfWeek().getValue()) + current_day - 1;
            mCalendarTextViews[index_to_TextView - 1].setBackgroundColor(0);
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
                ((TextView)v).setTextColor(0xFF0000);
            });
        }
        mView_CurrentMonth = view.findViewById(R.id.textView_current_month_name);
        mView_PreviousMonth = view.findViewById(R.id.view_previous_month_name);
        mView_PreviousMonth.setOnClickListener(v -> {
            mThisMonth = mThisMonth.minusMonths(1);
            fillCalendarView(mThisMonth);
        });
        mView_NextMonth = view.findViewById(R.id.view_next_month_name);
        mView_NextMonth.setOnClickListener(v -> {
            mThisMonth = mThisMonth.plusMonths(1);
            fillCalendarView(mThisMonth);
        });
        LocalDate date = java.time.LocalDate.now();
        fillCalendarView(date);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

