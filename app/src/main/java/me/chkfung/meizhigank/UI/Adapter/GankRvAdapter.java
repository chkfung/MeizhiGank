package me.chkfung.meizhigank.UI.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import me.chkfung.meizhigank.Model.Day;
import me.chkfung.meizhigank.R;
import me.chkfung.meizhigank.UI.GankInfoActivity;

/**
 * Created by Fung on 04/08/2016.
 */

public class GankRvAdapter extends RecyclerView.Adapter<GankRvAdapter.ViewHolder> {
    Day data;
    public GankRvAdapter(Day data) {
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
                //TODO Add try catch

                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,
                        anim1, anim2, anim3);
                Intent i = new Intent(context, GankInfoActivity.class);
                i.putParcelableArrayListExtra("Data", new ArrayList<Parcelable>(getDataOf(cat)));
                context.startActivity(i, optionsCompat.toBundle());

//                FragmentManager fm = ((AppCompatActivity) holder.itemView.getContext()).getSupportFragmentManager();
//                fm.beginTransaction()
//                        .add(new GankFragment(), "tag")
//                        .addSharedElement(holder.headerImage, "headerAnim")
//                        .addSharedElement(holder.abc, "abc")
//                        .commit();
//                GankFragment gankFragment = new GankFragment();
//
//                gankFragment.show(fm,"tag");
            }
        });
    }

    @Override
    public int getItemCount() {
        // Hide 福利 since it always come last after sorting
        return data.getCategory().size() - 1;
    }

    private List<Day.ResultsBean.DataBean> getDataOf(String selectedItem) {
        switch (selectedItem) {
            case "Android":
                return data.getResults().getAndroid();
            case "iOS":
                return data.getResults().getIOS();
            case "App":
                return data.getResults().getApp();
            case "休息视频":
                return data.getResults().getRestVideo();
            case "拓展资源":
                return data.getResults().getExtra();
            case "瞎推荐":
                return data.getResults().getRecommend();
            case "前端":
                return data.getResults().getFrontEnd();
            default:
                return null;
        }
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
