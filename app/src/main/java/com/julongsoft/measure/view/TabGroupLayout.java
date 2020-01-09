package com.julongsoft.measure.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by tao on 2017/7/24.
 */

public class TabGroupLayout extends LinearLayout {

    public static final String TAG = TabGroupLayout.class.getSimpleName();

    public TabGroupLayout(Context context)
    {
        super(context);
    }

    public TabGroupLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void select(int index)
    {

        int count = getChildCount();
        for (int i = 0; i < count; i++)
        {
            View v = getChildAt(i);
            if (i == index)
            {
                v.setSelected(true);
            }
            else
            {
                v.setSelected(false);
            }
        }
    }

    @Override
    public void setOnClickListener(OnClickListener l)
    {
        int count = getChildCount();
        for (int i = 0; i < count; i++)
        {
            View v = getChildAt(i);
            v.setOnClickListener(l);
        }
    }
}
