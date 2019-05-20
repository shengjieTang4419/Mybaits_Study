import com.framework.orm.mybatis.bean.Blog;
import com.framework.orm.mybatis.core.Configuration;
import com.framework.orm.mybatis.executor.Executor;
import com.framework.orm.mybatis.mapper.BlogMapper;
import com.framework.orm.mybatis.session.DefaultSqlSession;
import com.framework.orm.mybatis.session.SqlSessionFactory;

public class MybaitsTest {
    public static void main(String[] args) {
        SqlSessionFactory factory = new SqlSessionFactory();
        DefaultSqlSession sqlSession = factory.build().openSqlSession();
        // 获取MapperProxy代理
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        Blog blog = mapper.selectBlogById(1);
        System.out.println("第一次查询: " + blog);
        System.out.println();
        blog = mapper.selectBlogById(1);
        System.out.println("第二次查询: " + blog);

    }
}
