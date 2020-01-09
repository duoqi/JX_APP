package com.julongsoft.measure.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tao on 2017/8/17.
 */

public class SectionDetial implements Serializable{


    /**
     * id : 2926
     * segmentId : 158
     * num : 1
     * prdPay : 87812159
     * lstPrdPaySum : 0
     * thisPrdPaySum : 87812159
     * state : 1
     * code : YCZX-ZT2-1708-01
     * title : ZT2标第1期
     * numCode : 01
     * sgn : 106123
     * pactSumTotal : 840723886
     * contractor : 贵州桥梁建设集团有限责任公司
     * signData : [{"id":434568,"userId":0,"state":1,"sn":1,"roleId":92,"roleName":"财务科科长","myState":1,"myStateStr":"待我审核","time":null},{"id":434566,"userId":151,"state":2,"sn":1,"roleId":91,"roleName":"计划科科长","myState":2,"myStateStr":"已审核","time":"2017-08-31 14:33:19"},{"id":434567,"userId":149,"state":2,"sn":1,"roleId":80,"roleName":"工程科科长","myState":2,"myStateStr":"已审核","time":"2017-08-31 14:34:09"},{"id":434569,"userId":0,"state":0,"sn":2,"roleId":82,"roleName":"主管工程副处长","myState":0,"myStateStr":"未审核","time":null},{"id":434570,"userId":0,"state":0,"sn":2,"roleId":83,"roleName":"主管计划合同副处长","myState":0,"myStateStr":"未审核","time":null},{"id":434571,"userId":0,"state":0,"sn":3,"roleId":84,"roleName":"处长","myState":0,"myStateStr":"未审核","time":null}]
     * appSumChpt : [{"id":165611,"periodId":2926,"sn":1,"num":100,"title":"总则","type":"","drwIvst":2.990986241E7,"altIvst":0,"finalIvst":2.990986241E7,"prdIvst":2488633},{"id":165612,"periodId":2926,"sn":23,"num":200,"title":"路基","type":"","drwIvst":140006493,"altIvst":0,"finalIvst":140006493,"prdIvst":0},{"id":165613,"periodId":2926,"sn":124,"num":300,"title":"路面","type":"","drwIvst":70590441,"altIvst":0,"finalIvst":70590441,"prdIvst":0},{"id":165614,"periodId":2926,"sn":181,"num":400,"title":"桥梁、涵洞","type":"","drwIvst":89166815,"altIvst":0,"finalIvst":89166815,"prdIvst":0},{"id":165615,"periodId":2926,"sn":279,"num":500,"title":"隧道","type":"","drwIvst":481536959,"altIvst":0,"finalIvst":481536959,"prdIvst":0},{"id":165616,"periodId":2926,"sn":437,"num":600,"title":"安全设施及预埋管线","type":"","drwIvst":4424693,"altIvst":0,"finalIvst":4424693,"prdIvst":0}]
     * otherSumChpt : [{"id":540319,"periodId":2926,"sn":1,"num":1,"title":"清单合计","type":"","drwIvst":8.1563526341E8,"altIvst":0,"finalIvst":0,"prdIvst":2488633},{"id":540320,"periodId":2926,"sn":10,"num":10,"title":"计日工合计","type":"","drwIvst":0,"altIvst":0,"finalIvst":619564,"prdIvst":0},{"id":540321,"periodId":2926,"sn":20,"num":20,"title":"暂列金额","type":"","drwIvst":0,"altIvst":0,"finalIvst":24469058,"prdIvst":0},{"id":540322,"periodId":2926,"sn":30,"num":30,"title":"价格调整","type":"","drwIvst":0,"altIvst":0,"finalIvst":0,"prdIvst":0},{"id":540323,"periodId":2926,"sn":40,"num":40,"title":"回扣甲供材料款","type":"","drwIvst":0,"altIvst":0,"finalIvst":0,"prdIvst":0},{"id":540324,"periodId":2926,"sn":50,"num":50,"title":"索赔金额","type":"","drwIvst":0,"altIvst":0,"finalIvst":0,"prdIvst":0},{"id":540325,"periodId":2926,"sn":55,"num":55,"title":"合计","type":"","drwIvst":8.1563526341E8,"altIvst":0,"finalIvst":0,"prdIvst":2488633},{"id":540326,"periodId":2926,"sn":60,"num":60,"title":"开工预付款","type":"","drwIvst":0,"altIvst":0,"finalIvst":0,"prdIvst":84072389},{"id":540327,"periodId":2926,"sn":70,"num":70,"title":"回扣开工预付款","type":"","drwIvst":0,"altIvst":0,"finalIvst":0,"prdIvst":0},{"id":540328,"periodId":2926,"sn":80,"num":80,"title":"材料设备预付款","type":"","drwIvst":0,"altIvst":0,"finalIvst":0,"prdIvst":1500000},{"id":540329,"periodId":2926,"sn":90,"num":90,"title":"回扣材料设备预付款","type":"","drwIvst":0,"altIvst":0,"finalIvst":0,"prdIvst":0},{"id":540330,"periodId":2926,"sn":100,"num":100,"title":"保留金","type":"","drwIvst":0,"altIvst":0,"finalIvst":0,"prdIvst":-248863},{"id":540331,"periodId":2926,"sn":110,"num":110,"title":"农民工工资保障金","type":"","drwIvst":0,"altIvst":0,"finalIvst":0,"prdIvst":0},{"id":540332,"periodId":2926,"sn":120,"num":120,"title":"返还农民工工资保障金","type":"","drwIvst":0,"altIvst":0,"finalIvst":0,"prdIvst":0},{"id":540333,"periodId":2926,"sn":130,"num":130,"title":"违约金","type":"","drwIvst":0,"altIvst":0,"finalIvst":0,"prdIvst":0},{"id":540334,"periodId":2926,"sn":170,"num":170,"title":"应付款总额","type":"","drwIvst":8.1563526341E8,"altIvst":0,"finalIvst":0,"prdIvst":87812159}]
     */

