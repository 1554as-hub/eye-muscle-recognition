package com.eyescloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eyescloud.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author balabala
 * @since 2021-10-18
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
   // User getUserInfoById(String username);
}
