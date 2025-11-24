package com.example.mobile_app.VQD_DEV.Message;

import android.content.Context;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import java.util.List;

@Database(entities = {ChatMessage.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ChatDao chatDao();
    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "vqd_chat_database")
                    .allowMainThreadQueries() // Cho phép chạy trên main thread cho đơn giản
                    .build();
        }
        return INSTANCE;
    }

    @Dao
    public interface ChatDao {
        @Insert
        void insertMessage(ChatMessage chatMessage);

        @Query("SELECT * FROM chat_history")
        List<ChatMessage> getAllMessages();

        @Query("DELETE FROM chat_history")
        void deleteAll();

        // --- THÊM HÀM NÀY: Lấy tin nhắn mới nhất ---
        @Query("SELECT * FROM chat_history ORDER BY id DESC LIMIT 1")
        ChatMessage getLastMessage();
    }
}