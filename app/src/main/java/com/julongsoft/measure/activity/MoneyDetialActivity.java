package com.julongsoft.measure.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.julongsoft.measure.R;
import com.julongsoft.measure.db.GreenDaoHelper;
import com.julongsoft.measure.entity.DbUser;
import com.julongsoft.measure.entity.MoneyData;
import com.julongsoft.measure.entity.MoneyDetialData;
import com.julongsoft.measure.entity.MoneySignListData;
import com.julongsoft.measure.entity.SectionDetial;
import com.julongsoft.measure.http.BaseResultData;
import com.julongsoft.measure.http.HttpManager;
import com.julongsoft.measure.http.HttpResultCallback;
import com.julongsoft.measure.utils.Print;
import com.julongsoft.measure.view.MyScrollView;
import com.julongsoft.measure.view.NavigationBar;
import com.julongsoft.measure.view.NoScrollListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Response;


/**
 * Created by tao on 2017/8/1.
 */

public class MoneyDetialActivity extends BaseActivity implements View.OnClickListener {


    private static final String TAG = "MoneyDetialActivity";
    private static final int REPULSE = 1;
    private String note;
    private DbUser user;
    private TextView tv_unit_name;
    private TextView tv_hetong_pay;
    private TextView tv_last_pay;
    private TextView tv_this_pay;
    private TextView tv_this_totle_pay, tv_text1, tv_text2, tv_text3, tv_text4;
    private NoScrollAdapter mAdapter;


    private SectionDetial sectionDetial;

    private List<MoneySignListData> list = new ArrayList<>();
    private List<MoneySignListData> list_another = new ArrayList<>();
    private List<MoneySignListData> list_another1 = new ArrayList<>();
    private NoScrollListView no_scroll_listview;
    private MyScrollView msv_scroll_view;
    private RelativeLayout rl_layout;

