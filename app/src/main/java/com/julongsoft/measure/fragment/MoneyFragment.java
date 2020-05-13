package com.julongsoft.measure.fragment;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.julongsoft.measure.R;
import com.julongsoft.measure.activity.MainActivity;
import com.julongsoft.measure.activity.WaitAuditActivity;
import com.julongsoft.measure.db.GreenDaoHelper;
import com.julongsoft.measure.entity.DbSegments;
import com.julongsoft.measure.entity.DbUser;
import com.julongsoft.measure.entity.PeriodListData;
import com.julongsoft.measure.entity.ProjectTimeData;
import com.julongsoft.measure.entity.SectionData;
import com.julongsoft.measure.http.BaseResultData;
import com.julongsoft.measure.http.HttpManager;
import com.julongsoft.measure.http.HttpResultCallback;
import com.julongsoft.measure.utils.Print;
import com.julongsoft.measure.view.NavigationBar;
import com.julongsoft.measure.view.PopupWindowWork;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

import static com.julongsoft.measure.R.id.tv_title;

/**
 * Created by tao on 2017/7/24.
 */

public class MoneyFragment extends BaseFragment implements PopupWindowWork.OnClickItemListener, View.OnClickListener {


    private static final String TAG = "WorkFragment";
    private MainActivity mActivity;
    private View rightView;
    private ImageView ivRight;
    private TextView tv_name;
    private NavigationBar navigationBar;
    private TextView tv_state;
    private TextView tv_time;
    private TextView tv_section;
    private RelativeLayout rl_state;
    private RelativeLayout rl_time;
    private RelativeLayout rl_section;
    private View gray_layout;
    private PopupWindow popupWindow;

//    int fromYDelta;
    private View view_line_one;
    private View view_line_two;
    private View view_line_three;

    private TextView tvFooter;


    /**
     * 过滤条件状态
     */
    private boolean isWait = false;//待我审核
    private boolean isWaited = false;//我已审核

    private boolean isClickConfirm_state = false;//状态栏确认按钮是否确认
    private boolean confirm_state_after_isWait = false;//确认后待我审核的值
    private boolean confirm_state_after_isWaited = false;//确认后我已审核的值

    private boolean isClickConfirm_section = false;//标段状态栏确认按钮是否确认
    private boolean isClickConfirm_project = false;//工期状态栏确认按钮是否确认
    private String[] sectionValue = {"1标", "2标", "3标", "4标", "5标", "6标", "7标", "8标"};
    private String selectSection = "";
    private Long sectionId = 0L;//选择标段id
    private String selectProject = "";
    private SectionAdapter mAdapter;
    private ProjectTimeAdapter timeAdapter;

    private List<SectionData> list = new ArrayList<>();
    private List<ProjectTimeData> projectTimelist = new ArrayList<>();
    private ListView lv_data_list;
    private DbUser user;
    private List<DbSegments> segments;
    private WorkDataAdapter workDataAdapter;

    private int page = 1;
    private boolean isNextPageData = true;


    private List<PeriodListData.ListBean> periodListDatas = new ArrayList<>();


    private String sectionState = null; //工期状态(1.未审核，2待我审核，3已审核)
    private String segmentId = null; //标段Id
    private String num = null; //工期号

