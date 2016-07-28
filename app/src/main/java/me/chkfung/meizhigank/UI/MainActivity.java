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
import butterknife.OnClick;
import me.chkfung.meizhigank.Base.BaseActivity;
import me.chkfung.meizhigank.Constants;
import me.chkfung.meizhigank.Contract.MainContract;
import me.chkfung.meizhigank.Contract.Presenter.MainPresenter;
import me.chkfung.meizhigank.Model.Meizhi;
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
        setSupportActionBar(toolbar);

        mainPresenter.attachView(this);

        meizhiRvAdapter = new MeizhiRvAdapter(this, MeizhiData);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvMeizhi.setAdapter(meizhiRvAdapter);
        rvMeizhi.setLayoutManager(layoutManager);
        refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                summonMeizhi(true);
            }
        });

        rvMeizhi.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int[] lasPos = new int[layoutManager.getSpanCount()];
                layoutManager.findLastVisibleItemPositions(lasPos);
                if (MeizhiData.size() - Math.max(lasPos[0], lasPos[1]) < Constants.MEIZHI_PRELOAD &&
                        !refreshlayout.isRefreshing()) {
                    summonMeizhi(false);
                }
            }
        });
        summonMeizhi(true);
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
        //To Prevent ViewHolder that had been loaded blink/ load
        meizhiRvAdapter.notifyItemRangeChanged(10 * paging++, Constants.MEIZHI_AMOUNT);
        refreshlayout.setRefreshing(false);
    }

    @Override
    public void networkError(Throwable e) {
        refreshlayout.setRefreshing(false);
        Snackbar.make(fab, "Error Message: " + e.getMessage(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void summonMeizhi(boolean clearItem) {
        if (clearItem) {
            MeizhiData.clear();
            paging = 1;
        }
        refreshlayout.setRefreshing(true);
        mainPresenter.loadMeizhi(paging, MeizhiData);
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
//        if (item.getItemId() == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab)
    public void onClick(View v) {
        Snackbar.make(v, "Show Other Pages", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
