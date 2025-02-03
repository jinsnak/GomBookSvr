package com.dhflour.gombooksvr.beans;

public class ResultVO {
    private int code;
    private Object body;

    public void setCode(int code) {
        this.code = code;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public int getCode() {
        return code;
    }

    public Object getBody() {
        return body;
    }
}
