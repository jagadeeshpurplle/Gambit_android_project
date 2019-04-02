package com.test.gambit.gameList;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.test.gambit.R;
import com.test.gambit.adapters.GameAdapter;
import com.test.gambit.dataModels.PlayerData;
import com.test.gambit.retrofit.GetPlayersntractorImplementationClass;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GameFragment extends Fragment implements GameContract.GameView{


    private static String TAG = GameFragment.class.getSimpleName();
    @BindView(R.id.rv_players)
    RecyclerView rvPlayers;
    @BindView(R.id.progress_bar)
    ProgressBar mProgress;
    ArrayList<PlayerData> gameDataList = new ArrayList<>();
    private Context mContext;
    private GameAdapter mGameAdapter;
    private GameContract.Presenter mPresenter;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount,pageCount=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_game, container, false);
        ButterKnife.bind(this,view);
        mPresenter = createPresenter();
        setAdapter();
        Log.d(TAG, "onCreateView: ");
        return view;
    }

    private void setScrollListener(final LinearLayoutManager mLayoutManager) {
        rvPlayers.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                    Log.d(TAG, "onScrolled: visibleItemCount:: "+visibleItemCount);
                    Log.d(TAG, "onScrolled: totalItemCount:: "+totalItemCount);
                    Log.d(TAG, "onScrolled: pastVisiblesItems:: "+pastVisiblesItems);
                    if (loading)
                    {
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                        {
                            Log.d(TAG, "onScrolled: "+pageCount);
                            loading = false;
                            mPresenter.onRefreshData(pageCount);
                        }
                    }
                }
            }
        });
    }

    private GamePresenter createPresenter() {
        return new GamePresenter(this,new GetPlayersntractorImplementationClass(),mContext);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    private void setAdapter() {
        mGameAdapter = new GameAdapter(mContext, gameDataList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,
                false);
        rvPlayers.setLayoutManager(mLayoutManager);
        rvPlayers.setAdapter(mGameAdapter);
        setScrollListener(mLayoutManager);
    }

    @Override
    public void showProgress() {
        Log.d(TAG, "showProgress: ");
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        Log.d(TAG, "hideProgress: ");
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void setDataToRecyclerView(ArrayList<PlayerData> playerDataList) {
//        this.gameDataList = gameDataList;
        this.gameDataList.addAll(playerDataList);
        Log.d(TAG, "setDataToRecyclerView: size: "+this.gameDataList.size());
        mGameAdapter.notifyDataSetChanged();
        pageCount++;
        loading = true;
//        setAdapter();
    }

    @Override
    public void onResponseFailure(Throwable t) {
        Log.d(TAG, "onResponseFailure: "+t.getMessage());
        loading = true;
        Toast.makeText(mContext,mContext.getResources().getString(R.string.response_failure),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onAttachView(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.onDetachView(this);
    }

    @Override
    public void showNoInternetToast(String message) {
        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
    }

}
