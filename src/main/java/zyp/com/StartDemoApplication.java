package zyp.com;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class StartDemoApplication {

    private static final String resource = "mybatis-config.xml";

    public static void main(String[] args) {
        // 第一阶段初始化
        InputStream  input = null;
        try {
            input = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 得到SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(input);

        // 第二阶段，读写数据

        try(SqlSession sqlSession = sqlSessionFactory.openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user = new User();
            user.setSchoolName("YY");

            List<User> users = userMapper.queryUserBySchoolName(user);
            for (User u : users) {
                System.out.println(u);
            }

        }

    }

}
