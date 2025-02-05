package zyp.com;

import org.apache.ibatis.annotations.Mapper;
import zyp.com.User;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> queryUserBySchoolName(User user);

}
