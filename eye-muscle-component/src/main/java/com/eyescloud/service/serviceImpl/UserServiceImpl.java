package com.eyescloud.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eyescloud.entity.User;
import com.eyescloud.mapper.UserMapper;
import com.eyescloud.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author balabala
 * @since 2021-10-18
 */
@Service("userServiceImpl")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByUserName(String userName) {

        LambdaQueryWrapper<User> queryWrapper = new QueryWrapper<User>().lambda();
        queryWrapper.eq(User::getUsername , userName);
        List<User> users = userMapper.selectList(queryWrapper);
        if (users == null || users.size() == 0){
            return null;
        }

        if(users.size() > 1){
            throw new RuntimeException("数据库定义错误！");
        }

        return  users.get(0);
    }
}
