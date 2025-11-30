package com.example.mobile_app.VQD_DEV.Wallet;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment; // Import Fragment

import com.example.mobile_app.R;
// Import Fragment Chat mới tạo
import com.example.mobile_app.VQD_DEV.Message.MessageTabChatFragment;
// Import Fragment Wallet (Bạn tự tạo tương tự Chat nhé)
// import com.example.mobile_app.VQD_DEV.Wallet.vqd_WalletHomeFragment;

public class VqdMainActivity extends AppCompatActivity {

    private LinearLayout itemHome, itemOrder, itemWallet, itemChat, itemProfile;
    private ImageView iconHome, iconOrder, iconWallet, iconChat, iconProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vqd_activity_main_nav);

        initViews();
        setupEvents();

        // Mặc định load Ví Taker
        updateNavColor(3);
        if (savedInstanceState == null) {
            loadFragment(new vqd_WalletHomeFragment());
        }
    }

    private void initViews() {
        itemHome = findViewById(R.id.nav_item_1);
        itemOrder = findViewById(R.id.nav_item_2);
        itemWallet = findViewById(R.id.nav_item_3);
        itemChat = findViewById(R.id.nav_item_4);
        itemProfile = findViewById(R.id.nav_item_5);

        iconHome = findViewById(R.id.nav_icon_1);
        iconOrder = findViewById(R.id.nav_icon_2);
        iconWallet = findViewById(R.id.nav_icon_3);
        iconChat = findViewById(R.id.nav_icon_4);
        iconProfile = findViewById(R.id.nav_icon_5);
    }

    // --- HÀM MỚI: DÙNG ĐỂ LOAD FRAGMENT ---
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.vqd_fragment_container, fragment) // Thay thế nội dung trong khung container
                .commit();
    }
    // --------------------------------------

    private void setupEvents() {
        itemHome.setOnClickListener(v -> {
            updateNavColor(1);
            // loadFragment(new HomeFragment());
        });

        itemOrder.setOnClickListener(v -> {
            updateNavColor(2);
            // loadFragment(new OrderFragment());
        });

        itemWallet.setOnClickListener(v -> {
            updateNavColor(3);

            loadFragment(new vqd_WalletHomeFragment());
        });

        itemChat.setOnClickListener(v -> {
            updateNavColor(4);
            // Load Fragment Chat
            loadFragment(new MessageTabChatFragment());
        });

        itemProfile.setOnClickListener(v -> {
            updateNavColor(5);
            // loadFragment(new ProfileFragment());
        });
    }

    private void updateNavColor(int selectedIndex) {
        int activeColor = Color.parseColor("#2E7D32");
        int inactiveColor = Color.parseColor("#888888");

        iconHome.setColorFilter(inactiveColor);
        iconOrder.setColorFilter(inactiveColor);
        iconWallet.setColorFilter(inactiveColor);
        iconChat.setColorFilter(inactiveColor);
        iconProfile.setColorFilter(inactiveColor);

        switch (selectedIndex) {
            case 1: iconHome.setColorFilter(activeColor); break;
            case 2: iconOrder.setColorFilter(activeColor); break;
            case 3: iconWallet.setColorFilter(activeColor); break;
            case 4: iconChat.setColorFilter(activeColor); break;
            case 5: iconProfile.setColorFilter(activeColor); break;
        }
    }
}