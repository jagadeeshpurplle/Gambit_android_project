package com.test.gambit.retrofit;

import com.test.gambit.dataModels.PlayerMainModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetPlayersDataService {

    @GET("players")
    Call<PlayerMainModel> getPlayers();

    @GET("players")
    Call<PlayerMainModel> getNextPlayers(@Query("page") int pageCount);

    @GET("players")
    Call<PlayerMainModel> getSearchedPlayers(@Query("search") String searchString);

    @GET("games")
    Call<PlayerMainModel> getGames();

    @GET("games")
    Call<PlayerMainModel> getNextGames(@Query("page") int pageCount);

}
