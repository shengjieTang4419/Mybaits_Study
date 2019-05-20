package com.framework.orm.mybatis.executor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 结果集处理
 */
public class ResultSethandler {

    public <T> T handle(ResultSet resultSet,Class type){
        //调用class产生实例
        Object pojo = null;
        try {
            pojo  = type.newInstance();

            if (resultSet.next()) {
                for(Field field : pojo.getClass().getDeclaredFields()){
                    setValue(pojo,field,resultSet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (T)pojo;
    }

    private void setValue(Object pojo, Field field, ResultSet resultSet) {
        try {
            Method setMethod = pojo.getClass().getMethod("set"+firstWordUpper(field.getName()),field.getType());
            setMethod.invoke(pojo,getResult(resultSet,field));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据反射类型 从resultSet中获取对应类型的参数
     * @param resultSet
     * @param field
     * @return
     */
    private Object getResult(ResultSet resultSet, Field field) {
        Class type = field.getType();
        String dataName  = humpTounderLine(field.getName());
        try {
                if(Integer.class==type){
                    return resultSet.getInt(dataName);
                }else if(String.class == type){
                    return  resultSet.getString(dataName);
                }else if (Long.class == type){
                    return resultSet.getLong(dataName);
                }else if(Boolean.class == type){
                    return  resultSet.getBoolean(dataName);
                }else if (Double.class == type){
                    return resultSet.getDouble(dataName);
                }else{
                    return  resultSet.getString(dataName);
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 数据库下划线 转大写
     * @param param
     * @return
     */
    private String humpTounderLine(String param) {
        StringBuilder sb = new StringBuilder(param);
        int temp = 0;
        if(!param.contains("_")){
            for (int i = 0; i <param.length() ; i++) {
                if(Character.isUpperCase(param.charAt(i))){
                    sb.insert(i+temp,"_");
                    temp+=1;
                }
            }
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 单词首字母大写
     * @param word
     * @return
     */
    private String firstWordUpper(String word) {
        String first = word.substring(0,1);
        String tail =  word.substring(1);
        return  first.toUpperCase()+tail;
    }


}
