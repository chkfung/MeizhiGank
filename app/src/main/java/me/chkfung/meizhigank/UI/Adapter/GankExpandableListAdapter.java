package me.chkfung.meizhigank.UI.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import me.chkfung.meizhigank.Model.Day;
import me.chkfung.meizhigank.R;
import me.chkfung.meizhigank.Util.CommonUtil;

/**
 * Created by Fung on 16/08/2016.
 */

public class GankExpandableListAdapter extends BaseExpandableListAdapter {
    Day data;

    public GankExpandableListAdapter(Day day) {
        data = day;
    }

    @Override
    public int getGroupCount() {
        // Hide 福利 since it always come last after sorting
        return data.getCategory().size() - 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return CommonUtil.getDataOf(data, data.getCategory().get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expandable_gank_header, parent, false);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        title.setText(data.getCategory().get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            Logger.i("null Init");
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expandable_gank_list, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(viewHolder);
        } else {
            Logger.i("else Init");
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Logger.i(groupPosition + ":" + childPosition);
        viewHolder.title.setText(CommonUtil.getDataOf(data, data.getCategory().get(groupPosition)).get(childPosition).getDesc());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    static class ViewHolder {
        TextView title;
    }
}
