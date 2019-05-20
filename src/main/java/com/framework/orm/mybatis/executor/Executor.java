package com.framework.orm.mybatis.executor;

public interface Executor {
    public  <T> T query(String statement, Object[] parmas, Class pojo);
}
