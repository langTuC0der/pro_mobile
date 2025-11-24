package com.example.mobile_app.VQD_DEV.Message;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mobile_app.R;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

public class MessageChatDetailActivity extends AppCompatActivity {

    private ImageView vqd_btn_back_chat;
    private TextView chat_title;
    private ScrollView scrollViewChat;
    private LinearLayout chatContainer;
    private EditText editTextMessage;
    private ImageView btnSend;

    // --- LƯU Ý: TẠO KEY MỚI VÀ DÁN VÀO ĐÂY ---
    private static final String GEMINI_API_KEY = "AIzaSyDEUmi8cbokC2fBUJixjpsOlkRobQoeX3o";

    private GenerativeModelFutures model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vqd_message_activity_chat_detail);

        // --- SỬA QUAN TRỌNG: Đổi tên model thành gemini-1.5-flash ---
        GenerativeModel gm = new GenerativeModel("gemini-1.0-pro", GEMINI_API_KEY);
        model = GenerativeModelFutures.from(gm);

        initViews();
        setupEvents();

        addBotMessage("Xin chào! Tôi là trợ lý AI Gemini (Flash). Tôi có thể giúp gì cho bạn? 😊");
    }

    private void initViews() {
        vqd_btn_back_chat = findViewById(R.id.vqd_btn_back_chat);
        chat_title = findViewById(R.id.chat_title);
        scrollViewChat = findViewById(R.id.scrollViewChat);
        chatContainer = findViewById(R.id.chatContainer);
        editTextMessage = findViewById(R.id.editTextMessage);
        btnSend = findViewById(R.id.btnSend);
    }

    private void setupEvents() {
        vqd_btn_back_chat.setOnClickListener(v -> finish());

        btnSend.setOnClickListener(v -> {
            String userMessage = editTextMessage.getText().toString().trim();
            if (userMessage.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tin nhắn", Toast.LENGTH_SHORT).show();
                return;
            }

            // 1. Hiện tin nhắn User
            addUserMessage(userMessage);
            editTextMessage.setText("");

            // 2. Gọi AI
            sendMessageToGemini(userMessage);
        });
    }

    private void sendMessageToGemini(String message) {
        Content content = new Content.Builder()
                .addText(message)
                .build();

        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);

        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                String botResponse = result.getText();
                runOnUiThread(() -> addBotMessage(botResponse));
            }

            @Override
            public void onFailure(Throwable t) {
                // In lỗi chi tiết ra Logcat để debug
                t.printStackTrace();

                runOnUiThread(() -> {
                    // Hiển thị lý do lỗi cụ thể lên màn hình để bạn dễ biết
                    String errorMsg = t.getMessage();
                    if (errorMsg != null && errorMsg.contains("404")) {
                        addBotMessage("Lỗi 404: Sai tên Model. Hãy đổi sang gemini-1.5-flash");
                    } else if (errorMsg != null && errorMsg.contains("403")) {
                        addBotMessage("Lỗi 403: Sai API Key hoặc bị chặn quốc gia.");
                    } else {
                        addBotMessage("Lỗi kết nối: " + errorMsg);
                    }
                });
            }
        }, mainExecutor);
    }

    private void addUserMessage(String message) {
        View messageView = LayoutInflater.from(this).inflate(R.layout.item_message_user, chatContainer, false);
        TextView textView = messageView.findViewById(R.id.text_message_user);
        textView.setText(message);
        chatContainer.addView(messageView);
        scrollToBottom();
    }

    private void addBotMessage(String message) {
        View messageView = LayoutInflater.from(this).inflate(R.layout.item_message_bot, chatContainer, false);
        TextView textView = messageView.findViewById(R.id.text_message_bot);
        textView.setText(message);
        chatContainer.addView(messageView);
        scrollToBottom();
    }

    private void scrollToBottom() {
        scrollViewChat.post(() -> scrollViewChat.fullScroll(View.FOCUS_DOWN));
    }

    private java.util.concurrent.Executor mainExecutor = new java.util.concurrent.Executor() {
        private android.os.Handler handler = new android.os.Handler(android.os.Looper.getMainLooper());
        @Override
        public void execute(Runnable command) {
            handler.post(command);
        }
    };
}