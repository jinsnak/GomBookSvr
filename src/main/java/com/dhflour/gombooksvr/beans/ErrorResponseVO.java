package com.dhflour.gombooksvr.beans;

public class ErrorResponseVO {
    int code;
    String body;
    String time;

    public int getCode(){
        return code;
    }
    public String getBody(){
        return body;
    }
    public String getTime(){
        return time;
    }
    public void setCode(int code){
        this.code= code;
    }
    public void setBody(String message){
        this.body= body;
    }
    public void setTime(String time){
        this.time= time;
    }
    public ErrorResponseVO(int code , String body , String time) {
        this.code = code;
        this.body = body;
        this.time = time;
    }
}
