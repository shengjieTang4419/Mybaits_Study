package com.framework.orm.mybatis.mapper;
import com.framework.orm.mybatis.bean.Blog;

public interface BlogMapper {
    /**
     * 根据主键查询文章
     * @param bid
     * @return
     */
    public Blog selectBlogById(Integer bid);


}
