package com.example.mobile_app.VQD_DEV.Message;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "chat_history")
public class ChatMessage {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String message;
    public boolean isUser; // true: User, false: AI

    public ChatMessage(String message, boolean isUser) {
        this.message = message;
        this.isUser = isUser;
    }
}