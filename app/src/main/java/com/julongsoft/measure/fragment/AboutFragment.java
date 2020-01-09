package com.julongsoft.measure.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.julongsoft.measure.R;
import com.julongsoft.measure.activity.LoginActivity;
import com.julongsoft.measure.activity.MainActivity;
import com.julongsoft.measure.activity.UpdataPasswordActivity;
import com.julongsoft.measure.db.GreenDaoHelper;
import com.julongsoft.measure.entity.DbUser;
import com.julongsoft.measure.global.GlobalApplication;
import com.julongsoft.measure.http.BaseResultData;
import com.julongsoft.measure.http.HttpManager;
import com.julongsoft.measure.http.HttpResultCallback;
import com.julongsoft.measure.utils.PrefUtil;
import com.julongsoft.measure.view.NavigationBar;

import cn.jpush.android.api.JPushInterface;
import retrofit2.Response;

/**
 * Created by tao on 2017/7/24.
 */

public class AboutFragment extends BaseFragment {

    private MainActivity mActivity;
    private DbUser user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
        user = GreenDaoHelper.getInstance().getSession().getDbUserDao().queryBuilder().unique();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        NavigationBar navigationBar = (NavigationBar) view.findViewById(R.id.navigationBar);
        TextView tv_role = (TextView) view.findViewById(R.id.tv_role);
        TextView tv_unit_mame = (TextView) view.findViewById(R.id.tv_unit_mame);
        TextView tv_mame = (TextView) view.findViewById(R.id.tv_mame);
        Button btn_exit = (Button) view.findViewById(R.id.btn_login);
        navigationBar.setTitle("关于");
        navigationBar.hideLeftViews();
        navigationBar.clearRightViews();


        tv_role.setText(user.getNotes());
        tv_unit_mame.setText(user.getOrgName());
//        tv_mame.setText(user.getName());
        TextView textView = new TextView(mActivity);
        textView.setText("修改密码");
        textView.setTextColor(getResources().getColor(R.color.colorWrite));
        navigationBar.addRightView(textView);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, UpdataPasswordActivity.class));
            }
        });

        //退出登录
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mActivity.showAlertDialog("确定退出吗？", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//
//                        HttpManager.getInstance().getHttpService().exitLogin(user.getToken()).enqueue(new HttpResultCallback<BaseResultData<String>>() {
//                            @Override
//                            public void onSuc(Response<BaseResultData<String>> response) {
//                                JPushInterface.stopPush(GlobalApplication.getContext());
//                                PrefUtil.putBoolean(mActivity, "isLogin", false);
//                                GreenDaoHelper.getInstance().clearAllData();
//                                mActivity.finish();
//                                startActivity(new Intent(mActivity, LoginActivity.class));
//                            }
//
//                            @Override
//                            public void onFail(String message) {
//                                mActivity.showToastMessage(message);
//                            }
//                        });
//
//
//                    }
//                }, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });


                WindowManager m = mActivity.getWindowManager();
                Display d = m.getDefaultDisplay();
                final AlertDialog dialog = new AlertDialog.Builder(mActivity).create();
                dialog.show();
                Window window = dialog.getWindow();
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.width = (int) (d.getWidth() * 0.7); // 宽度设置为屏幕的0.65
                window.setAttributes(lp);
                window.setContentView(R.layout.dialog_wait_audit_pass);
                Button btn_right = (Button) window.findViewById(R.id.btn_right);
                Button btn_cancle = (Button) window.findViewById(R.id.btn_cancle);
                TextView tv_text = (TextView) window.findViewById(R.id.tv_text);
                tv_text.setText("确定退出吗？");
                btn_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btn_cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HttpManager.getInstance().getHttpService().exitLogin(user.getToken()).enqueue(new HttpResultCallback<BaseResultData<String>>() {
                            @Override
                            public void onSuc(Response<BaseResultData<String>> response) {
                                JPushInterface.stopPush(GlobalApplication.getContext());
                                PrefUtil.putBoolean(mActivity, "isLogin", false);
                                GreenDaoHelper.getInstance().clearAllData();
                                mActivity.finish();
                                startActivity(new Intent(mActivity, LoginActivity.class));
                            }

                            @Override
                            public void onFail(String message) {
                                mActivity.showToastMessage(message);
                            }
                        });
                        dialog.dismiss();
                    }
                });
            }
        });
    }

}
