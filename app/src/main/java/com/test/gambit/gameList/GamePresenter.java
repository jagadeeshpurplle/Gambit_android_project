package com.test.gambit.gameList;

import android.content.Context;

import com.test.gambit.R;
import com.test.gambit.dataModels.PlayerData;
import com.test.gambit.utils.PreferenceManager;
import com.test.gambit.utils.Utils;

import java.util.ArrayList;

public class GamePresenter implements GameContract.Presenter, GameContract.GetGames.OnFinishedListener{

    private final Context context;
    private GameContract.GameView gameView;
    private GameContract.GetGames getGames;

    public GamePresenter(GameContract.GameView gameView, GameContract.GetGames getGames, Context context){
        this.gameView = gameView;
        this.getGames = getGames;
        this.context = context;
    }

    @Override
    public void onAttachView(GameContract.GameView view) {
        gameView = view;
        fetchDataFromServer();
    }

    @Override
    public void onDetachView(GameContract.GameView view) {
        gameView = null;
    }

    @Override
    public void onRefreshData(int pageCount) {
        if(gameView!=null){
            gameView.showProgress();
        }
        if(Utils.isNetworkAvailable(context)) {
            getGames.fetchNextGames(this, pageCount);
        }else{
            if(gameView!=null){
                gameView.showNoInternetToast(context.getResources().getString(R.string.no_internet));
                gameView.hideProgress();
            }
        }
    }

    private void fetchDataFromServer() {
        if(Utils.isNetworkAvailable(context)) {
            if (gameView != null) {
                gameView.showProgress();
            }
            getGames.getGamesList(this);
        }else{
            if(gameView!=null){
                gameView.showNoInternetToast(context.getResources().getString(R.string.no_internet));
            }
            if(PreferenceManager.getGames()!=null && !PreferenceManager.getGames().isEmpty()) {
                onFinished(PreferenceManager.getGames());
            }
        }
    }

    @Override
    public void onFinished(ArrayList<PlayerData> gameDataArrayList) {
        if(gameView!=null) {
            gameView.hideProgress();
            if(gameDataArrayList.isEmpty()){
                gameView.showNoInternetToast(context.getResources().getString(R.string.no_data));
                return;
            }
            gameView.setDataToRecyclerView(gameDataArrayList);
        }
        PreferenceManager.saveGames(gameDataArrayList);
    }

    @Override
    public void onFailure(Throwable t) {
        if(gameView!=null) {
            gameView.hideProgress();
            gameView.onResponseFailure(t);
        }
    }
}
