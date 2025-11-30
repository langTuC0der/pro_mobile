package com.example.mobile_app.VQD_DEV.Message;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment; // Quan trọng: Dùng Fragment

import com.example.mobile_app.R;

public class MessageTabChatFragment extends Fragment { // Đổi thành Fragment

    private LinearLayout vqd_tab_cuocgoi;
    private LinearLayout vqd_layout_empty;
    private ScrollView vqd_layout_chat_list;
    private TextView tv_test_trigger;
    private LinearLayout vqd_item_chat_gemini;
    private TextView vqd_tv_title_reset;
    private TextView tv_last_message;

    private static final String PREFS_NAME = "ChatPrefs";
    private static final String KEY_IS_CHAT_VISIBLE = "is_chat_visible";

    // Fragment dùng onCreateView để nạp giao diện
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Nạp layout xml vào
        View view = inflater.inflate(R.layout.vqd_message_tab_chat, container, false);

        initViews(view); // Truyền view vào để ánh xạ
        setupEvents();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        checkChatState();
        updateLastMessageUI();
    }

    // Phải nhận tham số View để tìm ID bên trong nó
    private void initViews(View view) {
        vqd_layout_empty = view.findViewById(R.id.vqd_layout_empty);
        vqd_layout_chat_list = view.findViewById(R.id.vqd_layout_chat_list);
        tv_test_trigger = view.findViewById(R.id.tv_test_trigger);
        vqd_item_chat_gemini = view.findViewById(R.id.vqd_item_chat_gemini);
        vqd_tv_title_reset = view.findViewById(R.id.vqd_tv_title_reset);
        tv_last_message = view.findViewById(R.id.tv_last_message);

        vqd_tab_cuocgoi = view.findViewById(R.id.vqd_tab_cuocgoi);
    }

    private void updateLastMessageUI() {
        if (getContext() == null) return; // Kiểm tra an toàn

        AppDatabase db = AppDatabase.getDatabase(getContext()); // Dùng getContext() thay vì this
        ChatMessage lastMsg = db.chatDao().getLastMessage();

        if (lastMsg != null) {
            String content = lastMsg.message;
            if (lastMsg.isUser) {
                content = "Bạn: " + content;
            }
            tv_last_message.setText(content);
            tv_last_message.setTypeface(null, Typeface.NORMAL);
            tv_last_message.setTextColor(0xFF666666);
        } else {
            tv_last_message.setText("Bắt đầu trò chuyện ngay!");
            tv_last_message.setTypeface(null, Typeface.ITALIC);
            tv_last_message.setTextColor(0xFF00B14F);
        }
    }

    private void checkChatState() {
        if (getContext() == null) return;
        SharedPreferences settings = getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
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
        vqd_tv_title_reset.setOnClickListener(v -> {
            if (getContext() == null) return;
            SharedPreferences settings = getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            settings.edit().clear().apply();

            AppDatabase db = AppDatabase.getDatabase(getContext());
            db.chatDao().deleteAll();

            Toast.makeText(getContext(), "Đã Reset toàn bộ dữ liệu!", Toast.LENGTH_SHORT).show();

            // Reload lại Fragment này
            getParentFragmentManager().beginTransaction().detach(this).attach(this).commit();
        });

        tv_test_trigger.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Đã kích hoạt giao diện chat!", Toast.LENGTH_SHORT).show();
            vqd_layout_empty.setVisibility(View.GONE);
            vqd_layout_chat_list.setVisibility(View.VISIBLE);

            SharedPreferences settings = getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            settings.edit().putBoolean(KEY_IS_CHAT_VISIBLE, true).apply();

            updateLastMessageUI();
        });

        vqd_item_chat_gemini.setOnClickListener(v -> {
            // Chuyển sang Activity chi tiết thì VẪN DÙNG INTENT bình thường
            Intent intent = new Intent(getContext(), MessageChatDetailActivity.class);
            intent.putExtra("chat_name", "AI Bot Gemini");
            startActivity(intent);
        });

        // 3. Sự kiện: Đang ở Chat bấm sang Cuộc Gọi
        vqd_tab_cuocgoi.setOnClickListener(v -> {
            getParentFragmentManager()
                    .beginTransaction()
                    // R.id.vqd_fragment_container là cái khung trong VqdMainActivity
                    .replace(R.id.vqd_fragment_container, new MessageTabCallFragment())
                    .commit();
        });
    }
}