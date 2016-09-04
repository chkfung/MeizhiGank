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

package me.chkfung.meizhigank.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Fung on 02/09/2016.
 */

public class LoadingCircleView extends View {

    private Paint OuterCircle;
    private Paint PaddingCircle;
    private Paint ProgressCircle;
    private RectF rectF;
    private long animDuration = 2000;
    private float sweepAngle = 0;
    private float progressCirclePadding = 10;

    public LoadingCircleView(Context context) {
        super(context);
        init();
    }

    public LoadingCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public LoadingCircleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {

        LoadingCircleViewAnim mLoadingCircleViewAnim = new LoadingCircleViewAnim();
        mLoadingCircleViewAnim.setDuration(animDuration);

        OuterCircle = new Paint();
        OuterCircle.setAntiAlias(true);
        OuterCircle.setStyle(Paint.Style.STROKE);
        OuterCircle.setColor(Color.argb(255, 255, 255, 255));
        OuterCircle.setStrokeWidth(3f);

        PaddingCircle = new Paint();
        PaddingCircle.setAntiAlias(true);
        PaddingCircle.setStyle(Paint.Style.FILL);
        PaddingCircle.setColor(Color.argb(128, 0, 0, 0));

        ProgressCircle = new Paint();
        ProgressCircle.setAntiAlias(true);
        ProgressCircle.setStyle(Paint.Style.FILL);
        ProgressCircle.setColor(Color.argb(255, 255, 255, 255));

        rectF = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, getMeasuredWidth() / 2, PaddingCircle);
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, getMeasuredWidth() / 2 - progressCirclePadding / 2, OuterCircle);

        rectF.set(progressCirclePadding, progressCirclePadding, getMeasuredWidth() - progressCirclePadding, getMeasuredWidth() - progressCirclePadding);

        canvas.drawArc(rectF, -90f, sweepAngle, true, ProgressCircle);
    }

    public void setProgress(int progress) {
        sweepAngle = (float) (360 * progress / 100);
        invalidate();
    }

    private class LoadingCircleViewAnim extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            if (interpolatedTime < 1.0f) {
                sweepAngle = 360 * interpolatedTime;
                invalidate();
            }

        }
    }

}
