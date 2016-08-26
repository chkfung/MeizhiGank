package me.chkfung.meizhigank.UI.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.chkfung.meizhigank.MeizhiImageView;
import me.chkfung.meizhigank.Model.DataInfo;
import me.chkfung.meizhigank.R;
import me.chkfung.meizhigank.UI.GankActivity;
import me.chkfung.meizhigank.UI.MeizhiActivity;

/**
 * Created by Fung on 21/07/2016.
 */

public class MeizhiRvAdapter extends RecyclerView.Adapter<MeizhiRvAdapter.ViewHolder> {
    private List<DataInfo> meizhiList;

    public MeizhiRvAdapter() {
    }

    public void setMeizhiList(ArrayList<DataInfo> meizhiList) {
        this.meizhiList = meizhiList;
    }
    @Override
    public long getItemId(int position) {
        return meizhiList.get(position).hashCode();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meizhi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Context mContext = holder.itemView.getContext();
        //Content
        Glide.with(mContext)
                .load(meizhiList.get(position).getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.image);

        holder.title.setText(meizhiList.get(position).getDesc());

        //Event
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, MeizhiActivity.class);
                i.putExtra("URL", meizhiList.get(holder.getAdapterPosition()).getUrl());
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, holder.image, "MeizhiImage");
                mContext.startActivity(i, optionsCompat.toBundle());
            }
        });
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(meizhiList.get(holder.getAdapterPosition()).getPublishedAt());
                Intent i = new Intent(mContext, GankActivity.class);
                i.putExtra("Date", calendar.get(Calendar.YEAR)
                        + "/" + (calendar.get(Calendar.MONTH) + 1)
                        + "/" + calendar.get(Calendar.DAY_OF_MONTH));
                mContext.startActivity(i);
//                ((Activity) mContext).overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
//                ((Activity) mContext).overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);


            }
        });

    }


    @Override
    public int getItemCount() {
        return meizhiList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image)
        MeizhiImageView image;
        @BindView(R.id.title)
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
