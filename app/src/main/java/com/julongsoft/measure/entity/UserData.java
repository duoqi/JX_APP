package com.julongsoft.measure.entity;

import java.util.List;

/**
 * Created by duoqi.tao on 2017/7/11.
 */

public class UserData {


    /**
     * token : string
     * deadline : 2017-08-16T02:45:41.853Z
     * id : 0
     * code : string
     * name : string
     * password : string
     * isDeleted : 0
     * notes : string
     * tel : string
     * orgId : 0
     * orgName : string
     * segments : [{"id":0,"title":"string"}]
     * maxPeroid : 0
     */

    private String token;
    private String deadline;
    private int id;
    private String code;
    private String name;
    private String password;
    private int isDeleted;
    private String notes;
    private String tel;
    private int orgId;
    private String orgName;
    private int maxPeroid;
    private List<SegmentsBean> segments;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public int getMaxPeroid() {
        return maxPeroid;
    }

    public void setMaxPeroid(int maxPeroid) {
        this.maxPeroid = maxPeroid;
    }

    public List<SegmentsBean> getSegments() {
        return segments;
    }

    public void setSegments(List<SegmentsBean> segments) {
        this.segments = segments;
    }

    public static class SegmentsBean {
        /**
         * id : 0
         * title : string
         */

        private int id;
        private String title;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
