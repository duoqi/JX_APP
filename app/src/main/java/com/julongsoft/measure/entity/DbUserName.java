package com.julongsoft.measure.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by tao on 2017/9/29.
 */

@Entity
public class DbUserName {

    @Id
    private String username;

    @Generated(hash = 654711550)
    public DbUserName(String username) {
        this.username = username;
    }

    @Generated(hash = 1900517807)
    public DbUserName() {
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
