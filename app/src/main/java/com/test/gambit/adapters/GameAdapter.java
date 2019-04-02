package com.test.gambit.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.gambit.R;
import com.test.gambit.dataModels.PlayerData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder>{

    Context mContext;
    ArrayList<PlayerData> gameDataList;

    public GameAdapter(Context mContext, ArrayList<PlayerData> gameDataList) {
        this.mContext = mContext;
        this.gameDataList = gameDataList;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_game,viewGroup,false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder gameViewHolder, int i) {
        PlayerData gameData = gameDataList.get(i);
        gameViewHolder.tvAbbr1.setText(gameData.getHomeTeam().getAbbreviation());
        gameViewHolder.tvFullName1.setText(gameData.getHomeTeam().getFullName());
        gameViewHolder.tvAbbr2.setText(gameData.getVisitorTeam().getAbbreviation());
        gameViewHolder.tvFullName2.setText(gameData.getVisitorTeam().getFullName());
    }

    @Override
    public int getItemCount() {
        return gameDataList.size();
    }

    class GameViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_abbr_1)
        TextView tvAbbr1;
        @BindView(R.id.tv_abbr_2)
        TextView tvAbbr2;
        @BindView(R.id.tv_full_name_1)
        TextView tvFullName1;
        @BindView(R.id.tv_full_name_2)
        TextView tvFullName2;

        GameViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
