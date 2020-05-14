package com.julongsoft.measure.entity;

public class MoneyDetialData {

    /**
     * contractor : 中交第三公路工程局有限公司
     * approval_ivst : 0
     * plan_ivst : 1
     * period_pay_money : 1
     */

    private String contractor;
    private int approval_ivst;
    private int plan_ivst;
    private int period_pay_money;
    private int total_ivst;

    public String getContractor() {
        return contractor;
    }

    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    public int getApproval_ivst() {
        return approval_ivst;
    }

    public void setApproval_ivst(int approval_ivst) {
        this.approval_ivst = approval_ivst;
    }

    public int getPlan_ivst() {
        return plan_ivst;
    }

    public void setPlan_ivst(int plan_ivst) {
        this.plan_ivst = plan_ivst;
    }

    public int getPeriod_pay_money() {
        return period_pay_money;
    }

    public void setPeriod_pay_money(int period_pay_money) {
        this.period_pay_money = period_pay_money;
    }

    public int getTotal_ivst() {
        return total_ivst;
    }

    public void setTotal_ivst(int total_ivst) {
        this.total_ivst = total_ivst;
    }
}
