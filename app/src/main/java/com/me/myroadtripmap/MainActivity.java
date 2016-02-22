package com.me.myroadtripmap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.me.myroadtripmap.HTTP.CustomAdapter;
import com.me.myroadtripmap.HTTP.ServiceGenerator;
import com.me.myroadtripmap.HTTP.UserClient;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private GoogleMap googleMap;
    ListView listView;
    List<TripInfo> tripList;
    CustomAdapter customAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        initilizeMap();

        tripList = new ArrayList<TripInfo>();
        customAdapter = new CustomAdapter(this, tripList);
        listView.setAdapter(customAdapter);
        customAdapter.setOnLoadNextListener(() -> LoadTrips(customAdapter.getCount()/20+1));

        LoadTrips(1);

    }

    void LoadTrips(int page)
    {
        PolylineOptions options = new PolylineOptions().width(10).color(Color.RED).geodesic(true);

        UserClient userClient = ServiceGenerator.createService(UserClient.class);
        userClient.getTrips(page,20).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(tripInfos -> Observable.from(tripInfos))
                .map(tripInfo -> {
                    if (tripInfo.path != null)
                        googleMap.addPolyline(options.addAll(PolyUtil.decode(tripInfo.path)));
                    return tripInfo;
                })
                .toList()
                .subscribe(trips -> {

                    tripList.addAll(trips);
                    customAdapter.notifyDataSetChanged();

                    //use first trip to zoom in
                    CameraPosition camPos = new CameraPosition.Builder()
                            .target(new LatLng(tripList.get(0).start_location.lat, tripList.get(0).start_location.lon))
                            .zoom(16.8f)
                            .build();

                    CameraUpdate camUpdate = CameraUpdateFactory.newCameraPosition(camPos);
                    googleMap.moveCamera(camUpdate);

                }, e -> e.printStackTrace());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);

        menu.add(Menu.NONE, 0, Menu.NONE, "Sort alphabetical");
        menu.add(Menu.NONE, 1, Menu.NONE, "Sort by distance");
        menu.add(Menu.NONE, 2, Menu.NONE, "Sort by trip cost");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == 0)
            Collections.sort(tripList, (a, b) -> a.start_address.name.compareTo(b.start_address.name));
        else if (id == 1)
            Collections.sort(tripList, (a, b) -> a.distance_m - b.distance_m < 0 ? 1 : -1);
        else if (id == 2)
            Collections.sort(tripList, (a, b) -> a.fuel_cost_usd - b.fuel_cost_usd < 0 ? 1 : -1);

        customAdapter.notifyDataSetChanged();


        return super.onOptionsItemSelected(item);
    }

    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

}
