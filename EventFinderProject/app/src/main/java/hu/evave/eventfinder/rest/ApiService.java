package hu.evave.eventfinder.rest;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("/json_data.json")
    Call<SearchResponse> getMyJSON();
}
