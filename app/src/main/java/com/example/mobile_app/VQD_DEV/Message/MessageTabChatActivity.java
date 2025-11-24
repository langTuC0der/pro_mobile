package com.example.mobile_app.VQD_DEV.Message;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mobile_app.R;

/**
 * Activity cho màn hình Tab Chat (vqd_message_tab_chat.xml)
 * Hiển thị danh sách tin nhắn
 */
public class MessageTabChatActivity extends AppCompatActivity {

    private LinearLayout vqd_layout_empty;
    private ScrollView vqd_layout_chat_list;
    private LinearLayout vqd_item_chat_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vqd_message_tab_chat);

        // Ánh xạ views
        vqd_layout_empty = findViewById(R.id.vqd_layout_empty);
        vqd_layout_chat_list = findViewById(R.id.vqd_layout_chat_list);
        vqd_item_chat_1 = findViewById(R.id.vqd_item_chat_1);

        // Khi bấm vào empty layout - Hiển thị danh sách chat
        vqd_layout_empty.setOnClickListener(v -> {
            // Ẩn empty, hiện danh sách
            vqd_layout_empty.setVisibility(View.GONE);
            vqd_layout_chat_list.setVisibility(View.VISIBLE);
        });

        // Khi bấm vào item chat đầu tiên - Mở màn hình chat detail
        vqd_item_chat_1.setOnClickListener(v -> {
            Intent intent = new Intent(MessageTabChatActivity.this, MessageChatDetailActivity.class);
            intent.putExtra("chat_name", "Nguyễn Hoàng Bảo Nam");
            startActivity(intent);
        });
    }
}