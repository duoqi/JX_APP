package com.julongsoft.measure.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by tao on 2017/8/16.
 */
@Entity
public class DbSegments {
    @Id
    public Long id;
    public String title ;
    @Generated(hash = 479455204)
    public DbSegments(Long id, String title) {
        this.id = id;
        this.title = title;
    }
    @Generated(hash = 88758448)
    public DbSegments() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
   

}
