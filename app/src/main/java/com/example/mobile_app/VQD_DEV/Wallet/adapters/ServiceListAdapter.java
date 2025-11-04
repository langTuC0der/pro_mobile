package com.example.mobile_app.VQD_DEV.Wallet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_app.R;
import com.example.mobile_app.VQD_DEV.Wallet.models.Service;

import java.util.List;

/**
 * Adapter này hiển thị danh sách Dịch vụ trong Dialog
 */
public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.ViewHolder> {

    // 1. Interface để gửi sự kiện click ngược về Dialog
    public interface OnServiceClickListener {
        void onServiceClick(Service service, int position);
    }

    private List<Service> serviceList;
    private Context context;
    private OnServiceClickListener clickListener;

    // Constructor
    public ServiceListAdapter(Context context, List<Service> serviceList, OnServiceClickListener clickListener) {
        this.context = context;
        this.serviceList = serviceList;
        this.clickListener = clickListener;
    }

    /**
     * Tạo ViewHolder, "bơm" layout vqd_list_item_service.xml
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.vqd_list_item_service, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Gán dữ liệu và xử lý logic đổi màu
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Lấy dịch vụ tại vị trí hiện tại
        Service service = serviceList.get(position);

        // Gán tên dịch vụ
        holder.tvServiceName.setText(service.getServiceName());

        // ----- LOGIC ĐỔI MÀU NỀN -----
        if (service.isSelected()) {
            // Nếu ĐANG được chọn -> Dùng nền xanh lá
            holder.tvServiceName.setBackground(
                    ContextCompat.getDrawable(context, R.drawable.vqd_item_service_selected_bg)
            );
            holder.tvServiceName.setTextColor(ContextCompat.getColor(context, R.color.vqd_green_dark)); // Bạn cần định nghĩa màu này
        } else {
            // Nếu KHÔNG được chọn -> Dùng nền trắng mặc định
            holder.tvServiceName.setBackground(
                    ContextCompat.getDrawable(context, R.drawable.vqd_item_service_default_bg)
            );
            holder.tvServiceName.setTextColor(ContextCompat.getColor(context, android.R.color.black));
        }

        // ----- XỬ LÝ CLICK -----
        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                // Gọi về Dialog, báo rằng: "Item ở vị trí này vừa được click"
                clickListener.onServiceClick(service, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    /**
     * ViewHolder đơn giản, chỉ chứa 1 TextView
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvServiceName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ View từ file vqd_list_item_service.xml
            tvServiceName = itemView.findViewById(R.id.tv_service_name);
        }
    }
}