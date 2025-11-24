package com.example.mobile_app.VQD_DEV.Message;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.mobile_app.R;

public class MessageTabChatActivity extends AppCompatActivity {

    private LinearLayout vqd_layout_empty;
    private ScrollView vqd_layout_chat_list;
    private TextView tv_test_trigger;
    private LinearLayout vqd_item_chat_gemini;
    private TextView vqd_tv_title_reset;

    // Thêm biến cho TextView hiển thị tin cuối cùng
    private TextView tv_last_message;

    private static final String PREFS_NAME = "ChatPrefs";
    private static final String KEY_IS_CHAT_VISIBLE = "is_chat_visible";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vqd_message_tab_chat);

        initViews();
        setupEvents();
        // checkChatState sẽ được gọi trong onResume để luôn cập nhật mới nhất
    }

    // --- QUAN TRỌNG: Hàm này chạy mỗi khi màn hình hiện lên ---
    @Override
    protected void onResume() {
        super.onResume();
        checkChatState();
        updateLastMessageUI(); // Cập nhật tin nhắn mới nhất
    }

    private void initViews() {
        vqd_layout_empty = findViewById(R.id.vqd_layout_empty);
        vqd_layout_chat_list = findViewById(R.id.vqd_layout_chat_list);
        tv_test_trigger = findViewById(R.id.tv_test_trigger);
        vqd_item_chat_gemini = findViewById(R.id.vqd_item_chat_gemini);
        vqd_tv_title_reset = findViewById(R.id.vqd_tv_title_reset);

        // Ánh xạ TextView mới thêm
        tv_last_message = findViewById(R.id.tv_last_message);
    }

    private void updateLastMessageUI() {
        // Gọi Database lấy tin nhắn cuối
        AppDatabase db = AppDatabase.getDatabase(this);
        ChatMessage lastMsg = db.chatDao().getLastMessage();

        if (lastMsg != null) {
            // Nếu có tin nhắn: Hiển thị nội dung
            String content = lastMsg.message;

            // Logic phụ: Nếu là User nhắn thì thêm chữ "Bạn: " cho xịn
            if (lastMsg.isUser) {
                content = "Bạn: " + content;
            }

            tv_last_message.setText(content);
            tv_last_message.setTypeface(null, Typeface.NORMAL); // Chữ thường
            tv_last_message.setTextColor(0xFF666666); // Màu xám
        } else {
            // Nếu chưa có tin nhắn (hoặc vừa reset)
            tv_last_message.setText("Bắt đầu trò chuyện ngay!");
            tv_last_message.setTypeface(null, Typeface.ITALIC);
            tv_last_message.setTextColor(0xFF00B14F); // Màu xanh gợi ý
        }
    }

    private void checkChatState() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isChatVisible = settings.getBoolean(KEY_IS_CHAT_VISIBLE, false);

        if (isChatVisible) {
            vqd_layout_empty.setVisibility(View.GONE);
            vqd_layout_chat_list.setVisibility(View.VISIBLE);
        } else {
            vqd_layout_empty.setVisibility(View.VISIBLE);
            vqd_layout_chat_list.setVisibility(View.GONE);
        }
    }

    private void setupEvents() {
        // Reset toàn bộ
        vqd_tv_title_reset.setOnClickListener(v -> {
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            settings.edit().clear().apply();

            AppDatabase db = AppDatabase.getDatabase(this);
            db.chatDao().deleteAll();

            Toast.makeText(this, "Đã Reset toàn bộ dữ liệu!", Toast.LENGTH_SHORT).show();
            recreate();
        });

        // Nút Test (Kích hoạt chat)
        tv_test_trigger.setOnClickListener(v -> {
            Toast.makeText(this, "Đã kích hoạt giao diện chat!", Toast.LENGTH_SHORT).show();
            vqd_layout_empty.setVisibility(View.GONE);
            vqd_layout_chat_list.setVisibility(View.VISIBLE);

            SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            settings.edit().putBoolean(KEY_IS_CHAT_VISIBLE, true).apply();

            // Cập nhật lại UI dòng nhắn cuối
            updateLastMessageUI();
        });

        // Vào màn hình chat chi tiết
        vqd_item_chat_gemini.setOnClickListener(v -> {
            Intent intent = new Intent(MessageTabChatActivity.this, MessageChatDetailActivity.class);
            intent.putExtra("chat_name", "AI Bot Gemini");
            startActivity(intent);
        });
    }
}