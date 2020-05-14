package com.julongsoft.measure.entity;

public class MoneyData {

    /**
     * note : cfsum5205
     * createtime : 2019-11-18
     * data_id : 557161
     * user_ids : ,3003,3004,
     * step : 1
     * id : 208820
     * state : 1
     * title : CS1--2019年11月1期工程财务资金使用计划汇总表签字
     * type : 0
     * url : builtfunds/sum.do?method=getList&sgm_auto=1390&pid=157
     */

    private String note;
    private String createtime;
    private int data_id;
    private String user_ids;
    private int step;
    private int id;
    private int state;
    private String title;
    private int type;
    private String url;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public int getData_id() {
        return data_id;
    }

    public void setData_id(int data_id) {
        this.data_id = data_id;
    }

    public String getUser_ids() {
        return user_ids;
    }

    public void setUser_ids(String user_ids) {
        this.user_ids = user_ids;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
