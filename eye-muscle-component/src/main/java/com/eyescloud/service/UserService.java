package com.eyescloud.service;

import com.eyescloud.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author balabala
 * @since 2021-10-18
 */
public interface UserService extends IService<User> {
    public User getUserByUserName(String userName);
}
