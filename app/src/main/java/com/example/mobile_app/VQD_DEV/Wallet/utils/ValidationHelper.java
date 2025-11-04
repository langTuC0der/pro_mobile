package com.example.mobile_app.VQD_DEV.Wallet.utils;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ValidationHelper {

    public static boolean isEditTextEmpty(EditText editText, String errorMessage) {
        String text = editText.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            editText.setError(errorMessage);
            return true;
        }
        return false;
    }

    public static boolean isTextViewEmpty(TextView textView, String defaultHint, String errorMessage) {
        String text = textView.getText().toString();
        if (text.equals(defaultHint)) {
            textView.setError(errorMessage);
            return true;
        }
        return false;
    }
}
