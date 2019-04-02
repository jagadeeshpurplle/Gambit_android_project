package com.test.gambit.playerList;

import com.test.gambit.dataModels.PlayerData;

import java.util.ArrayList;

public class PlayerContract{

    interface Presenter{
        void onAttachView(PlayerView view);
        void onDetachView(PlayerView view);
        void onRefreshData(int count,boolean isSearch);
        void searchData(String searchString);
    }

    interface PlayerView{
        void showProgress();
        void hideProgress();
        void setDataToRecyclerView(ArrayList<PlayerData> noticeList,boolean isSearch);
        void onResponseFailure(Throwable t);
        void showToast(String string);
    }

    public interface GetPlayers{

        interface OnFinishedListener{
            void onFinished(ArrayList<PlayerData> noticeArrayList,boolean isSearch);
            void onFailure(Throwable t);
        }

        void getPlayersList(OnFinishedListener onFinishedListener);
        void fetchNextPlayers(OnFinishedListener onFinishedListener, int pageCount,boolean isSearch);
        void fetchSearchedPlayers(OnFinishedListener onFinishedListener,String searchString);

    }


}
