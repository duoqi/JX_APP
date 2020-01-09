package com.julongsoft.measure.activity;

import android.app.ActionBar;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.julongsoft.measure.R;
import com.julongsoft.measure.db.GreenDaoHelper;
import com.julongsoft.measure.entity.DbUser;
import com.julongsoft.measure.entity.SectionDetial;
import com.julongsoft.measure.http.BaseResultData;
import com.julongsoft.measure.http.HttpManager;
import com.julongsoft.measure.http.HttpResultCallback;
import com.julongsoft.measure.utils.Print;
import com.julongsoft.measure.view.NavigationBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Response;

/**
 * Created by tao on 2017/8/1.
 */

public class RepulseActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "RepulseActivity";
    private TextView tv_unit;
    private EditText et_content;
    private List<SectionDetial.SignDataBean> list = new ArrayList<>();
    private List<SectionDetial.SignDataBean> reTurnlist = new ArrayList<>();
    private int sn;//打回的步数 对应sn
    private int nowSn;//当前的步数 对应sn
    private String selectDes = "";
    private String menu[];
    private int periodId;
    private DbUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repulse);
        user = GreenDaoHelper.getInstance().getSession().getDbUserDao().queryBuilder().unique();
        list = (List<SectionDetial.SignDataBean>) getIntent().getSerializableExtra("list");
        periodId = getIntent().getIntExtra("periodId", 0);
        initData();
        initViews();
    }

    private void initViews() {
        NavigationBar navigationBar = (NavigationBar) findViewById(R.id.navigationBar);
        navigationBar.setTitle("审核未通过");

        tv_unit = (TextView) findViewById(R.id.tv_unit);
        et_content = (EditText) findViewById(R.id.et_content);
        ImageView iv_unit = (ImageView) findViewById(R.id.iv_unit);
        Button btn_cancle = (Button) findViewById(R.id.btn_cancle);
        Button btn_confirm = (Button) findViewById(R.id.btn_confirm);

        tv_unit.setOnClickListener(this);
        iv_unit.setOnClickListener(this);
        btn_cancle.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);
    }


    private void initData() {
        SectionDetial.SignDataBean signDataBean = list.get(list.size() - 1);
        menu = new String[signDataBean.getSn()];
        String des1 = "";
        String des2 = "";
        String des3 = "";
        String des4 = "";
        String des5 = "";
        String des6 = "";
        String des7 = "";
        for (int j = 0; j < list.size(); j++) {

            if (list.get(j).getSn() == 1 && list.get(j).getState() >= 1) {

                des1 = des1 + "、" + list.get(j).getRoleName();
                menu[0] = des1.substring(1, des1.length());

            }
            if (list.get(j).getSn() == 2 && list.get(j).getState() >= 1) {
                des2 = des2 + "、" + list.get(j).getRoleName();
                menu[1] = des2.substring(1, des2.length());
            }
            if (list.get(j).getSn() == 3 && list.get(j).getState() >= 1) {
                des3 = des3 + "、" + list.get(j).getRoleName();
                menu[2] = des3.substring(1, des3.length());
            }
            if (list.get(j).getSn() == 4 && list.get(j).getState() >= 1) {
                des4 = des4 + "、" + list.get(j).getRoleName();
                menu[3] = des4.substring(1, des4.length());
            }
            if (list.get(j).getSn() == 5 && list.get(j).getState() >= 1) {
                des5 = des5 + "、" + list.get(j).getRoleName();
                menu[4] = des5.substring(1, des5.length());
            }
            if (list.get(j).getSn() == 6 && list.get(j).getState() >= 1) {
                des6 = des6 + "、" + list.get(j).getRoleName();
                menu[5] = des6.substring(1, des6.length());
            }
            if (list.get(j).getSn() == 7 && list.get(j).getState() >= 1) {
                des7 = des7 + "、" + list.get(j).getRoleName();
                menu[6] = des7.substring(1, des7.length());
            }


        }

//        for(int i = 0;i<menu.length;i++){
//            Print.e(TAG,menu[i]);
//        }


        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getState() == 1 && list.get(i).getMyState() == 1) {
                nowSn = list.get(i).getSn();
            }
        }

    }

    /***
     * 去掉数组中的空元素
     * @param strArray
     * @return
     */
    private String[] removeArrayEmptyTextBackNewArray(String[] strArray) {
        List<String> strList = Arrays.asList(strArray);
        List<String> strListNew = new ArrayList<>();
        for (int i = 0; i < strList.size(); i++) {
            if (strList.get(i) != null && !strList.get(i).equals("")) {
                strListNew.add(strList.get(i));
            }
        }
        String[] strNewArray = strListNew.toArray(new String[strListNew.size()]);
        return strNewArray;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_unit:
            case R.id.iv_unit:
                LayoutInflater inflater = LayoutInflater.from(this);
                LinearLayout rootLinearLayout = (LinearLayout) inflater.inflate(R.layout.popupwindow_more, null);
                final PopupWindow popupWindow = new PopupWindow(rootLinearLayout, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
//                final String[] menu = getResources().getStringArray(R.array.repulse_menu);

                menu = removeArrayEmptyTextBackNewArray(menu);
                for (int i = 0; i < menu.length; i++) {
                    View view = View.inflate(this, R.layout.item_popu_repulse, null);
                    TextView textView = (TextView) view.findViewById(R.id.tv_text);
                    textView.setText(menu[i]);
                    popupWindow.setBackgroundDrawable(new BitmapDrawable());
                    popupWindow.setOutsideTouchable(true);
                    // 设置此参数获得焦点，否则无法点击
                    popupWindow.setFocusable(true);
                    popupWindow.setTouchable(true);
                    rootLinearLayout.addView(view);
                    view.setId(i);

                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (v.getId()) {
                                case 0:
                                    tv_unit.setText(menu[0]);
                                    sn = 1;
                                    break;
                                case 1:
                                    tv_unit.setText(menu[1]);
                                    sn = 2;
                                    break;
                                case 2:
                                    tv_unit.setText(menu[2]);
                                    sn = 3;
                                    break;
                                case 3:
                                    tv_unit.setText(menu[3]);
                                    sn = 4;
                                    break;
                                case 4:
                                    tv_unit.setText(menu[4]);
                                    sn = 5;
                                    break;
                                case 5:
                                    tv_unit.setText(menu[5]);
                                    sn = 6;
                                    break;
                                case 6:
                                    tv_unit.setText(menu[6]);
                                    sn = 7;
                                    break;
                                case 7:
                                    tv_unit.setText(menu[7]);
                                    sn = 8;
                                    break;
                                default:
                                    break;

                            }

                            popupWindow.dismiss();
                        }
                    });
                }
                popupWindow.setWidth(840);
                popupWindow.showAsDropDown(tv_unit);

                break;

            case R.id.btn_confirm:
                String content = et_content.getText().toString();


                Print.e("sn", sn);
                Print.e("nowSn", nowSn);
                for (int i = 0; i < list.size(); i++) {

                    if (list.get(i).getSn() <= nowSn && list.get(i).getSn() >= sn) {
                        reTurnlist.add(list.get(i));
                    }

                }
                Collections.reverse(reTurnlist); // 倒序排列


                for (int i = 0; i < reTurnlist.size(); i++) {
                    SectionDetial.SignDataBean signDataBean = reTurnlist.get(i);
                    Print.e("id", signDataBean.getId());

                    HttpManager.getInstance().getHttpService().sign(user.getToken(), periodId, signDataBean.getId(), content, 1).enqueue(new HttpResultCallback<BaseResultData<Boolean>>() {
                        @Override
                        public void onSuc(Response<BaseResultData<Boolean>> response) {
                            showToastMessage("已打回");
                        }

                        @Override
                        public void onFail(String message) {
                            showToastMessage(message);
                        }
                    });
                }
                finish();
                break;
            case R.id.btn_cancle:
                finish();
                break;
        }
    }
}
