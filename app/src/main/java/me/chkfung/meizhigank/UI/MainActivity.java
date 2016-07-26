package me.chkfung.meizhigank.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.chkfung.meizhigank.Base.BaseActivity;
import me.chkfung.meizhigank.Model.Meizhi;
import me.chkfung.meizhigank.Presenter.MainPresenter;
import me.chkfung.meizhigank.R;

public class MainActivity extends BaseActivity implements MainContract.View {

    final private List<Meizhi.ResultsBean> MeizhiData = new ArrayList<>();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_meizhi)
    RecyclerView rvMeizhi;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.refreshlayout)
    SwipeRefreshLayout refreshlayout;
    private MeizhiRvAdapter meizhiRvAdapter;
    private MainContract.Presenter mainPresenter = new MainPresenter();
    private int paging = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                refreshlayout.setRefreshing(true);
                mainPresenter.loadMeizhi(paging++, MeizhiData);
            }
        });
        mainPresenter.attachView(this);
        meizhiRvAdapter = new MeizhiRvAdapter(this, MeizhiData);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvMeizhi.setAdapter(meizhiRvAdapter);
        rvMeizhi.setLayoutManager(layoutManager);
        refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Clear Adapter
                MeizhiData.clear();
                meizhiRvAdapter.notifyDataSetChanged();
                paging = 1;
                mainPresenter.loadMeizhi(paging, MeizhiData);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.detachView();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void refreshRv() {
        meizhiRvAdapter.notifyDataSetChanged();
        refreshlayout.setRefreshing(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.getItemId() == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
