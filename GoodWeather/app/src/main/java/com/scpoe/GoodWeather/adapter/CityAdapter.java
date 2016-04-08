package com.scpoe.GoodWeather.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.scpoe.GoodWeather.other.MyApplication;
import com.scpoe.GoodWeather.R;

import java.util.ArrayList;



public class CityAdapter extends BaseAdapter {

    public ArrayList<String> cityList;
    TextView textView;

    public CityAdapter() {
        cityList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return cityList.size();
    }

    @Override
    public Object getItem(int position) {
        return cityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.drawer_menu_list, null);
        textView = (TextView) view.findViewById(R.id.city_list_item);
        textView.setText(cityList.get(position));
        return view;
    }

    public void addCity(String cityName) {
        cityList.add(cityName);
        notifyDataSetChanged();
    }

    public void removeCity(int position) {
        cityList.remove(position);
        notifyDataSetChanged();
    }
}
