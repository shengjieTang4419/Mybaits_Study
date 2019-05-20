package com.framework.orm.mybatis.core;

import com.framework.orm.mybatis.executor.CachingExecutor;
import com.framework.orm.mybatis.executor.Executor;
import com.framework.orm.mybatis.executor.SimpleExecutor;
import com.framework.orm.mybatis.proxy.MapperRegistry;
import com.framework.orm.mybatis.session.DefaultSqlSession;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 配置类
 */
public class Configuration {
    
    public final static ResourceBundle sqlMappings ;
    public static final ResourceBundle properties; // 全局配置
    public static final Map<String, String> mappedStatements = new HashMap<>(); // 维护接口方法与SQL关系
    public static final MapperRegistry MAPPER_REGISTRY = new MapperRegistry(); // 维护接口与工厂类关系

    static {
        sqlMappings = ResourceBundle.getBundle("sql");
        properties = ResourceBundle.getBundle("mybatis");
    }

    /**
     * 返回接口的代理对象
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getMapper(Class clazz, DefaultSqlSession sqlSession) {
        return (T) MAPPER_REGISTRY.getMapper(clazz, sqlSession);
    }


    /**
     * 初始化时解析全局配置文件
     */
    public Configuration()  {
        // 1.解析sql.properties
        for(String key : sqlMappings.keySet()){
            String statement = null;
            Class mapper = null;
            String pojoStr = null;
            Class pojo = null;
            // properties中的value用--隔开，第一个是SQL语句
            statement = sqlMappings.getString(key).split("--")[0];
            // properties中的value用--隔开，第二个是需要转换的POJO类型
            pojoStr = sqlMappings.getString(key).split("--")[1];
            try {
                mapper = Class.forName(key.substring(0,key.lastIndexOf(".")));
                pojo = Class.forName(pojoStr);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            MAPPER_REGISTRY.addMapper(mapper, pojo); // 接口与返回的实体类关系
            mappedStatements.put(key, statement); // 接口方法与SQL关系
        }
    }

    /**
     * 调用执行器 CachingExecutor与SimpleExecutor
     * 其中CachingExecutor装饰了SimpleExecutor
     * @return
     */
    public Executor newExecutor() {
        Executor executor = null;
        if (properties.getString("cache.enabled").equals("true")) {
            executor = new CachingExecutor(new SimpleExecutor());
        }else{
            executor = new SimpleExecutor();
        }
        return executor;
    }

    public String getMappedStatement(String id) {
        return mappedStatements.get(id);
    }

    /**
     * 根据statement判断是否存在映射的SQL
     * @param statementName
     * @return
     */
    public boolean hasStatement(String statementName) {
        return mappedStatements.containsKey(statementName);
    }
}
