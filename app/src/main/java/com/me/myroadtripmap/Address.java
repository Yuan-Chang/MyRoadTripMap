package com.me.myroadtripmap;

/**
 * Created by developer3 on 1/9/16.
 */
public class Address {
    public String name;
    public String display_name;
    public String street_number;
    public String street_name;
    public String city;
    public String state;
    public String country;

    @Override
    public String toString() {
        return "Address{" +
                "name='" + name + '\'' +
                ", display_name='" + display_name + '\'' +
                ", street_number='" + street_number + '\'' +
                ", street_name='" + street_name + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}


