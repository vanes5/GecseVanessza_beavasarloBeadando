package com.example.beavasarlasbeadando;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitApiService {

    // GET all users
    @GET("A3b6wN/termekek")
    Call<List<Termekek>> getAllTermek();

    // GET user by ID
    @GET("A3b6wN/termekek/{id}")
    Call<Termekek> getTermekById(@Path("id") int id);

    // POST (create a new user)
    @POST("A3b6wN/termekek")
    Call<Termekek> createTermek(@Body Termekek termek);

    // PUT (update a user)
    @PUT("A3b6wN/termekek/{id}")
    Call<Termekek> updateTermek(@Path("id") int id, @Body Termekek termek);

    // DELETE (delete a user by ID)
    @DELETE("A3b6wN/termekek/{id}")
    Call<Void> deleteTermek(@Path("id") int id);
}
