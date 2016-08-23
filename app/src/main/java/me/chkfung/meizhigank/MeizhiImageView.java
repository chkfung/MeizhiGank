package me.chkfung.meizhigank;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Fung on 25/07/2016.
 */

public class MeizhiImageView extends ImageView {

    public MeizhiImageView(Context context) {
        super(context);
    }

    public MeizhiImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MeizhiImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    public MeizhiImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }

    public void setOriginalSize(int originalWidth, int originalHeight) {
        int originalWidth1 = originalWidth;
        int originalHeight1 = originalHeight;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int width = getMeasuredWidth();
//        setMeasuredDimension(width, width);
    }
}
