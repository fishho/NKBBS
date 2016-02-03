package com.cc.dfish.nkbbs.view;

/**
 * Created by dfish on 2016/2/3.
 */
import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

public class MyProgressBar extends View {
    private Paint paint;
    private Paint paint1;
    private Paint paint2;
    private Paint paint3;
    private Paint paint4;

    private float cx0 = -10;
    private float cx1 = -10;
    private float cx2 = -10;
    private float cx3 = -10;
    private float cx4 = -10;

    private long delay = 100;
    private long duration = 1500;
    private float start = -10;
    private float end ;
    private int desiredWidth = 500;
    private int desiredHeight = 10;

    private ObjectAnimator animator;
    private ObjectAnimator animator1;
    private ObjectAnimator animator2;
    private ObjectAnimator animator3;
    private ObjectAnimator animator4;
    private boolean isRunning =  false;

    public MyProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
    }

    public MyProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public MyProgressBar(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(getResources().getColor(android.R.color.holo_purple));

        paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint1.setColor(getResources().getColor(android.R.color.holo_orange_light));

        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2.setColor(getResources().getColor(android.R.color.holo_green_light));

        paint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint3.setColor(getResources().getColor(android.R.color.holo_blue_bright));

        paint4 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint4.setColor(getResources().getColor(android.R.color.holo_red_light));

        animator = ObjectAnimator.ofFloat(this, "cx0",start,end);
        animator.setDuration(duration);
        animator.setInterpolator(new DecelerateAccelerateInterpolator());
        animator.start();

        animator1 = ObjectAnimator.ofFloat(this, "cx1", start,end);
        animator1.setDuration(duration);
        animator1.setStartDelay(delay);
        animator1.setInterpolator(new DecelerateAccelerateInterpolator());

        animator2 = ObjectAnimator.ofFloat(this, "cx2", start,end);
        animator2.setDuration(duration);
        animator2.setStartDelay(delay*2);
        animator2.setInterpolator(new DecelerateAccelerateInterpolator());

        animator3 = ObjectAnimator.ofFloat(this, "cx3", start,end);
        animator3.setDuration(duration);
        animator3.setStartDelay(delay*3);
        animator3.setInterpolator(new DecelerateAccelerateInterpolator());

        animator4 = ObjectAnimator.ofFloat(this, "cx4", start, end);
        animator4.setDuration(duration);
        animator3.setStartDelay(delay*4);
        animator4.setInterpolator(new DecelerateAccelerateInterpolator());
        animator4.addListener(new AnimatorListener() {


            @Override
            public void onAnimationEnd(Animator animation) {
                // TODO Auto-generated method stub
                if(isRunning){
                    start();
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationStart(Animator animation) {
                // TODO Auto-generated method stub

            }

        });
    }

    public void start() {
        if (animator != null &&animator1 !=null && animator2 != null && animator3 != null
                && animator4 != null) {
            animator.start();
            animator1.start();
            animator2.start();
            animator3.start();
            animator4.start();
            isRunning = true;
        }
    }

    public void cancle() {
        if (animator != null && animator1 != null && animator2 != null &&
                animator3 != null && animator4 != null) {
            animator.cancel();
            animator1.cancel();
            animator2.cancel();
            animator3.cancel();
            animator4.cancel();
            isRunning =  false;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        if(!isRunning) {
            start();
            isRunning = true;
        }
        canvas.drawCircle(cx0, 12, 10, paint);
        canvas.drawCircle(cx1, 12, 10, paint1);
        canvas.drawCircle(cx2, 12, 10, paint2);
        canvas.drawCircle(cx3, 12, 10, paint3);
        canvas.drawCircle(cx4, 12, 10, paint4);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int	heightMode = MeasureSpec.getMode(heightMeasureSpec);

        setMeasuredDimension(widthSize, heightSize);
        end = -start +widthSize;
        init();
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        // TODO Auto-generated method stub
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == View.VISIBLE && !isRunning) {
            Log.i("myProgressbar","可见，运行");
            start();
        } else if (visibility == View.GONE && isRunning) {
            Log.i("myProgressbar","不可见，停止");
            cancle();
        }

    }

    public float getCx0() {
        return cx0;
    }

    public void setCx0(float cx0) {
        this.cx0 = cx0;
        invalidate();
    }

    public float getCx1() {
        return cx1;
    }

    public void setCx1(float cx1) {
        this.cx1 = cx1;
        invalidate();
    }

    public float getCx2() {
        return cx2;
    }

    public void setCx2(float cx2) {
        this.cx2 = cx2;
        invalidate();
    }

    public float getCx3() {
        return cx3;
    }

    public void setCx3(float cx3) {
        this.cx3 = cx3;
        invalidate();
    }

    public float getCx4() {
        return cx4;
    }

    public void setCx4(float cx4) {
        this.cx4 = cx4;
        invalidate();
    }

    private class DecelerateAccelerateInterpolator implements TimeInterpolator {
        private DecelerateAccelerateInterpolator() {

        }
        @Override
        public float getInterpolation(float input) {
            // TODO Auto-generated method stub
            return (float)(Math.asin(2*input-1)/Math.PI+0.5);
        }

    }



}

