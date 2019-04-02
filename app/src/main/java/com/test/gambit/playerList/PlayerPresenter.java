package com.test.gambit.playerList;

import android.content.Context;

import com.test.gambit.R;
import com.test.gambit.dataModels.PlayerData;
import com.test.gambit.utils.PreferenceManager;
import com.test.gambit.utils.Utils;

import java.util.ArrayList;

public class PlayerPresenter implements PlayerContract.Presenter, PlayerContract.GetPlayers.OnFinishedListener{

    private final Context mContext;
    private PlayerContract.PlayerView playerView;
    private PlayerContract.GetPlayers getPlayers;

    public PlayerPresenter(PlayerContract.PlayerView playerView, PlayerContract.GetPlayers getPlayers, Context mContext){
        this.playerView = playerView;
        this.getPlayers = getPlayers;
        this.mContext = mContext;
    }

    @Override
    public void onAttachView(PlayerContract.PlayerView view) {
        playerView = view;
        fetchDataFromServer();
    }

    @Override
    public void onDetachView(PlayerContract.PlayerView view) {
        playerView = null;
    }

    @Override
    public void onRefreshData(int pageCount,boolean isSearch) {
        if(playerView!=null){
            playerView.showProgress();
        }
        if(Utils.isNetworkAvailable(mContext)) {
            getPlayers.fetchNextPlayers(this, pageCount,isSearch);
        }else {
            if(playerView!=null){
                playerView.showToast(mContext.getResources().getString(R.string.no_internet));
                playerView.hideProgress();
            }
        }
    }

    @Override
    public void searchData(String searchString) {
        if(playerView!=null){
            playerView.showProgress();
        }
        if(Utils.isNetworkAvailable(mContext)) {
            getPlayers.fetchSearchedPlayers(this,searchString);
        }else {
            if(playerView!=null){
                playerView.showToast(mContext.getResources().getString(R.string.no_internet));
                playerView.hideProgress();
            }
        }
    }

    private void fetchDataFromServer() {
        if(Utils.isNetworkAvailable(mContext)){
            if(playerView!=null){
                playerView.showProgress();
            }
            getPlayers.getPlayersList(this);
        }else{
            if(playerView!=null){
                playerView.showToast(mContext.getResources().getString(R.string.no_internet));
            }
            if(PreferenceManager.getPlayers()!=null && !PreferenceManager.getPlayers().isEmpty()) {
                onFinished(PreferenceManager.getPlayers(),false);
            }
        }

    }

    @Override
    public void onFinished(ArrayList<PlayerData> playerDataArrayList,boolean isSearch) {
        if(playerView!=null) {
            playerView.hideProgress();
            if(playerDataArrayList.isEmpty()){
                playerView.showToast(mContext.getResources().getString(R.string.no_data));
                return;
            }
            playerView.setDataToRecyclerView(playerDataArrayList,isSearch);
        }
        PreferenceManager.savePlayers(playerDataArrayList);
    }

    @Override
    public void onFailure(Throwable t) {
        if(playerView!=null) {
            playerView.hideProgress();
            playerView.onResponseFailure(t);
        }
    }
}
