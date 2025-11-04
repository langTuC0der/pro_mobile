package com.example.mobile_app.VQD_DEV.Wallet.dialogs;

import android.app.Dialog;
import android.content.Context; // <-- Thêm import
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import com.example.mobile_app.R;

public class SuccessDialog extends DialogFragment {

    // 1. Định nghĩa interface để giao tiếp ngược lại Activity
    public interface OnHomeClickListener {
        void onHomeClicked();
    }

    private OnHomeClickListener listener;
    private AppCompatButton btnGoHome;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.vqd_dialog_success, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnGoHome = view.findViewById(R.id.btn_go_home);

        btnGoHome.setOnClickListener(v -> {
            // 4. Gọi hàm của interface khi nút được click
            if (listener != null) {
                listener.onHomeClicked();
            }
            dismiss(); // Tắt dialog

            // (Không gọi finish() ở đây nữa)
        });
    }

    // 2. Gán Activity (phải là RegisterFundActivity) làm listener
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (OnHomeClickListener) context;
        } catch (ClassCastException e) {
            // Đảm bảo rằng Activity cha đã implement interface
            throw new ClassCastException(context.toString() + " phải implement OnHomeClickListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // (Code onStart của bạn để chỉnh kích thước đã đúng, giữ nguyên)
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                int width = (int)(getResources().getDisplayMetrics().widthPixels * 0.90);
                int height = ViewGroup.LayoutParams.WRAP_CONTENT;
                window.setLayout(width, height);
                window.setBackgroundDrawableResource(android.R.color.transparent);
            }
        }
    }
}