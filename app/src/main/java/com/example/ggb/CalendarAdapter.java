package com.example.ggb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Date> dates;
    private Calendar currentCalendar;
    private ArrayList<String> expenseList;

    public CalendarAdapter(Context context, ArrayList<Date> dates, Calendar currentCalendar) {
        this.context = context;
        this.dates = dates;
        this.currentCalendar = currentCalendar;
        this.expenseList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.calendar_item, parent, false);
        }

        TextView dayTextView = convertView.findViewById(R.id.dayTextView);
        TextView expenseTextView = convertView.findViewById(R.id.expenseTextView);

        Date date = dates.get(position);
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            dayTextView.setText(String.valueOf(dayOfMonth));

            String expense = null;
            if (position < expenseList.size()) {
                expense = expenseList.get(position);
            }

            if (expense != null) {
                expenseTextView.setText(expense);
            } else {
                expenseTextView.setText("");
            }

            if (calendar.get(Calendar.MONTH) != currentCalendar.get(Calendar.MONTH)) {
                dayTextView.setTextColor(ContextCompat.getColor(context, R.color.gray));
            } else {
                dayTextView.setTextColor(ContextCompat.getColor(context, R.color.black));
            }
        } else {
            dayTextView.setText("");
            expenseTextView.setText("");
        }

        return convertView;
    }

    public void setExpenseList(ArrayList<String> expenseList) {
        this.expenseList = expenseList;
    }
}
