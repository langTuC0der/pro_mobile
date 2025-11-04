package com.example.mobile_app.VQD_DEV.Wallet.models;

/**
 * Đây là lớp Model (POJO) định nghĩa dữ liệu cho một "Quỹ"
 * (tương ứng với 1 cái thẻ xanh trên giao diện)
 */
public class Fund {

    // 1. Các trường dữ liệu
    private String fundName;      // Tên quỹ (ví dụ: "Số dư (đ)")
    private double balance;       // Số dư (ví dụ: 0 hoặc 5298000)
    private String status;        // Trạng thái (ví dụ: "Chưa đăng ký quỹ")
    private boolean isFundRegistered;
    public Fund(String fundName, double balance, String status, boolean isFundRegistered) {
        this.fundName = fundName;
        this.balance = balance;
        this.status = status;
        this.isFundRegistered = isFundRegistered;
    }
    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isFundRegistered() {
        return isFundRegistered;
    }

    public void setFundRegistered(boolean fundRegistered) {
        isFundRegistered = fundRegistered;
    }
}