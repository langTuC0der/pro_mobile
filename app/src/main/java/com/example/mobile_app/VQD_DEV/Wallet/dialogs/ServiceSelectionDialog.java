package com.example.mobile_app.VQD_DEV.Wallet.dialogs;

import android.os.Bundle;
import android.os.Handler; // MỚI: Thêm import này
import android.os.Looper;  // MỚI: Thêm import này
import android.view.Gravity;  // MỚI: Thêm import này
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;       // MỚI: Thêm import này
import android.view.WindowManager; // MỚI: Thêm import này
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_app.R;
import com.example.mobile_app.VQD_DEV.Wallet.adapters.ServiceListAdapter;
import com.example.mobile_app.VQD_DEV.Wallet.models.Service;

import java.util.ArrayList;
import java.util.List;

public class ServiceSelectionDialog extends DialogFragment implements ServiceListAdapter.OnServiceClickListener {

    public interface OnServiceSelectedListener {
        void onServiceSelected(String serviceName);
    }

    private OnServiceSelectedListener mListener;
    private RecyclerView rcvServices;
    private ImageView btnCloseDialog;
    private ServiceListAdapter adapter;
    private List<Service> serviceList;
    private int currentSelectedIndex = -1;

    public void setOnServiceSelectedListener(OnServiceSelectedListener listener) {
        this.mListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vqd_dialog_service_selection, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcvServices = view.findViewById(R.id.rcv_services);
        btnCloseDialog = view.findViewById(R.id.btn_close_dialog);

        initData();

        adapter = new ServiceListAdapter(getContext(), serviceList, this);
        rcvServices.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvServices.setAdapter(adapter);

        btnCloseDialog.setOnClickListener(v -> {
            dismiss();
        });

        // Làm cho nền dialog trong suốt và bo góc
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    // ----- MỚI: SỬA LỖI 1 (Cho Dialog xuống dưới) -----
    // Thêm hàm onResume() này
    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        if (window == null) return;
        window.setBackgroundDrawableResource(R.drawable.vqd_dialog_white_bg);

        android.util.DisplayMetrics metrics = getResources().getDisplayMetrics();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = metrics.widthPixels;
        params.height = (int)(metrics.heightPixels * 0.5f);
        params.gravity = Gravity.BOTTOM;

        window.setAttributes(params);
    }
    // --------------------------------------------------

    private void initData() {
        serviceList = new ArrayList<>();
        serviceList.add(new Service("Đánh giầy"));
        serviceList.add(new Service("Tài xế xe máy"));
        serviceList.add(new Service("Tài xế ô tô"));
        serviceList.add(new Service("Xe ghép liên tỉnh"));
        serviceList.add(new Service("Giao hàng (bằng xe máy)"));
        serviceList.add(new Service("Giao đồ ăn (bằng xe máy)"));
    }

    /**
     * XỬ LÝ CLICK TỪ ADAPTER
     */
    @Override
    public void onServiceClick(Service service, int position) {

        // Cập nhật trạng thái "isSelected"
        if (currentSelectedIndex != -1 && currentSelectedIndex < serviceList.size()) {
            serviceList.get(currentSelectedIndex).setSelected(false);
        }
        serviceList.get(position).setSelected(true);
        currentSelectedIndex = position;

        // Báo cho Adapter biết dữ liệu đã thay đổi -> Adapter sẽ VẼ LẠI MÀU XANH
        adapter.notifyDataSetChanged();

        // Gửi dữ liệu về cho RegisterFundActivity
        if (mListener != null) {
            mListener.onServiceSelected(service.getServiceName());
        }

        // ----- MỚI: SỬA LỖI 2 (Hiển thị màu xanh) -----
        // Đừng đóng ngay lập tức, hãy đợi 300ms để người dùng thấy màu xanh
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            dismiss(); // Đóng dialog
        }, 300); // 300 mili-giây (0.3 giây)
        // ----------------------------------------------
    }
}