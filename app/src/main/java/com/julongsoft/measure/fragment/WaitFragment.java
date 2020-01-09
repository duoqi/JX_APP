package com.julongsoft.measure.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.julongsoft.measure.R;
import com.julongsoft.measure.activity.MainActivity;

/**
 * Created by tao on 2017/7/24.
 */

public class WaitFragment extends BaseFragment {

    private MainActivity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wait, container, false);
    }
}
