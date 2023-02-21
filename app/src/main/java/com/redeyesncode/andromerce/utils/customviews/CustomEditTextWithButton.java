package com.redeyesncode.andromerce.utils.customviews;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.redeyesncode.andromerce.R;

public class CustomEditTextWithButton extends LinearLayout {
    private EditText editText;
    private Button verifyButton;

    private onEvent onEventActivity;



    public void setOnVerifyButtonClickListener(onEvent listener) {
        this.onEventActivity = listener;
    }



    public CustomEditTextWithButton(Context context) {
        super(context);
        init();
    }

    public CustomEditTextWithButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomEditTextWithButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.custom_edit_text_button, this);
        editText = findViewById(R.id.edit_text);
        verifyButton = findViewById(R.id.btnVerifyOtp);
        verifyButton.setEnabled(false);
        verifyButton.setOnClickListener(v -> {
            if(verifyButton.isEnabled()){
                onEventActivity.onVerifyOtpClick(editText.getText().toString());

            }

        });


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 6) {
                    verifyButton.setEnabled(true);
                } else {
                    verifyButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed
            }
        });
    }

    public interface onEvent{
        void onVerifyOtpClick(String enteredOtp);

    }

    // Add any additional methods or listeners as needed
}