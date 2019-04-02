package com.test.gambit.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.test.gambit.R;
import com.test.gambit.gameList.GameFragment;
import com.test.gambit.playerList.PlayerFragement;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = ViewPagerAdapter.class.getSimpleName();
    private final Context context;

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        Log.d(TAG, "ViewPagerAdapter: Constructor");
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return new PlayerFragement();
        }
        return new GameFragment();
    }


    @Override
    public CharSequence getPageTitle(final int position) {
        if (position == 0) {
            return "Players";
        }
        return "Games";
    }

    public View getTabView(int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.tab_item, null);
        TextView txtCount = v.findViewById(R.id.tv_count);
        TextView tabTitle = v.findViewById(R.id.tab_title);
        txtCount.setText("0");
        if(position==0) {
            tabTitle.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            tabTitle.setText("Players");
            txtCount.setVisibility(View.VISIBLE);
        }else{
            txtCount.setVisibility(View.GONE);
            tabTitle.setText("Games");
        }
        return v;
    }



}
