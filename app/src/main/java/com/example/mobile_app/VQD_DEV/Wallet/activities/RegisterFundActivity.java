package com.example.mobile_app.VQD_DEV.Wallet.activities;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import android.app.Activity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.mobile_app.R;
import com.example.mobile_app.VQD_DEV.Wallet.dialogs.ServiceSelectionDialog;
import com.example.mobile_app.VQD_DEV.Wallet.dialogs.SuccessDialog;
import com.example.mobile_app.VQD_DEV.Wallet.utils.QRCodeHelper;
import com.example.mobile_app.VQD_DEV.Wallet.utils.ValidationHelper;

public class RegisterFundActivity extends AppCompatActivity
        implements ServiceSelectionDialog.OnServiceSelectedListener,SuccessDialog.OnHomeClickListener {

    private ImageView btnBack;
    private LinearLayout formSection;
    private EditText etFullName, etPhone, etCccd, etAddress, etFundAmount;
    private RelativeLayout rlSelectService;
    private TextView tvSelectService;
    private AppCompatButton btnContinueForm;
    private LinearLayout qrSection;
    // TÊN ĐÚNG CỦA BIẾN LÀ 'imgQrCode'
    private ImageView imgQrCode;
    private TextView tvBankInfo;
    private AppCompatButton btnUpload;
    private LinearLayout uploadSection;
    private ImageView imgUploadedProof;
    private AppCompatButton btnContinueFinal;

    private ActivityResultLauncher<String> galleryLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vqd_activity_register_fund);

        // Ánh xạ View
        btnBack = findViewById(R.id.btn_back);
        formSection = findViewById(R.id.form_section);
        etFullName = findViewById(R.id.et_full_name);
        etPhone = findViewById(R.id.et_phone);
        etCccd = findViewById(R.id.et_cccd);
        etAddress = findViewById(R.id.et_address);
        etFundAmount = findViewById(R.id.et_fund_amount);
        rlSelectService = findViewById(R.id.rl_select_service);
        tvSelectService = findViewById(R.id.tv_select_service);
        btnContinueForm = findViewById(R.id.btn_continue_form);
        qrSection = findViewById(R.id.qr_section);
        imgQrCode = findViewById(R.id.img_qr_code); // Ánh xạ đúng ID
        tvBankInfo = findViewById(R.id.tv_bank_info);
        btnUpload = findViewById(R.id.btn_upload);
        uploadSection = findViewById(R.id.upload_section);
        imgUploadedProof = findViewById(R.id.img_uploaded_proof);
        btnContinueFinal = findViewById(R.id.btn_continue_final);

        setupClickListeners();
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        showUploadSection(uri);
                    } else {
                        Toast.makeText(this, "Chưa chọn ảnh", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        showQrSection();

    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> finish());

        rlSelectService.setOnClickListener(v -> {
            ServiceSelectionDialog dialog = new ServiceSelectionDialog();
            dialog.setOnServiceSelectedListener(this);
            dialog.show(getSupportFragmentManager(), "ServiceSelectionDialog");
        });

        btnContinueForm.setOnClickListener(v -> {
            if (!validateForm()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }
        });

        btnUpload.setOnClickListener(v -> {
            galleryLauncher.launch("image/*");
        });

        btnContinueFinal.setOnClickListener(v -> {
            SuccessDialog successDialog = new SuccessDialog();
            successDialog.show(getSupportFragmentManager(), "SuccessDialog");
        });
    }

    private boolean validateForm() {
        if (ValidationHelper.isEditTextEmpty(etFullName, "Họ tên không được rỗng")) return false;
        if (ValidationHelper.isEditTextEmpty(etPhone, "SĐT không được rỗng")) return false;
        if (ValidationHelper.isEditTextEmpty(etCccd, "CCCD không được rỗng")) return false;
        if (ValidationHelper.isEditTextEmpty(etAddress, "Địa chỉ không được rỗng")) return false;
        if (ValidationHelper.isTextViewEmpty(tvSelectService, "Chọn dịch vụ", "Vui lòng chọn dịch vụ")) return false;
        if (ValidationHelper.isEditTextEmpty(etFundAmount, "Số tiền không được rỗng")) return false;
        return true;
    }

    // Hàm này đã xử lý việc tạo và hiển thị QR ĐÚNG CÁCH
    private void showQrSection() {
        btnContinueForm.setVisibility(View.GONE);
        String bankInfo = "Ngân hàng: Vietinbank\nSTK: 0374320573\nChủ TK: Ngô Thị Thanh Loan";
        tvBankInfo.setText(bankInfo);

        String amount = etFundAmount.getText().toString().trim();
        String qrContent = "00020101021238570010A00000072701270006970453010303743205730208QRIBFTTA5303704540" + amount.length() + amount + "5802VN6215NGOTHITHANHLOAN6304EAD8";
        // Gọi helper để tạo QR với 2 tham số (đúng)
        Bitmap qrBitmap = QRCodeHelper.generateQRCode(qrContent, 400);

        if (qrBitmap != null) {
            // Gán bitmap vào ImageView với tên biến đúng
            imgQrCode.setImageBitmap(qrBitmap);
        } else {
            Toast.makeText(this, "Lỗi tạo mã QR", Toast.LENGTH_SHORT).show();
        }
    }

    private void showUploadSection(Uri imageUri) {
        uploadSection.setVisibility(View.VISIBLE);
        btnContinueFinal.setVisibility(View.VISIBLE);
        imgUploadedProof.setImageURI(imageUri);
    }
    @Override
    public void onServiceSelected(String serviceName) {
        tvSelectService.setText(serviceName);
    }

    @Override
    public void onHomeClicked() {
        setResult(Activity.RESULT_OK);
        finish();
    }
}
