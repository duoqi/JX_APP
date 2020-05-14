package com.julongsoft.measure.entity;

import java.util.List;

/**
 * Created by tao on 2017/8/16.
 */

public class PeriodListData {

    /**
     * pageNo : 0
     * pageSize : 0
     * count : 0
     * list : [{"id":0,"code":"string","title":"string","num":0}]
     * totalPage : 0
     */

    private int pageNo;
    private int pageSize;
    private int count;
    private int totalPage;
    private List<ListBean> list;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 0
         * code : string
         * title : string
         * num : 0
         */

        private int id;
        private String code;
        private String title;
        private int num;
        private String startTime;
        private String state;
        private int signState;

        public int getSignState() {
            return signState;
        }

        public void setSignState(int signState) {
            this.signState = signState;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
