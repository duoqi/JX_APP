package com.julongsoft.measure.db;

import android.database.sqlite.SQLiteDatabase;

import com.julongsoft.measure.entity.DbSegments;
import com.julongsoft.measure.entity.DbUser;
import com.julongsoft.measure.entity.UserData;
import com.julongsoft.measure.global.GlobalApplication;

/**
 * Created by duoqi.tao on 2017/7/11.
 */

public class GreenDaoHelper {

    private volatile static GreenDaoHelper instance;
    private DaoSession daoSession;

    public synchronized static GreenDaoHelper getInstance() {
        if (instance == null) {
            synchronized (GreenDaoHelper.class) {
                if (instance == null) {
                    instance = new GreenDaoHelper();
                }
            }
        }

        return instance;
    }

    private GreenDaoHelper() {

        if (null == instance) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(GlobalApplication.getContext(), "measure.db", null);
            SQLiteDatabase db = helper.getWritableDatabase();
            DaoMaster mDaoMaster = new DaoMaster(db);
            daoSession = mDaoMaster.newSession();

        }

    }

    public DaoSession getSession() {
        return daoSession;
    }


    public void clearAllData() {

        daoSession.deleteAll(DbUser.class);
        daoSession.deleteAll(DbSegments.class);

        daoSession.clear();

    }
}
