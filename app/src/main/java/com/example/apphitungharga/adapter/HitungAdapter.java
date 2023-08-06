package com.example.apphitungharga.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.apphitungharga.R;
import com.example.apphitungharga.model.HitungModel;

import java.util.List;

public class HitungAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<HitungModel> lists;

    public HitungAdapter(Activity activity, List<HitungModel> lists){
        this.activity = activity;
        this.lists = lists;
    }

    public int getCount(){
        return lists.size();
    }

    public Object getItem(int i){
        return  lists.get(i);
    }

    public long getItemId(int i){
        return i;
    }

    public View getView(int i, View view, ViewGroup viewGroup){
        if(inflater == null){
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(view == null && inflater != null){
            view = inflater.inflate(R.layout.list_item, null);
        }
        if(view != null) {
            TextView nama = view.findViewById(R.id.nama);
            HitungModel hitungModel = lists.get(i);
            nama.setText("HPP "+hitungModel.getNama_umkm());
        }
        return view;
    }
}
