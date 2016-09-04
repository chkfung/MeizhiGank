/*
 * Meizhi & Gank.io
 * Copyright (C) 2016 ChkFung
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package me.chkfung.meizhigank.ui.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.chkfung.meizhigank.Model.DataInfo;
import me.chkfung.meizhigank.Model.Day;
import me.chkfung.meizhigank.R;
import me.chkfung.meizhigank.Util.CommonUtil;
import me.chkfung.meizhigank.ui.GankInfoActivity;

/**
 * Grid Adapter in Gank Activity Grid Layout
 * A Simple Adapter that open a Transparent Theme
 * {@link me.chkfung.meizhigank.ui.GankInfoActivity}
 * Created by Fung on 04/08/2016.
 */

public class GankRvAdapter extends RecyclerView.Adapter<GankRvAdapter.ViewHolder> {
    private Day data;

    public void setup(Day data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gank, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final String cat = data.getCategory().get(position);
        holder.headerText.setText(cat);
        holder.cardGank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = holder.itemView.getContext();
                Pair<View, String> anim1 = Pair.create((View) holder.cardGank, "GankTransitionCard");
                Pair<View, String> anim2 = Pair.create((View) holder.headerImage, "GankTransitionImage");
                Pair<View, String> anim3 = Pair.create((View) holder.headerText, "GankTransitionText");

                ActivityOptionsCompat optionsCompat;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    //noinspection unchecked
                    optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,
                            anim1, anim2, anim3);
                else
                    optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(holder.cardGank, 0, 0, holder.cardGank.getWidth(), holder.cardGank.getHeight());
                Intent i = new Intent(context, GankInfoActivity.class);
                List<DataInfo> dataInfosList = CommonUtil.getDataOf(data, cat);
                if (dataInfosList != null) {
                    i.putParcelableArrayListExtra("Data", new ArrayList<Parcelable>(dataInfosList));
                    context.startActivity(i, optionsCompat.toBundle());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        // Hide 福利 since it always come last after sorting
        return data.getCategory().size() - 1;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.headerImage)
        ImageView headerImage;
        @BindView(R.id.headerText)
        TextView headerText;
        @BindView(R.id.card_gank)
        CardView cardGank;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
