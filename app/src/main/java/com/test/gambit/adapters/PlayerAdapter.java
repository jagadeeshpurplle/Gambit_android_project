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

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>{


    private final Context context;
    private final ArrayList<PlayerData> playerDataList;

    public PlayerAdapter(Context context, ArrayList<PlayerData> playerDataList){
        this.context = context;
        this.playerDataList = playerDataList;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_player,viewGroup,false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder viewHolder, int i) {
        PlayerData playerData = playerDataList.get(i);
        viewHolder.tvFirstName.setText(playerData.getFirstName());
        viewHolder.tvLastName.setText(playerData.getLastName());
        viewHolder.tvTeamName.setText(playerData.getTeam().getName());
        viewHolder.tvTeamId.setText(context.getResources().getString(R.string.hash)+playerData.getTeam().getId());
        viewHolder.tvPosition.setText(context.getResources().getString(R.string.position)+" "+playerData.getPosition());
        viewHolder.tvFullName.setText(playerData.getTeam().getFullName());
        viewHolder.tvConference.setText(playerData.getTeam().getConference()+
                context.getResources().getString(R.string.slash)+playerData.getTeam().getDivision());
    }

    @Override
    public int getItemCount() {
        return playerDataList.size();
    }

    class PlayerViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_first_name)
        TextView tvFirstName;
        @BindView(R.id.tv_last_name)
        TextView tvLastName;
        @BindView(R.id.tv_team_name)
        TextView tvTeamName;
        @BindView(R.id.tv_team_id)
        TextView tvTeamId;
        @BindView(R.id.tv_position)
        TextView tvPosition;
        @BindView(R.id.tv_full_name)
        TextView tvFullName;
        @BindView(R.id.tv_conference)
        TextView tvConference;

        PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
