package com.julongsoft.measure.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.julongsoft.measure.R;
import com.julongsoft.measure.entity.SectionDetial;
import com.julongsoft.measure.utils.Print;
import com.julongsoft.measure.view.NavigationBar;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by tao on 2017/8/1.
 */

public class WaitDetialActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "WaitDetialActivity";
    private List<SectionDetial.AppSumChptBean> listAppSumChptBean = new ArrayList<>();
    private LinearLayout ll_layout_other;
    private LinearLayout ll_layout_which;
    private ListView lv_list_detial_data;
    private SectionDetial sectionDetial;
    private TextView tv_text1;
    private TextView tv_text2;
    private TextView tv_text3;
    private TextView tv_text4;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detial_audit);
        sectionDetial = (SectionDetial) getIntent().getSerializableExtra("sectionDetial");


        initData();

        initViews();
    }

    private void initData() {

        for (int i = 0; i < sectionDetial.getAppSumChpt().size(); i++) {
            listAppSumChptBean.add(sectionDetial.getAppSumChpt().get(i));
        }
    }


    private void initViews() {
        tv_text1 = (TextView) findViewById(R.id.tv_text1);
        tv_text2 = (TextView) findViewById(R.id.tv_text2);
        tv_text3 = (TextView) findViewById(R.id.tv_text3);
        tv_text4 = (TextView) findViewById(R.id.tv_text4);
        lv_list_detial_data = (ListView) findViewById(R.id.lv_list_detial_data);
        ll_layout_which = (LinearLayout) findViewById(R.id.ll_layout_which);
        ll_layout_other = (LinearLayout) findViewById(R.id.ll_layout_other);
        myAdapter = new MyAdapter();

        NavigationBar navigationBar = (NavigationBar) findViewById(R.id.navigationBar);
        navigationBar.setTitle("计量信息");

        final LinearLayout ll_contaner = (LinearLayout) findViewById(R.id.ll_contaner);

        for (int i = 0; i < listAppSumChptBean.size() + 1; i++) {
            final View view = View.inflate(WaitDetialActivity.this, R.layout.item_detial_wait_list, null);
            final Button btn_button = (Button) view.findViewById(R.id.btn_button);

            if (i == listAppSumChptBean.size()) {
                btn_button.setText("其它支付");
            } else {
                btn_button.setText(listAppSumChptBean.get(i).getNum() + "章");
            }


            //初始化第一个button为选择状态
            if (i == 0) {
                btn_button.setSelected(true);
                btn_button.setTextColor(getResources().getColor(R.color.colorTitle));
                tv_text1.setText(listAppSumChptBean.get(0).getDrwIvst() / 10000 + "");
                tv_text2.setText(listAppSumChptBean.get(0).getAltIvst() / 10000 + "");
                tv_text3.setText(listAppSumChptBean.get(0).getFinalIvst() / 10000 + "");
                tv_text4.setText(listAppSumChptBean.get(0).getPrdIvst() / 10000 + "");
            }

            final int finalI = i;
            btn_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btn_button.setSelected(true);
                    btn_button.setTextColor(getResources().getColor(R.color.colorTitle));
                    btn_button.setTag(finalI);

                    for (int j = 0; j < listAppSumChptBean.size() + 1; j++) {
                        LinearLayout linearLayout = (LinearLayout) ll_contaner.getChildAt(j);
                        Button button = (Button) linearLayout.getChildAt(0);
                        if ((int) btn_button.getTag() != j) {
                            button.setSelected(false);
                            button.setTextColor(getResources().getColor(R.color.textDetial));
                        }

                    }


                    //接口返回没有其它支付栏
                    if (finalI == listAppSumChptBean.size()) {
                        Print.e(TAG + "++++++++++++++++", "其它支付");
                        ll_layout_other.setVisibility(View.VISIBLE);
                        ll_layout_which.setVisibility(View.GONE);
                        lv_list_detial_data.setAdapter(myAdapter);
                    } else {
                        ll_layout_other.setVisibility(View.GONE);
                        ll_layout_which.setVisibility(View.VISIBLE);
                        tv_text1.setText(listAppSumChptBean.get(finalI).getDrwIvst() / 10000 + "");
                        tv_text2.setText(listAppSumChptBean.get(finalI).getAltIvst() / 10000 + "");
                        tv_text3.setText(listAppSumChptBean.get(finalI).getFinalIvst() / 10000 + "");
                        tv_text4.setText(listAppSumChptBean.get(finalI).getPrdIvst() / 10000 + "");
                    }

                }
            });
            ll_contaner.addView(view);
        }

    }

    @Override
    public void onClick(View v) {

    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return sectionDetial.getOtherSumChpt().size();
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
        public View getView(final int position, View convertView, final ViewGroup parent) {
            View view = View.inflate(WaitDetialActivity.this, R.layout.item_detial_right_list, null);
            TextView tv_text1 = (TextView) view.findViewById(R.id.tv_text1);
            TextView tv_text2 = (TextView) view.findViewById(R.id.tv_text2);

            tv_text1.setText(sectionDetial.getOtherSumChpt().get(position).getTitle() + "(万元)");

            tv_text2.setText(sectionDetial.getOtherSumChpt().get(position).getPrdIvst() / 10000 + "");
            return view;
        }
    }


}
