package com.example.mobile_app.VQD_DEV.Message;

import android.content.Context;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import java.util.List;

@Database(entities = {ChatMessage.class, AppDatabase.WalletItem.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ChatDao chatDao();
    public abstract WalletDao walletDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "vqd_chat_database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    // --- PHẦN HỖ TRỢ KIỂM TRA ĐÃ ĐĂNG KÝ CHƯA (Dành cho người mới) ---
    // Gọi hàm này: AppDatabase.isUserRegistered(context) -> trả về true/false
    public static boolean isUserRegistered(Context context) {
        // Đếm xem trong bảng ví có bao nhiêu dòng
        int count = getDatabase(context).walletDao().countWallets();
        // Nếu số lượng > 0 tức là Đã Đăng Ký
        return count > 0;
    }
    // ------------------------------------------------------------------

    // ChatDao cũ giữ nguyên
    @Dao
    public interface ChatDao {
        @Insert
        void insertMessage(ChatMessage chatMessage);
        @Query("SELECT * FROM chat_history")
        List<ChatMessage> getAllMessages();
        @Query("DELETE FROM chat_history")
        void deleteAll();
        @Query("SELECT * FROM chat_history ORDER BY id DESC LIMIT 1")
        ChatMessage getLastMessage();
    }

    // --- PHẦN VÍ TIỀN ---
    @Entity(tableName = "wallet_history")
    public static class WalletItem {
        @PrimaryKey(autoGenerate = true)
        public int id;
        public String serviceName;
        public String imagePath;

        public WalletItem(String serviceName, String imagePath) {
            this.serviceName = serviceName;
            this.imagePath = imagePath;
        }
    }

    @Dao
    public interface WalletDao {
        @Insert
        void insertWallet(WalletItem item);

        @Query("SELECT * FROM wallet_history")
        List<WalletItem> getAllWallets();

        // --- THÊM CÂU NÀY ĐỂ ĐẾM ---
        @Query("SELECT COUNT(*) FROM wallet_history")
        int countWallets();
    }
}