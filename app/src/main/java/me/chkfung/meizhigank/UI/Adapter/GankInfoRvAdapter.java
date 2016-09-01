package me.chkfung.meizhigank.UI.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.chkfung.meizhigank.Model.DataInfo;
import me.chkfung.meizhigank.R;
import me.chkfung.meizhigank.UI.WebActivity;

/**
 * Created by Fung on 10/08/2016.
 */

public class GankInfoRvAdapter extends RecyclerView.Adapter<GankInfoRvAdapter.ViewHolder> {
    private ArrayList<DataInfo> Data;

    public GankInfoRvAdapter(ArrayList<DataInfo> Data) {
        this.Data = Data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gankinfo, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final String titleText = Data.get(position).getDesc();
        holder.title.setText(titleText);
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = holder.itemView.getContext();
                context.startActivity(
                        WebActivity.newIntent(context, titleText, Data.get(holder.getAdapterPosition()).getUrl()));
            }
        });
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
