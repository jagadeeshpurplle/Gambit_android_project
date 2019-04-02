package com.test.gambit.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.test.gambit.BuildConfig;
import com.test.gambit.dataModels.PlayerData;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PreferenceManager {

    private static final String PREF_NAME = BuildConfig.APPLICATION_ID;
    private static final String PLAYER_DATA = "player_data";
    private static final String GAME_DATA = "game_data";

    private static SharedPreferences mSharedPreferences;
    private static Gson mGson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    public static void init(Context context){
        mSharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
    }

    public static void savePlayers(ArrayList<PlayerData> playerData) {
        mSharedPreferences.edit().putString(PLAYER_DATA, mGson.toJson(playerData)).apply();
    }

    public static ArrayList<PlayerData> getPlayers() {
        String playerStr = mSharedPreferences.getString(PLAYER_DATA, "");
        if(playerStr.isEmpty()){
            return new ArrayList<>();
        }
        Type type = new TypeToken<ArrayList<PlayerData>>(){}.getType();
        return mGson.fromJson(playerStr, type);
    }

    public static void saveGames(ArrayList<PlayerData> gameData) {
        mSharedPreferences.edit().putString(GAME_DATA, mGson.toJson(gameData)).apply();
    }

    public static ArrayList<PlayerData> getGames() {
        String gameStr = mSharedPreferences.getString(GAME_DATA, "");
        if(gameStr.isEmpty()){
            return new ArrayList<>();
        }
        Type type = new TypeToken<ArrayList<PlayerData>>(){}.getType();
        return new Gson().fromJson(gameStr, type);
    }

}
