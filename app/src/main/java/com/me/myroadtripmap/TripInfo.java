package com.me.myroadtripmap;

/**
 * Created by developer3 on 1/9/16.
 */
public class TripInfo {

    public Address start_address;
    public Address end_address;
    public float fuel_cost_usd;
    public float duration_s;
    public float distance_m;
    public String path;
    public Location start_location;
    public Location end_location;

    @Override
    public String toString() {
        return "TripInfo{" +
                "start_address=" + start_address +
                ", end_address=" + end_address +
                ", fuel_cost_usd=" + fuel_cost_usd +
                ", duration_s=" + duration_s +
                ", distance_m=" + distance_m +
                ", path='" + path + '\'' +
                '}';
    }
}
