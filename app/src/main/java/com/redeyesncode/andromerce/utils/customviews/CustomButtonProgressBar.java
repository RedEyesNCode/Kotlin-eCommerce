package com.redeyesncode.andromerce.utils.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.redeyesncode.andromerce.R;

public class CustomButtonProgressBar extends RelativeLayout {

    private TextView tvButtonText;
    private ProgressBar buttonProgress;
    private String originalButtonText;

    public CustomButtonProgressBar(Context context) {
        super(context);
        init(context);
    }

    public CustomButtonProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomButtonProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_button_progress_dialog_layout, this, true);
        tvButtonText = findViewById(R.id.button_text);
        buttonProgress = findViewById(R.id.button_progress);
    }
    public void setTvButtonText(String text){
        tvButtonText.setText(text);
    }

    public void showProgress(String progressText) {
        buttonProgress.setVisibility(VISIBLE);
        tvButtonText.setText(progressText);
    }
    public boolean isLoading(){
        if(View.VISIBLE== buttonProgress.getVisibility()){
            return true;
        }else{
            return false;
        }



    }
    public void shakeButton() {
        TranslateAnimation anim = new TranslateAnimation(0, 50, 0, 0);
        anim.setDuration(100);
        anim.setInterpolator(new CycleInterpolator(2));
        this.startAnimation(anim);
    }
    public void hideProgress(String buttonText) {
        buttonProgress.setVisibility(INVISIBLE);
        tvButtonText.setText(buttonText);
    }

    public void reset() {
        buttonProgress.setVisibility(INVISIBLE);
        tvButtonText.setText(originalButtonText);
    }

}
