package me.chkfung.meizhigank.Util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by Fung on 09/08/2016.
 */

public class ExpandView extends FrameLayout {
    public ExpandView(Context context) {
        super(context);
    }

    public ExpandView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }
}
