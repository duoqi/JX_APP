package com.julongsoft.measure.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.julongsoft.measure.R;
import com.julongsoft.measure.db.GreenDaoHelper;
import com.julongsoft.measure.entity.DbSegments;
import com.julongsoft.measure.entity.DbUser;
import com.julongsoft.measure.entity.DbUserName;
import com.julongsoft.measure.entity.UserData;
import com.julongsoft.measure.global.GlobalApplication;
import com.julongsoft.measure.http.BaseResultData;
import com.julongsoft.measure.http.HttpManager;
import com.julongsoft.measure.http.HttpResultCallback;
import com.julongsoft.measure.utils.PrefUtil;
import com.julongsoft.measure.utils.Print;

import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import retrofit2.Response;

/**
 * Created by tao on 2017/7/26.
 */

public class LoginActivity extends BaseActivity {


    private static final String TAG = "LoginActivity";
    private Button mLogin;
    private EditText mUser;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
    }

    private void initViews() {
        mUser = (EditText) findViewById(R.id.et_user);
        mPassword = (EditText) findViewById(R.id.et_password);
        mLogin = (Button) findViewById(R.id.btn_login);


        mPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mLogin.setEnabled(true);
//                    mLogin.setBackground(getDrawable(R.drawable.shape_login_button));
                    mLogin.setBackground(getResources().getDrawable(R.drawable.shape_login_button));
                    mLogin.setTextColor(getResources().getColor(R.color.colorWrite));
                } else {
                    mLogin.setEnabled(false);
                    mLogin.setTextColor(getResources().getColor(R.color.colorLogin_no_click));
//                    mLogin.setBackground(getDrawable(R.drawable.shape_login_button_no_click));
                    mLogin.setBackground(getResources().getDrawable(R.drawable.shape_login_button_no_click));
                }
            }
        });
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = mUser.getText().toString();
                String password = mPassword.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    showToastMessage("用户名或密码不能为空");
                    return;
                }

                showProgressDialog("登陆中...");


                HttpManager.getInstance().getHttpService().getLogin(username, password).enqueue(new HttpResultCallback<BaseResultData<UserData>>() {
                    @Override
                    public void onSuc(Response<BaseResultData<UserData>> response) {
                        BaseResultData<UserData> userData = response.body();
                        //登录接口返回数据保存数据库
                        saveDataFromLoginInterface(userData);
                        JPushInterface.resumePush(GlobalApplication.getContext());
                        JPushInterface.setAlias(GlobalApplication.getContext(), username, new TagAliasCallback() {
                            @Override
                            public void gotResult(int i, String s, Set<String> set) {
                                String logs = "";
                                switch (i) {
                                    case 0:
                                        logs = "Set tag and alias success";
                                        Log.i(TAG, logs);
                                        // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                                        break;
//                                    case 6002:
//                                        logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
//                                        Log.i(TAG, logs);
//                                        // 延迟 60 秒来调用 Handler 设置别名
//                                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
//                                        break;
                                    default:
                                        logs = "Failed with errorCode = " + i;
                                        Log.e(TAG, logs);
                                        break;
                                }
                            }
                        });
                        GreenDaoHelper.getInstance().getSession().getDbUserNameDao().deleteAll();
                        GreenDaoHelper.getInstance().getSession().getDbUserNameDao().insertOrReplace(new DbUserName(username));
                        PrefUtil.putBoolean(LoginActivity.this, "isLogin", true);
//                        进入主界面
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        hideProgressDialog();
                        finish();

                    }

                    @Override
                    public void onFail(String message) {
                        showToastMessage(message);
                        hideProgressDialog();
                    }
                });

            }
        });


        mUser.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Print.e("33", "22");
                    final List<DbUserName> userNameList = GreenDaoHelper.getInstance().getSession().getDbUserNameDao().queryBuilder().list();
                    if (null != userNameList || userNameList.size() > 0) {
                        View popupView = View.inflate(LoginActivity.this,R.layout.popu_login_username,null);
                        final PopupWindow popupWindow = new PopupWindow(popupView, mUser.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, true);
                        popupWindow.setBackgroundDrawable(new BitmapDrawable());
                        popupWindow.setOutsideTouchable(true);
                        // 设置此参数获得焦点，否则无法点击
                        popupWindow.setFocusable(false);
                        popupWindow.setTouchable(true);
                        ListView listView = (ListView)popupView.findViewById(R.id.lv_login_username);
                        listView.setAdapter(new BaseAdapter() {
                            @Override
                            public int getCount() {

                                return userNameList.size();
                            }

                            @Override
                            public Object getItem(int position) {
                                return null;
                            }

                            @Override
                            public long getItemId(int position) {
                                return 0;
                            }

                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                View view = View.inflate(LoginActivity.this,R.layout.item_login_username,null);
                                TextView tv_text = (TextView)view.findViewById(R.id.tv_text);
                                tv_text.setText(userNameList.get(position).getUsername());
                                return view;
                            }
                        });


                        popupWindow.showAsDropDown(mUser);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                mUser.setText(userNameList.get(position).getUsername());
                                popupWindow.dismiss();
                            }
                        });
                    }
                }
            }
        });

    }


    private void saveDataFromLoginInterface(BaseResultData<UserData> userData) {
        String token = userData.getContent().getToken();
        String deadline = userData.getContent().getToken();
        Long id = Long.valueOf(userData.getContent().getId());
        String code = userData.getContent().getCode();
        String name = userData.getContent().getName();
        String password = userData.getContent().getPassword();
        Integer isDeleted = userData.getContent().getIsDeleted();
        String notes = userData.getContent().getNotes();
        String tel = userData.getContent().getTel();
        Integer orgId = userData.getContent().getOrgId();
        String orgName = userData.getContent().getOrgName();
        Integer maxPeroid = userData.getContent().getMaxPeroid();
        DbUser dbUser = new DbUser(token, deadline, id, code,
                name, password, isDeleted, notes,
                tel, orgId, orgName, maxPeroid);
        GreenDaoHelper.getInstance().getSession().getDbUserDao().insertOrReplace(dbUser);


        for (int i = 0; i < userData.getContent().getSegments().size(); i++) {
            Long segmentsId = Long.valueOf(userData.getContent().getSegments().get(i).getId());
            String title = userData.getContent().getSegments().get(i).getTitle();
            DbSegments dbSegments = new DbSegments(segmentsId, title);
            GreenDaoHelper.getInstance().getSession().getDbSegmentsDao().insertOrReplace(dbSegments);
        }
    }
}
