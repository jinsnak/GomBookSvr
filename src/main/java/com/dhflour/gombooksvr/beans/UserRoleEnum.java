package com.dhflour.gombooksvr.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum UserRoleEnum {
    //사용자의 역할을 Enum값으로 정의함.(프로젝트에 따라 달라질 수 있음.)
    ROLE_EMP("EMP" , "사원"),
    ROLE_ADMIN("ADM" , "관리자"),
    ROLE_NOAUTH("NON","인증없음");

    private final String code;
    private final String name;

    //생성자
    UserRoleEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    //Role Code를 이용하여 명칭 return
    public static final String findByCode(String code){
        UserRoleEnum[] ures = values();
        String returnStr = "";
        for(UserRoleEnum ure : ures ){
            if(code.equals(ure.getCode())){
                returnStr =  ure.getName();
            }
        }
        return returnStr;
    }

    public String getCode(){
        return code;
    }

    public String getName(){
        return name;
    }

    //모든 Role 코드 리스트를 반환
    public List<String> getAllCodes(){
        List<String> returnList = new ArrayList<String>();

        UserRoleEnum[] ure = UserRoleEnum.values();
//        System.out.println("UserRoleEnum Count: " + ure.length);
        for(UserRoleEnum ue : ure){
//            System.out.println("Role: " + ue.getCode());
            returnList.add( ue.getCode() );
        }

        return returnList;
        //return null;    // or throw new IllegalArgumentException("역할 코드 없음")
    }

    //모든 Role 명칭 리스트를 반환
    public List<String> getAllNames(){
        List<String> returnList = new ArrayList<String>();

        UserRoleEnum[] ure = UserRoleEnum.values();
        for(UserRoleEnum ue : ure){
            returnList.add( ue.getName() );
        }

        return returnList;
    }

    //모든 Role 코드와 명칭을 Map 형태로 반환
    public Map<String,Object> getAllEnumMaps(){
        Map<String,Object> returnMap = new HashMap<String,Object>();

        UserRoleEnum[] ure = UserRoleEnum.values();
        for(UserRoleEnum ue : ure){
            returnMap.put(ue.getCode() , ue.getName());
        }

        return returnMap;
    }
}
