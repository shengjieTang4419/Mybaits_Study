package com.framework.orm.mybatis.executor;

import com.framework.orm.mybatis.core.Configuration;
import com.framework.orm.mybatis.paramter.ParamterHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StatementHandler {
    private ResultSethandler resultSethandler = new ResultSethandler();

    public  <T> T query(String statement, Object[] parmas, Class pojo){
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        Object result = null;

        conn = getConnection();
        try {
            preparedStatement = conn.prepareStatement(statement);
            ParamterHandler parameterHandler = new ParamterHandler(preparedStatement);
            parameterHandler.setParamters(parmas);
            preparedStatement.execute();
            try {
                result = resultSethandler.handle(preparedStatement.getResultSet(), pojo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return (T)result;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                conn = null;
            }
        }
        return  null;
    }

    /**
     * 获取连接
     * @return
     * @throws SQLException
     */
    private Connection getConnection() {
        String driver = Configuration.properties.getString("jdbc.driver");
        String url =  Configuration.properties.getString("jdbc.url");
        String username = Configuration.properties.getString("jdbc.username");
        String password = Configuration.properties.getString("jdbc.password");
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