    public static MoneyFragment instance;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
        user = GreenDaoHelper.getInstance().getSession().getDbUserDao().queryBuilder().unique();
        segments = GreenDaoHelper.getInstance().getSession().getDbSegmentsDao().queryBuilder().list();
        instance = this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void initViews(View view) {
        navigationBar = (NavigationBar) view.findViewById(R.id.navigationBar);
        navigationBar.setTitle("工作");
        navigationBar.hideLeftViews();
//        navigationBar.clearRightViews();
//        rightView = View.inflate(mActivity, R.layout.work_menu, null);
//        ivRight = (ImageView) rightView.findViewById(R.id.iv_image);
//        tv_name = (TextView) rightView.findViewById(R.id.tv_name);
//        navigationBar.addRightView(rightView);
//        rightView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PopupWindowWork popupWindowWork = new PopupWindowWork(mActivity, navigationBar);
//                popupWindowWork.setOnClickItemListener(WorkFragment.this);
//                popupWindowWork.showing();
//            }
//        });


        lv_data_list = (ListView) view.findViewById(R.id.lv_data_list);


        View footerView = View.inflate(mActivity, R.layout.item_add_footer_view, null);
        tvFooter = (TextView) footerView.findViewById(R.id.tv_footer);
        lv_data_list.addFooterView(footerView,null,false);


        lv_data_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PeriodListData.ListBean period = periodListDatas.get(position);
                Intent intent = new Intent(mActivity, WaitAuditActivity.class);
                intent.putExtra("periodId", period.getId());
                Print.e("periodId",period.getId());
                startActivity(intent);
            }
        });

        gray_layout = view.findViewById(R.id.gray_layout);
        rl_state = (RelativeLayout) view.findViewById(R.id.rl_state);
        rl_time = (RelativeLayout) view.findViewById(R.id.rl_time);
        rl_section = (RelativeLayout) view.findViewById(R.id.rl_section);
        tv_state = (TextView) view.findViewById(R.id.tv_state);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        tv_section = (TextView) view.findViewById(R.id.tv_section);
        view_line_one = view.findViewById(R.id.view_line_one);
        view_line_two = view.findViewById(R.id.view_line_two);
        view_line_three = view.findViewById(R.id.view_line_three);

        rl_state.setOnClickListener(this);
        rl_time.setOnClickListener(this);
        rl_section.setOnClickListener(this);


        for (int i = 0; i < segments.size(); i++) {
            SectionData sectionData = new SectionData();
            sectionData.setName(segments.get(i).getTitle());
            sectionData.setId(segments.get(i).getId());
            sectionData.setSelect(false);
            list.add(sectionData);
        }

        for (int i = 0; i < user.getMaxPeroid(); i++) {
            ProjectTimeData projectTimeData = new ProjectTimeData();
            projectTimeData.setName(i + 1 + "期");
            projectTimeData.setSelect(false);
            projectTimelist.add(projectTimeData);
        }


        lv_data_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:// 停止的状态
                        int lastVisiblePosition = lv_data_list.getLastVisiblePosition();
                        if (null != periodListDatas) {
                            if (lastVisiblePosition == periodListDatas.size()) {

                                if (isNextPageData) {
                                    getDataFromServer(++page, segmentId, num, sectionState);
                                } else {
                                    tvFooter.setText("没有更多数据");
                                }


                            }
                        }


                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });


        getDataFromServer(1, null, null, null);
    }


    /***
     * peroid peroid
     * 标段Id segmentId
     * 工期id id
     * @param page
     */

    public void getDataFromServer(final int page, String segmentId, String num, String state) {


        HttpManager.getInstance().getHttpService().getWorkListData(user.getToken(), page, segmentId, num, state).enqueue(new HttpResultCallback<BaseResultData<PeriodListData>>() {
            @Override
            public void onSuc(Response<BaseResultData<PeriodListData>> response) {


                if (response.body().getContent().getPageNo() < response.body().getContent().getTotalPage()) {
                    isNextPageData = true;
                } else {
                    isNextPageData = false;
                }

                if (page == 1) {
                    periodListDatas = response.body().getContent().getList();
                        workDataAdapter = new WorkDataAdapter(periodListDatas);

                    Print.e(TAG+ "_____________________",periodListDatas.size());
                    lv_data_list.setAdapter(workDataAdapter);
                } else {
                    periodListDatas.addAll(response.body().getContent().getList());
                    workDataAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFail(String message) {
                mActivity.showToastMessage(message);
                Print.e(TAG, message);
            }
        });
    }

    @Override
    public void onClickMeasurePay() {
        if (ivRight.getVisibility() == View.VISIBLE) {
            ivRight.setVisibility(View.GONE);
        }
        tv_name.setText("计量支付");
    }

    @Override
    public void onClickProjectChange() {
        if (ivRight.getVisibility() == View.VISIBLE) {
            ivRight.setVisibility(View.GONE);
        }
        tv_name.setText("工程变更");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_state:
                requirmentSelectState();
                break;
            case R.id.rl_time:
                //选择标段
                requirmentSelectSection();
                break;
            case R.id.rl_section:
                //选择工期
                requirmentProjectTime();
                break;
            default:
                break;
        }

    }

    /***
     * 控制选择状态栏控件
     */
    private void requirmentSelectState() {

        LayoutInflater inflater = LayoutInflater.from(mActivity);
        LinearLayout rootLinearLayout = (LinearLayout) inflater.inflate(R.layout.popupwindow_more_white, null);
        popupWindow = new PopupWindow(rootLinearLayout, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        View view = View.inflate(mActivity, R.layout.item_popu_work_state, null);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        rootLinearLayout.addView(view);

        final TextView tv_wait = (TextView) view.findViewById(R.id.tv_wait);
        final TextView tv_waited = (TextView) view.findViewById(R.id.tv_waited);
        Button btn_repirt = (Button) view.findViewById(R.id.btn_repirt);
        Button btn_confirm = (Button) view.findViewById(R.id.btn_confirm);

        //初始化状态
        if (isClickConfirm_state) {
            if (confirm_state_after_isWait) {
                tv_wait.setBackground(getResources().getDrawable(R.drawable.shape_work_state));
            }

            if (confirm_state_after_isWaited) {
                tv_waited.setBackground(getResources().getDrawable(R.drawable.shape_work_state));
            }
        }

//        int contentHeight = ViewUtils.getViewMeasuredHeight(view);
//        fromYDelta = -contentHeight - 50;
        if (!popupWindow.isShowing()) {
            // 显示窗口
            popupWindow.showAsDropDown(rl_state);
            gray_layout.setVisibility(View.VISIBLE);
            tv_state.setBackground(getResources().getDrawable(R.drawable.gz_arrow02));
            view_line_one.setVisibility(View.INVISIBLE);

        }
//        popupWindow.getContentView().startAnimation(AnimationUtil.createInAnimation(mActivity, fromYDelta));
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                gray_layout.setVisibility(View.GONE);
                tv_state.setBackground(getResources().getDrawable(R.drawable.gz_arrow01));
                view_line_one.setVisibility(View.VISIBLE);

                //如果不是确认后隐藏的  保持上一次确认后的状态
                if (!isClickConfirm_state) {
                    isWait = confirm_state_after_isWait;
                    isWaited = confirm_state_after_isWaited;
                } else {
                    //是确认后隐藏的
                }
            }
        });


        //待我审核
        tv_wait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isWait) {
                    tv_wait.setBackgroundColor(getResources().getColor(R.color.colorWrite));
                    isWait = false;
                } else {
                    tv_wait.setBackground(getResources().getDrawable(R.drawable.shape_work_state));
                    isWait = true;
                }

                if (isWaited) {
                    tv_waited.setBackgroundColor(getResources().getColor(R.color.colorWrite));
                    isWaited = false;
                }
            }
        });
        //我已审核
        tv_waited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isWaited) {
                    tv_waited.setBackgroundColor(getResources().getColor(R.color.colorWrite));
                    isWaited = false;
                } else {
                    tv_waited.setBackground(getResources().getDrawable(R.drawable.shape_work_state));
                    isWaited = true;
                }

                if (isWait) {
                    tv_wait.setBackgroundColor(getResources().getColor(R.color.colorWrite));
                    isWait = false;
                }

            }
        });

        //点击重置
        btn_repirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isWait) {
                    tv_wait.setBackgroundColor(getResources().getColor(R.color.colorWrite));

                }
                if (isWaited) {
                    tv_waited.setBackgroundColor(getResources().getColor(R.color.colorWrite));
                }

                isWaited = false;
                isWait = false;
                confirm_state_after_isWait = false;
                confirm_state_after_isWaited = false;

                isClickConfirm_state = false;

                tv_state.setText("状态");
                sectionState = null;
                page = 1;
                periodListDatas.clear();
                tvFooter.setText("滑动加载数据");
                getDataFromServer(1, segmentId, num, sectionState);
                popupWindow.dismiss();
            }
        });

        //点击确定
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isClickConfirm_state = true;
                confirm_state_after_isWait = isWait;
                confirm_state_after_isWaited = isWaited;
                popupWindow.dismiss();

                String stateName = "";

                if (isWait) {
                    tv_state.setText("待我审核");
                    stateName += "待我审核";
                }
                if (isWaited) {
                    stateName += "已审核";
                }

                if (stateName.isEmpty()) {
                    tv_state.setText("状态");
                } else {
                    tv_state.setText(stateName);
                }


                String selectId = "";
                if (isWait) {
                    selectId = "1";
                }

                if (isWaited) {
                    selectId = "2";
                }
                sectionState = selectId;
                getDataFromServer(1, segmentId, num, sectionState);

                Print.e(TAG + "_sectionId", segmentId);
                Print.e(TAG + "num", num);
                Print.e(TAG + "sectionState", sectionState);

            }
        });

    }


    /***
     *
     * 控制选择标段栏控件
     */
    private void requirmentSelectSection() {
        ListView lv_listview;
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        LinearLayout rootLinearLayout = (LinearLayout) inflater.inflate(R.layout.popupwindow_more_white, null);
        popupWindow = new PopupWindow(rootLinearLayout, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        View view = View.inflate(mActivity, R.layout.item_popu_work_section, null);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        rootLinearLayout.addView(view);

        lv_listview = (ListView) view.findViewById(R.id.lv_listview);
        Button btn_repirt = (Button) view.findViewById(R.id.btn_repirt);
        Button btn_confirm = (Button) view.findViewById(R.id.btn_confirm);


//        int contentHeight = ViewUtils.getViewMeasuredHeight(view);
//        fromYDelta = -contentHeight - 50;
        if (!popupWindow.isShowing()) {
            // 显示窗口
            popupWindow.showAsDropDown(rl_state);
            gray_layout.setVisibility(View.VISIBLE);
            tv_section.setBackground(getResources().getDrawable(R.drawable.gz_arrow02));
            view_line_two.setVisibility(View.INVISIBLE);

        }
//        popupWindow.getContentView().startAnimation(AnimationUtil.createInAnimation(mActivity, fromYDelta));
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                gray_layout.setVisibility(View.GONE);
                tv_section.setBackground(getResources().getDrawable(R.drawable.gz_arrow01));
                view_line_two.setVisibility(View.VISIBLE);

                //如果不是确认后隐藏的  保持上一次确认后的状态
                if (!isClickConfirm_section) {
                    tv_section.setText("标段");

                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setSelect(false);
                    }

                    selectSection = "";

                    if (mAdapter != null) {
                        mAdapter.notifyDataSetChanged();
                    }
                } else {
                    //是确认后隐藏的
                }


            }
        });

        mAdapter = new SectionAdapter();
        lv_listview.setAdapter(mAdapter);


        //点击重置
        btn_repirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_section.setText("标段");

                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isSelect()) {
                        list.get(i).setSelect(false);
                    }
                }

                selectSection = "";
                sectionId = 0L;

                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }

                isClickConfirm_section = false;
                segmentId = "";
                page = 1;

                periodListDatas.clear();
                tvFooter.setText("滑动加载数据");
                getDataFromServer(1, segmentId, num, sectionState);
                popupWindow.dismiss();
            }
        });

        //点击确定
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isClickConfirm_section = true;
                popupWindow.dismiss();

                if (selectSection.isEmpty()) {
                    tv_section.setText("标段");
                } else {
                    tv_section.setText(selectSection);
                }

                segmentId = String.valueOf(sectionId);
                getDataFromServer(1, segmentId, num, sectionState);
                Print.e(TAG + "_sectionId", segmentId);
                Print.e(TAG + "num", num);
                Print.e(TAG + "sectionState", sectionState);
            }
        });

    }


    private class SectionAdapter extends BaseAdapter {

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
            return position;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getView(position, convertView, parent);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = View.inflate(mActivity, R.layout.item_section_seclect, null);
            final Button tv_text1 = (Button) view.findViewById(R.id.tv_text1);

            final SectionData sectionData = list.get(position);
            tv_text1.setText(sectionData.getName());

            if (sectionData.isSelect()) {
                tv_text1.setSelected(true);
            } else {
                tv_text1.setSelected(false);
            }

            tv_text1.setOnClickListener(new WorkSectionClickListener(sectionData, tv_text1, position));

            return view;
        }


    }


    private class WorkSectionClickListener implements View.OnClickListener {
        SectionData sectionData;
        TextView tv_text1;
        int position;

        public WorkSectionClickListener(SectionData _sectionData, TextView textView, int _position) {
            sectionData = _sectionData;
            tv_text1 = textView;
            position = _position;
        }

        @Override
        public void onClick(View v) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isSelect() && i != position) {
                    list.get(i).setSelect(false);
                }
            }

            if (sectionData.isSelect()) {
                tv_text1.setSelected(false);
                sectionData.setSelect(false);
                selectSection = "";
            } else {
                tv_text1.setSelected(true);
                sectionData.setSelect(true);
                selectSection = String.valueOf(sectionData.getName());
                sectionId = sectionData.getId();

            }

            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }
        }
    }


    /***
     *
     * 控制选择工期栏控件
     */
    private void requirmentProjectTime() {
        ListView lv_listview;
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        LinearLayout rootLinearLayout = (LinearLayout) inflater.inflate(R.layout.popupwindow_more_white, null);
        popupWindow = new PopupWindow(rootLinearLayout, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        View view = View.inflate(mActivity, R.layout.item_popu_work_section, null);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        rootLinearLayout.addView(view);

        lv_listview = (ListView) view.findViewById(R.id.lv_listview);
        Button btn_repirt = (Button) view.findViewById(R.id.btn_repirt);
        Button btn_confirm = (Button) view.findViewById(R.id.btn_confirm);


//        int contentHeight = ViewUtils.getViewMeasuredHeight(view);
//        fromYDelta = -contentHeight - 50;
        if (!popupWindow.isShowing()) {
            // 显示窗口
            popupWindow.showAsDropDown(rl_state);
            gray_layout.setVisibility(View.VISIBLE);
            tv_time.setBackground(getResources().getDrawable(R.drawable.gz_arrow02));
            view_line_three.setVisibility(View.INVISIBLE);

        }
//        popupWindow.getContentView().startAnimation(AnimationUtil.createInAnimation(mActivity, fromYDelta));
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                gray_layout.setVisibility(View.GONE);
                tv_time.setBackground(getResources().getDrawable(R.drawable.gz_arrow01));
                view_line_three.setVisibility(View.VISIBLE);

                //如果不是确认后隐藏的  保持上一次确认后的状态
                if (!isClickConfirm_project) {
                    tv_time.setText("工期");

                    for (int i = 0; i < projectTimelist.size(); i++) {
                        projectTimelist.get(i).setSelect(false);
                    }

                    selectProject = "";

                    if (timeAdapter != null) {
                        timeAdapter.notifyDataSetChanged();
                    }
                } else {
                    //是确认后隐藏的
                    if (timeAdapter != null) {
                        timeAdapter.notifyDataSetChanged();
                    }
                }


            }
        });

        timeAdapter = new ProjectTimeAdapter();
        lv_listview.setAdapter(timeAdapter);


        //点击重置
        btn_repirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_time.setText("工期");

                for (int i = 0; i < projectTimelist.size(); i++) {
                    if (projectTimelist.get(i).isSelect()) {
                        projectTimelist.get(i).setSelect(false);
                    }
                }

                selectProject = "";

