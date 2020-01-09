package com.julongsoft.measure.view;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.julongsoft.measure.R;


/**
 * Created by Tao on 2016/6/3.
 */
public class PopupWindowWork {


    public interface OnClickItemListener {
        void onClickMeasurePay();

        void onClickProjectChange();
    }


    private OnClickItemListener _onClickItemListener;
    private static final int MENU_ADD_CHECK = 0;
    private static final int MENU_ADD_OVERSEE = 1;
    private String[] subMenus;
    private View showView;
    private final PopupWindow pop;
    private Activity context;

    private int[] img = {R.drawable.gz_jlzf_icon, R.drawable.gz_gcgl_icon};

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        _onClickItemListener = onClickItemListener;
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {

        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        context.getWindow().setAttributes(lp);
    }


    public PopupWindowWork(final Activity context, View showView) {
        this.showView = showView;
        this.context = context;

        subMenus = context.getResources().getStringArray(R.array.check_menu);
        LayoutInflater inflater = LayoutInflater.from(context);
        // 引入窗口配置文件
        LinearLayout rootLinearLayout = (LinearLayout) inflater.inflate(R.layout.popupwindow_more, null);
        // 创建PopupWindow对象
        pop = new PopupWindow(rootLinearLayout, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        pop.setBackgroundDrawable(new BitmapDrawable());
        // 设置点击窗口外边窗口消失
        pop.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        pop.setFocusable(true);
        pop.setAnimationStyle(R.style.popupAnimation);
        pop.setTouchable(true);
        //初始化popupwindow的item
        initItem(subMenus, rootLinearLayout);

        pop.setOnDismissListener(new poponDismissListener());

    }


    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1.0f);
        }

    }

    private void initItem(String[] text, LinearLayout rootView) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < text.length; i++) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.item_popupwindow_menu, null);
            rootView.addView(itemView, params);
            itemView.setId(i);
            ImageView iconImageView = (ImageView) itemView.findViewById(R.id.iv_icon);
            iconImageView.setImageResource(img[i]);
            TextView subMenuTextView = (TextView) itemView.findViewById(R.id.tv_submenu);
            subMenuTextView.setText(text[i]);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case MENU_ADD_CHECK:
                            _onClickItemListener.onClickMeasurePay();
                            break;
                        case MENU_ADD_OVERSEE:
                            _onClickItemListener.onClickProjectChange();
                            break;
                        default:
                            break;
                    }
                    pop.dismiss();
                }
            });
        }
    }

    public void showing() {
        if (!pop.isShowing()) {
            // 显示窗口
            backgroundAlpha(0.5f);
            pop.showAtLocation(showView, Gravity.TOP | Gravity.RIGHT, 5,
                    showView.getHeight() + getStatusBarHeight());
        }
    }

    public boolean isShowing() {
        return pop.isShowing();
    }

    public void hide() {
        if (pop.isShowing()) {
            pop.dismiss();
        }
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}

