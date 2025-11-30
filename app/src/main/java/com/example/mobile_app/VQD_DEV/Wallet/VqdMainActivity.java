package com.example.mobile_app.VQD_DEV.Wallet; // Đổi lại package cho đúng bài của bạn

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_app.R;
import com.example.mobile_app.VQD_DEV.Message.MessageTabChatActivity;

public class VqdMainActivity extends AppCompatActivity {

    // Khai báo các nút bấm (LinearLayout) và Ảnh (ImageView)
    private LinearLayout itemHome, itemOrder, itemWallet, itemChat, itemProfile;
    private ImageView iconHome, iconOrder, iconWallet, iconChat, iconProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vqd_activity_main_nav); // Đảm bảo tên layout đúng với file xml của bạn

        initViews();
        setupEvents();

        // Mặc định khi vào App: Chọn tab Ví Taker (Tab số 3)
        // Hoặc nếu muốn Trang chủ thì điền số 1
        updateNavColor(3);
    }

    private void initViews() {
        // Ánh xạ LinearLayout (để bắt sự kiện bấm)
        itemHome = findViewById(R.id.nav_item_1);
        itemOrder = findViewById(R.id.nav_item_2);
        itemWallet = findViewById(R.id.nav_item_3);
        itemChat = findViewById(R.id.nav_item_4);
        itemProfile = findViewById(R.id.nav_item_5);

        // Ánh xạ ImageView (để đổi màu)
        iconHome = findViewById(R.id.nav_icon_1);
        iconOrder = findViewById(R.id.nav_icon_2);
        iconWallet = findViewById(R.id.nav_icon_3);
        iconChat = findViewById(R.id.nav_icon_4);
        iconProfile = findViewById(R.id.nav_icon_5);
    }

    private void setupEvents() {
        // Bắt sự kiện bấm cho từng mục
        itemHome.setOnClickListener(v -> {
            updateNavColor(1);
            // TODO: Gọi hàm load Fragment Trang Chủ ở đây
        });

        itemOrder.setOnClickListener(v -> {
            updateNavColor(2);
            // TODO: Gọi hàm load Fragment Đơn Hàng ở đây
        });

        itemWallet.setOnClickListener(v -> {
            updateNavColor(3);
            // TODO: Gọi hàm load Fragment Ví ở đây
            Intent intent = new Intent(VqdMainActivity.this, vqd_WalletHomeActivity.class);
            startActivity(intent);
        });

        itemChat.setOnClickListener(v -> {
            updateNavColor(4);
            // TODO: Gọi hàm load Fragment Trò Chuyện ở đây
            Intent intent = new Intent(VqdMainActivity.this, MessageTabChatActivity.class);
            startActivity(intent);
        });

        itemProfile.setOnClickListener(v -> {
            updateNavColor(5);
            // TODO: Gọi hàm load Fragment Cá Nhân ở đây
        });
    }

    // HÀM QUAN TRỌNG: Đổi màu icon
    private void updateNavColor(int selectedIndex) {
        // 1. Định nghĩa màu
        int activeColor = Color.parseColor("#2E7D32"); // Xanh lá đậm
        int inactiveColor = Color.parseColor("#888888"); // Xám

        // 2. Reset tất cả về màu xám trước
        iconHome.setColorFilter(inactiveColor);
        iconOrder.setColorFilter(inactiveColor);
        iconWallet.setColorFilter(inactiveColor);
        iconChat.setColorFilter(inactiveColor);
        iconProfile.setColorFilter(inactiveColor);

        // 3. Tô màu xanh cho mục đang chọn
        switch (selectedIndex) {
            case 1:
                iconHome.setColorFilter(activeColor);
                break;
            case 2:
                iconOrder.setColorFilter(activeColor);
                break;
            case 3:
                iconWallet.setColorFilter(activeColor);
                break;
            case 4:
                iconChat.setColorFilter(activeColor);
                break;
            case 5:
                iconProfile.setColorFilter(activeColor);
                break;
        }
    }
}