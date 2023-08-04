package com.example.ggb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MonthAdapter extends BaseAdapter {

    Context mContext =null;
    LayoutInflater mLayoutInflater= null;
    ArrayList<MonthData> monthData;

    public MonthAdapter(Context mContext, ArrayList<MonthData> data)
    {
        this.mContext=mContext;
        monthData=data;
        mLayoutInflater=LayoutInflater.from(mContext);

    }

    @Override
    public int getCount() {
        return monthData.size();
    }

    @Override
    public Object getItem(int position) {
        return monthData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.month_item, null);

        TextView month = (TextView)view.findViewById(R.id.month);
        TextView income = (TextView)view.findViewById(R.id.income);
        TextView expenditure = (TextView)view.findViewById(R.id.expenditure);
        TextView sum = (TextView)view.findViewById(R.id.sum);

        month.setText(monthData.get(position).getMonth());
        income.setText(monthData.get(position).getIncome());
        expenditure.setText(monthData.get(position).getExpenditure());
        sum.setText(monthData.get(position).getSum());


        return view;
    }
}
