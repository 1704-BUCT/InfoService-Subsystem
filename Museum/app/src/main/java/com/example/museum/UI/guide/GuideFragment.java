package com.example.museum.UI.guide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.museum.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

/*
    导览页面
* */

public class GuideFragment extends Fragment {
    private BottomSheetBehavior sheetBehavior;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gudiepage, container, false);
        View view = root.findViewById(R.id.bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from(view);
        final TextView tool = (TextView)root.findViewById(R.id.nearby_museum);
        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // 监听BottomSheet状态的改变
                if (sheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    tool.setVisibility(View.VISIBLE);
                } else {
                    tool.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // 监听拖拽中的回调，根据slideOffset可以做一些动画
            }
        });
        tool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
        return root;
    }
}
