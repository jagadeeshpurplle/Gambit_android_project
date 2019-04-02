package com.test.gambit.playerList;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.test.gambit.activities.MainActivity;
import com.test.gambit.adapters.PlayerAdapter;
import com.test.gambit.dataModels.PlayerData;
import com.test.gambit.retrofit.GetPlayersntractorImplementationClass;
import com.test.gambit.R;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

public class PlayerFragement extends Fragment implements PlayerContract.PlayerView{

    private static String TAG = PlayerFragement.class.getSimpleName();
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.rv_players)
    RecyclerView rvPlayers;
    @BindView(R.id.progress_bar)
    ProgressBar mProgress;
    ArrayList<PlayerData> playerDataList = new ArrayList<>();
    private Context mContext;
    private PlayerAdapter mPlayerAdapter;
    private PlayerContract.Presenter mPresenter;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount,pageCount=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_players, container, false);
        ButterKnife.bind(this,view);
        mPresenter = createPresenter();
        setAdapter();
        etSearch.setSingleLine(true);
        etSearch.setImeOptions(EditorInfo.IME_ACTION_DONE);
        setSearchListener();
        Log.d(TAG, "onCreateView: ");

        etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                Log.i("jaga","clikd enter key "+i);
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (i)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            Log.i("jaga","clikd enter");

                            if(etSearch.getText().toString().isEmpty()){
                                mPresenter.onRefreshData(0,true);
                            }else{
                                mPresenter.searchData(etSearch.getText().toString());
                            }
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        /*new SimpleTooltip.Builder(mContext)
               .gravity(Gravity.BOTTOM)
                .animated(true)
                .transparentOverlay(false)
                .build()
                .show();*/

        return view;
    }

    private void setSearchListener() {
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.i("jaga","on edit "+actionId);

                if(actionId==EditorInfo.IME_ACTION_DONE){
                    if(etSearch.getText().toString().isEmpty()){
                        mPresenter.onRefreshData(0,true);
                    }else{
                        mPresenter.searchData(etSearch.getText().toString());
                    }
                }
                return false;
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty()){
                    ivClose.setVisibility(View.GONE);
                }else{
                    ivClose.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @OnClick(R.id.iv_close)
    public void endSearch(){
        etSearch.setText("");
        mPresenter.onRefreshData(0,true);
    }

    private void setScrollListener(final LinearLayoutManager mLayoutManager) {
        rvPlayers.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if(!etSearch.getText().toString().isEmpty()){
                    return;
                }
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
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            Log.d(TAG, "onScrolled: "+pageCount);
                            loading = false;
                            mPresenter.onRefreshData(pageCount,false);
                        }
                    }
                }
            }
        });
    }

    private PlayerPresenter createPresenter() {
        return new PlayerPresenter(this,new GetPlayersntractorImplementationClass(),mContext);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    private void setAdapter() {
        mPlayerAdapter = new PlayerAdapter(mContext,playerDataList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,
                false);
        rvPlayers.setLayoutManager(mLayoutManager);
        rvPlayers.setAdapter(mPlayerAdapter);
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
    public void setDataToRecyclerView(ArrayList<PlayerData> playerDataList,boolean isSearch) {
        Log.d(TAG, "setDataToRecyclerView: size: "+playerDataList.size());
        if(isSearch){
            this.playerDataList.clear();
        }
        this.playerDataList.addAll(playerDataList);
        Log.d(TAG, "setDataToRecyclerView: size: "+this.playerDataList.size());
        mPlayerAdapter.notifyDataSetChanged();
        pageCount++;
        loading = true;
        ((MainActivity)mContext).updateCounter(this.playerDataList.size());
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
    public void showToast(String message) {
        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
    }
}
