package com.example.smart_container;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceholder {
    @GET("smartcontainer/currentDate")
    Call<List<Post>> getPosts(@Query("date1") String dt);

    @GET("smartcontainer/range")
    Call<List<Post>> getPostsRanged(@Query("date1") String d1,
                                    @Query("date2") String d2);
}
