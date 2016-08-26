package me.chkfung.meizhigank.UI.Adapter;

import android.animation.ValueAnimator;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.chkfung.meizhigank.MeizhiApp;
import me.chkfung.meizhigank.Model.DataInfo;
import me.chkfung.meizhigank.Model.Day;
import me.chkfung.meizhigank.R;
import me.chkfung.meizhigank.Util.CommonUtil;

/**
 * Created by Fung on 17/08/2016.
 */

public class GankExpandableRvAdapter extends RecyclerView.Adapter<GankExpandableRvAdapter.ViewHolder> {
    private Day data;
    private List<GankExpandableRvSubAdapter> GankAdapterReuse;
    private int[] item_height;

    public void setup(Day day) {
        data = day;
        GankAdapterReuse = new ArrayList<>();
        for (String category : day.getCategory()) {
            GankAdapterReuse.add(new GankExpandableRvSubAdapter(CommonUtil.getDataOf(day, category)));
        }
        item_height = new int[this.getItemCount()];
    }

    @Override
    public GankExpandableRvAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expandable_gank_header, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GankExpandableRvAdapter.ViewHolder holder, int position) {
        String cat = data.getCategory().get(position);
        holder.title.setText(cat);
        holder.subItem.setAdapter(GankAdapterReuse.get(position));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(holder.itemView.getContext());
        holder.subItem.setLayoutManager(layoutManager);

        //Preserve Height Status
        holder.subItem.getLayoutParams().height = item_height[position];
        holder.subItem.requestLayout();
        holder.rv_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.subItem.getMeasuredHeight() > 0) {
                    holder.icRotate.animate().rotation(0f).setDuration(200).start();
                    Collapse(holder.subItem, holder.getAdapterPosition());
                } else {
                    holder.icRotate.animate().rotation(180f).setDuration(200).start();
                    Expand(holder.subItem, holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.getCategory().size() - 1;
    }

    /**
     * Expand View
     *
     * @param view     View to Expand
     * @param position Store the height of RecyclerView Cell before OnBind Called.
     */
    private void Expand(final View view, int position) {
        int widthMeasure = View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.AT_MOST);
        int heightMeasure = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthMeasure, heightMeasure);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, view.getMeasuredHeight());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.getLayoutParams().height = (int) animation.getAnimatedValue();
                view.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.setDuration(200);
        valueAnimator.start();
        item_height[position] = view.getMeasuredHeight();
    }

    /**
     * Collapse View
     *
     * @param view     View to Collapse
     * @param position Store the height of RecyclerView Cell before OnBind Called.
     */
    private void Collapse(final View view, int position) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(view.getMeasuredHeight(), 0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.getLayoutParams().height = (int) animation.getAnimatedValue();
                view.requestLayout();
            }
        });

        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(200);
        valueAnimator.start();
        item_height[position] = 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.ic_rotate)
        ImageView icRotate;
        @BindView(R.id.subItem)
        RecyclerView subItem;
        @BindView(R.id.rv_header)
        CardView rv_header;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    /**
     * Sub RecycleView
     */
    class GankExpandableRvSubAdapter extends RecyclerView.Adapter<GankExpandableRvSubAdapter.ViewHolder> {
        List<DataInfo> dataBean;

        GankExpandableRvSubAdapter(List<DataInfo> dataBean) {
            this.dataBean = dataBean;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expandable_gank_list, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            Context mContext = holder.itemView.getContext();
            DataInfo dataInfo = dataBean.get(position);

            String via = holder.itemView.getContext().getString(R.string.via_format, dataInfo.getWho());
            SpannableString spannableString = new SpannableString(via);
            spannableString.setSpan(new TextAppearanceSpan(mContext, R.style.ViaTextAppearance), 0, via.length(), 0);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(dataInfo.getDesc())
                    .append(spannableString);
            CharSequence DescText = spannableStringBuilder.subSequence(0, spannableStringBuilder.length());

            holder.title.setText(DescText);


            final String url = dataInfo.getUrl();
            holder.subItem_handle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context mcontext = v.getContext();
                    ((MeizhiApp) MeizhiApp.get(mcontext)).CustomTabLaunch(mcontext, Uri.parse(url));
                }
            });


        }

        @Override
        public int getItemCount() {
            return dataBean.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.title)
            TextView title;
            @BindView(R.id.subItem_handle)
            LinearLayout subItem_handle;

            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }

}
