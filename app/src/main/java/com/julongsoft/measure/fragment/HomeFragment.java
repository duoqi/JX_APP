package com.julongsoft.measure.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.julongsoft.measure.R;
import com.julongsoft.measure.activity.MainActivity;
import com.julongsoft.measure.db.GreenDaoHelper;
import com.julongsoft.measure.entity.DbUser;
import com.julongsoft.measure.entity.HomeDataBean;
import com.julongsoft.measure.http.BaseResultData;
import com.julongsoft.measure.http.HttpManager;
import com.julongsoft.measure.http.HttpResultCallback;
import com.julongsoft.measure.utils.Print;
import com.julongsoft.measure.view.NavigationBar;

import java.util.List;

import retrofit2.Response;

/**
 * Created by tao on 2017/7/24.
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "HomeFragment";
    private MainActivity mActivity;
    private Double[] totle = {800.0, 500.0, 600.0, 600.0, 900.0, 132.5};
    private Double[] some = {225.0, 200.0, 350.0, 100.0, 750.0, 67.5};
    //最高高度
    private int maxPx = 560;
    private View view1, view_totle1, view2,
            view_totle2, view3, view_totle3,
            view4, view_totle4, view5,
            view_totle5, view6, view_totle6;
    private TextView tv_top1, tv_top2, tv_top3,
            tv_top4, tv_top5, tv_top6, tv_bottom1,
            tv_bottom2, tv_bottom3, tv_bottom4,
            tv_bottom5, tv_bottom6;

    private DbUser user;
    private LinearLayout ll_picture;
    private ProgressBar tv_ipicture_text;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
        user = GreenDaoHelper.getInstance().getSession().getDbUserDao().queryBuilder().unique();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(mActivity, R.layout.fragment_home, null);
        initViews(view);
        return view;
    }

    private void initViews(View view) {

        ll_picture = (LinearLayout) view.findViewById(R.id.ll_picture);
        tv_ipicture_text = (ProgressBar) view.findViewById(R.id.tv_ipicture_text);
        ImageView iv_finish = (ImageView) view.findViewById(R.id.iv_finish);
        ImageView iv_wait_me = (ImageView) view.findViewById(R.id.iv_wait_me);
        TextView tv_finish = (TextView) view.findViewById(R.id.tv_finish);
        TextView tv_wait_me = (TextView) view.findViewById(R.id.tv_wait_me);
        iv_finish.setOnClickListener(this);
        iv_wait_me.setOnClickListener(this);
        tv_finish.setOnClickListener(this);
        tv_wait_me.setOnClickListener(this);

        NavigationBar navigationBar = (NavigationBar) view.findViewById(R.id.navigationBar);
        navigationBar.setTitle("延庆至崇礼高速公路河北段");
        navigationBar.clearLeftViews();
        navigationBar.clearRightViews();

        view1 = view.findViewById(R.id.view1);
        view_totle1 = view.findViewById(R.id.view_totle1);
        view2 = view.findViewById(R.id.view2);
        view_totle2 = view.findViewById(R.id.view_totle2);
        view3 = view.findViewById(R.id.view3);
        view_totle3 = view.findViewById(R.id.view_totle3);
        view4 = view.findViewById(R.id.view4);
        view_totle4 = view.findViewById(R.id.view_totle4);
        view5 = view.findViewById(R.id.view5);
        view_totle5 = view.findViewById(R.id.view_totle5);
        view6 = view.findViewById(R.id.view6);
        view_totle6 = view.findViewById(R.id.view_totle6);

        tv_top1 = (TextView) view.findViewById(R.id.tv_top1);
        tv_top2 = (TextView) view.findViewById(R.id.tv_top2);
        tv_top3 = (TextView) view.findViewById(R.id.tv_top3);
        tv_top4 = (TextView) view.findViewById(R.id.tv_top4);
        tv_top5 = (TextView) view.findViewById(R.id.tv_top5);
        tv_top6 = (TextView) view.findViewById(R.id.tv_top6);
        tv_bottom1 = (TextView) view.findViewById(R.id.tv_bottom1);
        tv_bottom2 = (TextView) view.findViewById(R.id.tv_bottom2);
        tv_bottom3 = (TextView) view.findViewById(R.id.tv_bottom3);
        tv_bottom4 = (TextView) view.findViewById(R.id.tv_bottom4);
        tv_bottom5 = (TextView) view.findViewById(R.id.tv_bottom5);
        tv_bottom6 = (TextView) view.findViewById(R.id.tv_bottom6);


        getDataFromServer();

    }

    private void getDataFromServer() {
        HttpManager.getInstance().getHttpService().getHomeData(user.getToken()).enqueue(new HttpResultCallback<BaseResultData<List<HomeDataBean>>>() {
            @Override
            public void onSuc(Response<BaseResultData<List<HomeDataBean>>> response) {
                for (int i = 0; i < response.body().getContent().size(); i++) {

                    if (i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 5) {

                        totle[i] = Math.floor(response.body().getContent().get(i).getTotalMoney() / 10000);
                        some[i] = Math.floor(response.body().getContent().get(i).getFinishMoney() / 10000);

                        Print.e(TAG, totle[i]);
                        Print.e(TAG, some[i]);
                    }

                }


                double max = getMax(totle);

                //计算高度并初始化控件
                count(max);
            }

            @Override
            public void onFail(String message) {
                mActivity.showToastMessage(message);
            }
        });
    }

    /***
     * 计算高度并初始化控件
     * @param max
     */
    private void count(double max) {

        double cou = maxPx / max;

        double s1 = cou * totle[0];
        double s2 = cou * totle[1];
        double s3 = cou * totle[2];
        double s4 = cou * totle[3];
        double s5 = cou * totle[4];
        double s6 = cou * totle[5];

        double y1 = cou * some[0];
        double y2 = cou * some[1];
        double y3 = cou * some[2];
        double y4 = cou * some[3];
        double y5 = cou * some[4];
        double y6 = cou * some[5];

        ViewGroup.LayoutParams layoutParams1 = view1.getLayoutParams();
        layoutParams1.height = (int) s1/2;
        view1.setLayoutParams(layoutParams1);

        ViewGroup.LayoutParams layoutParams2 = view2.getLayoutParams();
        layoutParams2.height = (int) s2/2;
        view2.setLayoutParams(layoutParams2);

        ViewGroup.LayoutParams layoutParams3 = view3.getLayoutParams();
        layoutParams3.height = (int) s3/2;
        view3.setLayoutParams(layoutParams3);

        ViewGroup.LayoutParams layoutParams4 = view4.getLayoutParams();
        layoutParams4.height = (int) s4/2;
        view4.setLayoutParams(layoutParams4);

        ViewGroup.LayoutParams layoutParams5 = view5.getLayoutParams();
        layoutParams5.height = (int) s5/2;
        view5.setLayoutParams(layoutParams5);

        ViewGroup.LayoutParams layoutParams6 = view6.getLayoutParams();
        layoutParams6.height = (int) s6/2;
        view6.setLayoutParams(layoutParams6);

        ViewGroup.LayoutParams layoutParams_1 = view_totle1.getLayoutParams();
        layoutParams_1.height = (int) y1/2;
        view_totle1.setLayoutParams(layoutParams_1);

        ViewGroup.LayoutParams layoutParams_2 = view_totle2.getLayoutParams();
        layoutParams_2.height = (int) y2/2;
        view_totle2.setLayoutParams(layoutParams_2);

        ViewGroup.LayoutParams layoutParams_3 = view_totle3.getLayoutParams();
        layoutParams_3.height = (int) y3/2;
        view_totle3.setLayoutParams(layoutParams_3);

        ViewGroup.LayoutParams layoutParams_4 = view_totle4.getLayoutParams();
        layoutParams_4.height = (int) y4/2;
        view_totle4.setLayoutParams(layoutParams_4);

        ViewGroup.LayoutParams layoutParams_5 = view_totle5.getLayoutParams();
        layoutParams_5.height = (int) y5/2;
        view_totle5.setLayoutParams(layoutParams_5);

        ViewGroup.LayoutParams layoutParams_6 = view_totle6.getLayoutParams();
        layoutParams_6.height = (int) y6/2;
        view_totle6.setLayoutParams(layoutParams_6);

        tv_top1.setText(totle[0] + "");
        tv_top2.setText(totle[1] + "");
        tv_top3.setText(totle[2] + "");
        tv_top4.setText(totle[3] + "");
        tv_top5.setText(totle[4] + "");
        tv_top6.setText(totle[5] + "");

        tv_bottom1.setText(some[0] + "");
        tv_bottom2.setText(some[1] + "");
        tv_bottom3.setText(some[2] + "");
        tv_bottom4.setText(some[3] + "");
        tv_bottom5.setText(some[4] + "");
        tv_bottom6.setText(some[5] + "");


        ll_picture.setVisibility(View.VISIBLE);
        tv_ipicture_text.setVisibility(View.GONE);
    }

    /***
     * 数组中获取最大值
     * @param arr
     * @return
     */
    public static double getMax(Double[] arr) {

        double max = arr[0];

        for (int x = 1; x < arr.length; x++) {
            if (arr[x] > max)
                max = arr[x];

        }
        return max;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_wait_me:
            case R.id.iv_wait_me:
                mActivity.setCurTab(MainActivity.TabTag.backlog);
                WorkFragment.instance.getDataFromServer(1, null,  null,  "1");
                break;
            case R.id.iv_finish:
            case R.id.tv_finish:
                mActivity.setCurTab(MainActivity.TabTag.backlog);
                WorkFragment.instance.getDataFromServer(1, null,  null,  "2");
                break;
            default:
                break;
        }
    }
}
