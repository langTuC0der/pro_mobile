package com.example.mobile_app.VQD_DEV.Wallet;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.mobile_app.R;

public class VqdMainActivity extends AppCompatActivity {

    private LinearLayout navItem1, navItem2, navItem3, navItem4, navItem5;
    private TextView navText1, navText2, navText3, navText4, navText5;
    private LinearLayout[] navItems;
    private TextView[] navTexts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vqd_activity_main_nav);

        initViews();
        setupEvents();

        // YÊU CẦU CỦA BẠN: Mặc định Tab 1 sáng
        selectTab(1);
    }

    private void initViews() {
        navItem1 = findViewById(R.id.nav_item_1);
        navItem2 = findViewById(R.id.nav_item_2);
        navItem3 = findViewById(R.id.nav_item_3);
        navItem4 = findViewById(R.id.nav_item_4);
        navItem5 = findViewById(R.id.nav_item_5);

        navText1 = findViewById(R.id.nav_text_1);
        navText2 = findViewById(R.id.nav_text_2);
        navText3 = findViewById(R.id.nav_text_3);
        navText4 = findViewById(R.id.nav_text_4);
        navText5 = findViewById(R.id.nav_text_5);

        navItems = new LinearLayout[]{navItem1, navItem2, navItem3, navItem4, navItem5};
        navTexts = new TextView[]{navText1, navText2, navText3, navText4, navText5};
    }

    private void setupEvents() {
        navItem1.setOnClickListener(v -> selectTab(1));
        navItem2.setOnClickListener(v -> selectTab(2));
        navItem3.setOnClickListener(v -> selectTab(3));
        navItem4.setOnClickListener(v -> selectTab(4));
        navItem5.setOnClickListener(v -> selectTab(5));
    }

    private void selectTab(int tabIndex) {
        // 1. Reset giao diện
        for (LinearLayout item : navItems) {
            item.setBackgroundResource(0);
        }
        for (TextView tv : navTexts) {
            tv.setTypeface(null, Typeface.NORMAL);
        }

        // 2. Highlight tab được chọn
        navItems[tabIndex - 1].setBackgroundResource(R.drawable.vqd_green_to_white_gradient);
        navTexts[tabIndex - 1].setTypeface(null, Typeface.BOLD);

        // 3. Load Fragment
        Fragment selectedFragment = null;
        switch (tabIndex) {
            case 1:
                // Mặc định gọi FragmentHome (Tab 1)
                //selectedFragment = new FragmentHome();
                break;
            case 2:
                // Đơn hàng (Tạm thời chưa có thì để null hoặc tạo FragmentOrder sau)
                // selectedFragment = new FragmentOrder();
                break;
            case 3:
                // YÊU CẦU CỦA BẠN: Hiện ra Ví Taker (chính là HomeActivity cũ)
                // Ta dùng FragmentWallet vì nó chứa giao diện của HomeActivity
                //selectedFragment = new FragmentWallet();
                break;
            case 4:
                // Trò chuyện
                // selectedFragment = new FragmentChat();
                break;
            case 5:
                // Cá nhân
                // selectedFragment = new FragmentProfile();
                break;
        }

        if (selectedFragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.vqd_fragment_container, selectedFragment)
                    .commit();
        }
    }
}