package com.framework.orm.mybatis.executor;

public class SimpleExecutor implements Executor {

    @Override
    public <T> T query(String statement, Object[] parmas, Class pojo){
        StatementHandler statementHandler = new StatementHandler();
        return statementHandler.query(statement, parmas, pojo);
    }
}
