package com.cheng.animationstudy;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class UiAnimStreamControl extends AppCompatActivity {

    private ObjectAnimator mAnimatior;
    private ImageView mShowAnimImgIV;
    private TextView mIsStartedTV;
    private TextView mIsRunningTV;
    private TextView mIsPausedTV;
    private TextView mMessageTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_animstreamcontrol);
        mIsStartedTV = (TextView) findViewById(R.id.sdi_isstarted_tv);
        mIsRunningTV = (TextView) findViewById(R.id.sdi_isrunning_tv);
        mIsPausedTV = (TextView) findViewById(R.id.sdi_ispaused_tv);
        mMessageTV = (TextView) findViewById(R.id.sdi_message_tv);
        mShowAnimImgIV = (ImageView) findViewById(R.id.sdi_showanim_iv);

        mAnimatior = ObjectAnimator.ofFloat(mShowAnimImgIV, "rotation", 0, 360);
        mAnimatior.setDuration(C.Int.ANIM_DURATION * 2);
        mAnimatior.setStartDelay(C.Int.ANIM_DURATION);
        mAnimatior.setRepeatCount(5);
        mAnimatior.setRepeatMode(ObjectAnimator.RESTART);

        Animator.AnimatorListener animationListener = new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                setStatusTexts();
                mMessageTV.setText("started");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                setStatusTexts();
                mMessageTV.setText("repeating");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setStatusTexts();
                mMessageTV.setText(mMessageTV.getText() + " -- ended");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                setStatusTexts();
                mMessageTV.setText("cancelled");
            }
        };


        mAnimatior.addListener(animationListener);
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            Animator.AnimatorPauseListener pauseListener = new Animator.AnimatorPauseListener() {

                @Override
                public void onAnimationResume(Animator animation) {
                    setStatusTexts();
                    mMessageTV.setText("resumed");
                }

                @Override
                public void onAnimationPause(Animator animation) {
                    setStatusTexts();
                    mMessageTV.setText("paused");
                }
            };
            mAnimatior.addPauseListener(pauseListener);
        }
    }

    @SuppressLint("NewApi")
    public void setStatusTexts() {
        if (android.os.Build.VERSION.SDK_INT >= 14)
            mIsStartedTV.setText("isStarted = " + mAnimatior.isStarted());

        mIsRunningTV.setText("isRunning = " + mAnimatior.isRunning());

        if (android.os.Build.VERSION.SDK_INT >= 19)
            mIsPausedTV.setText("isPaused = " + mAnimatior.isPaused());
    }

    public void startAnimation(View view) {
        mAnimatior.start();
    }

    public void endAnimation(View view) {
        mAnimatior.end();
    }

    public void cancelAnimation(View view) {
        mAnimatior.cancel();
    }

    @SuppressLint("NewApi")
    public void pauseAnimation(View view) {
        if (android.os.Build.VERSION.SDK_INT >= 19)
            mAnimatior.pause();
    }

    @SuppressLint("NewApi")
    public void resumeAnimation(View view) {
        if (android.os.Build.VERSION.SDK_INT >= 19)
            mAnimatior.resume();
    }
}