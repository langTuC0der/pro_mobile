package com.example.mobile_app.VQD_DEV.Wallet;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.mobile_app.R;

public class vqd_WalletRegisterFundActivity extends AppCompatActivity {

    private ImageView btnBack;
    private RelativeLayout rlSelectService;
    private TextView tvSelectService;
    private AppCompatButton btnUpload;
    private LinearLayout uploadSection;
    private ImageView imgUploadedProof;
    private AppCompatButton btnContinueForm;

    private boolean isProofUploaded = false;

    // 1. Khai báo biến launcher để mở thư viện ảnh
    private ActivityResultLauncher<String> pickImageLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vqd_wallet_activity_register_fund);

        initViews();
        setupImagePicker(); // Cấu hình bộ chọn ảnh
        setupEvents();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btn_back);
        rlSelectService = findViewById(R.id.rl_select_service);
        tvSelectService = findViewById(R.id.tv_select_service);

        btnUpload = findViewById(R.id.btn_upload);
        uploadSection = findViewById(R.id.upload_section);
        imgUploadedProof = findViewById(R.id.img_uploaded_proof);

        btnContinueForm = findViewById(R.id.btn_continue_form);
    }

    // 2. Cấu hình xử lý khi người dùng chọn xong ảnh
    private void setupImagePicker() {
        pickImageLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
            if (uri != null) {
                // Người dùng đã chọn 1 ảnh
                // A. Hiện layout chứa ảnh
                uploadSection.setVisibility(View.VISIBLE);

                // B. Gán ảnh vừa chọn vào ImageView
                imgUploadedProof.setImageURI(uri);

                // C. Đánh dấu đã upload
                isProofUploaded = true;

                Toast.makeText(this, "Đã chọn ảnh thành công!", Toast.LENGTH_SHORT).show();
            } else {
                // Người dùng hủy chọn (bấm back)
                Toast.makeText(this, "Chưa chọn ảnh nào", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupEvents() {
        btnBack.setOnClickListener(v -> finish());

        rlSelectService.setOnClickListener(v -> showServiceDialog());

        // 3. Sự kiện nút Up Build -> Mở thư viện ảnh
        btnUpload.setOnClickListener(v -> {
            // "image/*" nghĩa là chỉ lọc lấy các file là hình ảnh
            pickImageLauncher.launch("image/*");
        });

        btnContinueForm.setOnClickListener(v -> {
            if (!isProofUploaded) {
                Toast.makeText(this, "Vui lòng Up build thanh toán trước!", Toast.LENGTH_SHORT).show();
                return;
            }
            showSuccessDialog();
        });
    }

    // Dialog chọn dịch vụ
    private void showServiceDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.vqd_wallet_dialog_full_service);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        ImageView btnClose = dialog.findViewById(R.id.vqd_img_close);
        TextView tv1 = dialog.findViewById(R.id.vqd_tv_service_1);
        TextView tv2 = dialog.findViewById(R.id.vqd_tv_service_2);
        TextView tv3 = dialog.findViewById(R.id.vqd_tv_service_3);
        TextView tv4 = dialog.findViewById(R.id.vqd_tv_service_4);
        TextView tv5 = dialog.findViewById(R.id.vqd_tv_service_5);
        TextView tv6 = dialog.findViewById(R.id.vqd_tv_service_6);

        TextView[] allServices = {tv1, tv2, tv3, tv4, tv5, tv6};

        View.OnClickListener listener = view -> {
            for (TextView tv : allServices) tv.setBackgroundResource(R.drawable.vqd_square_white_bg);
            view.setBackgroundResource(R.drawable.vqd_square_light_green_bg);

            String selectedText = ((TextView) view).getText().toString();
            tvSelectService.setText(selectedText);
            dialog.dismiss();
        };

        for (TextView tv : allServices) tv.setOnClickListener(listener);
        btnClose.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    // Dialog thành công
    private void showSuccessDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.vqd_dialog_success);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        AppCompatButton btnGoHome = dialog.findViewById(R.id.btn_go_home);
        btnGoHome.setOnClickListener(v -> {
            // Lưu trạng thái ĐÃ ĐĂNG KÝ
            SharedPreferences prefs = getSharedPreferences("ViTakerPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("is_registered", true);
            editor.apply();

            dialog.dismiss();
            finish(); // Quay về HomeActivity
        });

        dialog.show();
    }
}