package com.test.gambit.activities;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.test.gambit.R;
import com.test.gambit.adapters.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.pager_content)
    ViewPager pagerContent;
    @BindView(R.id.tab_pager)
    TabLayout tabPager;
    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        handleTitle();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),this);
        pagerContent.setAdapter(viewPagerAdapter);
        tabPager.setupWithViewPager(pagerContent);
        for (int i = 0; i < tabPager.getTabCount(); i++) {
            TabLayout.Tab tab = tabPager.getTabAt(i);
            tab.setCustomView(viewPagerAdapter.getTabView(i));
        }

        tabPager.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                TabLayout.Tab tab=tabLayout.getTabAt(position);
                View view=tab.getCustomView();
                TextView tabTitle= view.findViewById(R.id.tab_title);
                tabTitle.setTextColor(ContextCompat.getColor(MainActivity.this,R.color.colorAccent));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view=tab.getCustomView();
                TextView tabTitle= view.findViewById(R.id.tab_title);
                tabTitle.setTextColor(ContextCompat.getColor(MainActivity.this,R.color.first_name));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void handleTitle() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("The NBA Scout");
                    collapsingToolbarLayout.setCollapsedTitleGravity(Gravity.CENTER_HORIZONTAL);
                    collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(MainActivity.this,android.R.color.white));
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    public void updateCounter(int size) {
        TabLayout.Tab tab=tabPager.getTabAt(0);
        View view=tab.getCustomView();
        TextView txtCount= view.findViewById(R.id.tv_count);
        txtCount.setText(String.valueOf(size));
    }
}
