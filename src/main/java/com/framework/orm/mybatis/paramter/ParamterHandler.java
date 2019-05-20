package com.framework.orm.mybatis.paramter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ParamterHandler {

    PreparedStatement psmt;

    public ParamterHandler(PreparedStatement psmt){
        this.psmt =  psmt;
    }

    /**
     * 从方法中获取参数 并遍历SQL设置的？占位符
     * @param paramters
     */
    public void setParamters(Object[] paramters){

        try {
            for (int i = 0; i < paramters.length; i++) {
            // PreparedStatement的参数从1开始
            int k  = i+1;
            if(paramters[i] instanceof  Integer){
                psmt.setInt(k,(Integer) paramters[i]);
            }else if(paramters[i] instanceof Long){
                psmt.setLong(k,(Long) paramters[i]);
            }else if(paramters[i] instanceof String){
                psmt.setString(k,(String) paramters[i]);
            }else if(paramters[i] instanceof Boolean){
                psmt.setBoolean(k,(Boolean) paramters[i]);
            }else{
                psmt.setString(k,String.valueOf(paramters[i]));
            }
        }} catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