    private boolean isPass = false;
    private List<MoneySignListData> list_boolean = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_audit);
        note = getIntent().getStringExtra("note");
        user = GreenDaoHelper.getInstance().getSession().getDbUserDao().queryBuilder().unique();
        initViews();
    }


    private void initViews() {
        NavigationBar navigationBar = (NavigationBar) findViewById(R.id.navigationBar);
        rl_layout = (RelativeLayout) findViewById(R.id.rl_layout);
        msv_scroll_view = (MyScrollView) findViewById(R.id.msv_scroll_view);
//        Button btn_repulse = (Button) findViewById(R.id.btn_repulse);
        Button btn_pass = (Button) findViewById(R.id.btn_pass);
        navigationBar.setTitle("待我审核");

        no_scroll_listview = (NoScrollListView) findViewById(R.id.no_scroll_listview);


//        btn_repulse.setOnClickListener(this);
        btn_pass.setOnClickListener(this);


        tv_unit_name = (TextView) findViewById(R.id.tv_unit_name);
        tv_hetong_pay = (TextView) findViewById(R.id.tv_hetong_pay);
        tv_last_pay = (TextView) findViewById(R.id.tv_last_pay);
        tv_this_pay = (TextView) findViewById(R.id.tv_this_pay);
        tv_text1 = (TextView) findViewById(R.id.tv_text1);
        tv_text2 = (TextView) findViewById(R.id.tv_text2);
        tv_text3 = (TextView) findViewById(R.id.tv_text3);
        tv_text4 = (TextView) findViewById(R.id.tv_text4);
        tv_this_totle_pay = (TextView) findViewById(R.id.tv_this_totle_pay);
        TextView tv_detial = (TextView) findViewById(R.id.tv_detial);
        ImageView tv_image1 = (ImageView) findViewById(R.id.tv_image1);
        tv_detial.setOnClickListener(this);
        tv_image1.setOnClickListener(this);
        msv_scroll_view.setVisibility(View.INVISIBLE);
        tv_detial.setVisibility(View.GONE);
        tv_image1.setVisibility(View.GONE);
        TextView tv = findViewById(R.id.tv_detial_title);
        tv.setText("资金计划");
        getDataFromServer();
    }


    private void getDataFromServer() {
        Print.e(TAG, user.getToken());
        Print.e(TAG, note);
        showProgressDialog("正在加载...");
        HttpManager.getInstance().getHttpService().getMoneySumData(user.getToken(), note).enqueue(new HttpResultCallback<BaseResultData<MoneyDetialData>>() {


            @SuppressLint("SetTextI18n")
            @Override
            public void onSuc(Response<BaseResultData<MoneyDetialData>> response) {
                hideProgressDialog();
                msv_scroll_view.setVisibility(View.VISIBLE);
                tv_text1.setText("累计收到筹建处款项金额");
                tv_text2.setText("本期计划支付金额");
                tv_text3.setText("本期批复金额");
                tv_text4.setText("累计实际支付");
                tv_unit_name.setText(response.body().getContent().getContractor());
                try {
                    tv_hetong_pay.setText((double) response.body().getContent().getTotal_ivst() + "元");
                    tv_last_pay.setText((double) response.body().getContent().getPlan_ivst() + "元");
                    tv_this_pay.setText((double) response.body().getContent().getApproval_ivst() + "元");
                    tv_this_totle_pay.setText((double) response.body().getContent().getPeriod_pay_money() + "元");
                } catch (Exception e) {
                    Print.e(TAG, "数据转换异常");
                }

            }

            @Override
            public void onFail(String message) {
                showToastMessage(message);
                Print.e(TAG, message);
                hideProgressDialog();
            }
        });

        HttpManager.getInstance().getHttpService().getMoneySignListData(user.getToken(), note).enqueue(new HttpResultCallback<BaseResultData<List<MoneySignListData>>>() {


            @Override
            public void onSuc(Response<BaseResultData<List<MoneySignListData>>> response) {
                hideProgressDialog();
                msv_scroll_view.setVisibility(View.VISIBLE);
                list = response.body().getContent();
                list_boolean.clear();
                for (int i = 0; i < response.body().content.size(); i++) {
                    if (list.get(i).getState() == 1 && user.getRoles().contains(list.get(i).getRole_id() + "")) {
                        list_boolean.add(response.body().content.get(i));
                    }
                }

                if (list_boolean.size() > 0) {
                    rl_layout.setVisibility(View.VISIBLE);
                } else {
                    rl_layout.setVisibility(View.GONE);
                }
//                list_boolean.clear();
                mAdapter = new NoScrollAdapter();
                no_scroll_listview.setAdapter(mAdapter);
            }

            @Override
            public void onFail(String message) {
                showToastMessage(message);
                Print.e(TAG, message);
                hideProgressDialog();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case btn_repulse:
//
//                for (int i = 0; i < list.size(); i++) {
//                    if (list.get(i).getState() == 1 && list.get(i).getMyState() == 1) {
//                        list_another1.add(list.get(i));
//                    }
//                }
//                if (list_another1.size() > 0) {
//                    Intent intent = new Intent(this, RepulseActivity.class);
//                    intent.putExtra("list", (Serializable) list);
//                    intent.putExtra("periodId", periodId);
//                    startActivityForResult(intent, REPULSE);
//                } else {
//                    showToastMessage("不能打回！");
//                }
//
//                break;
            case R.id.tv_detial:
            case R.id.tv_image1:
                Intent intent = new Intent(MoneyDetialActivity.this, WaitDetialActivity.class);
                intent.putExtra("sectionDetial", sectionDetial);
                startActivity(intent);

                break;
            case R.id.btn_pass:


//                for (int i = 0; i < list.size(); i++) {
//                    if (list.get(i).getState() == 1 && list.get(i).getMyState() == 1) {
//                        list_another.add(list.get(i));
//                    }
//                }
//
                Print.e(TAG + "list_another.size", list_another.size());
                if (list_boolean.size() > 0) {
                    WindowManager m = getWindowManager();
                    Display d = m.getDefaultDisplay();
                    final AlertDialog dialog = new AlertDialog.Builder(this).create();
                    dialog.show();
                    Window window = dialog.getWindow();
                    WindowManager.LayoutParams lp = window.getAttributes();
                    lp.width = (int) (d.getWidth() * 0.7); // 宽度设置为屏幕的0.65
                    window.setAttributes(lp);
                    window.setContentView(R.layout.dialog_wait_audit_pass);
                    Button btn_right = (Button) window.findViewById(R.id.btn_right);
                    Button btn_cancle = (Button) window.findViewById(R.id.btn_cancle);
                    btn_right.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    btn_cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            for (int i = 0; i < list_another.size(); i++) {

                            showProgressDialog("请稍后");
                            MoneySignListData signDataBean = list_boolean.get(0);
                            Print.e(TAG + "333", signDataBean.getId());
                            HttpManager.getInstance().getHttpService().signResult(user.getToken(), signDataBean.getId(), 1,"同意").enqueue(new HttpResultCallback<BaseResultData>() {
                                @Override
                                public void onSuc(Response<BaseResultData> response) {
                                    showToastMessage("已通过");
                                    getDataFromServer();
                                    hideProgressDialog();
                                }

                                @Override
                                public void onFail(String message) {
                                    showToastMessage(message);
                                    Print.e(TAG, message);
                                    hideProgressDialog();
                                }
                            });
//                            }


                            dialog.dismiss();
                        }
                    });
                } else {
                    showToastMessage("不能签字！");
                }


                break;

            default:
                break;
        }
    }

    private class NoScrollAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
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
            View view = View.inflate(MoneyDetialActivity.this, R.layout.item_wait_audit, null);
            View view2 = view.findViewById(R.id.view2);
            ImageView iv_state = (ImageView) view.findViewById(R.id.iv_state);
            TextView tv_unit = (TextView) view.findViewById(R.id.tv_unit);
            TextView tv_state = (TextView) view.findViewById(R.id.tv_state);
            TextView tv_time = (TextView) view.findViewById(R.id.tv_time);
            if (position == list.size()) {
                view2.setVisibility(View.GONE);
            }
            MoneySignListData signDataBean = list.get(position);
            tv_unit.setText(signDataBean.getRole_name());
            if (signDataBean.getState() == 1) {
                tv_state.setText("待审核");
            } else if (signDataBean.getState() == 0) {
                tv_state.setText("未审核");
            } else if (signDataBean.getState() == 2) {
                tv_state.setText("已审核");
            }

//            if (null != signDataBean.getTime()) {
//                tv_time.setText(signDataBean.getTime().substring(0, 10));
//            }
            if (signDataBean.getState() == 0) {
                iv_state.setImageResource(R.drawable.gz_dwsh_wtg01_icon);
                tv_state.setTextColor(getResources().getColor(R.color.workState));
            } else if (signDataBean.getState() == 1) {
                iv_state.setImageResource(R.drawable.gz_dwsh_wtg02_icon);
                tv_state.setTextColor(getResources().getColor(R.color.colorTitle));
            } else if (signDataBean.getState() == 2) {
                iv_state.setImageResource(R.drawable.gz_dwsh_tg_icon);
                tv_state.setTextColor(getResources().getColor(R.color.workStateRight));
            } else if (signDataBean.getState() == -1) {
                iv_state.setImageResource(R.drawable.gz_dwsh_wtg01_icon);
                tv_state.setTextColor(getResources().getColor(R.color.workState));
            }

            return view;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REPULSE:
                getDataFromServer();
                Print.e("123132132131313", "123132132113");
                if (null != mAdapter) {
                    mAdapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }
}
