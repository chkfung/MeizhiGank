package me.chkfung.meizhigank.UI;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.chkfung.meizhigank.Base.BaseActivity;
import me.chkfung.meizhigank.MeizhiApp;
import me.chkfung.meizhigank.Model.Meizhi;
import me.chkfung.meizhigank.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    final List<Meizhi.ResultsBean> data = new ArrayList<Meizhi.ResultsBean>();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_meizhi)
    RecyclerView rvMeizhi;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    MeizhiRvAdapter meizhiRvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar(toolbar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        meizhiRvAdapter = new MeizhiRvAdapter(this, data);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvMeizhi.setAdapter(meizhiRvAdapter);
        rvMeizhi.setLayoutManager(layoutManager);
        MeizhiApp meizhiApp = MeizhiApp.get(this);
//        try{
        Call<Meizhi> az = meizhiApp.getNetworkApi().getMeizhi(1);
        az.enqueue(new Callback<Meizhi>() {
            @Override
            public void onResponse(Call<Meizhi> call, Response<Meizhi> response) {
                Logger.json(response.body().toString());
                for (Meizhi.ResultsBean z : response.body().getResults()
                        ) {

                    data.add(z);
                }
                setupRV();
            }

            @Override
            public void onFailure(Call<Meizhi> call, Throwable t) {
                Logger.e(t, "Error Occur");
            }
        });
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
    }

    public void setupRV() {
        meizhiRvAdapter.notifyDataSetChanged();
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
