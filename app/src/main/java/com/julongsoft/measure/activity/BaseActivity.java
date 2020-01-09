package com.julongsoft.measure.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.julongsoft.measure.R;
import com.julongsoft.measure.db.GreenDaoHelper;
import com.julongsoft.measure.utils.PrefUtil;

/**
 * Created by tao on 2017/7/24.
 */

public class BaseActivity extends AppCompatActivity {

    protected ProgressDialog progressDialog;
    protected AlertDialog alertDialog;
    protected Toast toast;
    private MyReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 创建进度等待对话框
        progressDialog = new ProgressDialog(this, AlertDialog.THEME_HOLO_LIGHT);

        registerBroadcast();
    }


    //修改标题下面的蓝色线
    private void dialogTitleLineColor(Dialog dialog, int color) {
        Context context = dialog.getContext();
        int divierId = context.getResources().getIdentifier("android:id/titleDivider", null, null);
        View divider = dialog.findViewById(divierId);
        if (divider != null) {
            divider.setBackgroundColor(color);
        }

    }

    public void showProgressDialog(String message) {
        if (message != null) {
            progressDialog.setMessage(message);
        }
        progressDialog.show();
    }

    public void hideProgressDialog() {
        progressDialog.dismiss();
    }

    public void showAlertDialog(String message,
                                final DialogInterface.OnClickListener okListener,
                                final DialogInterface.OnClickListener noListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setTitle(null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (okListener != null) {
                    okListener.onClick(dialog, which);
                }
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (noListener != null) {
                    noListener.onClick(dialog, which);
                }
            }

        });
        alertDialog = builder.create();
        alertDialog.show();
        dialogTitleLineColor(alertDialog, getResources().getColor(R.color.colorTitle));
        //show之后才能拿到button
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorTitle));
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorTitle));
    }

    public void showAlertDialog(String message, String okText,
                                String cancelText,
                                final DialogInterface.OnClickListener okListener,
                                final DialogInterface.OnClickListener noListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setTitle(null);
        builder.setPositiveButton(okText,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (okListener != null) {
                            okListener.onClick(dialog, which);
                        }
                    }
                });

        builder.setNegativeButton(cancelText,
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (noListener != null) {
                            noListener.onClick(dialog, which);
                        }
                    }

                });
        alertDialog = builder.create();
        alertDialog.show();
        dialogTitleLineColor(alertDialog, getResources().getColor(R.color.colorTitle));
    }

    public void showAlertDialog(String message,
                                final DialogInterface.OnClickListener okListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setTitle(null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (okListener != null) {
                    okListener.onClick(dialog, which);
                }
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
        dialogTitleLineColor(alertDialog, getResources().getColor(R.color.colorTitle));
    }

    public void showAlertDialog(String title, String message,
                                String okText, final DialogInterface.OnClickListener okListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setPositiveButton(okText, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (okListener != null) {
                    okListener.onClick(dialog, which);
                }
            }
        });

        alertDialog = builder.create();
        alertDialog.show();
        dialogTitleLineColor(alertDialog, getResources().getColor(R.color.colorTitle));
        //show之后才能拿到button
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorTitle));
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(20);
    }

    public void showAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setTitle(null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
        dialogTitleLineColor(alertDialog, getResources().getColor(R.color.colorTitle));
    }

    public void showToastMessage(String message) {

        if (toast == null) {
            // 创建Toast对话框
            toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }


    public void registerBroadcast() {

        // 注册广播接收者
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("exit_app");
        registerReceiver(receiver, filter);
    }

    class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("exit_app")) {
                boolean isLogin = PrefUtil.getBoolean(context, "isLogin", false);
                if (isLogin) {

                    PrefUtil.putBoolean(context, "isLogin", false);
                    GreenDaoHelper.getInstance().clearAllData();
                    finish();
                    startActivity(new Intent(context, LoginActivity.class));
                    showToastMessage("登录失效，请重新登录");
//                    PushManager.getInstance().turnOffPush(BaseActivity.this);
                }

            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
