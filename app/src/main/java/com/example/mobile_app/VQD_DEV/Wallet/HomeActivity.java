package com.example.mobile_app.VQD_DEV.Wallet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mobile_app.R;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class HomeActivity extends AppCompatActivity {

    private TextView tvFundName; // Chữ "Số dư (đ)"
    private TextView tvBalance;  // Số tiền to
    private TextView tvStatus;   // Dòng ghi chú bên dưới
    private TextView btnRegister;
    private TextView btnTopUp;
    private TextView btnWithdraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vqd_activity_home);

        initViews();
        setupEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkUserStatus();
    }

    private void initViews() {
        tvFundName = findViewById(R.id.tv_fund_name);
        tvBalance = findViewById(R.id.tv_balance);
        tvStatus = findViewById(R.id.tv_status);
        btnRegister = findViewById(R.id.btn_register);
        btnTopUp = findViewById(R.id.btn_top_up);
        btnWithdraw = findViewById(R.id.btn_withdraw);
    }

    private void setupEvents() {
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, RegisterFundActivity.class);
            startActivity(intent);
        });

        btnTopUp.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, VqdActivityNapTien.class);
            startActivity(intent);
        });

        btnWithdraw.setOnClickListener(v -> {
            // Logic rút tiền
        });

        TextView tvTitle = findViewById(R.id.tv_title);

        tvTitle.setOnClickListener(v -> {
            // 1. Xóa toàn bộ dữ liệu trong SharedPreferences
            SharedPreferences prefs = getSharedPreferences("ViTakerPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear(); // Lệnh xóa sạch
            editor.apply();

            // 2. Thông báo
            android.widget.Toast.makeText(this, "Đã Reset dữ liệu test!", android.widget.Toast.LENGTH_SHORT).show();

            // 3. Load lại màn hình hiện tại để thấy thay đổi
            recreate();
        });
    }

    private void checkUserStatus() {
        SharedPreferences prefs = getSharedPreferences("ViTakerPrefs", MODE_PRIVATE);
        boolean isRegistered = prefs.getBoolean("is_registered", false);

        // Lấy số dư hiện tại (Mặc định là 0 nếu chưa có)
        long currentBalance = prefs.getLong("user_balance", 0);

        // Format tiền tệ
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#,###", symbols);
        tvBalance.setText(decimalFormat.format(currentBalance));

        // Luôn hiện chữ "Số dư (đ)"
        tvFundName.setVisibility(View.VISIBLE);

        if (!isRegistered) {
            // --- CHƯA ĐĂNG KÝ ---
            // Set text trạng thái
            tvStatus.setText("Trạng thái: Chưa đăng ký quỹ");
            tvStatus.setVisibility(View.VISIBLE);

            btnRegister.setVisibility(View.VISIBLE);
            btnTopUp.setVisibility(View.GONE);
            btnWithdraw.setVisibility(View.GONE);
        } else {
            // --- ĐÃ ĐĂNG KÝ ---
            // Set text lưu ý duy trì
            tvStatus.setText("Số tiền cần duy trì tối thiểu 200.000đ để nhận đơn hàng mới");
            tvStatus.setVisibility(View.VISIBLE);

            btnRegister.setVisibility(View.GONE);
            btnTopUp.setVisibility(View.VISIBLE);
            btnWithdraw.setVisibility(View.VISIBLE);
        }
    }
}