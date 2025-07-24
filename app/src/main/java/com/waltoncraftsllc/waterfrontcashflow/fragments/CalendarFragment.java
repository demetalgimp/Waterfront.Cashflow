package com.waltoncraftsllc.waterfrontcashflow.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.waltoncraftsllc.waterfrontcashflow.R;
import com.waltoncraftsllc.waterfrontcashflow.widget.DayButtonView;
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
//    private static TextView[] mCalendarTextViews;
    private static DayButtonView[] mCalendarDayTextViews;
    private LocalDate mThisMonth;
//    private GridLayout mGridLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    private void writeMonth(TextView view, LocalDate date) {
        Month month = date.getMonth();
        String month_name = month.getDisplayName(TextStyle.FULL, Locale.US);
        view.setText(String.format(Locale.US, "  %s (%04d)  ", month_name, date.getYear()));
//        view.setBackgroundColor(0xFF0000);
    }

    /** private void fillCalendarView(LocalDate date)
     * Fill
     * @param date - #date# - The month that needs to be populated.
     */
    private void fillCalendarView(LocalDate date) {
        mThisMonth = date;
        int start_day_of_month = LocalDate.of(date.getYear(), date.getMonth(), 1).getDayOfWeek().getValue();
        int days_in_current_month = mThisMonth.lengthOfMonth();
        int days_in_previous_month = mThisMonth.minusMonths(1).lengthOfMonth();
/* *****************************************************************************************************
                                                  [0...(start_day_of_month - 1)] = previous month
         [start_day_of_month...(start_day_of_month + days_in_current_month - 1)] = current month
[(start_day_of_month + days_in_current_month)...(mCalendarTextViews.length - 1)] = next month
****************************************************************************************************** */
        for (int i = 0; i < mCalendarDayTextViews.length; i++ ) {
            String background = "#303030";
            if ( i < start_day_of_month ) {
                mCalendarDayTextViews[i].setText(String.valueOf(i + days_in_previous_month - start_day_of_month + 1));
//                mCalendarDayTextViews[i].setTextColor(0x404040);
            }
            if ( start_day_of_month <= i  &&  i < start_day_of_month + days_in_current_month ) {
                mCalendarDayTextViews[i].setText(String.valueOf(i - start_day_of_month + 1));
//                mCalendarDayTextViews[i].setTextColor(0xFFFFFF);
                background = "#0000FF";
            }
            if ( (start_day_of_month + days_in_current_month) <= i  &&  i < mCalendarDayTextViews.length ) {
                mCalendarDayTextViews[i].setText(String.valueOf(i - (days_in_current_month + start_day_of_month) + 1));
//                mCalendarDayTextViews[i].setTextColor(0x404040);
            }
            mCalendarDayTextViews[i].setBackgroundColor(Color.parseColor(background));
            mCalendarDayTextViews[i].invalidate();
        }

        writeMonth(mView_CurrentMonth, mThisMonth);
        writeMonth(mView_PreviousMonth, mThisMonth.minusMonths(1));
        writeMonth(mView_NextMonth, mThisMonth.plusMonths(1));

        LocalDate now = LocalDate.now();
        if ( mThisMonth.getMonth() == now.getMonth()  &&  mThisMonth.getYear() == now.getYear() ) {
            int current_day = mThisMonth.getDayOfMonth();
            int index_to_TextView = (7 - mThisMonth.getDayOfWeek().getValue()) + current_day - 1;
            mCalendarDayTextViews[index_to_TextView - 1].setBackgroundColor(0);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCalendarDayTextViews = new DayButtonView[mCalendarIDs.length];
        for ( int i = 0; i < mCalendarIDs.length; i++ ) {
            mCalendarDayTextViews[i] = view.findViewById(mCalendarIDs[i]);
            mCalendarDayTextViews[i].setOnClickListener(v -> {
                ((DayButtonView)v).setBackgroundColor(Color.parseColor("#000000"));
                ((DayButtonView)v).setTextColor(Color.parseColor("#FF0000"));
                ((DayButtonView)v).invalidate();
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
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }
    final int[] mCalendarIDs = {
        R.id.sun_1, R.id.mon_1, R.id.tue_1, R.id.wed_1, R.id.thu_1, R.id.fri_1, R.id.sat_1,
        R.id.sun_2, R.id.mon_2, R.id.tue_2, R.id.wed_2, R.id.thu_2, R.id.fri_2, R.id.sat_2,
        R.id.sun_3, R.id.mon_3, R.id.tue_3, R.id.wed_3, R.id.thu_3, R.id.fri_3, R.id.sat_3,
        R.id.sun_4, R.id.mon_4, R.id.tue_4, R.id.wed_4, R.id.thu_4, R.id.fri_4, R.id.sat_4,
        R.id.sun_5, R.id.mon_5, R.id.tue_5, R.id.wed_5, R.id.thu_5, R.id.fri_5, R.id.sat_5,
        R.id.sun_6, R.id.mon_6, R.id.tue_6, R.id.wed_6, R.id.thu_6, R.id.fri_6, R.id.sat_6
    };
}

