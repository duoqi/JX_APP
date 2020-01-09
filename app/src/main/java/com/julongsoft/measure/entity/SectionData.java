package com.julongsoft.measure.entity;

/**
 * Created by tao on 2017/7/31.
 */

public class SectionData {

    private Long id;
    private String name;
    private boolean isSelect;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