//                if (mAdapter != null) {
                    timeAdapter.notifyDataSetChanged();
//                }

                isClickConfirm_project = false;
                num = "";
                page = 1;

                periodListDatas.clear();
                tvFooter.setText("滑动加载数据");
                getDataFromServer(1, segmentId, num, sectionState);
                popupWindow.dismiss();
            }
        });

        //点击确定
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isClickConfirm_project = true;
                popupWindow.dismiss();

                if (selectProject.isEmpty()) {
                    tv_time.setText("工期");
                } else {
                    tv_time.setText(selectProject);
                }

                Print.e("sss", selectProject);
                if(selectProject.isEmpty()){
                    num = "";
                }else{
                    num = selectProject.substring(0, 1);
                }

                getDataFromServer(1, segmentId, num, sectionState);
            }
        });

    }


    private class ProjectTimeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return projectTimelist.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getView(position, convertView, parent);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = View.inflate(mActivity, R.layout.item_section_seclect, null);
            final Button tv_text1 = (Button) view.findViewById(R.id.tv_text1);

            final ProjectTimeData projectTimeData = projectTimelist.get(position);
            tv_text1.setText(projectTimeData.getName());

            if (projectTimeData.isSelect()) {
                tv_text1.setSelected(true);
            } else {
                tv_text1.setSelected(false);
            }

            tv_text1.setOnClickListener(new ProjectTimeClickListener(projectTimeData, tv_text1, position));

            return view;
        }


    }


    private class ProjectTimeClickListener implements View.OnClickListener {
        ProjectTimeData projectTimeData;
        TextView tv_text1;
        int position;

        public ProjectTimeClickListener(ProjectTimeData _projectTimeData, TextView textView, int _position) {
            projectTimeData = _projectTimeData;
            tv_text1 = textView;
            position = _position;
        }

        @Override
        public void onClick(View v) {
            for (int i = 0; i < projectTimelist.size(); i++) {
                if (projectTimelist.get(i).isSelect() && i != position) {
                    projectTimelist.get(i).setSelect(false);
                }
            }

            if (projectTimeData.isSelect()) {
                tv_text1.setSelected(false);
                projectTimeData.setSelect(false);
                selectProject = "";
            } else {
                tv_text1.setSelected(true);
                projectTimeData.setSelect(true);
                selectProject = String.valueOf(projectTimeData.getName());
                Print.e("sss", selectSection);
            }

            if (mAdapter != null) {
                timeAdapter.notifyDataSetChanged();
            }
        }
    }


    private class WorkDataAdapter extends BaseAdapter {
        private List<PeriodListData.ListBean> periodListDatas ;

        public WorkDataAdapter(List<PeriodListData.ListBean> _periodListDatas){
            periodListDatas = _periodListDatas;
        }
        @Override
        public int getCount() {
            return periodListDatas.size();
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
            ViewHolder holder;
            if (null == convertView) {
                convertView = View.inflate(mActivity, R.layout.item_work_data, null);
                holder = new ViewHolder();
                holder.rlNumber = (RelativeLayout) convertView.findViewById(R.id.rl_number);
                holder.tv_title = (TextView) convertView.findViewById(tv_title);
                holder.tv_state = (TextView) convertView.findViewById(R.id.tv_state);
                holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
                holder.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_number.setText(position + 1 + "");
            holder.tv_title.setText(periodListDatas.get(position).getTitle());
            if (periodListDatas.get(position).getSignState().equals("0")) {
                holder.tv_state.setText("未审核");
                holder.tv_state.setTextColor(getResources().getColor(R.color.workState));
                holder.rlNumber.setBackground(getResources().getDrawable(R.drawable.shape_work_number_two));
            } else if (periodListDatas.get(position).getSignState().equals("1")) {
                holder.tv_state.setText("待我审核");
                holder.tv_state.setTextColor(getResources().getColor(R.color.colorTitle));
                holder.rlNumber.setBackground(getResources().getDrawable(R.drawable.shape_work_number_three));
            } else if (periodListDatas.get(position).getSignState().equals("2")) {
                holder.tv_state.setText("已审核");
                holder.tv_state.setTextColor(getResources().getColor(R.color.workStateRight));
                holder.rlNumber.setBackground(getResources().getDrawable(R.drawable.shape_work_number_four));
            }else if (periodListDatas.get(position).getSignState().equals("-1")) {
                holder.tv_state.setText("待审核");
                holder.tv_state.setTextColor(getResources().getColor(R.color.workState));
                holder.rlNumber.setBackground(getResources().getDrawable(R.drawable.shape_work_number_two));
            }else {
                holder.tv_state.setText("未审核");
                holder.tv_state.setTextColor(getResources().getColor(R.color.workState));
                holder.rlNumber.setBackground(getResources().getDrawable(R.drawable.shape_work_number_two));
            }

            holder.tv_time.setText(periodListDatas.get(position).getStartTime().substring(0, 10));


            return convertView;
        }
    }


    class ViewHolder {
        RelativeLayout rlNumber;
        TextView tv_title;
        TextView tv_state;
        TextView tv_time;
        TextView tv_number;
    }

}
