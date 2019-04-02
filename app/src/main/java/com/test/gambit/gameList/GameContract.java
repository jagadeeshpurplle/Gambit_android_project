package com.test.gambit.gameList;

import com.test.gambit.dataModels.PlayerData;

import java.util.ArrayList;

public class GameContract {

    interface Presenter{
        void onAttachView(GameView view);
        void onDetachView(GameView view);
        void onRefreshData(int count);
    }

    interface GameView{
        void showProgress();
        void hideProgress();
        void setDataToRecyclerView(ArrayList<PlayerData> noticeList);
        void onResponseFailure(Throwable t);
        void showNoInternetToast(String string);
    }

    public interface GetGames{

        interface OnFinishedListener{
            void onFinished(ArrayList<PlayerData> noticeArrayList);
            void onFailure(Throwable t);
        }

        void getGamesList(OnFinishedListener onFinishedListener);
        void fetchNextGames(OnFinishedListener onFinishedListener, int pageCount);
    }


}
