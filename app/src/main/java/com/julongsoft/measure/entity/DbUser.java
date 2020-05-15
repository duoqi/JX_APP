package com.julongsoft.measure.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by tao on 2017/8/16.
 */

@Entity
public class DbUser {

//"token": "string",
//        "deadline": "2017-08-16T02:45:41.853Z",
//        "id": 0,
//        "code": "string",
//        "name": "string",
//        "password": "string",
//        "isDeleted": 0,
//        "notes": "string",
//        "tel": "string",
//        "orgId": 0,
//        "orgName": "string",
//        "segments": [
//    {
//        "id": 0,
//            "title": "string"
//    }
//    ],
//            "maxPeroid": 0

    public String token;
    public String deadline;
    @Id
    public Long id;
    public String code;
    public String name;
    public String password;
    public Integer isDeleted;
    public String notes;
    public String tel;
    public Integer orgId;
    public String orgName;
    public Integer maxPeroid;
    public String roles;
    @Generated(hash = 710968092)
    public DbUser(String token, String deadline, Long id, String code, String name,
            String password, Integer isDeleted, String notes, String tel,
            Integer orgId, String orgName, Integer maxPeroid, String roles) {
        this.token = token;
        this.deadline = deadline;
        this.id = id;
        this.code = code;
        this.name = name;
        this.password = password;
        this.isDeleted = isDeleted;
        this.notes = notes;
        this.tel = tel;
        this.orgId = orgId;
        this.orgName = orgName;
        this.maxPeroid = maxPeroid;
        this.roles = roles;
    }
    @Generated(hash = 762027100)
    public DbUser() {
    }
    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getDeadline() {
        return this.deadline;
    }
    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Integer getIsDeleted() {
        return this.isDeleted;
    }
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
    public String getNotes() {
        return this.notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public String getTel() {
        return this.tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public Integer getOrgId() {
        return this.orgId;
    }
    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }
    public String getOrgName() {
        return this.orgName;
    }
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    public Integer getMaxPeroid() {
        return this.maxPeroid;
    }
    public void setMaxPeroid(Integer maxPeroid) {
        this.maxPeroid = maxPeroid;
    }
    public String getRoles() {
        return this.roles;
    }
    public void setRoles(String roles) {
        this.roles = roles;
    }



}
