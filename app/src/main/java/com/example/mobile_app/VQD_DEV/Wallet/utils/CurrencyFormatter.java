package com.example.mobile_app.VQD_DEV.Wallet.utils;
import java.text.NumberFormat;
import java.util.Locale;
// Format VNĐ
public class CurrencyFormatter {
    public static String formatVND(double amount) {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(localeVN);
        // Bỏ ký hiệu "₫" và thay bằng "đ" ở cuối nếu muốn
        return currencyFormatter.format(amount).replace(" ₫", "") + "đ";
        // Hoặc đơn giản là format số:
        // NumberFormat numberFormat = NumberFormat.getNumberInstance(localeVN);
        // return numberFormat.format(amount) + "đ";
    }
}
