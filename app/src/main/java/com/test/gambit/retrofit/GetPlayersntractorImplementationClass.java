package com.test.gambit.retrofit;

import com.test.gambit.dataModels.PlayerMainModel;
import com.test.gambit.gameList.GameContract;
import com.test.gambit.playerList.PlayerContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetPlayersntractorImplementationClass implements PlayerContract.GetPlayers, GameContract.GetGames {


    @Override
    public void getPlayersList(final PlayerContract.GetPlayers.OnFinishedListener onFinishedListener) {
        GetPlayersDataService service = RetrofitInstance.getRetrofitInstance().create(GetPlayersDataService.class);
        Call<PlayerMainModel> call = service.getPlayers();
        callPlayerApi(call,onFinishedListener,false);
    }

    @Override
    public void fetchNextPlayers(final PlayerContract.GetPlayers.OnFinishedListener onFinishedListener, int pageCount, boolean isSearch) {
        GetPlayersDataService service = RetrofitInstance.getRetrofitInstance().create(GetPlayersDataService.class);
        Call<PlayerMainModel> call = service.getNextPlayers(pageCount);
        callPlayerApi(call,onFinishedListener,isSearch);

    }

    @Override
    public void fetchSearchedPlayers(final PlayerContract.GetPlayers.OnFinishedListener onFinishedListener, String searchString) {
        GetPlayersDataService service = RetrofitInstance.getRetrofitInstance().create(GetPlayersDataService.class);
        Call<PlayerMainModel> call = service.getSearchedPlayers(searchString);
        callPlayerApi(call,onFinishedListener,true);
    }


    private void callPlayerApi(Call call, final PlayerContract.GetPlayers.OnFinishedListener onFinishedListener, final boolean isSearch){
        call.enqueue(new Callback<PlayerMainModel>() {


            @Override
            public void onResponse(Call<PlayerMainModel> call, Response<PlayerMainModel> response) {
                onFinishedListener.onFinished(response.body().getPlayerDataList(),isSearch);
            }

            @Override
            public void onFailure(Call<PlayerMainModel> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }

    @Override
    public void getGamesList(GameContract.GetGames.OnFinishedListener onFinishedListener) {
        GetPlayersDataService service = RetrofitInstance.getRetrofitInstance().create(GetPlayersDataService.class);
        Call<PlayerMainModel> call = service.getGames();
        callGamesApi(call,onFinishedListener);
    }

    @Override
    public void fetchNextGames(GameContract.GetGames.OnFinishedListener onFinishedListener, int pageCount) {
        GetPlayersDataService service = RetrofitInstance.getRetrofitInstance().create(GetPlayersDataService.class);
        Call<PlayerMainModel> call = service.getNextGames(pageCount);
        callGamesApi(call,onFinishedListener);
    }



    private void callGamesApi(Call call, final GameContract.GetGames.OnFinishedListener onFinishedListener){
        call.enqueue(new Callback<PlayerMainModel>() {


            @Override
            public void onResponse(Call<PlayerMainModel> call, Response<PlayerMainModel> response) {
                onFinishedListener.onFinished(response.body().getPlayerDataList());
            }

            @Override
            public void onFailure(Call<PlayerMainModel> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
