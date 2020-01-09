package com.julongsoft.measure.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;


/**
 * Created by Tao on 2015/9/2.
 */
public class BaseFragment extends Fragment {

    protected boolean isViewLoaded = false;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewLoaded = true;
        //fragment事件会传递，比如 fragment1 add fragment2 在fragment2 点击界面会出发fragment1 的监听事件
        getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewLoaded = false;
    }

    public <T extends View> T get(int id) {
        return (T) getView().findViewById(id);
    }

    protected String getEditTextContent(EditText editText) {
        if (null == editText)
            return null;
        Editable editable = editText.getText();
        return null == editable ? null : editable.toString().trim();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }


    public void onSelected() {
    }
}