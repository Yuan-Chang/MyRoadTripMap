package com.me.myroadtripmap.HTTP;

import com.me.myroadtripmap.TripInfo;
import com.squareup.okhttp.ResponseBody;

import java.util.List;

import retrofit.Call;
import retrofit.Response;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by developer3 on 1/3/16.
 */
public interface UserClient {

    //@GET("/Android/test.json")
    @GET("/trip/")
    Observable<TripInfo[]> getTrips(@Query("page") int page, @Query("limit") int limit);
}
