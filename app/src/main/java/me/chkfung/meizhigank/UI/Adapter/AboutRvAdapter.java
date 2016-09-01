package me.chkfung.meizhigank.ui.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.chkfung.meizhigank.R;

/**
 * Created by Fung on 23/08/2016.
 */

public class AboutRvAdapter extends RecyclerView.Adapter<AboutRvAdapter.ViewHolder> {
    private final String[][] data;

    public AboutRvAdapter(String[][] data) {
        this.data = data;
    }

    @Override
    public AboutRvAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_aboutme, parent, false));

    }

    @Override
    public void onBindViewHolder(AboutRvAdapter.ViewHolder holder, int position) {
        String formattedText = holder.itemView.getResources().getString(R.string.format_open_source_library_author, data[position][0], data[position][1]);
        holder.title.setText(formattedText);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
