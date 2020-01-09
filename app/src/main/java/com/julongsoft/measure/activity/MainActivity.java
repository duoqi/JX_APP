package com.julongsoft.measure.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;

import com.julongsoft.measure.R;
import com.julongsoft.measure.fragment.AboutFragment;
import com.julongsoft.measure.fragment.BaseFragment;
import com.julongsoft.measure.fragment.HomeFragment;
import com.julongsoft.measure.fragment.WorkFragment;
import com.julongsoft.measure.view.TabGroupLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {


    private ArrayList<BaseFragment> _fragmentList;
    private TabGroupLayout _tabGroupLayout;
    private ViewPager _viewPager;
    private View _vRedPoint;
    private static final String TAG = MainActivity.class.getSimpleName();

    public TabGroupLayout getTabGroupLayout() {
        return _tabGroupLayout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void initViews() {

        _fragmentList = new ArrayList<>();
        HomeFragment homeFragment = new HomeFragment();
        _fragmentList.add(homeFragment);
        WorkFragment waitFragment = new WorkFragment();
        _fragmentList.add(waitFragment);
        AboutFragment aboutFragment = new AboutFragment();
        _fragmentList.add(aboutFragment);
        _viewPager = (ViewPager) findViewById(R.id.viewpager);
        //设置缓存view 的个数（实际有3个，缓存2个+正在显示的1个）
        _viewPager.setOffscreenPageLimit(2);

        _vRedPoint = findViewById(R.id.v_red_wait_point);

        //给ViewPager设置适配器
        _viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), _fragmentList));
//        _viewPager.setCurrentItem(0);//设置当前显示标签页为第一页
        _viewPager.addOnPageChangeListener(new MyOnPageChangeListener());//页面变化时的监听器
        _tabGroupLayout = (TabGroupLayout) findViewById(R.id.main_tabs_layout);
        _tabGroupLayout.setOnClickListener(this);

        _tabGroupLayout.select(0);

    }

    public enum TabTag {
        Home, backlog, about
    }

    public void setCurTab(TabTag tag) {
        int index = tag.ordinal();
        setCurTab(index);
        _viewPager.setCurrentItem(index, true);//true 表示有滑动的效果
    }

    private void setCurTab(int index) {
        _tabGroupLayout.select(index);
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setCurTab(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        ArrayList<BaseFragment> list;

        public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<BaseFragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Fragment getItem(int arg0) {
            return list.get(arg0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_tab1_layout:
                setCurTab(TabTag.Home);
                _fragmentList.get(0).onSelected();
                break;
            case R.id.main_tab2_layout:
                setCurTab(TabTag.backlog);
                _fragmentList.get(1).onSelected();
                break;
            case R.id.main_tab3_layout:
                setCurTab(TabTag.about);
                _fragmentList.get(2).onSelected();
                break;
            default:
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (null != fragments) {
            for (Fragment fragment : fragments) {

                if (null == fragment) continue;
                if (!fragment.isAdded()) continue;


                if (!fragment.isHidden() && fragment instanceof BaseFragment) {
                    BaseFragment baseFragment = (BaseFragment) fragment;
                    boolean result = baseFragment.onKeyDown(keyCode, event);
                    if (result) return true;
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
