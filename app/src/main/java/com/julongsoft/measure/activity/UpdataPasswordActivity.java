package com.julongsoft.measure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.julongsoft.measure.R;
import com.julongsoft.measure.db.GreenDaoHelper;
import com.julongsoft.measure.entity.DbUser;
import com.julongsoft.measure.entity.UserData;
import com.julongsoft.measure.http.BaseResultData;
import com.julongsoft.measure.http.HttpManager;
import com.julongsoft.measure.http.HttpResultCallback;
import com.julongsoft.measure.utils.PrefUtil;
import com.julongsoft.measure.view.NavigationBar;

import retrofit2.Response;


/**
 * Created by Tao on 2016/6/29.
 */
public class UpdataPasswordActivity extends BaseActivity {

    private EditText et_first_password;
    private EditText et_second_password;
    private DbUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_password);
        user = GreenDaoHelper.getInstance().getSession().getDbUserDao().queryBuilder().unique();

        NavigationBar navigationBar = (NavigationBar) findViewById(R.id.navigationBar);
        navigationBar.setTitle("修改密码");


        et_first_password = (EditText) findViewById(R.id.et_first_password);
        et_second_password = (EditText) findViewById(R.id.et_second_password);
        Button btn_login = (Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstPassword = et_first_password.getText().toString();
                String secondPassword = et_second_password.getText().toString();

                if (firstPassword.length() < 6 || firstPassword.length() > 16) {
                    showToastMessage("密码格式不正确");
                    return;
                }

                if (firstPassword.isEmpty() || secondPassword.isEmpty()) {
                    showToastMessage("密码不能为空");
                    return;
                }

                if (!firstPassword.equals(secondPassword)) {
                    showToastMessage("两次输入的密码不一致");
                    return;
                }
                showProgressDialog("请稍后...");

                HttpManager.getInstance().getHttpService().updatePassword(user.getToken(), secondPassword).enqueue(new HttpResultCallback<BaseResultData<String>>() {
                    @Override
                    public void onSuc(Response<BaseResultData<String>> response) {
                        showToastMessage("修改成功");
                        hideProgressDialog();
                        finish();
                        Intent intent = new Intent();
                        intent.setAction("exit_app");
                        sendBroadcast(intent);
                    }

                    @Override
                    public void onFail(String message) {
                        hideProgressDialog();
                        showToastMessage(message);
                    }
                });


            }
        });

    }
}
