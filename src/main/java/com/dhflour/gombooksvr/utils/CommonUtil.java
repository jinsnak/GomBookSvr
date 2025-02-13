package com.dhflour.gombooksvr.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Component
public class CommonUtil {
    /* 공통으로 자주 쓰이는 함수에 대해 정의 */

    /**
     * nvlStr
     * 주어진 Object의 값이 null 이거나 비어있을경우, 대체값을 적용해서 String으로 return한다.
     * input : obj - 비교를 원하는 값 , alterStr - 대체할 String
     * */
    public String nvlStr(Object obj , String alterStr){
        if(obj == null){
            return alterStr;
        }else{
            String compStr = String.valueOf(obj);
            if(compStr.trim().length() == 0){
                return alterStr;
            }
            return compStr;
        }
    }

    /**
     * Object isEmpty
     * Object의 type에 따라서 isEmpty수행
     * */
    public boolean isEmpty(Object obj){
        if(obj != null){
            if(obj instanceof List){
                return ((List<?>) obj).isEmpty();
            }else if(obj instanceof Map){
                return ((Map) obj).isEmpty();
            }else{
                if("".equals(nvlStr(obj , ""))){
                    return true;
                }else{
                    return false;
                }
            }
        }else{
            return true;
        }
    }

    /**
     * 오늘날자 가져오기
     * return (string) :: YYYYMMDD
     * */
    public String getToday() {
        String returnToday = "";
        Calendar cal = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        returnToday = sdf.format(new Date());

        return returnToday;
    }

    /**
     * 일자계산
     * stdDt (string) : 기준일자 (YYYYMMDD)
     * flag (string) : Y - year / M - month / D - date
     * value (int) : minus - [-x]  / plus - [x]
     * return (string) :: YYYYMMDD
     * */
    public String addDate(String stdDt , String flag , int value){
        String returnDt = "";

        GregorianCalendar gc = new GregorianCalendar();
        // set stdDt
        gc.set(Integer.parseInt(stdDt.substring(0,4)), Integer.parseInt(stdDt.substring(4,6)) -1 , Integer.parseInt(stdDt.substring(6,8)));

        switch(flag) {
            case "Y":
                gc.add(Calendar.YEAR , value);
                break;
            case "M":
                gc.add(Calendar.MONTH , value);
                break;
            case "D":
                gc.add(Calendar.DATE , value);
                break;
            default:
                break;
        }

        int resultYear = gc.get(Calendar.YEAR);
        int resultMonth = gc.get(Calendar.MONTH) +1;
        int resultDt = gc.get(Calendar.DATE);

        String outYy = "" + resultYear;
        String outMm = resultMonth < 10 ? "0"+resultMonth : "" + resultMonth;
        String outDd = resultDt < 10 ? "0" + resultDt : "" + resultDt;

        returnDt = outYy + outMm + outDd;

        return returnDt;
    }

    /**
     * Map관련
     * Map에서 값을 꺼낼때 특정 형태로 꺼낸다. - String
     * */
    public String getMapString(Map sourceMap , String key){
        if(sourceMap == null || sourceMap.isEmpty()){
            return null;
        }else {
            return nvlStr(sourceMap.get(key), "");
        }
    }

    /**
     * Map관련
     * Map에서 값을 꺼낼때 특정 형태로 꺼낸다. - int
     * */
    public int getMapInt(Map sourceMap , String key) throws Exception{
        if(sourceMap == null || sourceMap.isEmpty()){
            throw new Exception("비어있는 Map입니다.");
        }else {
            String getStr = nvlStr(sourceMap.get(key), "");
            try {
                return Integer.parseInt(getStr);
            } catch (Exception e) {
                throw new Exception(e.toString());
            }
        }
    }
    /**
     * Map관련
     * Map에서 값을 꺼낼때 특정 형태로 꺼낸다. - List
     * */
    public List getMapList(Map sourceMap , String key) throws Exception{
        if(sourceMap == null || sourceMap.isEmpty()){
            throw new Exception("비어있는 Map입니다.");
        }else {
            List returnList = new ArrayList();
            try {
                if (sourceMap.get(key) != null) {
                    return (List) sourceMap.get(key);
                } else {
                    return returnList;
                }
            } catch (Exception e) {
                throw new Exception(e.toString());
            }
        }
    }
    /**
     * Map관련
     * Map에서 값을 꺼낼때 특정 형태로 꺼낸다. - Map
     * */
    public Map getMapMap(Map sourceMap , String key) throws Exception{
        if(sourceMap == null || sourceMap.isEmpty()){
            throw new Exception("비어있는 Map입니다.");
        }else {
            Map returnMap = new HashMap();
            try {
                if (sourceMap.get(key) != null) {
                    return (Map) sourceMap.get(key);
                } else {
                    return returnMap;
                }
            } catch (Exception e) {
                throw new Exception(e.toString());
            }
        }
    }
    /**
     * Map관련
     * Map에서 값을 꺼낼때 특정 형태로 꺼낸다. - Map
     * */
    public Object getMapObject(Map sourceMap , String key) {
        if(sourceMap == null || sourceMap.isEmpty()){
            return null;
        }else {
            if (sourceMap.get(key) != null) {
                return sourceMap.get(key);
            } else {
                return null;
            }
        }
    }
}
