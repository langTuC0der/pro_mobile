package com.example.mobile_app.VQD_DEV.Wallet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

// ===== IMPORT THÊM 3 DÒNG NÀY =====
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import android.app.Activity;
// ===================================

import androidx.appcompat.app.AppCompatActivity;
import com.example.mobile_app.R;
import com.example.mobile_app.VQD_DEV.Wallet.models.Fund;
// import com.example.mobile_app.VQD_DEV.Wallet.utils.CurrencyFormatter;

public class HomeActivity extends AppCompatActivity {

    private TextView tvFundName, tvBalance, tvStatus;
    private TextView btnRegister, btnTopUp, btnWithdraw;
    private Fund currentUserFund;

    // ===== 1. KHAI BÁO LAUNCHER =====
    private ActivityResultLauncher<Intent> registerFundLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vqd_activity_home);

        tvFundName = findViewById(R.id.tv_fund_name);
        tvBalance = findViewById(R.id.tv_balance);
        tvStatus = findViewById(R.id.tv_status);
        btnRegister = findViewById(R.id.btn_register);
        btnTopUp = findViewById(R.id.btn_top_up);
        btnWithdraw = findViewById(R.id.btn_withdraw);

        // ===== 2. KHỞI TẠO LAUNCHER =====
        // Đây là code sẽ chạy khi RegisterFundActivity đóng lại
        registerFundLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // 5. Kiểm tra nếu kết quả là OK
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // 6. Tải lại dữ liệu với trạng thái ĐÃ ĐĂNG KÝ
                        loadUserData(true);
                        updateUI();
                    }
                }
        );

        // 3. Tải dữ liệu lần đầu (chưa đăng ký)
        loadUserData(false);
        updateUI();

        setupClickListeners();
    }

    // ===== 4. SỬA HÀM NÀY ĐỂ NHẬN THAM SỐ =====
    private void loadUserData(boolean isUserRegistered) {
        // Bỏ dòng "boolean isUserRegistered = false;"
        // ------------------

        if (isUserRegistered) { // Dùng tham số
            currentUserFund = new Fund(
                    "Số dư (đ)",
                    0, // Sau khi đăng ký, số dư vẫn là 0
                    "Số tiền cần duy trì tối thiểu 200.000đ để nhận đơn hàng mới",
                    true
            );
        } else {
            currentUserFund = new Fund(
                    "Số dư (đ)",
                    0,
                    "Trạng thái: Chưa đăng ký quỹ",
                    false
            );
        }
    }

    private void updateUI() {
        if (currentUserFund == null) return;

        tvFundName.setText(currentUserFund.getFundName());
        tvStatus.setText(currentUserFund.getStatus());
        tvBalance.setText(String.format("%,.0f", currentUserFund.getBalance()));

        // Logic này đã đúng, nó sẽ tự động
        // Ẩn "Đăng ký ngay"
        // Hiện "Nạp tiền" và "Yêu cầu rút tiền"
        if (currentUserFund.isFundRegistered()) {
            btnTopUp.setVisibility(View.VISIBLE);
            btnWithdraw.setVisibility(View.VISIBLE);
            btnRegister.setVisibility(View.GONE);
        } else {
            btnTopUp.setVisibility(View.GONE);
            btnWithdraw.setVisibility(View.GONE);
            btnRegister.setVisibility(View.VISIBLE);
        }
    }

    private void setupClickListeners() {

        // ===== 7. SỬA CÁCH GỌI ACTIVITY =====
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, RegisterFundActivity.class);
            // 8. Dùng launcher để gọi
            registerFundLauncher.launch(intent);
        });
        // ------------------------------------

        btnTopUp.setOnClickListener(v -> {
            // (Code cho nút Nạp tiền sẽ làm sau)
        });

        // (Thêm sự kiện cho btnWithdraw sau)
    }
}