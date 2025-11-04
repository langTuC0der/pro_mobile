package com.example.mobile_app.VQD_DEV.Wallet.models;

/**
 * Model (POJO) đơn giản
 * Chỉ chứa thông tin cho một dịch vụ
 */
public class Service {

    private String serviceName;
    private boolean isSelected; // Dùng để biết item nào đang được chọn

    // Constructor
    public Service(String serviceName) {
        this.serviceName = serviceName;
        this.isSelected = false; // Mặc định là chưa được chọn
    }

    // Getters và Setters
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}