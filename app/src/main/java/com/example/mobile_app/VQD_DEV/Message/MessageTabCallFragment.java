package com.example.mobile_app.VQD_DEV.Message;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.mobile_app.R;

public class MessageTabCallFragment extends Fragment {

    private LinearLayout vqd_layout_empty;
    private ScrollView vqd_layout_call_list;
    private LinearLayout vqd_tab_tinnhan;
    private LinearLayout vqd_item_call_history_1;

    private static final String PREFS_NAME = "ChatPrefs";
    private static final String KEY_IS_CALL_VISIBLE = "is_call_visible";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vqd_message_tab_call, container, false);
        initViews(view);
        setupEvents();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Mỗi lần quay lại màn hình này thì check xem đã gọi chưa
        checkCallState();
    }

    private void initViews(View view) {
        vqd_layout_empty = view.findViewById(R.id.vqd_layout_empty);
        vqd_layout_call_list = view.findViewById(R.id.vqd_layout_call_list);
        vqd_tab_tinnhan = view.findViewById(R.id.vqd_tab_tinnhan);
        vqd_item_call_history_1 = view.findViewById(R.id.vqd_item_call_history_1);
    }

    private void checkCallState() {
        if (getContext() == null) return;

        // Kiểm tra trong bộ nhớ xem User đã bấm nút gọi bên Chat chưa
        SharedPreferences settings = getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean isCallVisible = settings.getBoolean(KEY_IS_CALL_VISIBLE, false);

        if (isCallVisible) {
            // Nếu đã gọi -> Ẩn hộp rỗng, Hiện list
            vqd_layout_empty.setVisibility(View.GONE);
            vqd_layout_call_list.setVisibility(View.VISIBLE);
        } else {
            // Nếu chưa gọi -> Hiện hộp rỗng, Ẩn list
            vqd_layout_empty.setVisibility(View.VISIBLE);
            vqd_layout_call_list.setVisibility(View.GONE);
        }
    }

    private void setupEvents() {
        // Click vào item lịch sử -> Gọi lại
        if (vqd_item_call_history_1 != null) {
            vqd_item_call_history_1.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), MessageCallDetailActivity.class);
                intent.putExtra("caller_name", "Nguyễn Hoàng Bảo Nam");
                startActivity(intent);
            });
        }

        // Chuyển Tab về Tin nhắn
        if (vqd_tab_tinnhan != null) {
            vqd_tab_tinnhan.setOnClickListener(v -> {
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.vqd_fragment_container, new MessageTabChatFragment())
                        .commit();
            });
        }
    }
}