package me.chkfung.meizhigank.UI.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.chkfung.meizhigank.Model.Day;
import me.chkfung.meizhigank.R;

/**
 * Created by Fung on 10/08/2016.
 */

public class GankInfoRvAdapter extends RecyclerView.Adapter<GankInfoRvAdapter.ViewHolder> {
    ArrayList<Day.ResultsBean.DataBean> Data;

    public GankInfoRvAdapter(ArrayList<Day.ResultsBean.DataBean> Data) {
        this.Data = Data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gankinfo, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(Data.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
