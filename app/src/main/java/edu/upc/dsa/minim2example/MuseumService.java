package edu.upc.dsa.minim2example;

import edu.upc.dsa.minim2example.models.Museums;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MuseumService {
    //GET the list of Museums
    @GET("json/pag-ini/1/pag-fi/15")
    Call<Museums> getMuseums();
}
