package me.chkfung.meizhigank.Util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ExpandableListView;

import com.orhanobut.logger.Logger;

/**
 * Created by Fung on 17/08/2016.
 */

public class AnimExpandableListVIew extends ExpandableListView {
    public AnimExpandableListVIew(Context context) {
        super(context);
    }

    public AnimExpandableListVIew(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimExpandableListVIew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean expandGroup(int groupPos) {
        Logger.i("Not Animated");
        return super.expandGroup(groupPos);
    }

    @Override
    public boolean performItemClick(View v, int position, long id) {
        return super.performItemClick(v, position, id);
    }

    @Override
    public boolean expandGroup(int groupPos, boolean animate) {
        Logger.i("Animate :" + animate + " Group Pos: " + groupPos);
        return super.expandGroup(groupPos, animate);
    }

    @Override
    public boolean collapseGroup(int groupPos) {
        return super.collapseGroup(groupPos);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
