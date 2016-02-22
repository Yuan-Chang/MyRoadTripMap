package com.me.myroadtripmap.HTTP;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;
import com.me.myroadtripmap.R;
import com.me.myroadtripmap.TripInfo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by developer3 on 1/9/16.
 */
public class CustomAdapter extends BaseAdapter {

    Context c;
    List<TripInfo> data;
    OnLoadNextListener onLoadNextListener;

    public CustomAdapter(Context c,List<TripInfo> data) {
        this.c = c;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public TripInfo getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = LayoutInflater.from(c).inflate(R.layout.trip_item_layout, null);

        TextView tripName = (TextView)view.findViewById( R.id.trip_name );
        TextView startAddress = (TextView)view.findViewById( R.id.start_address );
        TextView endAddress = (TextView)view.findViewById( R.id.end_address );
        TextView tripCost = (TextView)view.findViewById( R.id.trip_cost );
        TextView tripDuration = (TextView)view.findViewById( R.id.trip_duration );
        TextView tripDistance = (TextView)view.findViewById(R.id.trip_distance);

        tripName.setText(i+"");
        startAddress.setText(": " + getItem(i).start_address.name);
        endAddress.setText(": " + getItem(i).end_address.name);
        tripCost.setText(": " + getItem(i).fuel_cost_usd+" $");
        tripDuration.setText(": " + new DecimalFormat("##.##").format(getItem(i).duration_s/60)+" minutes");
        tripDistance.setText(": "+getItem(i).distance_m + " m");

        if (i == getCount()-8 && onLoadNextListener != null)
            onLoadNextListener.OnLoadNext();

        return view;
    }

    public interface OnLoadNextListener{
        void OnLoadNext();
    }

    public void setOnLoadNextListener(OnLoadNextListener listener)
    {
        onLoadNextListener = listener;
    }
}


