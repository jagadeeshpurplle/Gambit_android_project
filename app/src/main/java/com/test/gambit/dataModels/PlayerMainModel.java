package com.test.gambit.dataModels;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PlayerMainModel {

    @SerializedName("data")
    ArrayList<PlayerData> playerDataList = new ArrayList<>();

    public ArrayList<PlayerData> getPlayerDataList() {
        return playerDataList;
    }

    public void setPlayerDataList(ArrayList<PlayerData> playerDataList) {
        this.playerDataList = playerDataList;
    }

}
