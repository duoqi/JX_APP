package com.julongsoft.measure.http;

/**
 * Created by duoqi.tao on 2017/guide3/30.
 */

public class BaseResultData<T> {

    public int code;
    public String error;
    public T content;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