    private int id;
    private int segmentId;
    private int num;
    private String prdPay;
    private int lstPrdPaySum;
    private String thisPrdPaySum;
    private String state;
    private String code;
    private String title;
    private String numCode;
    private int sgn;
    private int pactSumTotal;
    private String contractor;
    private List<SignDataBean> signData;
    private List<AppSumChptBean> appSumChpt;
    private List<OtherSumChptBean> otherSumChpt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(int segmentId) {
        this.segmentId = segmentId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getPrdPay() {
        return prdPay;
    }

    public void setPrdPay(String prdPay) {
        this.prdPay = prdPay;
    }

    public int getLstPrdPaySum() {
        return lstPrdPaySum;
    }

    public void setLstPrdPaySum(int lstPrdPaySum) {
        this.lstPrdPaySum = lstPrdPaySum;
    }

    public String getThisPrdPaySum() {
        return thisPrdPaySum;
    }

    public void setThisPrdPaySum(String thisPrdPaySum) {
        this.thisPrdPaySum = thisPrdPaySum;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getNumCode() {
        return numCode;
    }

    public void setNumCode(String numCode) {
        this.numCode = numCode;
    }

    public int getSgn() {
        return sgn;
    }

    public void setSgn(int sgn) {
        this.sgn = sgn;
    }

    public int getPactSumTotal() {
        return pactSumTotal;
    }

    public void setPactSumTotal(int pactSumTotal) {
        this.pactSumTotal = pactSumTotal;
    }

    public String getContractor() {
        return contractor;
    }

    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    public List<SignDataBean> getSignData() {
        return signData;
    }

    public void setSignData(List<SignDataBean> signData) {
        this.signData = signData;
    }

    public List<AppSumChptBean> getAppSumChpt() {
        return appSumChpt;
    }

    public void setAppSumChpt(List<AppSumChptBean> appSumChpt) {
        this.appSumChpt = appSumChpt;
    }

    public List<OtherSumChptBean> getOtherSumChpt() {
        return otherSumChpt;
    }

    public void setOtherSumChpt(List<OtherSumChptBean> otherSumChpt) {
        this.otherSumChpt = otherSumChpt;
    }

    public static class SignDataBean implements Serializable{
        /**
         * id : 434568
         * userId : 0
         * state : 1
         * sn : 1
         * roleId : 92
         * roleName : 财务科科长
         * myState : 1
         * myStateStr : 待我审核
         * time : null
         */

        private int id;
        private int userId;
        private int state;
        private int sn;
        private int roleId;
        private String roleName;
        private int myState;
        private String myStateStr;
        private String time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getSn() {
            return sn;
        }

        public void setSn(int sn) {
            this.sn = sn;
        }

        public int getRoleId() {
            return roleId;
        }

        public void setRoleId(int roleId) {
            this.roleId = roleId;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public int getMyState() {
            return myState;
        }

        public void setMyState(int myState) {
            this.myState = myState;
        }

        public String getMyStateStr() {
            return myStateStr;
        }

        public void setMyStateStr(String myStateStr) {
            this.myStateStr = myStateStr;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

    public static class AppSumChptBean implements Serializable{
        /**
         * id : 165611
         * periodId : 2926
         * sn : 1
         * num : 100
         * title : 总则
         * type :
         * drwIvst : 2.990986241E7
         * altIvst : 0
         * finalIvst : 2.990986241E7
         * prdIvst : 2488633
         */

        private int id;
        private int periodId;
        private int sn;
        private int num;
        private String title;
        private String type;
        private double drwIvst;
        private float altIvst;
        private double finalIvst;
        private float prdIvst;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPeriodId() {
            return periodId;
        }

        public void setPeriodId(int periodId) {
            this.periodId = periodId;
        }

        public int getSn() {
            return sn;
        }

        public void setSn(int sn) {
            this.sn = sn;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public double getDrwIvst() {
            return drwIvst;
        }

        public void setDrwIvst(double drwIvst) {
            this.drwIvst = drwIvst;
        }

        public float getAltIvst() {
            return altIvst;
        }

        public void setAltIvst(float altIvst) {
            this.altIvst = altIvst;
        }

        public double getFinalIvst() {
            return finalIvst;
        }

        public void setFinalIvst(double finalIvst) {
            this.finalIvst = finalIvst;
        }

        public float getPrdIvst() {
            return prdIvst;
        }

        public void setPrdIvst(float prdIvst) {
            this.prdIvst = prdIvst;
        }
    }

    public static class OtherSumChptBean implements Serializable{
        /**
         * id : 540319
         * periodId : 2926
         * sn : 1
         * num : 1
         * title : 清单合计
         * type :
         * drwIvst : 8.1563526341E8
         * altIvst : 0
         * finalIvst : 0
         * prdIvst : 2488633
         */

        private int id;
        private int periodId;
        private int sn;
        private int num;
        private String title;
        private String type;
        private double drwIvst;
        private int altIvst;
        private int finalIvst;
        private float prdIvst;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPeriodId() {
            return periodId;
        }

        public void setPeriodId(int periodId) {
            this.periodId = periodId;
        }

        public int getSn() {
            return sn;
        }

        public void setSn(int sn) {
            this.sn = sn;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public double getDrwIvst() {
            return drwIvst;
        }

        public void setDrwIvst(double drwIvst) {
            this.drwIvst = drwIvst;
        }

        public int getAltIvst() {
            return altIvst;
        }

        public void setAltIvst(int altIvst) {
            this.altIvst = altIvst;
        }

        public int getFinalIvst() {
            return finalIvst;
        }

        public void setFinalIvst(int finalIvst) {
            this.finalIvst = finalIvst;
        }

        public float getPrdIvst() {
            return prdIvst;
        }

        public void setPrdIvst(float prdIvst) {
            this.prdIvst = prdIvst;
        }
    }
}
