package me.chkfung.meizhigank.UI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ViewSwitcher;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.chkfung.meizhigank.Contract.GankContract;
import me.chkfung.meizhigank.Model.Day;
import me.chkfung.meizhigank.R;
import me.chkfung.meizhigank.UI.Adapter.GankExpandableRvAdapter;
import me.chkfung.meizhigank.UI.Adapter.GankRvAdapter;

/**
 * Created by Fung on 26/08/2016.
 */

public class GankFragment extends Fragment implements GankContract.View {
    private final static String AGRS_DATE = "DATE";
    @Inject
    SharedPreferences sharedPreferences;
    //    @Inject
    GankExpandableRvAdapter gankExpandableRvAdapter = new GankExpandableRvAdapter();
    //    @Inject
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    ;
    //    @Inject
    GankRvAdapter gankRvAdapter = new GankRvAdapter();
    @Inject
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    GankContract.Presenter gankPresenter;
    @BindView(R.id.expandable_listview)
    RecyclerView expandableListview;
    @BindView(R.id.rv_gank)
    RecyclerView rvGank;
    @BindView(R.id.viewswitcher)
    ViewSwitcher viewswitcher;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.refresh)
    ImageButton refresh;
    @BindView(R.id.viewswitcher_loading)
    ViewSwitcher viewswitcherLoading;
    Day mDay;
    private String Date;

    public static GankFragment newInstance(String Date) {
        GankFragment gankFragment = new GankFragment();
        Bundle args = new Bundle();
        args.putString(AGRS_DATE, Date);
        gankFragment.setArguments(args);
        return gankFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        Date = bundle.getString(AGRS_DATE);
        if (savedInstanceState == null)
            onRefresh();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gank, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        viewswitcherLoading.setVisibility(View.INVISIBLE);
    }

    @Override
    public void animateToolbar() {

    }


    @Override
    public void setupRecycleView(Day day) {
        mDay = day;
        viewswitcherLoading.setVisibility(View.INVISIBLE);
        gankExpandableRvAdapter.setup(day);
        expandableListview.setAdapter(gankExpandableRvAdapter);
        expandableListview.setLayoutManager(linearLayoutManager);
        rvGank.setAdapter(gankRvAdapter);
        rvGank.setLayoutManager(staggeredGridLayoutManager);
    }

    @Override
    public void showError(Throwable e) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_gank, menu);
//        setMenuIcon(menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                onBackPressed();
                break;
            case R.id.action_viewswitcher:
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (viewswitcher.getCurrentView() == expandableListview) {
                    viewswitcher.showNext();
                    editor.putBoolean("GRID_LAYOUT", true);

                    item.setIcon(R.drawable.ic_view_expand);

                } else {
                    viewswitcher.showPrevious();
                    editor.putBoolean("GRID_LAYOUT", false);
                    item.setIcon(R.drawable.ic_view_grid);

                }
                editor.apply();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    public void setMenuIcon(Menu menu) {
        if (sharedPreferences.getBoolean("GRID_LAYOUT", false)) {
            viewswitcher.showNext();
            menu.findItem(R.id.action_viewswitcher).setIcon(R.drawable.ic_view_expand);
        } else {
            menu.findItem(R.id.action_viewswitcher).setIcon(R.drawable.ic_view_grid);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("Day", mDay);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            mDay = savedInstanceState.getParcelable("Day");
            gankExpandableRvAdapter.setup(mDay);
            expandableListview.setAdapter(gankExpandableRvAdapter);
            expandableListview.setLayoutManager(linearLayoutManager);
        }
    }

    @OnClick(R.id.refresh)
    public void onRefresh() {
//        if (viewswitcherLoading.getCurrentView() != progressbar)
//            viewswitcherLoading.showPrevious();
        gankPresenter.getGank(Date);

    }

    @Override
    public void setPresenter(GankContract.Presenter presenter) {
        gankPresenter = presenter;
    }
}
