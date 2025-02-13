package com.dhflour.gombooksvr.beans;

public class UserInfo {
    private String userId;
    private String password;
    String desc;
    String validDt;
    String userType;
    String token;
    String userName;
    String deptCd;
    String deptName;
    String isTmpPassword;
    String loginStayYn;

    public void setUserId(String userId){
        this.userId= userId;
    }
    public String getUserId(){
        return userId;
    }

    public void setPassword(String password){
        this.password= password;
    }
    public String getPassword(){
        return password;
    }

    public void setDesc(String desc){
        this.desc= desc;
    }
    public String getDesc(){
        return desc;
    }

    public void setToken(String token){
        this.token= token;
    }
    public String getToken(){
        return token;
    }

    public void setValidDt(String validDt) {
        this.validDt= validDt;
    }
    public String getValidDt() {
        return validDt;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
    public String getUserType() {
        return userType;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }

    public void setDeptCd(String deptCd) { this.deptCd = deptCd; }
    public String getDeptCd() { return deptCd; }

    public void setDeptName(String deptName) { this.deptName = deptName; }
    public String getDeptName() { return deptName; }

    public void setIsTmpPassword(String isTmpPassword) { this.isTmpPassword = isTmpPassword; }
    public String getIsTmpPassword() { return isTmpPassword; }

    public void setLoginStayYn(String loginStayYn) { this.loginStayYn = loginStayYn; }
    public String getLoginStayYn() { return loginStayYn; }
}
